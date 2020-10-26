import React from 'react';
import { Menu } from '@material-ui/core';
import { MenuItem } from '@material-ui/core';
import { Button } from '@material-ui/core';
import './MyMenu.css';

function MyMenu(props) {
  const [anchorEl, setAnchorEl] = React.useState(null);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleLogin = () => {
    setAnchorEl(null);
  };

  const handleTipsprogramClick = () => {
    setAnchorEl(null);
    props.setShowTipsprogram(true);
    props.setShowIntro(false);
  }

  const handleHemClick = () => {
        setAnchorEl(null);
        props.setShowIntro(true);
  }

  return (
    <div className="MenuStyle">
     <img src="menu.png" onClick={handleClick} aria-controls="simple-menu" aria-haspopup="true" alt="Meny"/>
     <Menu id="simple-menu" anchorEl={anchorEl} keepMounted open={Boolean(anchorEl)} onClose={handleClose}>
             <MenuItem onClick={handleHemClick}>Hem</MenuItem>
             <MenuItem onClick={handleClose}>Mitt tipssaldo</MenuItem>
             <MenuItem onClick={handleClose}>Tipsbolagen</MenuItem>
             <MenuItem onClick={handleTipsprogramClick}>Tipsprogram</MenuItem>
             <MenuItem onClick={handleClose}>Administration</MenuItem>
             <MenuItem onClick={handleLogin}>Login</MenuItem>
     </Menu>
    </div>
  );
}

export { MyMenu };