import {BFF} from './BFF';
import mock = jest.mock;
import * as httpMocks from 'node-mocks-http';

test('Hämtar initial vy för spelbolag sidan', async () => {
    // Given
    const service = new BFF();

    // When
    const result = await service.getInitialVyForSpelbolag();

    // Then
    expect(result['bffResult'][0]['namn']).toBe('The gamblers');
    expect(result['bffResult'][0]['antalSpelare']).toBe(3);
});

test('Hämtar initial vy för Mitt saldo sidan', async () => {
    // Given
    const service = new BFF();

    // When
    const result = await service.getInitialVyForMittsaldo('robert.georen@gmail.com');

    // Then
    expect(result['bffResult']['saldo']).toBeLessThan(0);
    expect(result['bffResult']['transaktioner'].length).toBeGreaterThan(1);
});

test('Hämtar initial vy för Administration sidan', async () => {
    // Given
    const service = new BFF();

    // When
    const result = await service.getInitialVyForAdministration('robert.georen@gmail.com');

    // Then
    expect(result['bffResult']['saldo']).toBeGreaterThan(0);
    expect(result['bffResult']['namn']).toBe('The gamblers');
});
