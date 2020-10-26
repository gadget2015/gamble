import React from 'react';
import './App.css';
import {Intro} from './components/Intro';
import {MyMenu} from './components/MyMenu';

function App() {
  return (
    <div>
        <MyMenu/>
        <div class="content">
            <Intro/>
        </div>
    </div>
  );
}

export default App;
