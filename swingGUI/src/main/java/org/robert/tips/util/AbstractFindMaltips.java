package org.robert.tips.util;

import org.robert.tips.maltips.types.MaltipsGame;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.types.GambleApplicationConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.HashSet;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
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
 * This is the superclass for a applications that are used
 * to find R-system on M�ltipset.
 * @author Robert Georen.
 */
abstract class AbstractFindMaltips implements MaltipsConstants,
                                              GambleApplicationConstants
{
    protected static MaltipsGame[] mathimaticalSystem;      // The mathimatical system, e.g unreduced system.
    protected static int maxTestingResult;  // indicates how near the testsystem is.
    protected int[] testSystem;   // The current reduced system to test. Points to rows in the mathimatical system.
    protected int numberOfGamesInSystem;    // Number of r system rows.
    protected int mathimaticalSystemLength; // Number of possible combination for the system.
    protected int systemN;     // Defines the n over r.
    protected int showIntervall = 10;  // override this to change show intervall, e.g the intervall for showing output on screen.
    Random random = new Random( System.currentTimeMillis() );
    private static final int MIN_RIGHTS = 5;    // minimum number of rights in system.
    
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
        mathimaticalSystem = new MaltipsGame[ mathimaticalSystemLength ];
        
        for ( int i = 0; i < size; i++ )
        {
            int[] row = ( int[] ) tmp.get( i );
            
            MaltipsGame game = new MaltipsGame( row );
            mathimaticalSystem[ i ] = game;
        }
    }    

    /**
     * Get a random generated test r-system. The test r-system is constructed by
     * generating random numbers between 0 - mathimaticalSystemLength. The test 
     * r-system consists of numberOfGamesInSystem random numbers.
     */
    protected void getRandomSystem()
    {
        HashSet hashSet = new HashSet( numberOfGamesInSystem - 1);
        int size = hashSet.size();
        
        
        // create random number
        while ( size < numberOfGamesInSystem )
        {
            int number = random.nextInt( mathimaticalSystemLength );
            Integer value = new Integer( number );
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
     * Check the given system. The check is looping over all rows in the
     * mathimatical system and check each of the rows in the mathimatical
     * system so each row gets at least MIN_RIGHTS rights within the testSystem.
     * @return boolean true=Test system is OK! otherwise false.
     */
    protected boolean checkReducedSystem()
    {
        boolean valid = true;
        int numberOfRights = 0;
        int i = 0; 
        
        for ( i = 0; i < mathimaticalSystemLength; i++ )
        {
            numberOfRights = getNumberOfRights( i );
            
            if ( numberOfRights < MIN_RIGHTS )
            {
                // reduced system gives less than MIN_RIGHTS rights. The testSystem isn't
                // good enought.
                maxTestingResult = ( maxTestingResult < i )? i: maxTestingResult;
                return false;
            }             
        }        
        
        System.out.println( "numberOfRights=" + numberOfRights + ",i=" + i );
        
        return valid;
    }
    
    /**
     * Get the number of rights the current row gives in the test system.
     * This implies to loop over all rows in the test system and calculate
     * have many rights each test system row gives against the current row.
     * If the currentRow gives at least 7 rights in the testsystem, the method
     * returns with that number, e.g a 7.
     * @param currentRow Array with games for the current row.
     */
    protected int getNumberOfRights( int currentRow )
    {
        int max = 0;
        
        for ( int i = 0; i < numberOfGamesInSystem; i++ )
        {     
            int rights = 0;

            // loop over all games in the current row
            for ( int j = 0; j < NUMBER_GAMES_IN_MALTIPS; j++ )
            {
                // loop over all games in the test system row.
                for ( int k = 0; k < NUMBER_GAMES_IN_MALTIPS; k++ )
                {
                    if ( mathimaticalSystem[ currentRow ].getMaltipsRow()[ j ] == mathimaticalSystem[ testSystem[ i ] ].getMaltipsRow()[ k ] )    
                    {
                        rights++;
                        break; // go to j-loop.
                    }
                }
                
                if ( j - rights == ( NUMBER_GAMES_IN_MALTIPS - MIN_RIGHTS ) )
                {
                    // row in testSystem gives less than 7 rights.
                    // Not neccesary to continue to calculate how many rights
                    // this rows gives, the row is invalid anyway for the system.
                    break;
                }                
            }
            
            max = ( max < rights ) ? rights: max;
            
            if ( max == MIN_RIGHTS )
            {
                return max; // system ok!
            }
        }
        
        return max;
    }

    /**
     * Write the test system to disk, e.g 'foundfile.txt'.
     */
    protected void writeTestSystemToDisk()
    {
        try
        {           
            File foundFile = new File( "foundfile.txt" );
            FileOutputStream fos = new FileOutputStream( foundFile );
            PrintWriter pw = new PrintWriter( fos );
            for ( int i = 0; i < numberOfGamesInSystem; i++ )
            {
                MaltipsGame game = mathimaticalSystem[ testSystem[ i ] ];
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
     * Start searching for the R-system.
     */
    public void startSearch()
    {
        testSystem = new int[ numberOfGamesInSystem ];
        createMathimaticalSystem( systemN );
        
        boolean valid = false;
        int numberOfTestedSystem = 0;
        long time = System.currentTimeMillis();
        
        while ( !valid )
        {
            getRandomSystem();
            valid = checkReducedSystem();
            numberOfTestedSystem++;
            
            if ( numberOfTestedSystem%showIntervall == 0 )
            {
                System.out.println( numberOfTestedSystem +", maxTestingResult=" + maxTestingResult +
                                    ",time=" + ( System.currentTimeMillis() -time ) );
                time = System.currentTimeMillis();
            }
            
            if ( valid )
            {
                try
                {
                    System.out.println( "SYSTEM FOUND" );                
                    File foundFile = new File( "foundfile.txt" );
                    FileOutputStream fos = new FileOutputStream( foundFile );
                    PrintWriter pw = new PrintWriter( fos );
                    for ( int i = 0; i < numberOfGamesInSystem; i++ )
                    {
                        MaltipsGame game = mathimaticalSystem[ testSystem[ i ] ];
                        pw.println( game.toString() );
                    }
                    
                    pw.close();
                }
                catch ( Exception e )
                {
                    e.printStackTrace();
                }
            }
            
        }        
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