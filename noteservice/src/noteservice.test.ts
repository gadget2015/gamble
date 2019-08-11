import {Noteservice} from './noteservice';
import mock = jest.mock;
import * as httpMocks from 'node-mocks-http';

test('first', async () => {

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
    console.log('Test.result = ' + JSON.stringify(result));
});