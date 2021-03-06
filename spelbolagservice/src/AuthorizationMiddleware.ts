import https from 'https';
import {Logger} from 'winston';
import {Spelbolagservice} from './spelbolagservice';

class AuthorizationMiddleware {
    logger: Logger;

    constructor(logger: Logger) {
        this.logger = logger;
    }

    authorization() {
        const mylog = this.logger;

        return async function(req, res, next) {
            if (req.url.startsWith('/bff/v1/spelare/transaktioner/') ||
                req.url === '/bff/v1/administration/') {
                // Användaren mäste vara authentiserad och även administratör
                mylog.debug('Detta anrop kräver att användaren är aukotriserad med administratör behörighet.');
                const userid = req['userid'];
                const service = new Spelbolagservice(mylog);
                const spelareResult = await service.getSpelare(userid);

                mylog.debug('Verifierar spelare ' + JSON.stringify(spelareResult));
                if (spelareResult['queryResult'].length === 0) {
                    mylog.error('En oregistrerad användare försöker komma åt administratörsfunktioner, userid = ' + userid);
                    res.status(200).send({success: false, message: 'Du är inte administratör, vilket krävs.'});
                } else {
                    const spelare = spelareResult['queryResult'][0];

                    if(spelare.administratorforspelbolag_id !== null) {
                        // Den inloggad är administratör för ett spelbolag = administratör = OK gå vidare.
                        mylog.debug('Användaren (' + userid + ') är administratör för ett spelbolag.')
                        next();
                    } else {
                        // Inloggad är inte administratör.
                         mylog.error('En användare försöker komma åt administratörsfunktioner utan att vara administratör.');
                         res.status(200).send({success: false, message: 'Du är inte administratör, vilket krävs.'});
                    }
                }
            } else {
                next();
            }
        }
    }
}

export {AuthorizationMiddleware};