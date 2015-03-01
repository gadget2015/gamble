package org.robert.tips.util;

/**
 * Try to find a Måltips R-system R-24.
 * Det är interpolerat till att bestå av 1500 rader. MIN_RIGHTS=5.
 * @author Robert Georen.
 */
public class FindMaltipsR24 extends AbstractFindMaltips
{
 
    
    /**
     * Create a new instance of this finder.
     */
    public FindMaltipsR24()
    {
        numberOfGamesInSystem = 2500;
        mathimaticalSystemLength = 735471;
        systemN = 24; 
        
        System.out.println( "Start searching for r-system:" + numberOfGamesInSystem );

        startSearch();
        
        System.out.println( "Sytem found!" );
    }
    
    /**
     * Entry point for this system finder.
     */
    public static void main( String[] args )
    {
        new FindMaltipsR24();    
    }
}
