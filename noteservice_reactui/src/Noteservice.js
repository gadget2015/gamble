
class Noteservice {
    constructor(endpoint) {
        this.destinationServer = endpoint;
    }

    async getNote(noteid) {
        let returnData;

        await fetch('http://localhost:3000/api/v1/notes/5')
            .then(async response => {
                if (!response.ok) {
                      throw new Error('Dåligt nätverk: response was not ok, code = ' + response);
                } else {
                    const data = await response.json();
                    returnData = data;
                }

            }, rejectedMessage => {
                console.log('Jättefel på nätverket, beskrivning = ' + rejectedMessage.message);

                returnData = 0;
            });

            return returnData;
    }
}

export {Noteservice};