package org.robert.tips.stryktips.ui.desktop.odds.oddsinput;

import javax.swing.JPanel;
import java.awt.GridLayout;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.types.StryktipsOddsField;

/**
 * This is where the odds are defined.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class OddsInputController extends JPanel implements StryktipsConstants                                                
 {
    private StryktipsDocument stryktipsDocument;
    private StryktipsOddsField[] stryktipsOddsField = new StryktipsOddsField[ NUMBER_OF_GAMES * NUMBER_OF_GAMEOPTIONS ];
    
    
    public OddsInputController( StryktipsDocument stryktipsDocument )
    {
        this.stryktipsDocument = stryktipsDocument;
        setLayout( new GridLayout( NUMBER_OF_GAMES, NUMBER_OF_GAMEOPTIONS ) );
        
        for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
        {
            // one
            stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS ] = new StryktipsOddsField( i * NUMBER_OF_GAMEOPTIONS  );
            OddsInputControllerHandler oneFieldHandler = new OddsInputControllerHandler( stryktipsDocument, stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS ] );
            stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS ].setText( Float.toString( stryktipsDocument.getStryktipsSystem().getOdds().getOddsSystem( i * NUMBER_OF_GAMEOPTIONS ) ) );
            stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS ].getDocument().addDocumentListener( oneFieldHandler );
            stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS ].addFocusListener( new OddsInputFocusListener( stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS ] ) );
            add( stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS ] );
            
            // tie
            stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS + 1 ] = new StryktipsOddsField( i * NUMBER_OF_GAMEOPTIONS + 1 );
            OddsInputControllerHandler tieFieldHandler = new OddsInputControllerHandler( stryktipsDocument, stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS + 1 ] );
            stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS + 1 ].setText( Float.toString( stryktipsDocument.getStryktipsSystem().getOdds().getOddsSystem( i * NUMBER_OF_GAMEOPTIONS + 1 ) ) );
            stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS + 1 ].getDocument().addDocumentListener( tieFieldHandler );
            stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS + 1 ].addFocusListener( new OddsInputFocusListener( stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS + 1 ] ) );
            add( stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS + 1 ] );
            
            // two
            stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS + 2 ] = new StryktipsOddsField( i * NUMBER_OF_GAMEOPTIONS + 2 );
            OddsInputControllerHandler twoFieldHandler = new OddsInputControllerHandler( stryktipsDocument, stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS + 2 ] );
            stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS + 2 ].setText( Float.toString( stryktipsDocument.getStryktipsSystem().getOdds().getOddsSystem( i * NUMBER_OF_GAMEOPTIONS + 2 ) ) );
            stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS + 2 ].getDocument().addDocumentListener( twoFieldHandler );
            stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS + 2 ].addFocusListener( new OddsInputFocusListener( stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS + 2 ] ) );
            add( stryktipsOddsField[ i * NUMBER_OF_GAMEOPTIONS + 2 ] );
        }
    }           
 }