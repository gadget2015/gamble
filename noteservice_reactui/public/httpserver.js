var cors = require('cors');
var httpProxy = require('http-proxy');
var apiProxy = httpProxy.createProxyServer();

var noteservice = 'http://localhost:4000';
var spelbolagservice = 'http://localhost:4002';

const app = express();
const port = 80;

app.use(cors());
app.use(express.static('build'));
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
   console.log('Redirect to Spelbolagservice. URL=' + req.url);
   var toCall = req.url.substring(10);
   console.log('Modified URL=' + toCall);
   req.url=toCall;
   apiProxy.web(req, res, {target: spelbolagservice});
});

app.listen(port, err => {
    if (err) {
        return console.error(err);
    }

    return console.log(`HTTP server is listening on ${port}`);
});
