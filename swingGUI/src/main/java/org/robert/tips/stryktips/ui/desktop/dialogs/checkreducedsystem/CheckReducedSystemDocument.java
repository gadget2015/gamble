package org.robert.tips.stryktips.ui.desktop.dialogs.checkreducedsystem;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.event.EventListenerList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.types.StryktipsGame;
import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.stryktips.StryktipsGameType;
import org.robert.tips.stryktips.StryktipsDocument;

/**
 * Defines the check right row document. 
 * Pattern: MVC.
 * @author Robert Siwerz.
 */
class CheckReducedSystemDocument implements StryktipsConstants
{
    protected EventListenerList changeListenerList = new EventListenerList();  // Used by MVC pattern
    protected ChangeEvent changeEvent;  // used by MVC pattern.
    
    /**
     * contains data for how many rights there are on the a given right, e.g { 0 -13 } rights
     * and how many rows in the reduced system that have for example one right. This implies
     * that numberOfRights[6]=10 is that there are 10 rows that have 6 rights.
     */
	private int[] numberOfRights = new int[ NUMBER_OF_GAMES + 1];    
	
	/**
	 * The right row. { '1', 'X','2',... }
	 */
	private char[] rightRow = new char[ NUMBER_OF_GAMES ];    ;
	
	/**
	 * Reference to the stryktips document.
	 */
	private StryktipsGameType stryktipsGameType;

    /**
     * Creates an instance of this document.
     */
    public CheckReducedSystemDocument( StryktipsGameType stryktipsGameType )
    {
        this.stryktipsGameType = stryktipsGameType;
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
	 * is sent to interesed actor can use this right row.
	 * @param row The row to set a game result.
	 * @param value The value to set for the row.
	 * @throws GameAlreadySetException A game result is already set for this game.
	 */
	public void setRightRow( int row, char value ) throws GameAlreadySetException
	{
	    if ( rightRow[ row ] != 0 && value != 0 )
	    {
	        throw new GameAlreadySetException( "Game is already set for this game" );
	    }
	    else
	    {
	        rightRow[ row ] = value;
	    }
	    
	    // check if all rows are set
	    for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
	    {
	        if ( rightRow[ i ] == 0 )
	        {
	            // at least one row wasn't set. just return.
	            return;     
	        }
	    }
	    
	    // all rows set, check how many rights this give in the reduced system.
	    checkNumberOfRights();
	}
	
	/**
	 * Get the result for the given row.
	 * @param row The row to get the result for. Rows are from 0 - NUMBER_OF_GAMES - 1.
	 * @return char The result for the given row.
	 */
	public char getRightRow( int row )
	{
	    return rightRow[ row ];
	}
	
	/**
	 * calculate number of rights in the reduced system with the single row set
	 * in this document model.
	 */
	private void checkNumberOfRights()
	{
	    StryktipsDocument stryktipsDocument = ( StryktipsDocument ) stryktipsGameType.getDocument();
	    Iterator iterator = stryktipsDocument.getStryktipsSystem().getReducedSystem().iterator();
	    numberOfRights = new int[ NUMBER_OF_GAMES + 1];  // clear this array.
	    
	    while ( iterator.hasNext() )
	    {
	        StryktipsGame game = ( StryktipsGame ) iterator.next();
	        char[] singleRow = game.getSingleRow();
	        
	        int numberOfRightsInCurrentRow = 0;
	        
	        for ( int i = 0; i < NUMBER_OF_GAMES; i++ )
	        {
	            if ( singleRow[ i ] == getRightRow( i ) )
	            {
	                numberOfRightsInCurrentRow++;    
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