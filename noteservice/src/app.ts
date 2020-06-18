import express from 'express';
import {Noteservice} from "./noteservice";
import bodyParser from 'body-parser';

const app = express();
const port = 3000;

// https://medium.com/@purposenigeria/build-a-restful-api-with-node-js-and-express-js-d7e59c7a3dfb
app.use(bodyParser.json());

app.get('/api/v1/notes/:id', (req, res) => {
    const noteservice = new Noteservice();

    return noteservice.getNote(req, res);
});

app.post('/api/v1/note', (req, res) => {
    console.log('app.body = ' + JSON.stringify(req.body));
    const noteservice = new Noteservice();

    return noteservice.createNote(req, res);
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