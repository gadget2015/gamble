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
    getTransaction(id_param : string) {
        const id = parseInt(id_param, 10);
        console.log('Search for transaction with id = ' + id);
        const sql = 'select * from stryktipsbolag.transaktion where id = ' + id + ';';

        let sqlpromise = this.createSQLPromise(sql);

        return sqlpromise;
    }

    /**
    * Hämtar alla transaktion för ett givet kontonummer.
    */
    getTransactions(kontonr_param : string) {
        const id = parseInt(kontonr_param, 10);
        console.log('Search for transactions with kontonr = ' + id);
        const sql = `SELECT stryktipsbolag.transaktion.ID, stryktipsbolag.transaktion.beskrivning, stryktipsbolag.transaktion.debet,
                            stryktipsbolag.transaktion.kredit, stryktipsbolag.transaktion.tid,
                    		stryktipsbolag.konto_transaktion.konto_id, stryktipsbolag.konto.kontonr
                    	FROM stryktipsbolag.transaktion
                        INNER JOIN stryktipsbolag.konto_transaktion ON stryktipsbolag.konto_transaktion.transaktioner_id = stryktipsbolag.transaktion.ID
                    	INNER JOIN stryktipsbolag.konto ON stryktipsbolag.konto_transaktion.konto_id = stryktipsbolag.konto.ID
                        WHERE stryktipsbolag.konto.kontonr = ` + id +
                        ' ORDER BY  stryktipsbolag.transaktion.TID ASC;';

        let sqlpromise = this.createSQLPromise(sql);

        return sqlpromise;
    }

    /**
    * Hämtar en spelare med givet userid.
    */
    getSpelare(userid_params : string) {
        const userid = userid_params;
        console.log('Hämtar en Spelare med userid = ' + userid);
        const sql = 'select * from stryktipsbolag.spelare where userid = "' + userid + '";';

        let sqlpromise = this.createSQLPromise(sql);

        return sqlpromise;
    }

    /**
    * Hämtar ett Spelbolag med namn.
    */
    getSpelbolag(namn_params : string) {
        const namn = (namn_params == null) ? '%': namn_params;
        console.log('Hämtar ett Spelbolag med namn = ' + namn);
        const sql = 'select * from stryktipsbolag.spelbolag where namn LIKE "' + namn + '";';

        let sqlpromise = this.createSQLPromise(sql);

        return sqlpromise;
    }

    /**
    * Hämtar ett konto för givet kontonummer.
    */
    getKonto(kontonummer : string) {
        const sql = 'select * from stryktipsbolag.konto where kontonr = ' + kontonummer + ';';
        let sqlpromise = this.createSQLPromise(sql);

        return sqlpromise;
    }

    /**
    * Hämtar ett konto för givet ID.
    */
    getKontoByID(id : string) {
        const sql = 'select * from stryktipsbolag.konto where ID = ' + id + ';';
        let sqlpromise = this.createSQLPromise(sql);

        return sqlpromise;
    }


    /**
    * Hämtar alla spelbolag.
    */
    getAllaSpelbolag() {
        const sql = 'select * from stryktipsbolag.spelbolag;';
        let sqlpromise = this.createSQLPromise(sql);

        return sqlpromise;
    }

    /**
    * Lägg in en transaktionpost på givet kontonummer.
    */
    async addTransaktion(beskrivning, kredit_params, debit_params, kontonummer_params, tidpunkt = this.convertToMySqlDate(new Date())) {
        const debet = (debit_params == null) ? 0: debit_params;
        const kredit = (kredit_params == null) ? 0: kredit_params;
        const kontonummer = kontonummer_params;
        console.log('Skapar en transaktion med {beskrivning:' + beskrivning + ', debet:' + debet + ', kredit:' + kredit +', kontonummer:' + kontonummer + ', tid:' + tidpunkt +'}.');

        try {
            // Calculate the next sequnece ID used in the sql insert into statement for transaktion.
            const sqlLastID = 'SELECT ID FROM stryktipsbolag.transaktion ORDER BY ID DESC LIMIT 1;';
            const transaktionIDPromise = this.createSQLPromise(sqlLastID);
            let result = await transaktionIDPromise;
            const id = parseInt(result['queryResult'][0].ID, 10);
            const nextTransaktionId = id + 1;

            // Lägger in en ny transaktion i databasen. Timestamp har formatet YYYY-MM-DD hh:mm:ss.fraction
            const sqlInsertTransaktion = 'INSERT INTO stryktipsbolag.transaktion (ID, BESKRIVNING, DEBET,KREDIT, TID) ' +
                        'VALUES (' + nextTransaktionId + ', \'' + beskrivning + '\',' + debet + ', ' + kredit + ', \'' + tidpunkt +'\');';
            const insertTransaktionPromise = this.createSQLPromise(sqlInsertTransaktion);
            await insertTransaktionPromise;

            // Lägger in relation mellan konto och transaktion.
            const konto = await this.getKonto(kontonummer);
            const konto_id = konto['queryResult'][0]['ID'];
            const sqlInsertRelation = 'INSERT INTO stryktipsbolag.konto_transaktion (konto_id, transaktioner_id) ' +
                        'VALUES (' + konto_id + ', ' + nextTransaktionId +');';
            const insertRelationPromise = this.createSQLPromise(sqlInsertRelation);
            await insertRelationPromise;

            return {queryResult:{affectedRows:1}};
        } catch(e) {
            console.log('Major error while inserting transaction.' + JSON.stringify(e));
            throw e;
        }
    }

    /**
    * Hämtar alla spelare som är med i givet spelbolag id.
    */
    async getAllaSpelareForSpelbolag(id_param : string) {
        console.log('Hämtar alla spelare för givet spelbolag ID(' + id_param + ').');

        const sqlQuery = `SELECT stryktipsbolag.spelare.ID, stryktipsbolag.spelare.userid, stryktipsbolag.spelare.administratorforspelbolag_id, stryktipsbolag.spelare.konto_id
                                              	FROM stryktipsbolag.spelare
                                                  INNER JOIN stryktipsbolag.spelbolag_spelare ON stryktipsbolag.spelbolag_spelare.spelare_id = stryktipsbolag.spelare.ID
                                                  WHERE stryktipsbolag.spelbolag_spelare.spelbolag_id = ` +
                                                  id_param +';';
        const queryPromise = this.createSQLPromise(sqlQuery);

        return queryPromise;
    }

    /**
    * Beräknar saldo för givet kontonummer.
    */
    async getSaldo(kontonummer) {
        const transaktioner = await this.getTransactions(kontonummer);
        let saldo = 0;

        for (var i = 0; i < transaktioner['queryResult'].length; i++) {
            const kredit = transaktioner['queryResult'][i].kredit;
            const debet = transaktioner['queryResult'][i].debet;
            saldo += debet - kredit;
        }

        return saldo;
    }

    /**
    * Ta betalt av alla spelare som ingår i givet spelbolag.
    */
    async taBetaltForEnOmgang(spelbolagnamn, tidpunkt) {
        // Hämtar hur mycket som ska ta betalts av varje spelare.
        const spelbolag = await this.getSpelbolag(spelbolagnamn);
        const insatsperomgang = spelbolag['queryResult'][0]['insatsperomgang'];

        // Hämtar spelbolaget kontonummer
        const spelbolagsKonto = await this.getKontoByID(spelbolag['queryResult'][0]['konto_id']);
        const spelbolagKontonummer = spelbolagsKonto['queryResult'][0].kontonr;

        // Hämtar alla spelare för givet spelbolag.
        const spelbolag_id = spelbolag['queryResult'][0]['ID'];
        const spelare = await this.getAllaSpelareForSpelbolag(spelbolag_id);

        // Lägger till en kredit (-) för varje spelare och debet (+) för spelbolaget.
        for (var i = 0; i < spelare['queryResult'].length; i++) {
             const text = 'Tar betalt av '+ spelare['queryResult'][i].userid + ' för spelbolaget ' + spelbolagnamn;
             const kredit = insatsperomgang;
             const debet = 0;
             const konto_id = spelare['queryResult'] [i].konto_id;
             const konto = await this.getKontoByID(konto_id);
             const kontonummer = konto['queryResult'][0].kontonr;

            try {
             await this.addTransaktion(text, kredit, debet, kontonummer);
             await this.addTransaktion('Får betalt av spelaren ' + spelare['queryResult'][i].userid, 0, insatsperomgang, spelbolagKontonummer);
            } catch(e){
                console.log('Major error: Kan inte ta betalt av spelare. Trace=' + JSON.stringify(e) );
                throw new Error(e);
            }
          }
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
     createSQLPromise(sql) {
        let sqlpromise = new Promise((resolve, reject) => {
             const con = this.connectToDb();
             con.query(sql, function (err, result) {
                 if (err) {
                     console.log('Connection.Error: ' + err);
                     console.log('Connection.Error.SQL query: ' + sql);
                     con.end();
                     reject('SQLerror' + JSON.stringify(err));
                 } else {
                    con.end();

                    resolve({queryResult: result});
                 }
             });
         });

         return sqlpromise;
     }

     /**
     * Konverterar JavaScript datum till MySQL datumformat, dvs. ISO 8601 datum (2020-11-20T08:30:05.823Z) till
     * mysql datum (2020-11-20 08:30.05.823)
     */
     private convertToMySqlDate(jsDate: Date) {
        return jsDate.toLocaleString( 'sv' ) + '.'+ jsDate.getMilliseconds();
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