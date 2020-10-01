import {Spelbolagservice} from './spelbolagservice';
import mock = jest.mock;
import * as httpMocks from 'node-mocks-http';

test('Find a transaction with given ID.', async () => {
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


test('Cant find a transaction with given ID.', async () => {
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


test('Find transactions for given kontonummer.', async () => {
    // Given
    const service = new Spelbolagservice();

    let req = httpMocks.createRequest({
        method: 'GET',
        url: '/api/v1/transactions/',
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
        console.log('Rad #' + i + ' = ' + result['queryResult'] [i].ID  + ' | ' + result['queryResult'][i].beskrivning
        + ' | ' + result['queryResult'][i].debit
        + ' | ' + result['queryResult'][i].kredit
        + ' | ' + result['queryResult'][i].tid
        + ' | ' + result['queryResult'][i].kontonr);
     }
}