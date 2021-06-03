// imports
var express = require('express');
var cors = require('cors');
var winston = require('winston');
var httpProxy = require('http-proxy');
var logform = require('logform');
const process = require('process');
const https = require('https');
const fs = require('fs');

// globala deklarationer
var apiProxy = httpProxy.createProxyServer();
var noteservice = 'http://localhost:4000';
var spelbolagservice = 'http://localhost:4002';
const app = express();
const port = 80;

// Skapar en logger med Winston.
const myFormat = logform.format.printf(({ level, message, label, timestamp }) => {
  return `${timestamp} ${level}: ${message}`;
});

const logger = winston.createLogger({
  level: 'debug',
  format: logform.format.combine(logform.format.timestamp(), myFormat),
  transports: [
    new winston.transports.Console(),
    new winston.transports.File({ filename: 'webserver.log' })
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



// Lägger in middleware till Express
app.use(cors());
app.use('/noterepo/', express.static('noterepo/build'));
app.use('/spelbolag/', express.static('spelbolag/build'));

// Konfigurerar routes
app.all("/api/v1/notes/*", function(req, res) {
    logger.debug('Redirecting to Noteservice.');
    apiProxy.web(req, res, {target: noteservice});
});

app.all("/api/v1/note/*", function(req, res) {
    logger.debug('Redirecting to Noteservice.');
    apiProxy.web(req, res, {target: noteservice});
});


app.all("/spelbolag/bff/*", function(req, res) {
   logger.debug('Redirect to Spelbolagservice. incoming URL=' + req.url);
   var toCall = req.url.substring(10);
   req.url=toCall;
   apiProxy.web(req, res, {target: spelbolagservice});
});

app.all("*", function(req, res) {
    logger.info('Webserver called with hostname =' + req.hostname + ', url = ' + req.url);

    if (req.hostname === 'stryktipsbolag.se' || req.hostname === 'www.stryktipsbolag.se') {
        if (req.url === '/' ||
            req.url === '/robots.txt' ||
            req.url === '/gamble.jnlp' ||
            req.url === '/favicon.png' ||
            req.url === '/fotboll.png' ||
            req.url === '/tips-1.0.jar' ||
            req.url === '/xercesImpl-2.6.2.jar' ||
            req.url === '/xmlParserAPIs-2.0.0.jar' ||
            req.url === '/bff/v1/mittsaldo/' ||
            req.url === '/bff/v1/tipsbolag/' ||
            req.url.startsWith('/bff/v1/transaktioner/') ||
            req.url === '/bff/v1/administration/' ||
            req.url === '/bff/v1/spelbolag/transaktioner/' ||
            req.url === '/bff/v1/spelbolag/' ||
            req.url === '/bff/v1/spelare/transaktioner/' ) {
            logger.debug('Reverse proxy: Skickar till spelbolag, req.url = ' + req.url +'.');
            apiProxy.web(req, res, {target: 'http://localhost/spelbolag/'});
        } else {
            logger.error('Ogiltig path till stryktipsbolag.se.');
            logger.error('Remote IP = ' + req.ip);
            res.status(401).send('Ogiltig path');
        }
    } else if(req.hostname === 'noterepo.com' || req.hostname === 'www.noterepo.com'){
        if (req.url === '/' ||
            req.url === '/robots.txt' ||
            req.url.startsWith('/?noteid=') ||
            req.url.startsWith('/api/v1/note')) {
            logger.debug('Reverse proxy: Skickar till noterepo, req.url = ' + req.url);
            apiProxy.web(req, res, {target: 'http://localhost/noterepo/'});
        } else {
            logger.error('Ogiltig path till noterepo.com.');
            logger.error('Remote IP = ' + req.ip);
            res.status(401).send('Ogiltig path');
        }
    } else {
        logger.error('Det saknas applikation. url = ' + req.url + ', hostname = ' + req.hostname );
        logger.error('Remote IP = ' + req.ip);
        res.status(200).send('Två applikationer finns:<br/><a href="/noterepo/">/noterepo/</a><br/><a href="/spelbolag/">/spelbolag/</a>.');
    }
});

app.listen(port, err => {
    if (err) {
        return console.error(err);
    }

    logger.info('HTTP server is listening on ' + port + '.');
});

// HTTPS server
var key = fs.readFileSync(__dirname + '/selfsigned.key');
var cert = fs.readFileSync(__dirname + '/selfsigned.crt');
const httpsPort = 443;

var credentials = {
  key: key,
  cert: cert
};

var httpsServer = https.createServer(credentials, app);
httpsServer.listen(httpsPort, () => {
  logger.info("HTTPS server listing on port : " + httpsPort)
});