# Reverse proxy för Node.js webbapplikationer
Agerar Webserver och reverse proxy för statiska resurser och REST-API'er i Amazon AWS EC2 miljö.

Hanterar:
- noterepo.com
- stryktipsbolag.se

# Bygga
Behövs inte eftersom det en en node.js applikation.

# Generera ett cetifikat för att kunna köra servern med https/TLS.
https://github.com/sagardere/set-up-SSL-in-nodejs

# Starta
> node src\httpserver.js

eller

>> npm start

# Deploy
1. Bygg > npm install
2. Paketera > npm run bundle
3. Logga in på AWS och deploya.
``cd /var/local/webapps``

``tar -xvf webserver.tgz``

Loggfil: /var/local/webapps/webserver.log