import React, {useState, useEffect} from 'react';
import './App.css';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import {Noteservice} from './Noteservice';

function App() {

    const [text, setText] = useState('');
    const [lastSaved, setLastSaved] = useState();
    const [noteId, setNoteId] = useState();
    var onlyOnce = 5;

    function handleChange(text) {
        setText(text);
    }

    /**
    * Get hostname for backend-services. Different depending if it's development mode
    * or production.
    */
    function getServerHost() {
        if (document.location.port) {
            return document.location.protocol + '//' + document.location.hostname + ':' + document.location.port;
        } else {
           return document.location.protocol + '//' + document.location.hostname;
        }
    }

    function spara(event) {
        const service  = new Noteservice(getServerHost());

        if(noteId != null) {
            service.updateNote(noteId, text).then( (status) => {
                console.log('update ' + status);
            });
        } else  {
            service.saveNewNote(text).then( (noteId) => {
                setNoteId(noteId);
                window.location.search = '?noteid=' + noteId;
                onlyOnce++; // force new fetch.
            });
        }
    }

    useEffect( () =>  {
        const windowUrl = window.location.search;
        const params = new URLSearchParams(windowUrl);
        const noteid = params.get('noteid');

        if (noteid != null) {
            // Fetch Note from REST API.
            const service  = new Noteservice(getServerHost());

            service.getNote(noteid).then( (note) => {
                    if(note.success === 'true' ) {
                        setText(note.note[0].TEXT);
                        setLastSaved(note.note[0].LASTSAVED);
                        setNoteId(note.note[0].ID);
                    }
                }, rejection => {
                    console.log('Network connection error when calling REST API, status code = ' + rejection);
            });
        }
    }, [onlyOnce]);

    return (
        <div className="container_ui">
            <div className="grid-container">
              <div className="grid-item1"><ReactQuill them="snow" onChange={handleChange} value={text}/></div>
              <div className="grid-item2">Senast sparad {lastSaved}</div>
              <div className="grid-item3"><button onClick={spara}>Spara</button></div>
            </div>
        </div>
    );
}

export default App;
