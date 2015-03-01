package org.robert.tips.util;

/**
 * Try to find a Måltips R-system R-21.
 * Det är interpolerat till att bestå av 800 rader.
 * @author Robert Georen.
 */
public class FindMaltipsR21 extends AbstractFindMaltips
{
 
    
    /**
     * Create a new instance of this finder.
     */
    public FindMaltipsR21()
    {
        numberOfGamesInSystem = 900;
        mathimaticalSystemLength = 203490;
        systemN = 21; 
        
        System.out.println( "Start searching for r-system:" + numberOfGamesInSystem );

        startSearch();
        
        System.out.println( "Sytem found!" );
    }
    
    /**
     * Entry point for this system finder.
     */
    public static void main( String[] args )
    {
        new FindMaltipsR21();    
    }
}
