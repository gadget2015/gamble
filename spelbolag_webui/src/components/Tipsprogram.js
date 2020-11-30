import React from 'react';

function Tipsprogram() {
  return (
    <div>
    <h1>Stryktips och Måltips- program - Gamble Application</h1>
    <h2>Introduktion</h2><br/>
    Gamble Application är ett tipsprogram som ökar sannolikheten för att få in 13 rätt på stryktipset och Måltipset genom att använda smarta reducerade system. Efter att ett system gjorts i ordning så skapas en fil, som man sedan skickar upp till www.svenskaspel.se.
    <br/><br/>
    Systemkrav<br/>
    Programmet är ett Java program, vilket ger fördelar som plattformsoberoende och automatisk uppdatering av ny versioner. Systemkravet är dock att man har Java installerat på sin maskin. Den version av Java som krävas är Java2 ( version 1.5 eller högre ).
    <br/><br/>
    Starta Gamble application, Klicka <a href="gamble.jnlp">här</a>.<br/>
    <a href="gambleapplication.pdf">Manual</a>
    <br/><br/>
    Skärmdumpar på Gamble Application<br/>
    <img src="screenshot_1.gif" alt="Skärmbild på Stryktipset."/><br/>
    <img src="screenshot_2.gif" alt="Skärmbild på Stryktipset."/><br/>
    </div>
  );
}

export { Tipsprogram };