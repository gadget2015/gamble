import express from 'express';
import {Noteservice} from "./noteservice";
import bodyParser from 'body-parser';
import cors from 'cors';
import winston from 'winston';
import {format} from 'logform';

const process = require('process');
const app = express();
const port = 4000;

// Skapar en logger med Winston.
const myFormat = format.printf(({ level, message, label, timestamp }) => {
  return `${timestamp} ${level}: ${message}`;
});

const logger = winston.createLogger({
  level: 'debug',
  format: format.combine(format.timestamp(), myFormat),
  transports: [
    new winston.transports.Console(),
    new winston.transports.File({ filename: 'noteservice.log' })
  ]
});

// Crash log
process.on('uncaughtException', function (exception, origin) {
  console.log(exception);
  logger.error('Major error i Node processen. Trace = ' + exception + ', origin = ' + origin);
});

process.on('unhandledRejection', (reason, promise) => {
  console.log('Unhandled rejection at ' + promise + ', reason=' + reason);
  logger.error('Unhandled rejection at' + promise + ', reason=' + reason);
  process.exit(1)
});


// https://medium.com/@purposenigeria/build-a-restful-api-with-node-js-and-express-js-d7e59c7a3dfb
app.use(bodyParser.json());
app.use(cors());
app.use(function(req, res, next) {
	logger.debug('Server called with URL:' + req.url);
	next();
});


app.get('/api/v1/notes/:id', (req, res) => {
    const noteservice = new Noteservice(logger);

    noteservice.getNote(req, res).then( (result) => {
            const theNote = result['queryResult'];
            res.status(200).send({
                            success: 'true',
                            message: 'Notes retrieved successfully',
                            note: theNote
                        });
        }, rejection => {
            res.status(200).send({
                                success: 'false',
                                message: 'Error while query database.'
                            });
        });
});

app.post('/api/v1/notes', (req, res) => {
    // Skapar en ny anteckning.
    const noteservice = new Noteservice(logger);

    noteservice.createNote(req, res).then( (result) => {
        const noteId = result;
        res.status(200).send({
                    success: 'true',
                    message: 'Note saved successfully with Id = ' + noteId + '.',
                    noteid: noteId
                });

        }, rejection => {
            res.status(200).send({
                success: 'false',
                message: 'Error while query database.' + JSON.stringify(rejection)
            });
        });
});

app.put('/api/v1/note', (req, res) => {
    // Uppdaterar en anteckning.
    const noteservice = new Noteservice(logger);

    noteservice.updateNote(req, res).then( (result) => {
             const affectedRows = result['queryResult'].affectedRows;
             if (affectedRows === 1) {
                res.status(200).send({
                     success: 'true',
                     message: 'Notes updated successfully'
                 });
             } else {
                 res.status(200).send({
                      success: 'false',
                      message: 'Failed to update note.'
                  });
             }
         }, rejection => {
             res.status(200).send({
                                 success: 'false',
                                 message: 'Error while query database.'
                             });
         });;
});

app.listen(port, err => {
    if (err) {
        return console.error(err);
    }

    logger.info(`Server is listening on ${port}.`);
});