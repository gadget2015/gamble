package org.robert.tips.stryktips.domain.model.reduce;

import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.types.StryktipsGame;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.types.ReducerIF;
import java.util.ArrayList;
import java.util.Iterator;
import org.robert.tips.stryktips.domain.model.Mathimatical;
import org.robert.tips.stryktips.domain.model.StryktipsSystem;
import org.robert.tips.stryktips.exceptions.GameNotSetException;

/**
 * This class is used to create a mathimatical system with 
 * the game set.This is a utility class.
 * @author Robert Siwerz.
 */
public class MathimaticalSystemReducer implements StryktipsErrorMessages,
                                                  ErrorMessages,
                                                  StryktipsConstants,
                                                  ReducerIF
{
    private StryktipsSystem stryktipsSystem;
    
    /**
     * Create an instance of this class.
     * @param stryktipsDocument The stryktips model.
     */
    public MathimaticalSystemReducer( StryktipsSystem stryktipsSystem )
    {
        this.stryktipsSystem = stryktipsSystem;
    }
    
    /**
     * Create a mathimatical system from the Mathimatical document model.
     * <pre>
     * Flow:
     * 1. Check so that all rows are set in the mathimatical system.
     * 3. Create the mathimatical system.
     * </pre>
     * @throws GeneralApplicationException Major error.
     */
    public void reduce() throws GameNotSetException
    {
        // check
        checkMathimaticalSystemSet();
        
        // create mathimatical system.
        ArrayList rows = stryktipsSystem.getMathimatical().createsSingleRowsFromMathimaticalSystem();
        
        // fix up mathimatical system
        ArrayList mathimaticalSingleRows = new ArrayList();        
        Iterator iterator = rows.iterator();
        
        while ( iterator.hasNext() )
        {
            char[] row = ( char[] ) iterator.next();
            StryktipsGame game = new StryktipsGame( row );
            mathimaticalSingleRows.add( game );
        }    
        
        // Set the mathimatical rows in the document
        stryktipsSystem.setReducedSystem( mathimaticalSingleRows );
    }
    
    /**
     * Check so that all rows are set in the mathimatical system.
     * @throws GeneralApplicationException Major error.     
     */
    private boolean checkMathimaticalSystemSet() throws GameNotSetException
    {
        // Check so that there are game options set for all rows.
        boolean valid = true;
        
        for ( int i = 0; i < NUMBER_OF_GAMES; i++ ) // iterates over all rows.
        {
            char one = stryktipsSystem.getMathimatical().getMathimaticalRow( i * NUMBER_OF_GAMEOPTIONS );
            char tie = stryktipsSystem.getMathimatical().getMathimaticalRow( i * NUMBER_OF_GAMEOPTIONS + 1 );
            char two = stryktipsSystem.getMathimatical().getMathimaticalRow( i * NUMBER_OF_GAMEOPTIONS + 2 );
            
            if ( one == 0 && tie == 0 && two == 0)
            {
                valid = false;
                break;
            }
        }
        
        if ( !valid )
        {
           throw new GameNotSetException("There is no system to reduce.");
        }
        else
        {
           return true;
        }
    }
}

