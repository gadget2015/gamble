import React from 'react';
import { Menu } from '@material-ui/core';
import { MenuItem } from '@material-ui/core';
import { Button } from '@material-ui/core';
import './MyMenu.css';

function MyMenu() {
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

  return (
    <div className="MenuStyle">
     <img src="menu.png" onClick={handleClick} aria-controls="simple-menu" aria-haspopup="true"/>
     <Menu id="simple-menu" anchorEl={anchorEl} keepMounted open={Boolean(anchorEl)} onClose={handleClose}>
             <MenuItem onClick={handleClose}>Hem</MenuItem>
             <MenuItem onClick={handleClose}>Mitt tipssaldo</MenuItem>
             <MenuItem onClick={handleClose}>Tipsbolagen</MenuItem>
             <MenuItem onClick={handleClose}>Tipsprogram</MenuItem>
             <MenuItem onClick={handleClose}>Administration</MenuItem>
             <MenuItem onClick={handleLogin}>Login</MenuItem>
     </Menu>
    </div>
  );
}

export { MyMenu };