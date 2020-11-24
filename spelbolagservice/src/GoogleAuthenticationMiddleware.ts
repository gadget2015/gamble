import https from 'https';
import {Logger} from 'winston';

class GoogleAuthenticationMiddleware {
    logger: Logger;

    constructor(logger: Logger) {
        this.logger = logger;
    }

    authentication() {
        const mylog = this.logger;
        return function(req, res, next) {
            if (req.url.startsWith('/bff/v1/mittsaldo') ||
                req.url.startsWith('/bff/v1/administration')) {
                let access_token = req.cookies['access_token_by_robert'];
                let util = new GoogleAuthenticationMiddleware(mylog);
                util.verifyUserAccessToken(access_token, req, res, next);
            } else {
                next();
            }
        }
    }

    verifyUserAccessToken(access_token, req, res, next) {
        this.logger.info('Detta anrop kräver att användaren är autentiserad.');

        // Verifiera token mot https://oauth2.googleapis.com/tokeninfo?id_token=eyJhbGci
        const options = {
          hostname: 'oauth2.googleapis.com',
          port: 443,
          path: '/tokeninfo?id_token=' + access_token,
          method: 'GET'
        }

        const request = https.request(options, respone => {
            let responseString = '';
            const mylog = this.logger;  // behövs för att function ska kunna komma åt loggern.
            respone.on('data', d => {
                responseString +=d;
            });

            respone.on('end', function() {
                //console.log('responseString=' + responseString);
                let json = JSON.parse(responseString);
                let error = json['error'];

                if ( error === 'invalid_token') {
                    mylog.error('Användaren är inte autentiserad, svar ifrån Google =' + responseString);
                    res.status(200).send({success: false, message: 'Du är inte autentiserad.'});
                } else {
                    let userid = json['email'];
                    mylog.info('Användaren är autentiserad som ' + userid + '.');
                    req.userid = userid;    // Sätter userid i req så att BFF/REST-tjänst kan hämta ut det därifrån
                    next();
                }
            });
        });

        request.on('error', error => {
            this.logger.error(error)
        });

        request.end();
    }
}

export {GoogleAuthenticationMiddleware};