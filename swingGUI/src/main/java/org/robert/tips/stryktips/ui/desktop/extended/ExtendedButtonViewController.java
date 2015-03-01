package org.robert.tips.stryktips.ui.desktop.extended;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.types.TipsButton;

/**
 * This is where the extended system are defined.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class ExtendedButtonViewController extends JPanel implements StryktipsConstants,
                                                     ChangeListener
 {
    private StryktipsDocument stryktipsDocument;
    private TipsButton[] stryktipsButtons = new TipsButton[ NUMBER_OF_GAMES * NUMBER_OF_GAMEOPTIONS ];
    
    
    public ExtendedButtonViewController( StryktipsDocument stryktipsDocument )
    {
        this.stryktipsDocument = stryktipsDocument;
        setLayout( new GridLayout( NUMBER_OF_GAMES, NUMBER_OF_GAMEOPTIONS ) );
        ExtendedSystemButtonViewControllerHandler extendedSystemButtonViewControllerHandler = new ExtendedSystemButtonViewControllerHandler( stryktipsDocument );
        
        for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
        {
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS ] = new TipsButton( GAMEVALUE_ONE, i );
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS ].addActionListener( extendedSystemButtonViewControllerHandler );
            add( stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS ] );
            
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 1 ] = new TipsButton( GAMEVALUE_TIE, i );
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 1 ].addActionListener( extendedSystemButtonViewControllerHandler );
            add( stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 1 ] );
            
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 2] = new TipsButton( GAMEVALUE_TWO, i );
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 2].addActionListener( extendedSystemButtonViewControllerHandler );
            add( stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 2 ] );
        }
        
        stryktipsDocument.addChangeListener( this );    
    }
    
    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged( ChangeEvent event )
    {
        // Update the view with data from the extended system.
        
        for ( int i = 0; i < ( NUMBER_OF_GAMEOPTIONS * NUMBER_OF_GAMES ); i++ )
        {
            char value = stryktipsDocument.getStryktipsSystem().getExtended().getExtendedRow( i );
            
            if ( value != 0 )
            {
                stryktipsButtons[ i ].setState( true );
            }
        }
    }    
 }