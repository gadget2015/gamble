package org.robert.tips.util;

/**
 * Try to find a Måltips R-system R-25.
 * Det är interpolerat till att bestå av 400 rader. MIN_RIGHTS=5.
 * @author Robert Georen.
 */
public class FindMaltipsR25 extends AbstractFindMaltips
{    
    /** Creates a new instance of FindMaltipsR25 */
    public FindMaltipsR25() 
    {
        numberOfGamesInSystem = 250;
        mathimaticalSystemLength = 1081575;
        systemN = 25; 
        
        System.out.println( "Start searching for r-system:" + numberOfGamesInSystem );

        startSearch();
        
        System.out.println( "System found!" );
    }
    
    /**
     * Entry point for this system finder.
     */
    public static void main( String[] args )
    {
        new FindMaltipsR25();    
    }    
}
