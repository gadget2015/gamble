package org.robert.tips.maltips.types;

import java.util.StringTokenizer;

/**
 * Defines a maltips game with 8 games in.
 * @author Robert Siwerz.
 */
public class MaltipsGame implements MaltipsConstants
{
    private int[] maltipsRow;
    
    /**
     * Creates a maltips game instance.
     * @param maltipsRow The maltips game.
     */
    public MaltipsGame( int[] maltipsRow )
    {
        this.maltipsRow = maltipsRow;
    }
    
    /**
     * Creates a maltips game instance.
     * @param game The maltips game as text, the same as when calling this.toString().
     */
    public MaltipsGame( String game )
    {
        StringTokenizer st = new StringTokenizer( game, "," );
        maltipsRow = new int[ NUMBER_GAMES_IN_MALTIPS ];
        int pos = 0;
        
        while ( st.hasMoreTokens() )
        {
            String tmpValue = st.nextToken();
            int value = Integer.parseInt( tmpValue );
            maltipsRow[ pos++ ] = value;
        }
    }
    
    /**
     * Get the maltips row.
     * @return int[] The maltips row.
     */
    public int[] getMaltipsRow()
    {
        return maltipsRow;
    }
    
    /**
     * Compare this object to the given object.
     * @param o The object to compare with.
     * @return boolean true = object is equal to the given one.
     */
    public boolean equals( Object o )
    {
        if ( o instanceof MaltipsGame )
        {
            MaltipsGame maltipsGame = ( MaltipsGame ) o;  
            int[] toCompareWith = maltipsGame.getMaltipsRow();
            
            for ( int i = 0; i < NUMBER_GAMES_IN_MALTIPS; i++ )
            {
                if ( toCompareWith[ i ] != maltipsRow[ i ] )
                {
                    return false;   // object is not equal.
                }
            }
            
            return true;    // object is equal.
        }
        else
        {
            return false;   // object is not equal.
        }
    }
    
    
    /**
     * Get the debug string.
     * @return String The debug string.
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        
        for ( int i = 0; i < NUMBER_GAMES_IN_MALTIPS; i++ )
        {
            sb.append( String.valueOf( maltipsRow[ i ] ) + "," );
        }
        
        return sb.substring( 0, ( sb.length() - 1 ) );
    }
}