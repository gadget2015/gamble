import {Noteservice} from './noteservice';
import mock = jest.mock;
import * as httpMocks from 'node-mocks-http';

test('Find a note with given ID.', async () => {
    // Given
    const service = new Noteservice();

    let req = httpMocks.createRequest({
        method: 'GET',
        url: '/api/v1/notes/',
        params: {
            id: 5
        }
    });

    let res = httpMocks.createResponse();

    // When
    const result = await service.getNote(req, res);

    // Then
    expect(result['queryResult'][0]['TEXT']).toBe('TODO');
});

test('Cant find note with given ID.', async () => {
    // Given
    const service = new Noteservice();

    let req = httpMocks.createRequest({
        method: 'GET',
        url: '/api/v1/notes/text',
        params: {
            id: 1967
        }
    });

    let res = httpMocks.createResponse();

    // When
    const result = await service.getNote(req, res);

    // Then
    expect(result['queryResult'].length).toBeLessThan(1);
});

test('Cant find note with not present note ID (NaN).', async () => {
    // Given
    const service = new Noteservice();

    let req = httpMocks.createRequest({
        method: 'GET',
        url: '/api/v1/notes/text',
        params: {
            id: NaN
        }
    });

    const res = httpMocks.createResponse();

    // When
    await service.getNote(req, res).then( (note) => {
        console.log('Search ok');
        fail('Det borde inte gå att köra SQL kommandot.');
    }, rejection => {
        console.log('failed to query database, which is OK.');
    });
});

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
        params: {
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

/**
* Skriver ut resultset.
*/
function printResult(result) {
    for (var i = 0; i < result['queryResult'].length; i++) {
        console.log('Rad #' + i + ' = ' + result['queryResult'] [i].ID  + ' | ' + result['queryResult'][i].TEXT);
     }
}