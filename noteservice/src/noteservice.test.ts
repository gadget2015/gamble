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
    let result = await service.getNote(req, res);

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
    let result = await service.getNote(req, res);

    // Then
    expect(result['queryResult'].length).toBeLessThan(1);
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
    let result = await service.createNote(req, res);

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
    let result = await service.searchNote(req, res);

    // Then should return more than one result
    expect(result['queryResult'].length).toBeGreaterThan(1);
});

/**
* Skriver ut resultset.
*/
function printResult(result) {
    for (var i = 0; i < result['queryResult'].length; i++) {
        console.log('Rad #' + i + ' = ' + result['queryResult'] [i].ID  + ' | ' + result['queryResult'][i].TEXT);
     }
}