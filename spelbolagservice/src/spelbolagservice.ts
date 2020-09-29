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

    getTransaction(req: Request, res: Response) {
        const id = parseInt(req.params.id, 10);
        console.log('Search for transaction with id = ' + id);
        const con = this.connectToDb();
        const sql = 'select * from stryktipsbolag.transaktion where id = ' + id + ';';

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
                        WHERE stryktipsbolag.konto.kontonr = ` + id + ';';

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

    async createNote(req: Request, res: Response) {
        const text = req.body['TEXT'];
        console.log('Create a new note with TEXT = ' + text);

        const con = this.connectToDb();

        // Calculate the next sequnece ID used in the sql insert into statement.
        const sqlLastID = 'SELECT ID FROM noterepo.note ORDER BY ID DESC LIMIT 1;';

        let sqlpromise = new Promise((resolve, reject) => {
            con.query(sqlLastID, function (err, result) {
                if (err) {
                    console.log('Error: ' + err);
                    throw err;
                }

                resolve(result);
            });
        });

        let result = await sqlpromise;
        const id = parseInt(result[0]["ID"], 10);
        const nextId = id + 1;

        // Insert new Note in database.
        const sqlInsert = 'insert into noterepo.note (ID, ADMINUSERID, PRIVATEACCESS, TEXT, LASTSAVED) VALUES (' + nextId + ',\'\', 0, \'' + text + '\', CURRENT_TIMESTAMP);';

        sqlpromise = new Promise((resolve, reject) => {
            con.query(sqlInsert, function (err, result) {
                if (err) {
                    console.log('Error: ' + err);
                    throw err;
                }

                resolve(result);
            });

            con.end();
        });

        result = await sqlpromise;

        res.status(200).send({
            success: 'true',
            message: 'Note saved successfully with Id = ' + nextId + '.',
			noteid: nextId,
			databaseinformation: JSON.stringify(result)
        });

        return nextId;
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

     async searchNote(req: Request, res: Response) {
            const queryString = '%' + req.params.text + '%';
            console.log('Search for note with text = ' + queryString);
            const con = this.connectToDb();
            const sql = 'select * from noterepo.note where text LIKE ?';

            let sqlpromise = new Promise((resolve, reject) => {
                con.query(sql, [queryString], function (err, result) {

                    if (err) {
                        console.log('Error: ' + err);
                        throw err;
                    }

                    res.status(200).send({
                        success: 'true',
                        message: 'Notes retrieved successfully',
                        note: result
                    });

                    resolve({queryResult: result});
                });

                con.end();
            });

            let result = await sqlpromise;

            return result; // Just for the unittests.
        }

/**
*   Params are case sensitive.
*/
        updateNote(req: Request, res: Response) {
            const text = req.body['text'];
            const id = parseInt(req.body['id'], 10);
            console.log('Update note with id = ' + id + ', and text = ' + text);
            const con = this.connectToDb();
            const sql = 'update noterepo.note set TEXT = \'' + text + '\', LASTSAVED = CURRENT_TIMESTAMP where id = ' + id + ';';

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