package org.robert.tips.util;

import org.robert.tips.maltips.types.MaltipsGame;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.types.GambleApplicationConstants;
import org.robert.tips.exceptions.GeneralApplicationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.HashSet;
import java.net.URL;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;

/**
 * Abstract class used to subclass for using when creating statistics
 * for a system.
 * @author Robert Georen.
 */
abstract class StatisticsSystem implements MaltipsConstants,
                                         GambleApplicationConstants
{
    protected static MaltipsGame[] mathimaticalSystem;      // The mathimatical system, e.g unreduced system.
    private static final String OUT_FILE_EXTENSION = ".statistics";
    protected int[] systemMapping;      // contains the extended system mapped into the mathimatical system.
    
   /**
     * Create the mathimatical system. This implies to create all possible
     * combination for the given system type.
     * @param n The size of the system, it's the n part in n over r algorithm.
     */
    protected void createMathimaticalSystem( int n )
    {
        Combination mathimaticalCombinations = new Combination( n, NUMBER_GAMES_IN_MALTIPS );
        mathimaticalCombinations.createCombinations();
        
        ArrayList tmp = mathimaticalCombinations.getCombinations();
        int size = tmp.size();
        mathimaticalSystem = new MaltipsGame[ size ];
        
        for ( int i = 0; i < size; i++ )
        {
            int[] row = ( int[] ) tmp.get( i );
            
            MaltipsGame game = new MaltipsGame( row );
            // game contains rows that are defined as 0,1,2 ... osv. Fix it
            // so it's 1,2,3 ... osv, e.g add +1 to each row.
            int[] tmp2 = new int[ NUMBER_GAMES_IN_MALTIPS ];
            int[] gameValue = game.getMaltipsRow();
            
            for ( int j = 0; j < NUMBER_GAMES_IN_MALTIPS; j++ )
            {
                tmp2[ j ] = gameValue[ j ] + 1;
            }
            
            mathimaticalSystem[ i ] = new MaltipsGame( tmp2 );
        }
    }     

    /**
     * Load rystem from disk.
     * @param resourceName The resource name.
     */
    protected ArrayList loadRSystem( String resourceName ) throws GeneralApplicationException
    {
        URL resourceURL = null;
        ArrayList loadedSystem = new ArrayList();
                    
        try
        {
            String resource = RESOURCES_PATH + resourceName;
            
            ClassLoader cl = this.getClass().getClassLoader(); 
            resourceURL = cl.getResource( resource ); 
            InputStream is = resourceURL.openStream();
            
            InputStreamReader isr = new InputStreamReader( is );
            LineNumberReader lnr = new LineNumberReader( isr );            
            
            // read all line in file
            String currentLine = null;
            
            while( ( currentLine = lnr.readLine() ) != null )
            {
                // each line contains a string like this:
                // '1,3,4,5,6,7,8,11'
                StringTokenizer tokenizer = new StringTokenizer( currentLine, "," );
                int numberOfTokens = tokenizer.countTokens();
                int[] row = new int[ numberOfTokens ];
                
                for ( int i = 0; i < numberOfTokens; i++ )
                {
                    String token = tokenizer.nextToken();
                    int value = Integer.parseInt( token );
                    row[ i ] = value;
                }
                
                MaltipsGame game = new MaltipsGame( row );
                loadedSystem.add( game );
            }
            
            lnr.close();       
            
            return loadedSystem;
        }
        catch ( IOException e )
        {
            throw new GeneralApplicationException( "Resource not found: " + resourceURL + "," + e.getMessage() );    
        }        
    } 
    
    /**
     * Write container to disk.
     * @param iterator An iterator to the container.
     */
    protected void writeArrayToDisk( int[] data, String fileName )
    {
        try
        {
            File outFile = new File( RESOURCES_PATH + fileName + OUT_FILE_EXTENSION );
            FileWriter fw = new FileWriter( outFile );
            PrintWriter pw = new PrintWriter( fw );
            
            int length = data.length;
            
            for ( int i = 0; i < length; i++ )
            {
                pw.println( data[ i ] );                
            }
            
            pw.close();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Map the given system to the positions in the mathimatical system.
     * @param system Container with the system.
     * @param systemN The n in n over r.
     */
    protected void mapSystemToMathimaticalSystem( ArrayList system, int systemN )
    {
        System.out.println( "Creating mathimatical system ..." );
        createMathimaticalSystem( systemN ); // create mathimatical system.
        System.out.println( "Mathimatical system created." );
        
        int size = system.size();
        systemMapping = new int[ size ];        
        
        for ( int i = 0; i < size; i++ )
        {
            MaltipsGame game = ( MaltipsGame ) system.get( i );
            int pos = numberInCollection( mathimaticalSystem, game );
            
            systemMapping[ i ] = pos;
        }        
    }
    
    /**
     * Check which row the given MaltipsGame have in the given collection.
     * The collection contains real maltips rows, e.g { 1,2,3,4,5,6,7,8 ...osv }.
     * @param mathimaticalSystem The mathimatical system.
     * @param game The maltips game check if it's in the container.
     * @return int Position in the collection, -1= not in collection.
     */
    protected int numberInCollection( MaltipsGame[] mathimaticalSystem, MaltipsGame game )
    {
        int length = mathimaticalSystem.length;
        
        for ( int i = 0; i < length; i++ )
        {
            MaltipsGame row = mathimaticalSystem[ i ];
            
            if ( row.equals( game ) )
            {
                return i;
            }
        }
        
        // not in contianer.
        return -1;           
    }
        
}