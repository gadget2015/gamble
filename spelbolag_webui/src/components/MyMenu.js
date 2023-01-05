import React from 'react';
import {useState} from 'react';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import './MyMenu.css';
import jwt from 'jsonwebtoken';
import { setCookie, deleteCookie, getCookie } from './../cookieUtil';

var username;
var loggedInDone = false;   // Used to make sure that after login the dispatch to the tipssaldo page is only performed once.

function MyMenu(props) {
  const [anchorEl, setAnchorEl] = useState(null);
  const [eventAttached, setEventAttached] = useState(false);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    resetMenuChooice();
  };

  const handleLogout = () => {
    deleteCookie('access_token_by_robert');
    handleHemClick();
  }

  const handleTipsprogramClick = () => {
    resetMenuChooice();
    props.setShowTipsprogram(true);
  }

  const handleVisaTipssaldo = () => {
    resetMenuChooice();
    props.setShowTipssaldo(true);
  }

  const handleHemClick = () => {
        resetMenuChooice();
        props.setShowIntro(true);
  }

    const dispatchLogin = function(e){
        console.log('Fångar login event i React applikationen.');

        if (loggedInDone === false) {
            loggedInDone = true;
            handleVisaTipssaldo();
        }
    };

    // Only attach event listener once.
    if(eventAttached !== true) {
        console.log('add event listener');
        setEventAttached(true);
        window.addEventListener('loggedin', dispatchLogin);
    }

    // Hanterar menyvalet login & logout.
    let authenticationContent;
    var access_token = getCookie('access_token_by_robert');
    var inloggad = false;
    if (access_token !== '') {
        inloggad = true;
    }

    if (inloggad === false) {
        authenticationContent = (<div className="g_id_signin" data-type="standard"></div>);
    } else {
        authenticationContent = (<MenuItem onClick={handleLogout}>Logout</MenuItem>);
    }

  const handleVisaTipsbolagen = () => {
    resetMenuChooice();
    props.setShowTipsbolag(true);
  }



  const handleAdministrationClick = () => {
    resetMenuChooice();
    props.setShowAdministration(true);
  }

  const resetMenuChooice = () => {
    setAnchorEl(null);
    props.setShowTipsprogram(false);
    props.setShowIntro(false);
    props.setShowTipsbolag(false);
    props.setShowTipssaldo(false);
    props.setShowAdministration(false);
  }

  return (
    <div className="MenuStyle row">
     <div className="column-header1">
         <img src="./menu.png" onClick={handleClick} aria-controls="simple-menu" aria-haspopup="true" alt="Meny"/>
         <Menu id="simple-menu" anchorEl={anchorEl} keepMounted open={Boolean(anchorEl)} onClose={handleClose}>
                 <MenuItem onClick={handleHemClick}>Hem</MenuItem>
                 <MenuItem onClick={handleVisaTipssaldo}>Mitt tipssaldo</MenuItem>
                 <MenuItem onClick={handleVisaTipsbolagen}>Tipsbolagen</MenuItem>
                 <MenuItem onClick={handleTipsprogramClick}>Tipsprogram</MenuItem>
                 <MenuItem onClick={handleAdministrationClick}>Administration</MenuItem>
                 {authenticationContent}
         </Menu>
     </div>
     <div className="column-header2">
        <h1>Spelbolagsajten</h1>
        <i>Inloggad som: {username}</i>
     </div>
    </div>
  );
}

/**
* Anropas ifrån html-sidan direkt.
*/
function loginCredentialResponse(jwtn) {
        var data = jwt.decode(jwtn.credential);
        //console.log(JSON.stringify(jwtn));
        console.log('Inloggning performed well.');
        username = data.email;

        // Set cookie with access_token that the REST API should use to authenticate users request.
        setCookie('access_token_by_robert', jwtn.credential, 1);
        console.log('JWT cookie username =' + jwt.decode(getCookie('access_token_by_robert')).email);

        // Trigger event so that React application can update UI.
        const event = new Event('loggedin');
        window.dispatchEvent(event);
}

export { MyMenu, loginCredentialResponse };