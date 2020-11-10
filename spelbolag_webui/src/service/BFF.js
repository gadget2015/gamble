

/**
 * BFF/REST tjänst för Spelbolagssajten.
 */
class BFF {
    spelbolagStartSida() {
        const fetchDataPromise = new Promise((resolve, reject) => {
            const urlToBFF = this.getServerHost() + '/bff/v1/tipsbolag/';

            fetch(urlToBFF)
                .then(async response => {
                    if (!response.ok) {
                          console.log('Dåligt nätverk: response was not ok, code = ' + response);
                          reject(0);
                    } else {
                        const data = await response.json();
                        resolve(data);
                    }
                }, rejectedMessage => {
                    console.log('Jättefel på nätverket, beskrivning = ' + rejectedMessage.message);

                    reject(0);
                });
        });

        return fetchDataPromise;
    }

    transaktionerForSpelboalg(kontonummer) {
            const fetchDataPromise = new Promise((resolve, reject) => {
                const urlToBFF = this.getServerHost() + '/bff/v1/tipsbolag/transaktioner/' + kontonummer;

                fetch(urlToBFF)
                    .then(async response => {
                        if (!response.ok) {
                              console.log('Dåligt nätverk: response was not ok, code = ' + response);
                              reject(0);
                        } else {
                            const data = await response.json();

                            resolve(data);
                        }
                    }, rejectedMessage => {
                        console.log('Jättefel på nätverket, beskrivning = ' + rejectedMessage.message);

                        reject(0);
                    });
            });

            return fetchDataPromise;
    }
   /**
    * Get hostname for backend-services. Different depending if it's development mode
    * or production.
    */
    getServerHost() {
        if (document.location.port) {
            // Development mode.
            return document.location.protocol + '//' + document.location.hostname + ':4001';
        } else {
           return document.location.protocol + '//' + document.location.hostname;
        }
    }
}

export {BFF};