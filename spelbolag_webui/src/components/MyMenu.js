import React from 'react';
import {useState} from 'react';
import { Menu } from '@material-ui/core';
import { MenuItem } from '@material-ui/core';
import './MyMenu.css';
import { OAuth2ImplicitFlow } from './OAuth2ImplicitFlow';

function MyMenu(props) {
  const [anchorEl, setAnchorEl] = useState(null);
  const [inloggad, setInloggad] = useState(false);
  const [username, setUsername] = useState('');
  const loginService = new OAuth2ImplicitFlow(setInloggad, setUsername);
  loginService.handleClientLoad();  // Förbereder inloggning mot Google med Oauth2 Implicit Flow.

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleLogin = () => {
    setAnchorEl(null);
    loginService.login();
  };

  const handleLogout = () => {
    setAnchorEl(null);
    loginService.logout();
  }

  const handleTipsprogramClick = () => {
    setAnchorEl(null);
    props.setShowTipsprogram(true);
    props.setShowIntro(false);
  }

  const handleHemClick = () => {
        setAnchorEl(null);
        props.setShowIntro(true);
  }

    // Hanterar menyvalet login & logout.
    let authenticationContent;

    if (inloggad === false) {
        authenticationContent = (<MenuItem onClick={handleLogin}>Login</MenuItem>);
    } else {
        authenticationContent = (<MenuItem onClick={handleLogout}>Logout</MenuItem>);
    }

  return (
    <div className="MenuStyle row">
     <div className="column">
     <img src="menu.png" onClick={handleClick} aria-controls="simple-menu" aria-haspopup="true" alt="Meny"/>
     <Menu id="simple-menu" anchorEl={anchorEl} keepMounted open={Boolean(anchorEl)} onClose={handleClose}>
             <MenuItem onClick={handleHemClick}>Hem</MenuItem>
             <MenuItem onClick={handleClose}>Mitt tipssaldo</MenuItem>
             <MenuItem onClick={handleClose}>Tipsbolagen</MenuItem>
             <MenuItem onClick={handleTipsprogramClick}>Tipsprogram</MenuItem>
             <MenuItem onClick={handleClose}>Administration</MenuItem>
             {authenticationContent}
     </Menu>
     </div>
     <div className="column">
        <h1>Spelbolagsajten</h1>
        Inloggad som: {username}
     </div>
    </div>
  );
}

export { MyMenu };