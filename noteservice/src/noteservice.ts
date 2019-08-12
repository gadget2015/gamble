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
        console.log('Search for note with id = ' + id);
        const con = this.connectToDb();
        const sql = 'select * from noterepo.note where id = ' + id +';';

        let sqlpromise = new Promise((resolve, reject) => {
            con.query(sql, function (err, result) {
                if (err) {
                    console.log('Error: ' + err);
                    throw err;
                }

                res.status(200).send({
                    success: 'true',
                    message: 'todos retrieved successfully',
                    note: result
                });

                resolve(result);
            });
        });

        let result = await sqlpromise;

        return result;
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