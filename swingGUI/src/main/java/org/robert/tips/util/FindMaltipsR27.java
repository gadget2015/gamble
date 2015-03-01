package org.robert.tips.util;

/**
 * Try to find a Måltips R-system R-27.
 * Det år interpolerat till att bestå av 400 rader. MIN_RIGHTS=5.
 * @author Robert Georen.
 */
public class FindMaltipsR27 extends AbstractFindMaltips
{    
    public FindMaltipsR27() 
    {
        numberOfGamesInSystem = 450;
        mathimaticalSystemLength = 2220075;
        systemN = 27; 
        
        System.out.println( "Start searching for r-system:" + numberOfGamesInSystem );

        startSearch();
        
        System.out.println( "System found!" );
    }
    
    /**
     * Entry point for this system finder.
     */
    public static void main( String[] args )
    {
        new FindMaltipsR27();    
    }    
}
