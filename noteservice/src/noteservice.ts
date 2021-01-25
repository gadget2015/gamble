import {Request, Response} from 'express';
import * as db from 'mysql';
import myprops = require('properties-reader');
import {Logger} from 'winston';

/**
 * The Note service, that handles CRUD operations.
 *
 */
export class Noteservice {
    logger: Logger;

    constructor(logger : Logger) {
        this.logger = logger;
    }

    getNote(req: Request, res: Response) {
        const id = parseInt(req.params.id, 10);
        this.logger.debug('Search for note with id = ' + id);
        const con = this.connectToDb();
        const sql = 'select * from noterepo.note where id = ' + id + ';';

        let sqlpromise = new Promise((resolve, reject) => {
            con.query(sql, this.createReadCallbackFunction(resolve, reject, this.logger));
            con.end();
        });

        return sqlpromise;
    }

    async createNote(req: Request, res: Response) {
        const text = req.body['TEXT'];
        this.logger.debug('Create a new note with TEXT = ' + text);

        const con = this.connectToDb();

        // Calculate the next sequnece ID used in the sql insert into statement.
        const sqlLastID = 'SELECT ID FROM noterepo.note ORDER BY ID DESC LIMIT 1;';

        let sqlpromise = new Promise((resolve, reject) => {
            con.query(sqlLastID, this.createReadCallbackFunction(resolve, reject, this.logger));
        });

        let result = await sqlpromise;
        const id = parseInt(result['queryResult'][0]["ID"], 10);
        const nextId = id + 1;

        // Insert new Note in database.
        const sqlInsert = 'insert into noterepo.note (ID, ADMINUSERID, PRIVATEACCESS, TEXT, LASTSAVED) VALUES (' + nextId + ',\'\', 0, \'' + text + '\', CURRENT_TIMESTAMP);';

        sqlpromise = new Promise((resolve, reject) => {
            con.query(sqlInsert, this.createInsertCallbackFunction(resolve, reject, this.logger));
            con.end();
        });

        const returnPromise = new Promise(async (reslove, reject) => {
            const result = await sqlpromise;
            const affectedRows = result['affectedRows'];

            if (affectedRows === 1 ){
                reslove(nextId);
            } else {
                reject('Error while inserting Note.');
            }
        });

        return returnPromise;
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
            this.logger.debug('Search for note with text = ' + queryString);
            const con = this.connectToDb();
            const sql = 'select * from noterepo.note where text LIKE ?';

            let sqlpromise = new Promise((resolve, reject) => {
                con.query(sql, [queryString], function (err, result) {

                    if (err) {
                        this.logger.error('Error while searching for a Note: ' + err);
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
            this.logger.debug('Update note with id = ' + id + ', and text = ' + text);
            const con = this.connectToDb();
            const sql = 'update noterepo.note set TEXT = ' + con.escape(text) + ', LASTSAVED = CURRENT_TIMESTAMP where id = ' + id + ';';

            let sqlpromise = new Promise((resolve, reject) => {
                con.query(sql, this.createReadCallbackFunction(resolve, reject, this.logger));
                con.end();
            });

            return sqlpromise;
        }

        createReadCallbackFunction(resolve, reject, logger) {
            return function(err, result) {
                if (err) {
                    logger.error('Error while updating a Note: ' + err);
                    reject('SQLerror');
                }

                resolve({queryResult: result});
            }
        }

        createInsertCallbackFunction(resolve, reject, logger) {
            return function (err, result) {
                if (err) {
                    this.logger.error('Error while finding last ID: ' + err);
                    reject(err);
                }

                resolve(result);
            }
        }
}