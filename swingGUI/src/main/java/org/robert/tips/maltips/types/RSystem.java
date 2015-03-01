package org.robert.tips.maltips.types;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.PropertyResourceBundle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.types.GambleApplicationConstants;

/**
 * Defines data regarding a R-system used by the matlips game type.
 * @author Robert Siwerz.
 */
public class RSystem implements GambleApplicationConstants,
                                MaltipsConstants
{
    /**
     * Defines the r-system type for this instance.
     */
    private RSystemType systemType;

    /**
     * Contains the system definition. It's a container with MaltipsGame in.
     */
    private ArrayList rSystemDefinition;
    
    public RSystem(){}
    
    /**
     * Creates a new instance of this class.
     * @param systemType System type.
     */
    public RSystem( RSystemType systemType )
    {
        this.systemType = systemType;
    }

    /**
     * Load all maltips R systems from resource. Contains key=maltips definition filename.
     * @return PropertyResourceBundle Resource boundle with r-system definitions filenames in.
     */
    public PropertyResourceBundle loadRSystems() throws GeneralApplicationException
    {
        URL resourceURL = null;
        String resource = RESOURCES_PATH + MALTIPS_RSYSTEM_RESOURCENAME;

        try
        {
            ClassLoader cl = this.getClass().getClassLoader(); 
            resourceURL = cl.getResource( resource ); 
            InputStream is = resourceURL.openStream();
            
            PropertyResourceBundle resourceBoundle = new PropertyResourceBundle( is );    
            
            return resourceBoundle;
        }
        catch ( Throwable e )
        {
            File temp = new File("");
            String path = temp.getAbsolutePath();
            throw new GeneralApplicationException( "Resource not found: " + resourceURL + "," + path +resource + ": Error message=" +e.getMessage() );
        }
    }  

    /**
     * Get a R-system from disk. This method reads the system definition for this 
     * instance system type.
     */
    private void getRSystemFromResource() throws GeneralApplicationException
    {
        URL resourceURL = null;
        rSystemDefinition = new ArrayList();
        String fileName = null;
        
        try
        {
            // get r-system filename
            PropertyResourceBundle rsystems = loadRSystems();
            fileName = RESOURCES_PATH + rsystems.getString( systemType.getSystemType() );
            
            // load file
            ClassLoader cl = this.getClass().getClassLoader(); 
            resourceURL = cl.getResource( fileName ); 
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
                rSystemDefinition.add( game );
            }
            
            lnr.close();
        }
        catch ( Throwable e )
        {
            throw new GeneralApplicationException( "Resource not found (filename, resource URL, error message): " + fileName + "," + resourceURL + "," + e.getMessage() );    
        }
    }
    
    /**
     * Reduce the given original system with this R-system.
     * @param originalSystem The original system is a int[] containing 1-30.
     * @return ArrayList The reduced system.
     * @throws GeneralApplicationException Error while creating a reduced system.
     */
    public ArrayList createRSystem( int[] originalSystem ) throws GeneralApplicationException
    {
        ArrayList reducedSystem = new ArrayList();
        getRSystemFromResource();   // initialize system definition
        
        // iterate over all R-system rows and map them against the original system.
        Iterator iterator = rSystemDefinition.iterator();
        
        while ( iterator.hasNext() )
        {
            MaltipsGame definitionGame = ( MaltipsGame ) iterator.next();
            int[] maltipsDefinitionGame = definitionGame.getMaltipsRow();
            
            int[] reducedRow = new int[ NUMBER_GAMES_IN_MALTIPS ];
            
            for ( int i = 0; i < NUMBER_GAMES_IN_MALTIPS; i++ )
            {
                reducedRow[ i ] = originalSystem[ maltipsDefinitionGame[ i ] - 1 ];
            }
            
            MaltipsGame reducedGame = new MaltipsGame( reducedRow );
            reducedSystem.add( reducedGame );
        }        
        
        return reducedSystem;
    }    
}