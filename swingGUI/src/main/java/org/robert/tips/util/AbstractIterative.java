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
import java.util.LinkedList; 

/**
 * Abstract class som anv�nds f�r att skapa R-system genom en Iterativ metod.
 */
abstract class AbstractIterative implements MaltipsConstants,
                                            GambleApplicationConstants
{
    protected static MaltipsGame[] mathimaticalSystem;      // The mathimatical system, e.g unreduced system.
    protected int maxTestingResult;  // indicates how near the testsystem is.
    protected int[] testSystem;   // The current reduced system to test. Points to rows in the mathimatical system.
    protected int numberOfGamesInSystem;    // Number of r system rows.
    protected int mathimaticalSystemLength; // Number of possible combination for the system.
    protected int systemN;     // Defines the n-part for n over r.
    protected int showIntervall = 100;  // override this to change show intervall, e.g the intervall for showing output on screen.
    Random random = new Random( System.currentTimeMillis() );
    private static final int MIN_RIGHTS = 5;    // minimum number of rights in system.
    protected LinkedList removedSystemRows = new LinkedList();    // inneh�ller alla borttagna rader.
    protected int currentRemoveTestSystemRow; // senast borttagna raden.
    
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
    
    /**
     * Create the mathimatical system. This implies to create all possible
     * combination for the given system type.
     * @param n The size of the system, it's the n part in n over r algorithm.
     */
    protected void createMathimaticalSystem( int n )
    {
        System.out.println( "Create mathimatical system ..." );
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
        
        System.out.println( "Mathimatical system created." );
    }    

    /**
     * Check the test system. The check is looping over all rows in the
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
     * Get the number of rights the current row in mathimaticalsystem
     * gives in the test system.
     * This implies to loop over all rows in the test system and calculate
     * have many rights each test system row gives against the current row.
     * If the currentRow gives more than MIN_RIGHTS in the testsystem, the method
     * returns MIN_RIGHTS, else 0.
     * @param currentRow The current row in the mathimatical system.     
     * @return Number of rights, 0 or MIN_RIGHTS.
     */
    protected int getNumberOfRights( int currentRow )
    {
        // loop over all testSystem rows.
        for ( int i = 0; i < numberOfGamesInSystem; i++ )
        {     
            int rights = 0;

            // loop over all games in the current row
            for ( int j = 0; j < NUMBER_GAMES_IN_MALTIPS; j++ )
            {
                // loop over all games in the test system row.
                for ( int k = 0; k < NUMBER_GAMES_IN_MALTIPS; k++ )
                {
                    if ( mathimaticalSystem[ currentRow ].getMaltipsRow()[ j ] == 
                         mathimaticalSystem[ testSystem[ i ] ].getMaltipsRow()[ k ] 
                       )    
                    {
                        rights++;
                        break; // go to j-loop.
                    }
                }
                
                if ( rights >= MIN_RIGHTS ) 
                {
                    // row in testSystem gives atleast MIN_RIGHTS.
                    // Not neccesary to continue to calculate how many rights
                    // this rows gives, the row is invalid anyway for the system.
                    //System.out.println( mathimaticalSystem[ currentRow ].toString() );
                    //System.out.println( mathimaticalSystem[ testSystem[ i ] ] );
                    
                    return rights;
                }                
            }
        }
        
        return 0;
    }
    
    /**
     * Load statistics system from disk.
     * @param resourceName The resource name.
     * @return ArrayList containing MaltipsGame objects.
     */
    protected int[] loadStatisticSystem( String resourceName ) throws GeneralApplicationException
    {
        URL resourceURL = null;        
                    
        try
        {
            String resource = RESOURCES_PATH + resourceName;
            
            ClassLoader cl = this.getClass().getClassLoader(); 
            resourceURL = cl.getResource( resource ); 
            InputStream is = resourceURL.openStream();
            
            InputStreamReader isr = new InputStreamReader( is );
            LineNumberReader lnr = new LineNumberReader( isr );            
            
            // read all line in file
            ArrayList loadedSystem = new ArrayList();
            String currentLine = null;
            
            while( ( currentLine = lnr.readLine() ) != null )
            {
                // each line contains a string with one number.
                int value = Integer.parseInt( currentLine );
               
                loadedSystem.add( new Integer( value ) );
            }
            
            lnr.close();       
            
            // Fix upp return value so it's a int[]
            int size = loadedSystem.size();
            int[] returnArray = new int[ size ];
            
            for ( int i = 0; i < size; i++ )
            {
                returnArray[ i ] = ( ( Integer ) loadedSystem.get( i ) ).intValue();
            }
            
            return returnArray;
        }
        catch ( IOException e )
        {
            throw new GeneralApplicationException( "Resource not found: " + resourceURL + "," + e.getMessage() );    
        }        
    }  
    
   /**
     * Write the test system to disk, e.g 'foundfile.txt'.
     */
    protected void writeTestSystemToDisk()
    {
        try
        {           
            File foundFile = new File( "foundfile_" + testSystem.length + ".txt" );
            FileOutputStream fos = new FileOutputStream( foundFile );
            PrintWriter pw = new PrintWriter( fos );
            for ( int i = 0; i < numberOfGamesInSystem; i++ )
            {
                MaltipsGame game = mathimaticalSystem[ testSystem[ i ] ];
                int[] tmpGame = game.getMaltipsRow();
                String gameValue = "";
                
                for ( int j = 0; j < NUMBER_GAMES_IN_MALTIPS; j++ )
                {
                    if ( j == ( NUMBER_GAMES_IN_MALTIPS - 1 ) )
                    {
                        gameValue += tmpGame[ j ] + 1;  // last game shouldn't have a trailing ','.
                    }
                    else
                    {
                        gameValue += tmpGame[ j ] + 1 + ",";
                    }
                }
                
                pw.println( gameValue );
            }
                    
            pw.close();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }         
    } 
    
    /**
     * Get a test system. The test system is constructed by
     * generating one random number between 0 - test system. Then
     * it removes that row from the test system.
     */
    protected void getRandomSystem()
    {      
        // get random number in test system to remove from test system.
        int testSystemLength = testSystem.length;        
        
        // m�ste vara lite slump f�r att det ska vara n�gon vits med det hela.
        currentRemoveTestSystemRow = random.nextInt( testSystemLength );
        int value = testSystem[ currentRemoveTestSystemRow ];
        removedSystemRows.addLast( new Integer( value ) );
        //System.out.println( "Remove:" + value );
        numberOfGamesInSystem = testSystemLength - 1;

        // fix up test system, e.g remove the random generated row
        // from the test system.
        int[] tmpTestSystem = new int[ testSystemLength - 1 ];
        int pos = 0;
        
        for ( int i = 0; i < testSystemLength; i++ )
        {
            if ( i != currentRemoveTestSystemRow )
            {
                tmpTestSystem[ pos++ ] = testSystem[ i ];
            }
        }
        
        testSystem = new int[ testSystemLength - 1 ];
        
        System.arraycopy( tmpTestSystem, 0, testSystem, 0, testSystem.length );
    } 
    
    /**
     * Undo the last remove of a test system row, e.g put back the 
     * current removed row.
     */
    protected void undoLastRemove()
    {
        int testSystemLength = testSystem.length;        
        numberOfGamesInSystem++;

        // fix up test system
        int[] tmpTestSystem = new int[ numberOfGamesInSystem ];
        System.arraycopy( testSystem, 0, tmpTestSystem, 0, testSystem.length ); 
        Integer rowToAdd = ( Integer ) removedSystemRows.getLast();
        tmpTestSystem[ numberOfGamesInSystem - 1 ] = rowToAdd.intValue();
        //System.out.println( "L�gger tillbaka:" + rowToAdd );
        
        testSystem = new int[ numberOfGamesInSystem ];
        System.arraycopy( tmpTestSystem, 0, testSystem, 0, testSystem.length );               
    } 
    
    /**
     * Add a newly generated row from the mathimatical system to the test system.
     */
    protected void addNewRowToTestSystem()
    {
        // creates a new random number and check so it isn't already
        // in the testsystem
        boolean notValid = true;
        int newRow = 0;
        
        while ( notValid )
        {
            newRow = random.nextInt( mathimaticalSystemLength ); 
            notValid = false;   // start with that the random number is ok.
            
            for ( int i = 0; i < numberOfGamesInSystem; i++ )
            {
                if ( newRow == testSystem[ i ] )
                {
                    notValid = true;
                    System.out.println( "Random number already in testsystem!" );
                    break;  // try another random generated number.
                }                                
            }            
        }
        
        // Add it to the removed systems container and do an 'undo' which
        // then adds the random number to the testsystem.
        removedSystemRows.addLast( new Integer( newRow ) );
        System.out.println( "Adding row #" + newRow );
        undoLastRemove();
    }
    
   /**
     * Start searching for the R-system.
     */
    protected void startSearch()
    {        
        createMathimaticalSystem( systemN );
        
        boolean valid = true;
        int numberOfTestedSystem = 0;
        long time = System.currentTimeMillis();
        int numberOfRemovedTimes = 0;
        int bestSystemFound = numberOfGamesInSystem;
        
        while ( valid )
        {
            getRandomSystem();  // skapar ett nytt testsystem
            valid = checkReducedSystem();
            numberOfTestedSystem++;
                        
            if ( valid )
            {
                System.out.println( "Test system length:" + testSystem.length );
                
                if ( bestSystemFound > testSystem.length )
                {
                    bestSystemFound = testSystem.length;
                    System.out.println( "A new system is found!" + bestSystemFound );                    
                    writeTestSystemToDisk();
                }
            } 
            else
            {
                System.out.println( "Undo last remove of:" + currentRemoveTestSystemRow + ", numberOfRemovedTimes=" + numberOfRemovedTimes );
                undoLastRemove();
                valid=true; // shall continue to search.
                numberOfRemovedTimes++;
                
                if ( numberOfRemovedTimes == testSystem.length )
                {
                    System.out.println( "Couldn't remove any system row!, system length=" + testSystem.length );
                    addNewRowToTestSystem();
                    numberOfRemovedTimes = 0;   // reset.
                }
            }            
        }        
    }    
}