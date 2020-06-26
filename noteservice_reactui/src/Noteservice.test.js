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

it('Hittar inte en anteckning', async () => {
    // Given
    const service  = new Noteservice('http://localhost:4000');

    // When
    const note = await service.getNote(0);

    // Then
    expect(note).not.toEqual(0);
    expect(note.note.length).toEqual(0);
});

it('Borde vara ett jättefel på nätverket.', async () => {
    // Given
    const service  = new Noteservice('http://localhost:1967');

    // When
    await service.getNote(0).then( (note) => {
        // Then
        fail('Det borde inte komma tillbaka en note.');
    }, reject => {
        // Then
        expect(reject).toEqual(0);
    });
});

it('Borde spara en ny anteckning.', async () => {
    // Given
    const service  = new Noteservice('http://localhost:4000');

    // When
    await service.saveNote('UI sparar.').then(noteId => {
            expect(noteId).toBeGreaterThan(1);   // Det har sparats en ny anteckning.
        }, reject => {
            fail('My Error=' + reject);
        });
});

it('Borde bli fel vid anrop till servern vid spara operationen', async () => {
  // Given
    const service  = new Noteservice('http://localhost:1967');

    // When
    await service.saveNote('UI sparar.').then(noteId => {
            fail('Borde inte gå bra att spara, ' + noteId);
        }, reject => {
            // Detta borde inträffa, dvs. det ska gå fel.
        });
});