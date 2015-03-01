package org.robert.tips.stryktips.ui.desktop.dialogs.combinationgraph;

import java.io.Serializable;
import org.robert.tips.stryktips.types.StryktipsConstants;
import javax.swing.event.EventListenerList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Contains the combination graph document.
 * @author Robert Siwerz.
 */
public class CombinationGraphDocument implements StryktipsConstants, 
                                                 Serializable
{
    /**
     * contains the graph data.
     */
    private int[] graphData = new int[ NUMBER_OF_GAMES + 1 ];          

    protected EventListenerList changeListenerList = new EventListenerList();  // Used by MVC pattern
    protected ChangeEvent changeEvent;  // used by MVC pattern.
	
	/**
	 * Creates a new instance of the combination graph document.
	 */
	public CombinationGraphDocument()
	{ 
	    for ( int i = 0; i < ( NUMBER_OF_GAMES + 1 ); i++ )
	    {
	        graphData[ i ] = 0;
	    }
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
	 * Get the combination graph.
	 * @return int[] The combination graph.
	 */
	public int[] getCombinationGraph()
	{
	    return graphData;
	}
	
	/**
	 * Set combination graph data value.
	 * @param value A graph value.
	 * @param position Position in graph.
	 */
	public void setCombinationGraphValue( int value, int position )
	{
	    graphData[ position ] = value;
	    fireStateChanged();
	}

}