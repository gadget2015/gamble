import React, {useState, useEffect} from 'react';
import './App.css';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

function App() {

    const [text, setText] = useState();
    let lastSaved = '2020-06-22 12:00:00T234';

    const windowUrl = window.location.search;
    const params = new URLSearchParams(windowUrl);

    function handleChange(text) {
        setText(text);
    }

    function spara(event) {
        alert(text);
    }

    useEffect( () =>  {
        const noteid = params.get('noteid');
        console.log('Use Effect, load noteid ' + noteid);
        }
    );

    return (
        <div className="container_ui">
            <div className="grid-container">
              <div className="grid-item1"><ReactQuill them="snow" onChange={handleChange}/></div>
              <div className="grid-item2">Senast sparat {lastSaved}</div>
              <div className="grid-item3"><button onClick={spara}>Spara</button></div>
            </div>
        </div>
    );
}

export default App;
