import express from 'express';
import {Spelbolagservice} from "./spelbolagservice";
import bodyParser from 'body-parser';
import cors from 'cors';

const app = express();
const port = 4001;

// https://medium.com/@purposenigeria/build-a-restful-api-with-node-js-and-express-js-d7e59c7a3dfb
app.use(bodyParser.json());
app.use(cors());
app.use(function(req, res, next) {
	console.log('Server called with URL:' + req.url);
	next();
});


app.get('/api/v1/transactions/:id', (req, res) => {
    const spelbolagservice = new Spelbolagservice();

    spelbolagservice.getTransaction(req, res).then( (result) => {
            const theNote = result['queryResult'];
            res.status(200).send({
                            success: 'true',
                            message: 'Transaktion retrieved successfully',
                            note: theNote
                        });
        }, rejection => {
            res.status(200).send({
                                success: 'false',
                                message: 'Error while query database.'
                            });
        });
});


app.listen(port, () => {
    return console.log(`Server is listening on ${port}.`);
    });