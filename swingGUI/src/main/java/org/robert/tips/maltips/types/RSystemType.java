package org.robert.tips.maltips.types;

import java.util.StringTokenizer;

/**
 * Defines a R system type for the maltips game type.
 * @author Robert Siwerz.
 */
public class RSystemType
{
    /**
     * R-system type. The type consists of two parts. R-x-y. 
     * Where:
     * x = number of games selected.
     * y = number of rows this system type consists of.
     */
     private String systemType; 
     
     private int x;
     private int y;
    
    /**
     * Create a new instance of this class.
     * @param systemType The system type as plain text.
     */
    public RSystemType( String systemType )
    {
        this.systemType = systemType;    
        
        if ( systemType.length() != 0 )
        {
            // R-x-y. calculate the x and y.
            StringTokenizer st = new StringTokenizer( systemType, "-" );
            
            String token = st.nextToken();
            token = st.nextToken();
            x = Integer.parseInt( token );
            token = st.nextToken();
            y = Integer.parseInt( token );
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
     * Get number of games in this system.
     * @return int Number of games.
     */
    public int getNumberOfGamesInSystem()
    {
        return x;
    }
    
    /**
     * Get number of system rows this system consists of.
     */
    public int getNumberOfSystemRows()
    {
        return y;
    }
}
