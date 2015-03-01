package org.robert.tips.util;

import org.robert.tips.maltips.types.MaltipsGame;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.exceptions.GeneralApplicationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.HashSet;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;

/**
 * Try to find a M�ltips R-system R-23 with an iterativ method.
 * Med detta menas att man tar ett R-23 och sedan plockar man bort
 * en rad i R-23 systemet f�r att se om man fortfarande
 * f�r samma # garanti. Om det inte g�r bra att plocka bort en rad
 * s� l�gger man tillbaka den och plockar bort n�sta rad.
 * @author Robert Georen.
 */
public class FindMaltipsR23Iterativ extends AbstractIterative
{
    private static final String R_SYSTEM = "R-23-160.txt";
    
    /** 
     * Anger mappningsfilen f�r r-systemet.
     */
    private static final String R_SYSTEM_STATISTICS = "R-23-160.txt.statistics";    
    
    /**
     * Create a new instance of this finder.
     */
    public FindMaltipsR23Iterativ()
    {
        try
        {
            // initiera
            ArrayList rsystem = loadRSystem( R_SYSTEM );
            numberOfGamesInSystem = rsystem.size();            
            testSystem = new int[ numberOfGamesInSystem ];
            testSystem = loadStatisticSystem( R_SYSTEM_STATISTICS );
            
            mathimaticalSystemLength = 490314;
            systemN = 23;
            
            System.out.println( "Start searching for r-system:" + numberOfGamesInSystem );
            startSearch();
            
            System.out.println( "Sytem found!" );
        }
        catch ( GeneralApplicationException e )
        {
            e.printStackTrace();
        }
    }
   
    /**
     * Entry point for this system finder.
     */
    public static void main( String[] args )
    {
        new FindMaltipsR23Iterativ();    
    }
}
