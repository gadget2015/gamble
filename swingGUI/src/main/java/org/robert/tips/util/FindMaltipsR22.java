package org.robert.tips.util;

/**
 * Try to find a Måltips R-system R-15.
 * Det är interpolerat till att bestå av 321 rader.
 * @author Robert Georen.
 */
public class FindMaltipsR22 extends AbstractFindMaltips
{
 
    
    /**
     * Create a new instance of this finder.
     */
    public FindMaltipsR22()
    {
        numberOfGamesInSystem = 1250;
        mathimaticalSystemLength = 319770;
        systemN = 22; 
        
        System.out.println( "Start searching for r-system:" + numberOfGamesInSystem );

        startSearch();
        
        System.out.println( "Sytem found!" );
    }
    
    /**
     * Entry point for this system finder.
     */
    public static void main( String[] args )
    {
        new FindMaltipsR22();    
    }
}
