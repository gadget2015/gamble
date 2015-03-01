package org.robert.tips.util;

import org.robert.tips.maltips.types.MaltipsGame;
import org.robert.tips.maltips.types.MaltipsConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.HashSet;

/**
 * Test to add one game to a R14 mathimatical system.
 * @author Robert Georen.
 */
public class ExtendedMatimaticalSystem extends ExtendedSystem
{
    /**
     * Creates a new instance of this class.
     */
    public ExtendedMatimaticalSystem()
    {       
        createMathimaticalSystem( 14 );
        
        ArrayList extendedSystem = createExtendedSystem( 14 );
        System.out.println( "size=" + extendedSystem.size() );
    }
    
 
    /**
     * Entry point.
     */
    public static void main( String[] args )
    {
        new ExtendedMatimaticalSystem();   
    }
}