var express = require('express');
var cors = require('cors');
var httpProxy = require('http-proxy');
var apiProxy = httpProxy.createProxyServer();

var noteservice = 'http://localhost:4000';
var spelbolagservice = 'http://localhost:4002';

const app = express();
const port = 80;

app.use(cors());
app.use('/noterepo/', express.static('noterepo/build'));
app.use('/spelbolag/', express.static('spelbolag/build'));

app.all("/api/v1/notes/*", function(req, res) {
    console.log('redirecting to Noteservice');
    apiProxy.web(req, res, {target: noteservice});
});

app.all("/api/v1/note/*", function(req, res) {
    console.log('redirecting to Noteservice');
    apiProxy.web(req, res, {target: noteservice});
});


app.all("/spelbolag/bff/*", function(req, res) {
   console.log('Redirect to Spelbolagservice. incoming URL=' + req.url);
   var toCall = req.url.substring(10);
   console.log('Modified URL=' + toCall);
   req.url=toCall;
   apiProxy.web(req, res, {target: spelbolagservice});
});

app.all("*", function(req, res) {
    console.log('Webserver called with hostname =' + req.hostname + ', url = ' + req.url);

    if (req.hostname === 'stryktipsbolag.se' || req.hostname === 'www.stryktipsbolag.se') {
        if (req.url === '/' ||
            req.url === '/bff/v1/mittsaldo/' ||
            req.url === '/bff/v1/tipsbolag/' ||
            req.url.startsWith('/bff/v1/transaktioner/') ||
            req.url === '/bff/v1/administration/' ||
            req.url === '/bff/v1/spelbolag/transaktioner/' ||
            req.url === '/bff/v1/spelbolag/' ||
            req.url === '/bff/v1/spelare/transaktioner/' ) {
            console.log('Reverse proxy: Skickar till spelbolag, req.url = ' + req.url);
            apiProxy.web(req, res, {target: 'http://localhost/spelbolag/'});
        } else {
            console.log('Ogiltig path till applikationen.');
            console.log('Remote IP = ' + req.ip);
            res.status(401).send('Ogiltig path');
        }
    } else if(req.hostname === 'noterepo.com' || req.hostname === 'www.noterepo.com'){
        if (req.url === '/' ||
            req.url.startsWith('/?noteid=') ||
            req.url.startsWith('/api/v1/note')) {
            console.log('Reverse proxy: Skickar till noterepo, req.url = ' + req.url);
            apiProxy.web(req, res, {target: 'http://localhost/noterepo/'});
        } else {
            console.log('Ogiltig path till applikationen.');
            console.log('Remote IP = ' + req.ip);
            res.status(401).send('Ogiltig path');
        }
    } else if(req.hostname === '127.0.0.1') {
        if (req.url === '/') {
            console.log('Reverse proxy: Skickar till localhost, req.url = ' + req.url);
            apiProxy.web(req, res, {target: 'http://localhost/'});
        } else {
            console.log('Ogiltig path till applikationen.');
            res.status(401).send('Ogiltig path');
        }
    } else {
        console.log('Det saknas applikation. url = ' + req.url + ', hostname = ' + req.hostname );
        console.log('Remote IP = ' + req.ip);
        res.status(200).send('Två applikationer finns:<br/><a href="/noterepo/">/noterepo/</a><br/><a href="/spelbolag/">/spelbolag/</a>.');
    }
});

app.listen(port, err => {
    if (err) {
        return console.error(err);
    }

    return console.log(`HTTP server is listening on ${port}`);
});
