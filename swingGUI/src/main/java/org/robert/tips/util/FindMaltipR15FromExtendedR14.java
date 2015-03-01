package org.robert.tips.util;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.types.GambleApplicationConstants;
import org.robert.tips.maltips.types.MaltipsGame;
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
 * Try to find a M�ltips R-system R-15. It uses a R-14 rsystem with an extend
 * game, #14. It's interpolated to consists of 321 rows.
 * @author Robert Georen.
 */
public class FindMaltipR15FromExtendedR14 extends AbstractFindMaltips implements GambleApplicationConstants
{
    private static final String EXTENDED_R14_RESOURCE = "R14_extended.txt"; 
    private static final String R14_RESOURCE = "R-14-138.txt"; 
    private int[] extendedMapping;      // contains the extended system mapped into the mathimatical system.
    private int[] rsystemMapping;       // contains the R system mapped into the mathimatical system.
    private ArrayList rSystem;          // contains the R system.
    
    /**
     * Create a new instance of this finder.
     */
    public FindMaltipR15FromExtendedR14()
    {
        numberOfGamesInSystem = 700;
        mathimaticalSystemLength = 6435;
        systemN = 15; 
        showIntervall = 1000;
        
        System.out.println( "Start searching for r-system:" + numberOfGamesInSystem );
        System.out.println( "Initialize ... " );
        ArrayList extendedR14System = loadExtendedR14System();
        rSystem = loadR14System();
        mapRSystemToMathimaticalSystem( rSystem, systemN );
        
        mapExtendedSystemToMathimaticalSystem( extendedR14System, systemN );
        System.out.println( "Initialize done." );
        
        System.out.println( "Start seacrhing ... " );
        startSearch();
        
        System.out.println( "Sytem found!" );
    }
    
    /**
     * Load extended R-14 system from disk.
     * @return ArrayList Container with the load extended rsystem, contains MaltipsGame.
     */
    private ArrayList loadExtendedR14System()
    {
        URL resourceURL = null;
        ArrayList loadedSystem = new ArrayList();
                    
        try
        {
            String resource = RESOURCES_PATH + EXTENDED_R14_RESOURCE;
            
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
            System.out.println( "Resource not found: " + resourceURL + "," + e.getMessage() );    
            e.printStackTrace();
            throw new RuntimeException( e.getMessage() );
        }             
    }
    
    /**
     * Map the loaded extended system to the positions in the mathimatical system.
     * @param extendedSystem Container with the extended system.
     * @param systemN The n in n over r.
     */
    private void mapExtendedSystemToMathimaticalSystem( ArrayList extendedSystem, int systemN )
    {
        createMathimaticalSystem( systemN ); // create mathimatical system.
        
        int size = extendedSystem.size();
        extendedMapping = new int[ size ];        
        
        for ( int i = 0; i < size; i++ )
        {
            MaltipsGame extendedGame = ( MaltipsGame ) extendedSystem.get( i );
            int pos = numberInCollection( mathimaticalSystem, extendedGame );
            
            extendedMapping[ i ] = pos;
        }        
    }
    
    /**
     * Check which row the given MaltipsGame have in the given collection,
     * @param mathimaticalSystem The mathimatical system.
     * @param game The maltips game check if it's in the container.
     * @return int Position in the collection, -1= not in collection.
     */
    private int numberInCollection( MaltipsGame[] mathimaticalSystem, MaltipsGame game )
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
    
    /**
     * Overloaded method. First always use the R14 system, then get random
     * numbers from the extended system.
     */
    protected void getRandomSystem()
    {
        HashSet hashSet = new HashSet( numberOfGamesInSystem - 1);
        int size = hashSet.size();
        
        // First add the R14 system to the test system
        int rsystemLength = rsystemMapping.length;
        
        for ( int i = 0; i < rsystemLength; i++ )
        {
            Integer value = new Integer( rsystemMapping[ i ] );
            hashSet.add( value );            
        }
        
        
        Random random = new Random( System.currentTimeMillis() );
        int mappingLength = extendedMapping.length;
        
        // create random number
        while ( size < numberOfGamesInSystem )
        {
            int number = random.nextInt( mappingLength );
            Integer value = new Integer( extendedMapping[ number ] );
            hashSet.add( value );
            size = hashSet.size();
        }
        
        // fix up generated container
        Iterator iterator = hashSet.iterator();
        int pos = 0;
        
        while ( iterator.hasNext() )
        {
            int value = ( ( Integer ) iterator.next() ).intValue();
            testSystem[ pos++ ] = value;
        }
    }

    /**
     * Load R-14 system from disk.
     * @return ArrayList Container with the load extended rsystem, contains MaltipsGame.
     */
    private ArrayList loadR14System()
    {
        URL resourceURL = null;
        ArrayList loadedSystem = new ArrayList();
                    
        try
        {
            String resource = RESOURCES_PATH + R14_RESOURCE;
            
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
            System.out.println( "Resource not found: " + resourceURL + "," + e.getMessage() );    
            e.printStackTrace();
            throw new RuntimeException( e.getMessage() );
        }             
    }

    /**
     * Map the loaded R system to the positions in the extended system.
     * @param extendedSystem Container with the extended system.
     * @param systemN The n in n over r.
     */
    private void mapRSystemToMathimaticalSystem( ArrayList rSystem, int systemN )
    {
        createMathimaticalSystem( systemN ); // create mathimatical system.
        
        int size = rSystem.size();
        rsystemMapping = new int[ size ];        
        
        for ( int i = 0; i < size; i++ )
        {
            MaltipsGame extendedGame = ( MaltipsGame ) rSystem.get( i );
            int pos = numberInCollection( mathimaticalSystem, extendedGame );
            
            rsystemMapping[ i ] = pos;
        }        
    }
    
    /**
     * Entry point for this system finder.
     */
    public static void main( String[] args )
    {
        new FindMaltipR15FromExtendedR14();    
    }
}
