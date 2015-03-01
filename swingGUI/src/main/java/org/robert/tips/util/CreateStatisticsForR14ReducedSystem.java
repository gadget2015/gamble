package org.robert.tips.util;

import org.robert.tips.maltips.types.MaltipsGame;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.exceptions.GeneralApplicationException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Create statistics for the R14 reduced system. Med detta menas att
 * ladda ett R-14 system och g� igenom alla raderna f�r att se vilker
 * radnummer som varje rad i R-system pekar p�. Dvs vilken rad som
 * t.ex systemraden { 1,2,3,4,5,6,7,14 } i R-systemet pekar p� i det
 * matimatiska systemet som best�r av 3003 rader. I detta fallet pekar
 * r-system raden p� rad #6 i det matimatiska systemet.
 * @author Robert Georen.
 */
public class CreateStatisticsForR14ReducedSystem extends StatisticsSystem 
                                        
{
    private static final String RESOURCE_FILENAME = "R-14-138.txt";
    
    /**
     * Creates a new instance of this class.
     */
    public CreateStatisticsForR14ReducedSystem()
    {   
        try
        {
            System.out.println( "Begin creating statistics" );
            ArrayList rsystem = loadRSystem( RESOURCE_FILENAME );

            mapSystemToMathimaticalSystem( rsystem, 14 );
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
        new CreateStatisticsForR14ReducedSystem();   
    }
}