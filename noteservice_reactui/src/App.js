import React, {useState, useEffect} from 'react';
import './App.css';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import {Noteservice} from './Noteservice';

function App() {

    const [text, setText] = useState();
    let lastSaved = '2020-06-22 12:00:00T234';
    const onlyOnce = 5;

    function handleChange(text) {
        setText(text);
    }

    function spara(event) {
        alert(text);
    }

    useEffect( () =>  {
        const windowUrl = window.location.search;
        const params = new URLSearchParams(windowUrl);
        const noteid = params.get('noteid');
        console.log('Use Effect, load noteid ' + noteid);
        const service  = new Noteservice('http://localhost:4000');
        service.getNote(noteid).then( (note) => {
            console.log('note=' + JSON.stringify(note));
            setText(note.note[0].TEXT);
        });
    }, [onlyOnce]);

    return (
        <div className="container_ui">
            <div className="grid-container">
              <div className="grid-item1"><ReactQuill them="snow" onChange={handleChange} value={text}/></div>
              <div className="grid-item2">Senast sparat {lastSaved}</div>
              <div className="grid-item3"><button onClick={spara}>Spara</button></div>
            </div>
        </div>
    );
}

export default App;
