package org.robert.tips.stryktips.types;

import org.robert.tips.stryktips.types.StryktipsConstants;
import java.util.Comparator;

/**
 * Comparator for a StryktipsGame object.
 * @author Robert Siwerz.
 */
public class StryktipsGameComparator implements Comparator,
                                                StryktipsConstants
{
    /**
     * Implemented method from interface.
     */
    public int compare(Object o1, Object o2)    
    {
        if ( o1 instanceof StryktipsGame && 
             o2 instanceof StryktipsGame )
        {
            StryktipsGame stryktipsGame1 = ( StryktipsGame ) o1;  
            StryktipsGame stryktipsGame2 = ( StryktipsGame ) o2;
            
            char[] game1 = stryktipsGame1.getSingleRow();
            char[] game2 = stryktipsGame2.getSingleRow();
            
            for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
            {
                if ( game1[ i ] != game2[ i ] )
                {
                    if ( (  game1[ i ] == GAMEVALUE_ONE ) ||
                         ( game1[ i ] == GAMEVALUE_TIE && game2[ i ] == GAMEVALUE_TWO ) )
                    {
                        //game1 < game2
                        return -1;   // object is not equal.
                    }
                    else
                    {
                        // game1 > game2
                        return 1;
                    }
                }
            }
            
            return 0;    // object is equal.
        }
        else
        {
            throw new RuntimeException( "Not compareable objects" );
        }
    }
}