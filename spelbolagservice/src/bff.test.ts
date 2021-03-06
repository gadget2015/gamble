import {BFF} from './BFF';
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


test('Hämtar initial vy för spelbolag sidan', async () => {
    // Given
    const service = new BFF(logger);

    // When
    const result = await service.getInitialVyForSpelbolag();

    // Then
    expect(result['bffResult'][0]['namn']).toBe('The gamblers');
    expect(result['bffResult'][0]['antalSpelare']).toBe(3);
});

test('Hämtar initial vy för Mitt saldo sidan', async () => {
    // Given
    const service = new BFF(logger);

    // When
    const result = await service.getInitialVyForMittsaldo('robert.georen@gmail.com');

    // Then
    expect(result['bffResult']['saldo']).toBeLessThan(0);
    expect(result['bffResult']['transaktioner'].length).toBeGreaterThan(1);
});

test('Hämtar initial vy för Administration sidan', async () => {
    // Given
    const service = new BFF(logger);

    // When
    const result = await service.getInitialVyForAdministration('robert.georen@gmail.com');

    // Then
    expect(result['bffResult']['saldo']).toBeGreaterThan(0);
    expect(result['bffResult']['namn']).toBe('The gamblers');
});

test('Ta betalt av alla spelare i ett givet Spelbolag', async () => {
    // OBS! Detta testet körs samtidigt som andra tester av Jest, så
    // det går inte att ha aktivt eftersom det påverkar andra viktigare tester.
    // främtst testet i spelbolagservice.taBetaltForEnOmgang.
    // Given
    const service = new BFF(logger);

    // When
//    const myPromise = service.taBetaltAvSpelare('The gamblers');
    // Then
    //expect(result['bffResult']['saldo']).toBeGreaterThan(0);
    //expect(result['bffResult']['spelarInfo'].length).toBe(3);
});
