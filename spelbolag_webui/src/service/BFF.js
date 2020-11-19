

/**
 * BFF/REST tjänst för Spelbolagssajten.
 */
class BFF {
    spelbolagStartSida() {
        return this.createFetchDataPromise('/bff/v1/tipsbolag/');
    }

    transaktionerForKontonummer(kontonummer) {
        return this.createFetchDataPromise('/bff/v1/transaktioner/' + kontonummer);
    }

    tipssaldoStartSida() {
        return this.createFetchDataPromise('/bff/v1/mittsaldo/');
    }

    administrationStartSida() {
        return this.createFetchDataPromise('/bff/v1/administration/');
    }

    laggTillTransaktion(beskrivning, kredit, debet, kontonummer) {
        const fetchDataPromise = new Promise((resolve, reject) => {
            const data = {'beskrivning': beskrivning, 'kredit': kredit, 'debet': debet, 'kontonummer': kontonummer};
            console.log('Lägger till ny transaktion = ' + JSON.stringify(data));

            fetch(this.getServerHost() + '/api/v1/transaktioner/', {
                   method: 'POST',
                   headers: {
                         'Content-Type': 'application/json'
                       },
                   body: JSON.stringify(data) })
                   .then( async (response) => {
                        // Hämtar ut returdata
                        await response.json().then( (resolved) => {
                                resolve(resolved);
                            }, (rejectedMessage) => {
                                console.log('Rejected when parsing return data, message = ' + rejectedMessage);
                                reject(0);
                            });
                   }, (rejectedMessage) => {
                        console.log('Rejected when posting note to server, message = ' + rejectedMessage);
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
            return document.location.protocol + '//' + document.location.hostname + ':' + document.location.port;
        } else {
           return document.location.protocol + '//' + document.location.hostname;
        }
    }

    /**
    * Skapar ett generellt Promise för att anropa backend/BFF.
    */
    createFetchDataPromise(bffEndpoint) {
        const fetchDataPromise = new Promise((resolve, reject) => {
            const urlToBFF = this.getServerHost() + bffEndpoint;

            fetch(urlToBFF, {credentials: "same-origin"})
                .then(async response => {
                    if (!response.ok) {
                          console.log('Dåligt nätverk: response was not ok, code = ' + JSON.stringify(response));
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
}

export {BFF};