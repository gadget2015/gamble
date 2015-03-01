package org.robert.tips.stryktips.ui.desktop.dialogs.checkreducedsystem;

import javax.swing.JPanel;
import java.awt.GridLayout;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.types.TipsButton;

/**
 * This is where the number of rights are defined from.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class RightRowController extends JPanel implements StryktipsTextMessages, 
                                                 StryktipsConstants
 {
    private CheckReducedSystemDocument checkReducedSystemDocument;
    private TipsButton[] stryktipsButtons = new TipsButton[ NUMBER_OF_GAMES * NUMBER_OF_GAMEOPTIONS ];
     
    public RightRowController( CheckReducedSystemDocument checkReducedSystemDocument ) throws GeneralApplicationException
    {
        this.checkReducedSystemDocument = checkReducedSystemDocument;
        setLayout( new GridLayout( NUMBER_OF_GAMES, NUMBER_OF_GAMEOPTIONS ) );
        
        RightRowControllerHandler rightRowControllerHandler = new RightRowControllerHandler( checkReducedSystemDocument );
        
        for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
        {
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS ] = new TipsButton( GAMEVALUE_ONE, i );
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS ].addActionListener( rightRowControllerHandler );
            add( stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS ] );
            
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 1 ] = new TipsButton( GAMEVALUE_TIE, i );
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 1 ].addActionListener( rightRowControllerHandler );
            add( stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 1 ] );
            
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 2] = new TipsButton( GAMEVALUE_TWO, i );
            stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 2].addActionListener( rightRowControllerHandler );
            add( stryktipsButtons[ i * NUMBER_OF_GAMEOPTIONS + 2] );
        }
    }  
 }