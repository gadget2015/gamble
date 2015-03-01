package org.robert.tips.maltips.ui.dialogs.checkreducesystem;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.event.EventListenerList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.maltips.MaltipsGameType;
import org.robert.tips.maltips.MaltipsDocument;
import org.robert.tips.maltips.types.MaltipsGame;

/**
 * Defines the check right row document. 
 * Pattern: MVC.
 * @author Robert Siwerz.
 */
class CheckReducedSystemDocument implements MaltipsConstants
{
    protected EventListenerList changeListenerList = new EventListenerList();  // Used by MVC pattern
    protected ChangeEvent changeEvent;  // used by MVC pattern.
    
    /**
     * Contains data for how many rights there are for a number of rights, 
     * e.g { 0 - 8 } rights. This is for example 
     * that numberOfRights[6]=10 is that there are 10 rows that gives 6 rights.
     */
	private int[] numberOfRights = new int[ NUMBER_GAMES_IN_MALTIPS + 1];    
	
	/**
	 * The right row. { 1, 2, 3,... }
	 */
	private int[] rightRow = new int[ NUMBER_OF_GAMES ];    
	
	/**
	 * Reference to the maltips document.
	 */
	private MaltipsGameType maltipsGameType;

    /**
     * Creates an instance of this document.
     */
    public CheckReducedSystemDocument( MaltipsGameType maltipsGameType )
    {
        this.maltipsGameType = maltipsGameType;
    }

	/**
	 * Add a listener for this model. Used by the MVC pattern.
	 * @param listener Listener to add for this model.
	 */
	public void addChangeListener( ChangeListener cl )
	{
	    changeListenerList.add( ChangeListener.class, cl );
	    cl.stateChanged( new ChangeEvent( this ) );
	}
	
	/**
	 * Remove a listener for this model. Used by MVC pattern.
	 * @param listener Listener to remove.
	 */
	public void removeChangeListener( ChangeListener cl )
	{
	    changeListenerList.remove( ChangeListener.class, cl );
	}
	
	/**
	 * Notify all listeners that have registered interest for
	 * notification on this event type.
	 * Used by MVC pattern.
	 */
	protected void fireStateChanged()
	{
	    // guaranteed to return a non-null array
	    Object[] listeners = changeListenerList.getListenerList();
	    
	    // process the listeners last to first, notifying
	    // those that are interested int this event.
	    for ( int i = listeners.length - 2; i >= 0; i -= 2 )
	    {
	        if ( listeners[i] == ChangeListener.class )
	        {
	            if ( changeEvent == null )
	            {
	                changeEvent = new ChangeEvent( this );
	            }
	            ChangeListener cl = ( ChangeListener ) listeners[i+1];
	            cl.stateChanged( changeEvent );
	        }
	    }
	}
	
	/**
	 * Set a right row game result. If all rows are set, then an update request
	 * is sent to interesed actor that they can use this right row.
	 * @param row The row to set a game result.
	 * @param value The value of the game.
	 * @throws GameAlreadySetException A game result is already set for this game.
	 */
	public void setRightRow( int row, int value ) throws GameAlreadySetException
	{
	    if ( rightRow[ row ] != 0 && value != 0 )
	    {
	        throw new GameAlreadySetException( "Game is already set for this game#:" + row );
	    }
	    else
	    {
	        rightRow[ row  ] = value;
	    }
	    
	    // check if all 8 rows are set
	    int numberOfSetRows = 0;
	    
	    for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
	    {
	        if ( rightRow[ i ] != 0 )
	        {
	            numberOfSetRows++;
	        }
	    }
	    
	    if ( numberOfSetRows == NUMBER_GAMES_IN_MALTIPS )
	    {
	        // all rows set, check how many rights this give in the reduced system.
	        checkNumberOfRights();
	    }
	    else
        {
	        numberOfRights = new int[ NUMBER_GAMES_IN_MALTIPS + 1];  // clear this array.
	        fireStateChanged();	        
	    }
	}
	
	/**
	 * Get the result for the given row.
	 * @param row The row to get the result for. Rows are from 0 - NUMBER_OF_GAMES - 1.
	 * @return int The result for the given row, 0 = the row isn't set.
	 */
	public int getRightRow( int row )
	{
	    return rightRow[ row ];
	}
	
	/**
	 * Calculate number of rights in the reduced system with the single row set
	 * in this document model.
	 */
	private void checkNumberOfRights()
	{
	    MaltipsDocument maltipsDocument = ( MaltipsDocument ) maltipsGameType.getDocument();
	    Iterator iterator = maltipsDocument.getMaltipsSystem().getReducedSystem().iterator();
	    numberOfRights = new int[ NUMBER_GAMES_IN_MALTIPS + 1];  // clear this array.
	    
	    while ( iterator.hasNext() )
	    {
	        MaltipsGame game = ( MaltipsGame ) iterator.next();
	        int[] maltipsRow = game.getMaltipsRow();
	        
	        int numberOfRightsInCurrentRow = 0;
	        
	        // check the curret reduced row againt the right row
	        for ( int i = 0; i < NUMBER_GAMES_IN_MALTIPS; i++ )
	        {
	            for ( int j = 0; j < NUMBER_OF_GAMES; j++ )
	            {
	                if ( maltipsRow[ i ] == getRightRow( j ) )
	                {
	                    numberOfRightsInCurrentRow++;   
	                    break;
	                }
	            }
	        }
	        
	        numberOfRights[ numberOfRightsInCurrentRow ]++;
	    }
	    
	    fireStateChanged();
	}
	
	/**
	 * Get number of rights array.
	 * @return int[] Contains number of rights data.
	 */
	public int[] getNumberOfRights()
	{
	    return numberOfRights;
	}

}