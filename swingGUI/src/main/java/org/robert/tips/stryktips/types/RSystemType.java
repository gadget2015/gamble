package org.robert.tips.stryktips.types;

import java.util.StringTokenizer;

/**
 * Defines a R system type.
 * @author Robert Siwerz.
 */
public class RSystemType
{
    /**
     * R-system type. The type consists of three parts. R-x-y-z. 
     * Where:
     * x = number of full set game options 'helgarderingar'.
     * y = number of halv game set  options 'halvgarderingar'.
     * z = number of rows this system type consists of.
     */
     private String systemType; 
     
     private int x;
     private int y;
     private int z;
    
    /**
     * Create a new instance of this class.
     * @param systemType The system type.
     */
    public RSystemType( String systemType )
    {
        this.systemType = systemType;    
        
        if ( systemType.length() != 0 )
        {
            // R-x-y-z. calculate the x,y and z.
            StringTokenizer st = new StringTokenizer( systemType, "-" );
            
            String token = st.nextToken();
            token = st.nextToken();
            x = Integer.parseInt( token );
            token = st.nextToken();
            y = Integer.parseInt( token );
            token = st.nextToken();
            z = Integer.parseInt( token );
        }
    }
    
    /**
     * Get system type.
     * @return String System type.
     */
    public String getSystemType()
    {
        return systemType;
    }
    
    /**
     * Get number of bankers in this system.
     * @return int Number of bankers.
     */
    public int getNumberOfBankers()
    {
        return ( 13 - x - y );
    }
    
    /**
     * Get number of half set games in this system.
     * @return int Number of half set games.
     */
    public int getNumberOfHalfSetGames()
    {        
        return y;
    }
    
    /**
     * Get number of full set games in this system.
     * @return int Number of full set games.
     */
    public int getNumberofFullSetGames()
    {
        return x;    
    }
    
    /**
     * Get number of system rows this system consists of.
     */
    public int getNumberOfSystemRows()
    {
        return z;
    }
}
