import {Spelbolagservice} from './spelbolagservice';
import mock = jest.mock;
import * as httpMocks from 'node-mocks-http';

test('Dummy test', () => {
});
/**
test('Leta fram en transaktion med givet ID.', async () => {
    // Given
    const service = new Spelbolagservice();

    let req = httpMocks.createRequest({
        method: 'GET',
        url: '/api/v1/transactions/',
        params: {
            id: 1
        }
    });

    let res = httpMocks.createResponse();

    // When
    const result = await service.getTransaction(req, res);

    // Then
    expect(result['queryResult'][0]['beskrivning']).toBe('Spelar stryktipset');
});


test('Kan inte hitta en transaktion med givet ID.', async () => {
    // Given
    const service = new Spelbolagservice();

    let req = httpMocks.createRequest({
        method: 'GET',
        url: '/api/v1/transactions/',
        params: {
            id: 1967
        }
    });

    let res = httpMocks.createResponse();

    // When
    const result = await service.getTransaction(req, res);

    // Then
    expect(result['queryResult'].length).toBeLessThan(1);
});


test('Hämtar alla transaktioner för ett givet kontonummer.', async () => {
    // Given
    const service = new Spelbolagservice();

    let req = httpMocks.createRequest({
        method: 'GET',
        url: '/api/v1/transaktioner/',
        params: {
            kontonr: 234
        }
    });

    const res = httpMocks.createResponse();

    // When
    const result = await service.getTransactions(req, res);

    // Then
    expect(result['queryResult'].length).toBe(2);
});

test('Leta fram en seplare', async () => {
    // Given
    const service = new Spelbolagservice();

    let req = httpMocks.createRequest({
        method: 'GET',
        url: '/api/v1/spelare/',
        params: {
            userid: 'robert.georen@gmail.com'
        }
    });

    const res = httpMocks.createResponse();

    // When
    const result = await service.getSpelare(req, res);

    // Then
    expect(result['queryResult'].length).toBe(1);
    expect(result['queryResult'][0]['userid']).toBe('robert.georen@gmail.com');
});

test('Letar fram ett Spelbolag', async () => {
    // Given
    const service = new Spelbolagservice();

    let req = httpMocks.createRequest({
        method: 'GET',
        url: '/api/v1/spelbolag/',
        params: {
            namn: 'The gamblers'
        }
    });

    const res = httpMocks.createResponse();

    // When
    const result = await service.getSpelbolag(req, res);

    // Then
    expect(result['queryResult'].length).toBe(1);
    expect(result['queryResult'][0]['insatsperomgang']).toBe(50);
});

test('Hämtar alla spelbolag', async () => {
    // Given
    const service = new Spelbolagservice();

    let req = httpMocks.createRequest({
        method: 'GET',
        url: '/api/v1/spelbolag/',
        params: {
        }
    });

    const res = httpMocks.createResponse();

    // When
    const result = await service.getSpelbolag(req, res);

    // Then
    expect(result['queryResult'].length).toBe(2);
});

test('Skapa en transaktion för givet kontonummer.', async() => {
 // Given
    const service = new Spelbolagservice();

    let req = httpMocks.createRequest({
        method: 'POST',
        url: '/api/v1/transaktioner/',
        body: {
            "beskrivning": "Spelar på Stryktipset",
            "kredit": 30,
            "kontonummer": 1967
        }
    });

    let res = httpMocks.createResponse();

    // When
    const result = await service.addTransaktion(req, res);

   // Then
    expect(result['queryResult']['affectedRows']).toBe(1);

});

/**
test('Create a new Note.', async ()=> {
   // Given
    const service = new Noteservice();

    let req = httpMocks.createRequest({
        method: 'POST',
        url: '/api/v1/notes/',
        body: {
            "TEXT": "Morsning"
        }
    });

    let res = httpMocks.createResponse();

    // When
    const result = await service.createNote(req, res);

   // Then
    expect(result).toBeGreaterThan(1);
});

test('Search for a note that contains the given text', async() => {
    // Given
    const service = new Noteservice();

    let req = httpMocks.createRequest({
        method: 'GET',
        url: '/api/v1/notes/',
        params: {
            text: 'morsning'
        }
    });

    let res = httpMocks.createResponse();

    // When
    const result = await service.searchNote(req, res);

    // Then should return more than one result
    expect(result['queryResult'].length).toBeGreaterThan(1);
});


test('Update a Note.', async ()=> {
   // Given
    const service = new Noteservice();

    let req = httpMocks.createRequest({
        method: 'PUT',
        url: '/api/v1/note/',
        body: {
            id: 5,
            text: "TODO"
        }
    });

    let res = httpMocks.createResponse();

    // When
    const result = await service.updateNote(req, res);

    // Then
    const affectedRows = result['queryResult'].affectedRows;
    expect(affectedRows).toEqual(1);
});


**/

/**
* Skriver ut resultset.
*/
function printResult(result) {
    for (var i = 0; i < result['queryResult'].length; i++) {
        console.log('Rad #' + i + ' = ID:' + result['queryResult'] [i].ID  + ' , beskrivning: ' + result['queryResult'][i].beskrivning
        + ' , debit: ' + result['queryResult'][i].debit
        + ' , kredit: ' + result['queryResult'][i].kredit
        + ' , tid: ' + result['queryResult'][i].tid
        + ' , kontonummer: ' + result['queryResult'][i].kontonr);
     }
}