package org.robert.tips.maltips.ui.dialogs.checkreducesystem;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.types.TipsButton;
import org.robert.tips.maltips.types.MaltipsConstants;

/**
 * This is the controller where the right row are defined.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class RightRowController extends JPanel implements MaltipsConstants
 {
    private CheckReducedSystemDocument checkReducedSystemDocument;
    private TipsButton[] maltipsButtons = new TipsButton[ NUMBER_OF_GAMES ];
     
    public RightRowController( CheckReducedSystemDocument checkReducedSystemDocument ) throws GeneralApplicationException
    {
        this.checkReducedSystemDocument = checkReducedSystemDocument;
        setLayout( new GridLayout( NUMBER_OF_ROWS, NUMBER_OF_COLUMNS ) );
        
        RightRowControllerHandler rightRowControllerHandler = new RightRowControllerHandler( checkReducedSystemDocument );
        
        for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
        {
            maltipsButtons[ i ] = new TipsButton( ( i + 1 ) );
            maltipsButtons[ i ].addActionListener( rightRowControllerHandler );
            add( maltipsButtons[ i ] );
        }
    }  
 }