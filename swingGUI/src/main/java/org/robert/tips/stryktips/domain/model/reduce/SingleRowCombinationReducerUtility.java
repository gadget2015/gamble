package org.robert.tips.stryktips.domain.model.reduce;

import java.util.ArrayList;
import java.util.Iterator;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.util.Combination;

/**
 * Utility class used to create single row combination systems.
 * patterns: Singleton
 * @author Robert Siwerz.
 */
public class SingleRowCombinationReducerUtility implements StryktipsConstants
{
    private static SingleRowCombinationReducerUtility utility;
    
    /**
     * Create an instance of this utility class.
     */
    private SingleRowCombinationReducerUtility()
    {
    }
    
    /**
     * Get the only instance of this utility class.
     * @return SingleRowCombinationReducerUtility The utility class.
     */
    public static SingleRowCombinationReducerUtility newInstance()
    {
        if ( utility == null )
        {
            utility = new SingleRowCombinationReducerUtility();
        }
        
        return utility;
    }
        
    /**
     * Create all possible combination from the single row system. This means to
     * create all possible combination between min and max rights in the single row
     * system. 
     *<pre>
     * SUM( ( NUMBER_OF_GAMES! ) / ( ( NUMBER_OF_GAMES - r ) * r! ) )
     * Where r = min to max.
     * </pre>
     * @param min The minimum number of rights in the single row system.
     * @param max The maximum number of rights in the single row system.
     * @return ArrayList Container wiht int[] data reflecting the postions in the single row system.
     */
    public ArrayList createCombinations( int min, int max )
    {
        ArrayList allCombinations = new ArrayList();
        
        for ( int i = min; i <= max; i++ )
        {
            Combination combination = new Combination( NUMBER_OF_GAMES, i );
            combination.createCombinations();
            ArrayList combinations = combination.getCombinations();
            
            Iterator iterator = combinations.iterator();
            
            while ( iterator.hasNext() )
            {
                int[] row = ( int[] ) iterator.next();
                allCombinations.add( row );
            }
        }
        
        return allCombinations;
    }

    /**
     * Check if the given row is valid in the combinations.
     * @param currentGameRow Current reduced system row.
     * @param combinations ArrayList of all combinations.
     * @param singleRowSystem The single row system.
     */
    public boolean isCurrentReducedRowValid( char[] currentGameRow, ArrayList combinations, char[] singleRowSystem )
    {
        boolean valid = false;
        Iterator iterator = combinations.iterator();

        // iterate over all combinations.
        while ( iterator.hasNext() )
        {
            int[] currentCombination = ( int[] ) iterator.next();
            int combinationLength = currentCombination.length;
            boolean rowValidForThisCombination = true;
            
            // check so that the combination from the single row system
            // is in the reduced row.
            for ( int i = 0; i < combinationLength; i++ )
            {
                int singleRowNumber = currentCombination[ i ];
                char singleRowOption = singleRowSystem[ singleRowNumber ];
                
                if ( currentGameRow[ singleRowNumber ] != singleRowOption )
                {
                    // the reduced game row doesn't fulfill the combination.
                    rowValidForThisCombination = false;
                    break;
                }
            }
            
            // don't do the following check if row already have failed
            if ( rowValidForThisCombination )
            {
                // check so that the rows that not are in the combination rows isn't 
                // the same as in the single row system.
                for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
                {     
                    char currentGameOption = currentGameRow[ i ];
                    
                    // check if current row position isn't a row in the combination
                    boolean found = false;
                    
                    for ( int j = 0; j < combinationLength; j++ )
                    { 
                        int singleRowNumber = currentCombination[ j ];
                        
                        if ( i == singleRowNumber )
                        { 
                            found = true;   // currentGameOption shall not be compared
                        }
                    }

                    if ( !found )
                    {
                        // check so that the current row haven't the same game option as 
                        // in the single row system                        
                        char singleRowOption = singleRowSystem[ i ];
                        
                        if ( currentGameOption == singleRowOption )
                        {
                            // the reduced game have a gameoption that are the same in
                            // the single row system, but isn't in the combination.
                            rowValidForThisCombination = false;
                            break;
                        }
                    }            
                }
            }
            
            if ( rowValidForThisCombination )
            {
                valid = true;
                break;
            }
        }
        
        return valid;
    }          
}
