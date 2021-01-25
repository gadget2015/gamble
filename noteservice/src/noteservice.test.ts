import {Noteservice} from './noteservice';
import mock = jest.mock;
import * as httpMocks from 'node-mocks-http';
import winston from 'winston';
import {format} from 'logform';

// Skapar en logger med Winston.
const myFormat = format.printf(({ level, message, label, timestamp }) => {
  return `${timestamp} ${level}: ${message}`;
});

const logger = winston.createLogger({
  format: format.combine(format.timestamp(), myFormat),
  transports: [
    new winston.transports.Console()
  ]
});

test('Find a note with given ID.', async () => {
    // Given
    const service = new Noteservice(logger);

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
    const service = new Noteservice(logger);

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
    const service = new Noteservice(logger);

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
    const service = new Noteservice(logger);

    let req = httpMocks.createRequest({
        method: 'POST',
        url: '/api/v1/notes/',
        body: {
            "TEXT": "Morsning"
        }
    });

    let res = httpMocks.createResponse();

    // When
    const noteId = await service.createNote(req, res);

   // Then
    expect(noteId).toBeGreaterThan(1);
});

test('Update a Note.', async ()=> {
   // Given
    const service = new Noteservice(logger);

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

test('Fungerar att uppdater en anteckning trots att den innehåller specialtecken som annars gör SQL statement ogiltigt.', async ()=> {
   // Given
    const service = new Noteservice(logger);

    let req = httpMocks.createRequest({
        method: 'PUT',
        url: '/api/v1/note/',
        body: {
            id: 6,
            text: "specialtecken enkel citationstecken='"
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