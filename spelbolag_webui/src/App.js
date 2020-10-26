import React from 'react';
import {useState} from 'react';
import './App.css';
import {Intro} from './components/Intro';
import {MyMenu} from './components/MyMenu';
import {Tipsprogram} from './components/Tipsprogram';

function App() {
    const [showIntro, setShowIntro] = useState(true);
    const [showTipsprogram, setShowTipsprogram] = useState(false);

    const myF = function hello(data) {
        setShowIntro(data);
        console.log('hello anropas');
    };


    let content;

    if (showIntro === true) {
        content = (<Intro/>);
    } else if (showTipsprogram === true) {
        content = (<Tipsprogram/>);
    }



  return (
    <div>
        <MyMenu setShowIntro={setShowIntro} setShowTipsprogram={setShowTipsprogram}/>

        <div className="content">
            {content}
        </div>
    </div>
  );
}

export default App;
