package org.robert.tips.util;

/**
 * Try to find a Måltips R-system R-15.
 * Det år interpolerat till att bestå av 321 rader.
 * @author Robert Georen.
 */
public class FindMaltipsR18 extends AbstractFindMaltips
{
 
    
    /**
     * Create a new instance of this finder.
     */
    public FindMaltipsR18()
    {
        numberOfGamesInSystem = 4000;
        mathimaticalSystemLength = 43758;
        systemN = 18; 
        
        System.out.println( "Start searching for r-system:" + numberOfGamesInSystem );

        startSearch();
        
        System.out.println( "Sytem find!" );
    }
    
    /**
     * Entry point for this system finder.
     */
    public static void main( String[] args )
    {
        new FindMaltipsR18();    
    }
}
