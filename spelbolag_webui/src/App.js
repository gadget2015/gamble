import React from 'react';
import {useState} from 'react';
import './App.css';
import {Intro} from './components/Intro';
import {MyMenu} from './components/MyMenu';
import {Tipsprogram} from './components/Tipsprogram';
import {Tipsbolag} from './components/Tipsbolag';

function App() {
    const [showIntro, setShowIntro] = useState(true);
    const [showTipsprogram, setShowTipsprogram] = useState(false);
    const [showTipsbolag, setShowTipsbolag] = useState(false);

    let content;

    if (showIntro === true) {
        content = (<Intro/>);
    } else if (showTipsprogram === true) {
        content = (<Tipsprogram/>);
    } else if( showTipsbolag === true) {
        content = (<Tipsbolag/>);
    }



  return (
    <div>
        <MyMenu setShowIntro={setShowIntro} setShowTipsprogram={setShowTipsprogram} setShowTipsbolag={setShowTipsbolag}/>

        <div className="content">
            {content}
        </div>
    </div>
  );
}

export default App;
