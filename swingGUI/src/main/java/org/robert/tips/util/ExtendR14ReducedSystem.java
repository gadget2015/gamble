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
 * Extend R14 reduced system with an extra game, e.g number 14.
 * @author Robert Georen.
 */
public class ExtendR14ReducedSystem extends ExtendedSystem implements GambleApplicationConstants
{
    private static final String RESOURCE_FILENAME = "R-14-138_findconverted.txt";
    private static final String OUT_EXTENDE_FILE = "R14_extended.txt";
    
    /**
     * Creates a new instance of this class.
     */
    public ExtendR14ReducedSystem()
    {   
        try
        {
            ArrayList rsystem = loadRSystem();
            int size = rsystem.size();
            mathimaticalSystem = new MaltipsGame[ size ];
            
            for ( int i = 0; i < size; i++ )
            {
                mathimaticalSystem[ i ] = ( MaltipsGame ) rsystem.get( i );
            }
            
            System.out.println( "size before=" + mathimaticalSystem.length );
            ArrayList extendedSystem = createExtendedSystem( 14 );
            writeContainer( extendedSystem.iterator() );
            System.out.println( "size after=" + extendedSystem.size() );
        }
        catch ( GeneralApplicationException e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Load rystem from disk.
     */
    private ArrayList loadRSystem() throws GeneralApplicationException
    {
        URL resourceURL = null;
        ArrayList loadedSystem = new ArrayList();
                    
        try
        {
            String resource = RESOURCES_PATH + RESOURCE_FILENAME;
            
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
    private void writeContainer( Iterator iterator )
    {
        try
        {
            File outFile = new File( RESOURCES_PATH + OUT_EXTENDE_FILE );
            FileWriter fw = new FileWriter( outFile );
            PrintWriter pw = new PrintWriter( fw );
            
            while( iterator.hasNext() )
            {
                MaltipsGame game = ( MaltipsGame ) iterator.next();  
                pw.println( game.toString() );                
            }
            
            pw.close();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Entry point.
     */
    public static void main( String[] args )
    {
        new ExtendR14ReducedSystem();   
    }
}