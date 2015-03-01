package org.robert.tips.stryktips.ui.desktop.combination.singlesystem;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.types.TipsButton;

/**
 * This is where the one row is defined.
 * It's this row that for example 'Afontbladet' or 'Vi Tippar' put together.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class ButtonController extends JPanel implements StryktipsConstants,
                                                     ChangeListener
 {
    private StryktipsDocument stryktipsDocument;
    private TipsButton[] stryktipsButtons = new TipsButton[ NUMBER_OF_GAMES * NUMBER_OF_GAMEOPTIONS ];
    
    public ButtonController( StryktipsDocument stryktipsDocument )
    {
        this.stryktipsDocument = stryktipsDocument;
        setLayout( new GridLayout( NUMBER_OF_GAMES, NUMBER_OF_GAMEOPTIONS ) );
        SingleSystemButtonControllerHandler singleSystemButtonControllerHandler = new SingleSystemButtonControllerHandler( stryktipsDocument );
        
        for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
        {
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS ] = new TipsButton( GAMEVALUE_ONE, i );
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS ].addActionListener( singleSystemButtonControllerHandler );
            add( stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS ] );
            
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 1 ] = new TipsButton( GAMEVALUE_TIE, i );
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 1 ].addActionListener( singleSystemButtonControllerHandler );
            add( stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 1 ] );
            
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 2] = new TipsButton( GAMEVALUE_TWO, i );
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 2].addActionListener( singleSystemButtonControllerHandler );
            add( stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 2] );
        }
        
        stryktipsDocument.addChangeListener( this );
    }
    
    
    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged( ChangeEvent event )
    {
        // Update view with extended system games selected in the Single row system.
        
        for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
        {
            char value = stryktipsDocument.getStryktipsSystem().getCombination().getSingleRow( i );
            
            if ( value == GAMEVALUE_ONE )
            {
                stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS ].setState( true );
            }
            else
            {
                stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS ].setState( false );
            }
            
            if ( value == GAMEVALUE_TIE )
            {
                stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 1 ].setState( true );
            }
            else
            {
                stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 1 ].setState( false );
            }
            
            if ( value == GAMEVALUE_TWO )
            {
                stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 2 ].setState( true );
            }  
            else
            {
                stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 2 ].setState( false );
            }
        }
    }        
 }