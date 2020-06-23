import {Noteservice} from './Noteservice';


it('Load a note', async () => {
    // Given
    const service  = new Noteservice('localhost://mock.se/?3434');

    // When
    const note = await service.getNote('5');
    //console.log('note=' + JSON.stringify(note));

    // Then
    expect(note).not.toEqual(0);
    expect(note.note[0].TEXT).toEqual('TODO');
});
