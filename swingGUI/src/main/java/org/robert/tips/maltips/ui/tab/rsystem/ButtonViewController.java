package org.robert.tips.maltips.ui.tab.rsystem;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import org.robert.tips.maltips.MaltipsDocument;
import org.robert.tips.types.TipsButton;
import org.robert.tips.maltips.types.MaltipsConstants;

/**
 * This is where the R-system are defined.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class ButtonViewController extends JPanel implements ChangeListener,
                                                     MaltipsConstants
 {
    private MaltipsDocument maltipsDocument;
    private TipsButton[] maltipsButtons = new TipsButton[ NUMBER_OF_GAMES ];    
    
    public ButtonViewController( MaltipsDocument maltipsDocument )
    {        
        this.maltipsDocument = maltipsDocument;
        setLayout( new GridLayout( NUMBER_OF_ROWS, NUMBER_OF_COLUMNS ) );
        RSystemButtonViewControllerHandler rSystemButtonViewControllerHandler = new RSystemButtonViewControllerHandler( maltipsDocument );
        
        for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
        {
            maltipsButtons[ i ] = new TipsButton( ( i + 1 ) );
            maltipsButtons[ i ].addActionListener( rSystemButtonViewControllerHandler );
            add( maltipsButtons[ i ] );
        }
        
        maltipsDocument.addChangeListener( this ); 
    }
    
    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged( ChangeEvent event )
    {
        // Update the view with data from the single system.
        
        for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
        {
            int value = maltipsDocument.getMaltipsSystem().getRsystem().getRSystemRow( i );
            
            if ( value != 0 )
            {
                maltipsButtons[ i ].setState( true );
            }
        }
    }    
 }