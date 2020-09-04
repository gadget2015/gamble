var express = require('express');
var cors = require('cors');
var httpProxy = require('http-proxy');
var apiProxy = httpProxy.createProxyServer();

var noteservice = 'http://localhost:4000';
const app = express();
const port = 80;

app.use(cors());
app.use(express.static('build'));

app.all("/api/v1/notes/*", function(req, res) {
    console.log('redirecting to Noteservice');
    apiProxy.web(req, res, {target: noteservice});
});

app.all("/api/v1/note/*", function(req, res) {
    console.log('redirecting to Noteservice');
    apiProxy.web(req, res, {target: noteservice});
});

app.listen(port, err => {
    if (err) {
        return console.error(err);
    }

    return console.log(`HTTP server is listening on ${port}`);
});