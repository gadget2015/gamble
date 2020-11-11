import React from 'react';
import {useState} from 'react';
import './App.css';
import {Intro} from './components/Intro';
import {MyMenu} from './components/MyMenu';
import {Tipsprogram} from './components/Tipsprogram';
import {Tipsbolag} from './components/Tipsbolag';
import {Tipssaldo} from './components/Tipssaldo';

function App() {
    const [showIntro, setShowIntro] = useState(true);
    const [showTipsprogram, setShowTipsprogram] = useState(false);
    const [showTipsbolag, setShowTipsbolag] = useState(false);
    const [showTipssaldo, setShowTipssaldo] = useState(false);

    let content;

    if (showIntro === true) {
        content = (<Intro/>);
    } else if (showTipsprogram === true) {
        content = (<Tipsprogram/>);
    } else if( showTipsbolag === true) {
        content = (<Tipsbolag/>);
    } else if( showTipssaldo === true) {
        content = (<Tipssaldo/>);
    }



  return (
    <div>
        <MyMenu setShowIntro={setShowIntro} setShowTipsprogram={setShowTipsprogram} setShowTipsbolag={setShowTipsbolag} setShowTipssaldo={setShowTipssaldo}/>

        <div className="content">
            {content}
        </div>
    </div>
  );
}

export default App;
