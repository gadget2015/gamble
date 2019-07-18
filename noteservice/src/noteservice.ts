import {Request, Response} from 'express';
import * as db from 'mysql';

/**
 * The Note service, that handles CRUD operations.
 */
export class Noteservice {
    constructor() {

    }

    getNote(req: Request, res: Response) {
        const id = parseInt(req.params.id, 10);
        const con = this.connectToDb();
        const sql = 'select * from noterepo.note;';

        con.query(sql, function (err, result) {
            if (err) throw err;
            console.log("Result: " + result);
        });

        res.status(200).send({
            success: 'true',
            message: 'todos retrieved successfully',
            todos: {'id': id}
        });
    }

    connectToDb() {
        var con = db.createConnection({
            host: "localhost",
            user: "root",
            password: ""
        });

        con.connect(function (err) {
            if (err) {
                throw err;
            } else {
                console.log("Connected to MySQL database.");
            }
        });

        return con;
    }
}