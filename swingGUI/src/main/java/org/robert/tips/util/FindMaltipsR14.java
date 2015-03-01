package org.robert.tips.util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.HashSet;

/**
 * Try to find a Måltips R-system R-14.
 * Det är interpolerat till att bestå av 137 rader för 6 rätts garanti.
 * @author Robert Georen.
 */
public class FindMaltipsR14 extends AbstractFindMaltips
{
 
    
    /**
     * Create a new instance of this finder.
     */
    public FindMaltipsR14()
    {
        numberOfGamesInSystem = 393;
        mathimaticalSystemLength = 3003;
        systemN = 14; 
        showIntervall = 5000;
        
        System.out.println( "Start searching for r-system:" + numberOfGamesInSystem );

        startSearch();
        
        System.out.println( "Sytem found!" );
    }
  
    /**
     * Entry point for this system finder.
     */
    public static void main( String[] args )
    {
        new FindMaltipsR14();    
    }
}
