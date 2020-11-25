import {Logger} from 'winston';

/**
* Loggar inkommande http anrop.
*/
class RequestLoggerMiddleware {
    logger: Logger;

    constructor(logger: Logger) {
        this.logger = logger;
    }

    requestLogger() {
        const mylog = this.logger;

        return async function(req, res, next) {
            mylog.info('Server called with URL: ' + req.url);
            next();
        }
    }
}

export {RequestLoggerMiddleware};