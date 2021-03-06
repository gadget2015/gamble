import React from 'react';
import {useState} from 'react';
import {useEffect} from 'react';
import { Menu } from '@material-ui/core';
import { MenuItem } from '@material-ui/core';
import './MyMenu.css';
import { OAuth2ImplicitFlow } from './OAuth2ImplicitFlow';

function MyMenu(props) {
  const [anchorEl, setAnchorEl] = useState(null);
  const [inloggad, setInloggad] = useState(false);
  const [username, setUsername] = useState('');
  //const [access_token_by_robert, setAccess_token_by_robert] = useState();

  const loginService = new OAuth2ImplicitFlow(setInloggad, setUsername);
  loginService.handleClientLoad();  // Förbereder inloggning mot Google med Oauth2 Implicit Flow.

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    resetMenuChooice();
  };

  const handleLogin = () => {
    resetMenuChooice();
    loginService.login();
  };

  const handleLogout = () => {
    resetMenuChooice();
    loginService.logout();
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

    // Hanterar menyvalet login & logout.
    let authenticationContent;

    if (inloggad === false) {
        authenticationContent = (<MenuItem onClick={handleLogin}>Login</MenuItem>);
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

    // Visar mitt tipssaldo efter lyckad inloggning, vilket triggas genom att username sätts.
    useEffect( () => {
       const getCookie = function (cname) {
          var name = cname + "=";
          var decodedCookie = decodeURIComponent(document.cookie);
          var ca = decodedCookie.split(';');
          for(var i = 0; i <ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) === ' ') {
              c = c.substring(1);
            }
            if (c.indexOf(name) === 0) {
              return c.substring(name.length, c.length);
            }
          }
          return "";
        }

        console.log('useEffect[username]: username = ' + username + '.');
        console.log('useEffect[username]: access_token_by_robert = ' + getCookie('access_token_by_robert'));

        if (username !== '') {
            // Visar mittsaldo sidan om man är inloggad och har fått tillbaka username ifrån Google.
            console.log('useEffect[username]: visar mittsaldo sidan.');
            handleVisaTipssaldo();
        } else {
            console.log('useEffect[username]: usename är tomt.');
        }
    }, [username]);

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

export { MyMenu };