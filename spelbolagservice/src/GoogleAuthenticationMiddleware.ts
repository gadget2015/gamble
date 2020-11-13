import https from 'https';

class GoogleAuthenticationMiddleware {

    authentication() {
        return function(req, res, next) {
            if (req.url.startsWith('/bff/v1/mittsaldo')) {
                let access_token = req.cookies['access_token_by_robert'];
                console.log('Detta anrop kräver att användaren är autentiserad.');
                console.log('Cookies=' + JSON.stringify(req.cookies));

                let util = new GoogleAuthenticationMiddleware();
                util.verifyUserAccessToken(access_token, req, res, next);
            } else {
                next();
            }
        }
    }

    verifyUserAccessToken(access_token, req, res, next) {
        console.log('access_token=' + access_token);
        // Verifiera token mot https://oauth2.googleapis.com/tokeninfo?id_token=eyJhbGci
        const options = {
          hostname: 'oauth2.googleapis.com',
          port: 443,
          path: '/tokeninfo?id_token=' + access_token,
          method: 'GET'
        }

        const request = https.request(options, respone => {
            let responseString = '';

            respone.on('data', d => {
                responseString +=d;
            });

            respone.on('end', function() {
                console.log('responseString=' + responseString);
                let json = JSON.parse(responseString);
                let error = json['error'];

                if ( error === 'invalid_token') {
                    res.status(200).send({error: 'Du är inte autentiserad.'});
                } else {
                    let userid = json['email'];
                    console.log('Du är autentiserad som ' + userid + '.');
                    req.userid = userid;    // Sätter userid i req så att BFF/REST-tjänst kan hämta ut det därifrån
                    next();
                }
            });
        });

        request.on('error', error => {
            console.error(error)
        });

        request.end();
    }
}

export {GoogleAuthenticationMiddleware};