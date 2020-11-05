import {Spelbolagservice} from './spelbolagservice';
import mock = jest.mock;
import * as httpMocks from 'node-mocks-http';

test('Leta fram en transaktion med givet ID.', async () => {
    // Given
    const service = new Spelbolagservice();

    // When
    const result = await service.getTransaction('1');

    // Then
    expect(result['queryResult'][0]['beskrivning']).toBe('Spelar stryktipset');
});


test('Kan inte hitta en transaktion med givet ID.', async () => {
    // Given
    const service = new Spelbolagservice();

    // When
    const result = await service.getTransaction('1967');

    // Then
    expect(result['queryResult'].length).toBeLessThan(1);
});


test('Hämtar alla transaktioner för ett givet kontonummer.', async () => {
    // Given
    const service = new Spelbolagservice();

    // When
    const result = await service.getTransactions('234');

    // Then
    expect(result['queryResult'].length).toBeGreaterThan(2);
});

test('Leta fram en seplare', async () => {
    // Given
    const service = new Spelbolagservice();

    // When
    const result = await service.getSpelare('robert.georen@gmail.com');

    // Then
    expect(result['queryResult'].length).toBe(1);
    expect(result['queryResult'][0]['userid']).toBe('robert.georen@gmail.com');
});

test('Letar fram ett Spelbolag', async () => {
    // Given
    const service = new Spelbolagservice();

    // When
    const result = await service.getSpelbolag('The gamblers');

    // Then
    expect(result['queryResult'].length).toBe(1);
    expect(result['queryResult'][0]['insatsperomgang']).toBe(50);
});

test('Hämtar alla spelbolag', async () => {
    // Given
    const service = new Spelbolagservice();

    // When
    const result = await service.getSpelbolag(null);

    // Then
    expect(result['queryResult'].length).toBe(2);
});

test('Skapa en transaktion för givet kontonummer.', async() => {
    // Given
    const service = new Spelbolagservice();

    // When
    const result = await service.addTransaktion('Spelar på Stryktipset', 0, 30, 1967);

   // Then
    expect(result['queryResult']['affectedRows']).toBe(1);

});

test('Hämtar alla spelare i ett Spelbolag.', async () => {
    // Given
    const service = new Spelbolagservice();

    // When
    const result = await service.getAllaSpelareForSpelbolag('1');

    // Then
    expect(result['queryResult'].length).toBe(3);
});

test('Hämtar saldo för en givet kontonummer efter debet (+)', async () => {
    // Given
    const service = new Spelbolagservice();

    // When
    const saldoBefore = await service.getSaldo('1967');
    const result = await service.addTransaktion('Spelar på Stryktipset', 0, 30, 1967);
    const saldoAfter = await service.getSaldo('1967');

    // Then
    expect(saldoAfter - saldoBefore).toBe(30);
});

test('Hämtar saldo för en givet kontonummer efter kredit (-)', async () => {
    // Given
    const service = new Spelbolagservice();

    // When
    const saldoBefore = await service.getSaldo('1967');
    const result = await service.addTransaktion('Spelar på Stryktipset', 50, 0, 1967);
    const saldoAfter = await service.getSaldo('1967');

    // Then
    expect(saldoAfter - saldoBefore).toBe(-50);
});

test('Ta betalt av alla seplare i ett spelbolag', async () => {
    // Given
    const service = new Spelbolagservice();
    const spelare1Saldo = await service.getSaldo('234');
    const spelare2Saldo = await service.getSaldo('89');
    const spelare3Saldo = await service.getSaldo('90');
    const spelbolagSaldo = await service.getSaldo('88');

    // When
    const result = await service.taBetaltForEnOmgang('The gamblers', '2020-10-21 08:00:00');

    // Then
    expect(await service.getSaldo('234') - spelare1Saldo).toBe(-50);    // Det borde dragits 50 ifrån spelarkontot.
    expect(await service.getSaldo('89') - spelare2Saldo).toBe(-50);    // Det borde dragits 50 ifrån spelarkontot.
    expect(await service.getSaldo('90') - spelare3Saldo).toBe(-50);    // Det borde dragits 50 ifrån spelarkontot.
    expect(await service.getSaldo('88') - spelbolagSaldo).toBe(150);
});

test('Hämtar alla spelbolag.', async () => {
    // Given
    const service = new Spelbolagservice();

    // When
    const result = await service.getAllaSpelbolag();

    // Then
    expect(result['queryResult'].length).toBe(2);
});

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