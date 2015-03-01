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
 * Abstract class used to subclass for using when creating extended system.
 * @author Robert Georen.
 */
abstract class ExtendedSystem implements MaltipsConstants,
                                         GambleApplicationConstants
{
    protected static MaltipsGame[] mathimaticalSystem;      // The mathimatical system, e.g unreduced system.
    
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
            mathimaticalSystem[ i ] = game;
        }
    }     
    
    /**
     * Create an extended system. Add given number to each of the rows int
     * the mathimatical system.
     * @param extendedNumber The number to add.
     * @return ArrayList Container with the maltips games in.
     */
    protected ArrayList createExtendedSystem( int extendedNumber )
    {
        ArrayList extendedSystem = new ArrayList();
               
        // loop over all rows in the mathimatical system
        int mathimaticalSystemLength = mathimaticalSystem.length;
        
        for ( int i = 0; i < mathimaticalSystemLength; i++ )
        {
            // get current row
            MaltipsGame game = mathimaticalSystem[ i ];
            int[] currentRow = game.getMaltipsRow();
            
            // create a new system with one extra games in, e.g number 14.
            int[] newRow = new int[ NUMBER_GAMES_IN_MALTIPS + 1 ];
            
            for ( int j = 0; j < NUMBER_GAMES_IN_MALTIPS; j++ )
            {
                newRow[ j ] = currentRow[ j ];
            }
            
            newRow[ NUMBER_GAMES_IN_MALTIPS ] = extendedNumber; // this is the 15:th selected game.  
            
            
            // create combination for the extended row
            Combination extendedRow = new Combination( ( NUMBER_GAMES_IN_MALTIPS + 1 ), NUMBER_GAMES_IN_MALTIPS );
            extendedRow.createCombinations();        
            
            // map combinations to extended system.
            ArrayList tmp = extendedRow.getCombinations();
            int size = tmp.size();
            
            // loop over all combinations, should be 9 loops.
            for ( int j = 0; j < size; j++ )
            {
                int[] row = ( int[] ) tmp.get( j );
                int[] tmpExtendedRow = new int [ NUMBER_GAMES_IN_MALTIPS ];
                
                for ( int k = 0; k < NUMBER_GAMES_IN_MALTIPS; k++ )
                {
                    // The mapping.
                    tmpExtendedRow[ k ] = newRow[ row[ k ] ];
                }
                
                MaltipsGame newGame = new MaltipsGame( tmpExtendedRow );
                boolean inCollection = isInCollection( extendedSystem.iterator(), newGame );
                
                if ( !inCollection )
                {
                    extendedSystem.add( newGame );
                }
            } 
        }
        
        return extendedSystem;
    }
    
    /**
     * Check if row already is in collection.   
     * @param iterator Iterator to the container
     * @param game The maltips game check if it's in the container.
     * @return boolean true = Maltips game is in container.
     */
    private boolean isInCollection( Iterator iterator, MaltipsGame game )
    {
        while ( iterator.hasNext() )
        {
            MaltipsGame row = ( MaltipsGame ) iterator.next();
            
            if ( row.equals( game ) )
            {
                return true;
            }
        }
        
        // not in contianer.
        return false;           
    }

    /**
     * Load rystem from disk.
     * @param resourceName The resource name.
     * @return ArrayList containing MaltipsGame objects.
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
}