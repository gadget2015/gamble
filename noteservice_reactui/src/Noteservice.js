
class Noteservice {
    constructor(endpoint) {
        this.destinationServer = endpoint;
    }

    getNote(noteid) {
        console.log('Get note id = ' + noteid);

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

    saveNewNote(text) {
        const fetchDataPromise = new Promise((resolve, reject) => {
            const data = {'TEXT': text};
            console.log('Save new note with TEXT = ' + text);

            fetch(this.destinationServer + '/api/v1/notes/', {
                   method: 'POST',
                   headers: {
                         'Content-Type': 'application/json'
                       },
                   body: JSON.stringify(data) })
                   .then( async (response) => {
                        // Hämtar ut returdata (JSON objekt).
                        await response.json().then( (resolved) => {
                                console.log('JSON data=' + JSON.stringify(resolved));
                                const noteId = resolved.noteid;
                                resolve(noteId);
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

        updateNote(noteId, text) {
            const fetchDataPromise = new Promise((resolve, reject) => {
                const data = {'id': noteId, 'text': text};
                console.log('Update note: ' + JSON.stringify(data));
                fetch(this.destinationServer + '/api/v1/note/', {
                       method: 'PUT',
                       headers: {
                             'Content-Type': 'application/json'
                           },
                       body: JSON.stringify(data) })
                       .then( async (response) => {
                            // Hämtar ut returdata (JSON objekt).
                            await response.json().then( (resolved) => {
                                    const updateOk = resolved.success;
                                    resolve(updateOk);
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
}

export {Noteservice};