import express from 'express';
import {Noteservice} from "./noteservice";
import bodyParser from 'body-parser';
import cors from 'cors';

const app = express();
const port = 4000;

// https://medium.com/@purposenigeria/build-a-restful-api-with-node-js-and-express-js-d7e59c7a3dfb
app.use(bodyParser.json());
app.use(cors());
app.use(function(req, res, next) {
	console.log('Server called with URL:' + req.url);
	next();
});


app.get('/api/v1/notes/:id', (req, res) => {
    const noteservice = new Noteservice();

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
    const noteservice = new Noteservice();

    noteservice.createNote(req, res);
});

app.put('/api/v1/note', (req, res) => {
    // Uppdaterar en anteckning.
    const noteservice = new Noteservice();

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

app.get('/api/v1/notes/text/:text', (req, res) => {
    const noteservice = new Noteservice();

    return noteservice.searchNote(req, res);
});

app.listen(port, err => {
    if (err) {
        return console.error(err);
    }

    return console.log(`Server is listening on ${port}`);
});