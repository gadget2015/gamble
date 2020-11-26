import express from 'express';
import {Spelbolagservice} from "./spelbolagservice";
import {BFF} from './BFF';
import {GoogleAuthenticationMiddleware} from './GoogleAuthenticationMiddleware';
import {AuthorizationMiddleware} from './AuthorizationMiddleware';
import {RequestLoggerMiddleware} from './RequestLoggerMiddleware';
import {paketeraSuccessResponse, paketeraFailResponse} from './ResponseUtil';
import bodyParser from 'body-parser';
import cors from 'cors';
import winston from 'winston';
import {format} from 'logform';

const cookieParser = require('cookie-parser')

const app = express();
const port = 4002;

// Skapar en logger med Winston.
const myFormat = format.printf(({ level, message, label, timestamp }) => {
  return `${timestamp} ${level}: ${message}`;
});

const logger = winston.createLogger({
  level: 'debug',
  format: format.combine(format.timestamp(), myFormat),
  transports: [
    new winston.transports.Console(),
    new winston.transports.File({ filename: 'spelbolagservice.log' })
  ]
});

const oauth2 = new GoogleAuthenticationMiddleware(logger);
const authz = new AuthorizationMiddleware(logger);
const reqLogger = new RequestLoggerMiddleware(logger);

// https://medium.com/@purposenigeria/build-a-restful-api-with-node-js-and-express-js-d7e59c7a3dfb
app.use(bodyParser.json());
app.use(cors());
app.use(cookieParser());
app.use(reqLogger.requestLogger()); // Loggar inkommande anrop.
app.use(oauth2.authentication());   // Authentication middleware
app.use(authz.authorization());     // Authorization middleware


app.get('/bff/v1/mittsaldo', (req, res) => {
    const bffService = new BFF(logger);
    const successCallback = paketeraSuccessResponse(res);
    const failCallback = paketeraFailResponse(res);

    bffService.getInitialVyForMittsaldo(req['userid']).then(successCallback, failCallback);
});

app.get('/bff/v1/tipsbolag/', (req, res) => {
    const bffService = new BFF(logger);
    const successCallback = paketeraSuccessResponse(res);
    const failCallback = paketeraFailResponse(res);

    bffService.getInitialVyForSpelbolag().then( successCallback, failCallback);
});

app.get('/bff/v1/transaktioner/:kontonummer', (req, res) => {
    const bffService = new BFF(logger);
    const successCallback = paketeraSuccessResponse(res);
    const failCallback = paketeraFailResponse(res);
    const kontonummer = req.params.kontonummer;

    bffService.transaktionerForEttKonto(kontonummer).then( successCallback, failCallback);
});

app.get('/bff/v1/administration', (req, res) => {
    const bffService = new BFF(logger);
    const successCallback = paketeraSuccessResponse(res);
    const failCallback = paketeraFailResponse(res);

    bffService.getInitialVyForAdministration(req['userid']).then(successCallback, failCallback);
});

app.post('/bff/v1/spelbolag/transaktioner/', (req, res) => {
    // Skapar en ny transaktion.
    const bffService = new BFF(logger);
    const failCallback = paketeraFailResponse(res);
    const beskrivning = req.body['beskrivning'];
    const kredit = req.body['kredit'];
    const debet = req.body['debet'];
    const kontonummer = req.body['kontonummer'];

    bffService.addTransaktion(beskrivning, kredit, debet, kontonummer).then( (result) => {
         const retData = result['bffResult'];
         const affectedRows = retData.affectedRows;

         if (affectedRows === 1) {
            res.status(200).send({
                 success: 'true',
                 message: 'Transaktion tillagd.'
             });
         } else {
             res.status(200).send({
                  success: 'false',
                  message: 'Failed to spara en ny transaktion.'
              });
         }
     }, failCallback);
});

app.post('/bff/v1/spelbolag/', (req, res) => {
    // tar betalt av alla spelare.
    const bffService = new BFF(logger);
    const successCallback = paketeraSuccessResponse(res);
    const failCallback = paketeraFailResponse(res);
    const spelbolagsnamn = req.body['spelbolagsnamn'];

    bffService.taBetaltAvSpelare(spelbolagsnamn).then( successCallback, failCallback);
});

app.post('/bff/v1/spelare/transaktioner/', (req, res) => {
    // Skapar en ny transaktion för en spelare.
    const bffService= new BFF(logger);
    const failCallback = paketeraFailResponse(res);
    const beskrivning = req.body['beskrivning'];
    const kredit = req.body['kredit'];
    const debet = req.body['debet'];
    const userid = req.body['userid'];
    const datum = req.body['tidpunkt'];

    bffService.addTransaktionForSpelare(datum, beskrivning, kredit, debet, userid, req['userid']).then( (result) => {
         const retData = result['bffResult'];
         const affectedRows = retData.affectedRows;

         if (affectedRows === 1) {
            res.status(200).send({
                 success: 'true',
                 message: 'Transaktion tillagd.',
                 data: retData
             });
         } else {
            logger.error('Ingen transaktion inlagd.');
             res.status(200).send({
                  success: 'false',
                  message: 'Failed to spara en ny transaktion.'
              });
         }
     }, failCallback);
});

// Start servern
app.listen(port, () => {
    return logger.info(`Server is listening on ${port}.`);
});