import React from 'react';
import './Intro.css';

function Intro() {
  return (
    <div className="row">
      <div className="column">
     Sajten för dig som är med i ett spelbolag
     Denna hemsida är till för dig som är med i ett spelbolag eller administrerar ett spelbolag.<br/><br/>

     När man är med i ett spelbolag så behövs vissa saker göras vilket denna sajt hjälper till med. Följande grundläggande funktioner finns här:<br/>
     <ul>
     <li>Se efter om man betalt in tillräcklig till sitt tipskonto.</li>
     <li>Verifiera att den senaste inbetalningen kommit in på sitt tipskonto.</li>
     <li>Se lite information om tipsbolaget du är med i, vad ni spelat på och eventuella vinster.</li>
    </ul>
        <br/><br/><br/>Är det något som inte fungerar som det borde, maila mig: robert.georen@gmail.com.
        </div>
        <div className="column">
            <img src="intro_bild.png" alt="Så glada blir vi!"/>
        </div>
        <i>version 1.0 (2020)</i>
    </div>
  );
}

export { Intro };
