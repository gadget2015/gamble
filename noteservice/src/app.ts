import express from 'express';
import {Noteservice} from "./noteservice";

const app = express();
const port = 3000;

// https://medium.com/@purposenigeria/build-a-restful-api-with-node-js-and-express-js-d7e59c7a3dfb

app.get('/api/v1/notes/:id', (req, res) => {
    const noteservice = new Noteservice();

    return noteservice.getNote(req, res);
});


app.listen(port, err => {
    if (err) {
        return console.error(err);
    }
    return console.log(`server is listening on ${port}`);
});