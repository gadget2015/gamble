import express from 'express';
import {Spelbolagservice} from "./spelbolagservice";
import {BFF} from './BFF';
import {GoogleAuthenticationMiddleware} from './GoogleAuthenticationMiddleware';
import bodyParser from 'body-parser';
import cors from 'cors';

const cookieParser = require('cookie-parser')
const oauth2 = new GoogleAuthenticationMiddleware();
const app = express();
const port = 4001;

// https://medium.com/@purposenigeria/build-a-restful-api-with-node-js-and-express-js-d7e59c7a3dfb
app.use(bodyParser.json());
app.use(cors());
app.use(cookieParser());
// Middleware log request.
app.use(function(req, res, next) {
	console.log('Server called with URL:' + req.url);
	next();
});

app.use(oauth2.authentication());   // Authentication middleware

app.get('/bff/v1/mittsaldo', (req, res) => {
    res.status(200).send('Hello world.' + req['userid']);
});

app.get('/bff/v1/tipsbolag/', (req, res) => {
    const bffService = new BFF();

    bffService.getInitialVyForSpelbolag().then( (result) => {
            const spelbolag = result['bffResult'];
            res.status(200).send({
                            success: 'true',
                            message: 'Hämtat initial vydata för Spelbolag sidan, ' + spelbolag.length + ' Spelbolag hittade.',
                            data: spelbolag
                        });
        }, rejection => {
            res.status(200).send({
                                success: 'false',
                                message: 'Error while query database.'
                            });
        });
});

app.get('/bff/v1/tipsbolag/transaktioner/:kontonummer', (req, res) => {
    const bffService = new BFF();
    const kontonummer = req.params.kontonummer;

    bffService.transaktionerForEttKonto(kontonummer).then( (result) => {
            const bffResult = result['bffResult'];
            res.status(200).send({
                            success: 'true',
                            message: 'Hämtat transaktioner för ett spelbolag.',
                            data: bffResult
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