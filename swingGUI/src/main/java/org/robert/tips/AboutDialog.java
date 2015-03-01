package org.robert.tips;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * This is the about dialog.
 * @author Robert Siwerz.
 */
public class AboutDialog extends JPanel 
{
  
  public AboutDialog() 
  {
    JLabel jLabel1 = new JLabel( "Gamble Application, version 1.6 (2010-10-03)" );
    JLabel jLabel2 = new JLabel( "Robert Georen Siwerz" );
    JLabel jLabel3 = new JLabel( "Copyright (c), 2003" );
    setLayout( new GridLayout( 3,1 ) );
    setBorder( new EtchedBorder() );
    add( jLabel1 );
    add( jLabel2 );
    add( jLabel3 );
  }
}

 