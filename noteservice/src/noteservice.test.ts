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
    expect(result[0]['TEXT']).toBe('TODO');
});

test('Cant find note with given ID.', async () => {
    // Given
    const service = new Noteservice();

    let req = httpMocks.createRequest({
        method: 'GET',
        url: '/api/v1/notes/',
        params: {
            id: 1967
        }
    });

    let res = httpMocks.createResponse();

    // When
    let result = await service.getNote(req, res);

    // Then
    expect(JSON.stringify(result)).toBe('[]');
});

/*
fetch('http://localhost:3000/api/v1/note', {
   method: 'POST',
   body: JSON.stringify({
     TEXT: 'foo'
   }),
   headers: {
     'Content-type': 'application/json; charset=UTF-8'
   }
 }).then(res => res.json())
 .then(console.log);
*/
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