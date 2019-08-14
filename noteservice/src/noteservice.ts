import {Request, Response} from 'express';
import * as db from 'mysql';
import {next} from "jest-express/lib/next";

/**
 * The Note service, that handles CRUD operations.
 *
 */
export class Noteservice {
    constructor() {

    }

    async getNote(req: Request, res: Response) {
        const id = parseInt(req.params.id, 10);
        console.log('Search for note with id = ' + id);
        const con = this.connectToDb();
        const sql = 'select * from noterepo.note where id = ' + id + ';';

        let sqlpromise = new Promise((resolve, reject) => {
            con.query(sql, function (err, result) {
                if (err) {
                    console.log('Error: ' + err);
                    throw err;
                }

                res.status(200).send({
                    success: 'true',
                    message: 'Notes retrieved successfully',
                    note: result
                });

                resolve(result);
            });
        });

        let result = await sqlpromise;

        return result;
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
     * Create a database connection what can be used to query data/write data.
     */
    connectToDb(): db.Connection {
        let con = db.createConnection({
            host: "localhost",
            user: "root",
            password: ""
        });

        con.connect();

        return con;
    }
}