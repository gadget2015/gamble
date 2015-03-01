package org.robert.tips.util;

import org.robert.tips.maltips.types.MaltipsGame;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.exceptions.GeneralApplicationException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Create statistics for the R16 reduced system.
 * @author Robert Georen.
 */
public class CreateStatisticsForR16ReducedSystem extends StatisticsSystem 
                                        
{
    private static final String RESOURCE_FILENAME = "R-16-1250_statistics.txt";
    
    /**
     * Creates a new instance of this class.
     */
    public CreateStatisticsForR16ReducedSystem()
    {   
        try
        {
            System.out.println( "Begin creating statistics" );
            ArrayList rsystem = loadRSystem( RESOURCE_FILENAME );

            mapSystemToMathimaticalSystem( rsystem, 16 );
            writeArrayToDisk( systemMapping, RESOURCE_FILENAME );
            System.out.println( "End creating statstics" );
        }
        catch ( GeneralApplicationException e )
        {
            e.printStackTrace();
        }
    }        
    
    /**
     * Entry point.
     */
    public static void main( String[] args )
    {
        new CreateStatisticsForR16ReducedSystem();   
    }
}