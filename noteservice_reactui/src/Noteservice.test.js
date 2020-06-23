import {Noteservice} from './Noteservice';


it('Load a note', async () => {
    // Given
    const service  = new Noteservice('http://localhost:4000');

    // When
    const note = await service.getNote(5);

    // Then
    expect(note).not.toEqual(0);
    expect(note.note[0].TEXT).toEqual('TODO');
});
