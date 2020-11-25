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
            if (req.url.startsWith('/bff/v1/spelare/transaktioner/')) {
                // Användaren mäste vara authentiserad och även administratör
                const userid = req['userid'];
                const service = new Spelbolagservice(mylog);
                const spelareResult = await service.getSpelare(userid);
                const spelare = spelareResult['queryResult'][0];

                if(spelare.administratorforspelbolag_id !== null) {
                    // Den inloggad är administratör för ett spelbolag = administratör = OK gå vidare.
                    next();
                } else {
                    // Inloggad är inte administratör.
                     mylog.error('En användare försöker komma åt administratörsfunktioner utan att vara administratör.');
                     res.status(200).send({success: false, message: 'Du är inte administratör, vilket krävs.'});
                }
            } else {
                next();
            }
        }
    }
}

export {AuthorizationMiddleware};