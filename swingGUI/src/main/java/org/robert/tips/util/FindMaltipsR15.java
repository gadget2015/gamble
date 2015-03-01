package org.robert.tips.util;

/**
 * Try to find a Måltips R-system R-15.
 * Det är interpolerat till att bestå av 321 rader.
 * @author Robert Georen.
 */
public class FindMaltipsR15 extends AbstractFindMaltips
{
 
    
    /**
     * Create a new instance of this finder.
     */
    public FindMaltipsR15()
    {
        numberOfGamesInSystem = 700;
        mathimaticalSystemLength = 6435;
        systemN = 15; 
        
        System.out.println( "Start searching for r-system:" + numberOfGamesInSystem );

        startSearch();
        
        System.out.println( "Sytem found!" );
    }
    
    /**
     * Entry point for this system finder.
     */
    public static void main( String[] args )
    {
        new FindMaltipsR15();    
    }
}
