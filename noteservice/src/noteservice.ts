import {Request, Response} from 'express';
import * as db from 'mysql';

/**
 * The Note service, that handles CRUD operations.
 *
 */
export class Noteservice {
    constructor() {

    }

    async getNote(req: Request, res: Response) {
        const id = parseInt(req.params.id, 10);
        console.log('Search for  note id = ' + id);
        const con = this.connectToDb();
        const sql = 'select * from noterepo.note;';

        console.log('Call Database..');

        let promise = new Promise((resolve, reject) => {
            con.query(sql, function (err, result) {
                if (err) {
                    console.log('Error: ' + err);
                    throw err;
                }
                console.log("SQL result:" + result);
                res.status(200).send({
                    success: 'true',
                    message: 'todos retrieved successfully',
                    todos: {'id': id}
                });

                resolve(result);
            });
        });

        let result = await promise;

        return result;
    }

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