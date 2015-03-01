package org.robert.tips.maltips.domain.model.reduce;

import java.util.ArrayList;
import java.util.Iterator;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.util.Combination;

/**
 * Utility class used to create single system combination systems.
 * patterns: Singleton
 * @author Robert Siwerz.
 */
class SingleSystemCombinationReducerUtility implements StryktipsConstants
{
    private static SingleSystemCombinationReducerUtility utility;
    
    /**
     * Create an instance of this utility class.
     */
    private SingleSystemCombinationReducerUtility()
    {
    }
    
    /**
     * Get the only instance of this utility class.
     * @return SingleRowCombinationReducerUtility The utility class.
     */
    public static SingleSystemCombinationReducerUtility newInstance()
    {
        if ( utility == null )
        {
            utility = new SingleSystemCombinationReducerUtility();
        }
        
        return utility;
    }
        
    /**
     * Create all possible combination from the single system. This means to
     * create all possible combination between min and max rights in the single
     * system. 
     *<pre>
     * SUM( ( numberOfGames! ) / ( ( numberOfGames - r ) * r! ) )
     * Where r = min to max.
     * </pre>
     * @param numberOfGames Number of games to select from.
     * @param min The minimum number of rights in the single system.
     * @param max The maximum number of rights in the single system.
     * @return ArrayList Container wiht int[] data reflecting the postions in the single system.
     */
    public ArrayList createCombinations( int numberOfGames, int min, int max )
    {
        ArrayList allCombinations = new ArrayList();
        
        for ( int i = min; i <= max; i++ )
        {
            Combination combination = new Combination( numberOfGames, i );
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
     * Check if the given row is valid compared to the combinations.
     * @param reducedGameRow Current reduced system row.
     * @param combinations ArrayList of all combinations.
     * @param singleSystem The single row system.
     */
    public boolean isCurrentReducedRowValid( int[] reducedGameRow, ArrayList combinations, int[] singleSystem )
    {
        boolean valid = false;

        Iterator iterator = combinations.iterator();

        // iterate over all combinations. If the reduced row match one
        // combination, the reduced row is valid.
        while ( iterator.hasNext() )
        {
            int[] currentCombination = ( int[] ) iterator.next();
            
            // check so that all combination games are in the reduced row.
            boolean currentValid = checkForAllCombinationInSingleSystem( reducedGameRow, currentCombination, singleSystem );
            
            // check so that the other games set in the single system, that aren't
            // in the combination, aren't in the reduced row.
            if ( currentValid )
            {
                currentValid = checkForSingleSystemNotInReducedRow( reducedGameRow, currentCombination, singleSystem );
                
                if ( currentValid )
                {
                    // The reduced row is valid againts the current combination, so
                    // the reduced row is valid.
                    return true;    
                }
            }
        }
        
        // the reduced row isn't valid againts any combination.
        return valid;
    } 
    
    /**
     * Check so that all combination games are in the reduced row.
     * @param reducedGameRow Current reduced system row.
     * @param combination int[] The combination.
     * @param singleSystem The single row system.     
     */
    private boolean checkForAllCombinationInSingleSystem( int[] reducedGameRow, int[] combination, int[] singleSystem )
    {
        boolean valid = true;
        int combinationLength = combination.length;
        
        // loop over all combinations and check so they are in the current game row
        for ( int i = 0; i < combinationLength; i++ )
        {
            int combinationValue = singleSystem[ combination[ i ] ];
            
            // Check to that the combination game is in the reduced row.
            boolean found = searchForValue( combinationValue, reducedGameRow );
                       
            if ( !found )
            {
                // combination isn't in the reduced row, return false
                return false;
            }
        }
        
        return valid;
    }
    
    /**
     * Check so that the other games set in the single system, that aren't
     * in the combination, aren't in the reduced row.    
     * @param reducedGameRow Current reduced system row.
     * @param combination int[] The combination point to posistions in the single system.
     * @param singleSystem The single row system.     
     */
    private boolean checkForSingleSystemNotInReducedRow( int[] reducedGameRow, int[] combination, int[] singleSystem )
    {
        boolean valid = true;
        
        // loop over all games in the single system
        int singleSystemLength = singleSystem.length;
        
        for ( int i = 0; i < singleSystemLength; i++ )
        {
            // chech so that the single system game isn't in the combination.
            int singleSystemValue = singleSystem[ i ];
            int[] mappedCombination = getMappedCombinationToSingleSystem( combination, singleSystem );
            boolean found = searchForValue( singleSystemValue, mappedCombination );            

            // check so that the single system game isn't in the reduced game row.
            if ( !found )
            {
                found = searchForValue( singleSystemValue, reducedGameRow ); 
                
                if ( found )
                {
                    // single system value is in the reduced row, invalid.
                    valid = false;
                    break;
                }
            }
            else
            {
                // single system value is in the combination, this is ok. This
                // already searched for.
   
            }
        }    
        
        return valid;
    } 
    
    /**
     * Search for the given value in the given array of values. A helper method.
     * @param valueToSearchFor The value to search for.
     * @param values The values to search in.
     * @return boolean true=value is found in the given values.
     */
    private boolean searchForValue( int valueToSearchFor, int[] values )
    {
        int valuesLength = values.length;
        boolean found = false;
        
        for ( int i = 0; i < valuesLength; i++ )
        {
            if ( valueToSearchFor == values[ i ] )
            {
                found = true;
                break;
            }
        }
        
        return found;
    }
    
    /**
     * Map the combination to the values in the single system. A helper method.
     * @param combination The combination.
     * @param singleSystem The single system.
     * @return int[] Mapped array.
     */
    private int[] getMappedCombinationToSingleSystem( int[] combination, int[] singleSystem )
    {
        int combinationLength = combination.length;
        int[] mappedCombination = new int[ combinationLength ];
        
        for ( int i = 0; i < combinationLength; i++ )
        {
            mappedCombination[ i ] = singleSystem[ combination[ i ] ];
        }
        
        return mappedCombination;
    }
}
