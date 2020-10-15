import {Request, Response} from 'express';
import * as db from 'mysql';
import myprops = require('properties-reader');

/**
 * The Spelbolag service, that handles CRUD operations.
 *
 */
export class Spelbolagservice {
    constructor() {
    }

    /**
    * Hämtar en transaktion för ett givet transaktionsid.
    */
    getTransaction(req: Request, res: Response) {
        const id = parseInt(req.params.id, 10);
        console.log('Search for transaction with id = ' + id);
        const con = this.connectToDb();
        const sql = 'select * from stryktipsbolag.transaktion where id = ' + id + ';';

        let sqlpromise = this.createSQLPromise(sql, con);

        return sqlpromise;
    }

    /**
    * Hämtar alla transaktion för ett givet kontonummer.
    */
    getTransactions(req: Request, res: Response) {
        const id = parseInt(req.params.kontonr, 10);
        console.log('Search for transaction with kontonr = ' + id);
        const con = this.connectToDb();
        const sql = `SELECT stryktipsbolag.transaktion.ID, stryktipsbolag.transaktion.beskrivning, stryktipsbolag.transaktion.debit,
                            stryktipsbolag.transaktion.kredit, stryktipsbolag.transaktion.tid,
                    		stryktipsbolag.konto_transaktion.konto_id, stryktipsbolag.konto.kontonr
                    	FROM stryktipsbolag.transaktion
                        INNER JOIN stryktipsbolag.konto_transaktion ON stryktipsbolag.konto_transaktion.transaktioner_id = stryktipsbolag.transaktion.ID
                    	INNER JOIN stryktipsbolag.konto ON stryktipsbolag.konto_transaktion.konto_id = stryktipsbolag.konto.ID
                        WHERE stryktipsbolag.konto.kontonr = ` + id +
                        ' ORDER BY  stryktipsbolag.transaktion.TID ASC;';

        let sqlpromise = this.createSQLPromise(sql, con);

        return sqlpromise;
    }

    /**
    * Hämtar en spelare med givet userid.
    */
    getSpelare(req: Request, res: Response) {
        const userid = req.params.userid;
        console.log('Hämtar en Spelare med userid = ' + userid);
        const con = this.connectToDb();
        const sql = 'select * from stryktipsbolag.spelare where userid = "' + userid + '";';

        let sqlpromise = this.createSQLPromise(sql, con);

        return sqlpromise;
    }

    /**
    * Hämtar ett Spelbolag med namn.
    */
    getSpelbolag(req: Request, res: Response) {
        const namn = (req.params.namn == null) ? '%': req.params.namn;
        console.log('Hämtar ett Spelbolag med namn = ' + namn);
        const con = this.connectToDb();
        const sql = 'select * from stryktipsbolag.spelbolag where namn LIKE "' + namn + '";';

        let sqlpromise = this.createSQLPromise(sql, con);

        return sqlpromise;
    }

    /**
    * Hämtar ett konto för givet kontonummer.
    */
    getKonto(kontonummer) {
        const con = this.connectToDb();
        const sql = 'select * from stryktipsbolag.konto where kontonr = ' + kontonummer + ';';
        let sqlpromise = this.createSQLPromise(sql, con);

        return sqlpromise;
    }

    /**
    * Lägg in en transaktionpost på givet kontonummer.
    */
    async addTransaktion(req: Request, res: Response) {
        const beskrivning = req.body['beskrivning'];
        const kredit = (req.body['kredit'] == null) ? 0: req.body['kredit'];
        const debit = (req.body['dedit'] == null) ? 0: req.body['debit'];
        const kontonummer = req.body['kontonummer'];
        console.log('Skapar en transaktion med {beskrivning:' + beskrivning + ', debit:' + debit + ', kredit:' + kredit +', kontonummer:' + kontonummer + '}.');

        let con = this.connectToDb();

        // Calculate the next sequnece ID used in the sql insert into statement for transaktion.
        const sqlLastID = 'SELECT ID FROM stryktipsbolag.transaktion ORDER BY ID DESC LIMIT 1;';
        let transaktionIDPromise = this.createSQLPromise(sqlLastID, con);
        let result = await transaktionIDPromise;
        const id = parseInt(result['queryResult'][0].ID, 10);
        const nextTransaktionId = id + 1;


        // Lägger in en ny transaktion i databasen.
        con = this.connectToDb();   // need new connection to db, the previous is stalled.
        const sqlInsertTransaktion = 'INSERT INTO stryktipsbolag.transaktion (ID, BESKRIVNING, DEBIT,KREDIT, TID) ' +
                    'VALUES (' + nextTransaktionId + ', \'' + beskrivning + '\',' + debit + ', ' + kredit + ',CURRENT_TIMESTAMP);';
        let insertTransaktionPromise = this.createSQLPromise(sqlInsertTransaktion, con);

        // Lägger in relation mellan konto och transaktion.
        const konto = await this.getKonto(kontonummer);
        const konto_id = konto['queryResult'][0]['ID'];
        let conRelation = this.connectToDb();   // need new connection to db, the previous is stalled.
        const sqlInsertRelation = 'INSERT INTO stryktipsbolag.konto_transaktion (konto_id, transaktioner_id) ' +
                    'VALUES (' + konto_id + ', ' + nextTransaktionId +');';
        let insertRelationPromise = this.createSQLPromise(sqlInsertRelation, conRelation);

        // Skapar ett promise för båda insert statements.
        const insertPromise = new Promise(async (resolve, reject) => {
           await insertTransaktionPromise;
           const result = await insertRelationPromise;
           resolve(result);
        });

        return insertPromise;
    }

    /**
     * Create a database connection that can be used to query data/write data.
     */
    connectToDb(): db.Connection {
        let properties = myprops('database.properties');
        let dbHost = properties.get('database.host');
        let dbUser  = properties.get('database.user');
        let dbPassword = properties.get('database.password');

        let con = db.createConnection({
            host: dbHost,
            user: dbUser,
            password: dbPassword
        });

        con.connect();

        return con;
    }

    /**
     * Skapar ett Promise som exekverar ett SQL statement.
     */
     createSQLPromise(sql, con) {
        let sqlpromise = new Promise((resolve, reject) => {
             con.query(sql, function (err, result) {
                 if (err) {
                     console.log('Error: ' + err);
                     reject('SQLerror');
                 }

                 resolve({queryResult: result});
             });

             con.end();
         });

         return sqlpromise;
     }
}

/**
* Skriver ut resultset.
*/
function printResult(result) {
    for (var i = 0; i < result['queryResult'].length; i++) {
        console.log('Rad #' + i + ' = ' + result['queryResult'] [i].ID  + ' | ' + result['queryResult'][i].beskrivning
        + ' | ' + result['queryResult'][i].debit
        + ' | ' + result['queryResult'][i].kredit
        + ' | ' + result['queryResult'][i].tid
        + ' | ' + result['queryResult'][i].kontonr);
     }
}