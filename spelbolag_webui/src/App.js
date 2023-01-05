import React from 'react';
import {useState} from 'react';
import './App.css';
import {Intro} from './components/Intro';
import {MyMenu, loginCredentialResponse} from './components/MyMenu';
import {Tipsprogram} from './components/Tipsprogram';
import {Tipsbolag} from './components/Tipsbolag';
import {Tipssaldo} from './components/Tipssaldo';
import {Administration} from './components/Administration';

// Make function available outside react application, used by authentication.
window.loginCredentialResponse = loginCredentialResponse;

function App() {
    const [showIntro, setShowIntro] = useState(true);
    const [showTipsprogram, setShowTipsprogram] = useState(false);
    const [showTipsbolag, setShowTipsbolag] = useState(false);
    const [showTipssaldo, setShowTipssaldo] = useState(false);
    const [showAdministration, setShowAdministration] = useState(false);

    let content;

    if (showIntro === true) {
        content = (<Intro/>);
    } else if (showTipsprogram === true) {
        content = (<Tipsprogram/>);
    } else if( showTipsbolag === true) {
        content = (<Tipsbolag/>);
    } else if( showTipssaldo === true) {
        content = (<Tipssaldo/>);
    } else if( showAdministration === true) {
        content = (<Administration/>);
    }



  return (
    <div>
        <MyMenu setShowIntro={setShowIntro} setShowTipsprogram={setShowTipsprogram} setShowTipsbolag={setShowTipsbolag} setShowTipssaldo={setShowTipssaldo} setShowAdministration={setShowAdministration}/>

        <div className="content">
            {content}
        </div>
    </div>
  );
}

export default App;
