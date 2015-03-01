package org.robert.tips.maltips.ui.tab.mathimatical;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import org.robert.tips.maltips.MaltipsDocument;
import org.robert.tips.types.TipsButton;
import org.robert.tips.maltips.types.MaltipsConstants;

/**
 * This is where the mathimatical system are defined.
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
        MathimaticalSystemButtonViewControllerHandler mathimaticalSystemButtonViewControllerHandler = new MathimaticalSystemButtonViewControllerHandler( maltipsDocument );
        
        for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
        {
            maltipsButtons[ i ] = new TipsButton( ( i + 1 ) );
            maltipsButtons[ i ].addActionListener( mathimaticalSystemButtonViewControllerHandler );
            add( maltipsButtons[ i ] );
        }
        
        maltipsDocument.addChangeListener( this ); 
    }
    
    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged( ChangeEvent event )
    {
        // Update the view with data from the mathimatical system.
        
        for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
        {
            int value = maltipsDocument.getMaltipsSystem().getMathimatical().getMathimaticalRow( i );
            
            if ( value != 0 )
            {
                maltipsButtons[ i ].setState( true );
            }
        }
    }    
 }