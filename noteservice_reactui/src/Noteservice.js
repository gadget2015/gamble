
class Noteservice {
    constructor(endpoint) {
        this.destinationServer = endpoint;
    }

    getNote(noteid) {
        const fetchDataPromise = new Promise((resolve, reject) => {
            fetch(this.destinationServer + '/api/v1/notes/' + noteid)
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
}

export {Noteservice};