package org.robert.tips.maltips.ui.dialogs.viewcombinationgraph;

import javax.swing.event.EventListenerList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.robert.tips.maltips.types.MaltipsConstants;

/**
 * Defines the data used for the combination graph dialog. 
 * Pattern: MVC.
 * @author Robert Siwerz.
 */
public class CombinationGraphDocument implements MaltipsConstants {

    protected EventListenerList changeListenerList = new EventListenerList();  // Used by MVC pattern
    protected ChangeEvent changeEvent;  // used by MVC pattern.
    /**
     * Contains data for how many rights there are for a number of rights, 
     * e.g { 0 - 8 } rights. This is for example 
     * that numberOfRights[6]=10 is that there are 10 rows that gives 6 rights.
     */
    private int[] numberOfRights = new int[NUMBER_GAMES_IN_MALTIPS + 1];

    public CombinationGraphDocument(){
    }

    /**
     * Add a listener for this model. Used by the MVC pattern.
     * @param listener Listener to add for this model.
     */
    public void addChangeListener(ChangeListener cl) {
        changeListenerList.add(ChangeListener.class, cl);
        cl.stateChanged(new ChangeEvent(this));
    }

    /**
     * Remove a listener for this model. Used by MVC pattern.
     * @param listener Listener to remove.
     */
    public void removeChangeListener(ChangeListener cl) {
        changeListenerList.remove(ChangeListener.class, cl);
    }

    /**
     * Notify all listeners that have registered interest for
     * notification on this event type.
     * Used by MVC pattern.
     */
    protected void fireStateChanged() {
        // guaranteed to return a non-null array
        Object[] listeners = changeListenerList.getListenerList();

        // process the listeners last to first, notifying
        // those that are interested int this event.
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ChangeListener cl = (ChangeListener) listeners[i + 1];
                cl.stateChanged(changeEvent);
            }
        }
    }

    /**
     * Set the value of rights for a given number of rights in the
     * combination system.
     * @param value The number of rows for the given number of rights in
     *              the combination system.
     * @param combination The combination to set value for, e.g { 0 ... 8 }.
     */
    public void setCombinationValue(int value, int combination) {
        numberOfRights[combination] = value;
        fireStateChanged();

    }

    /**
     * Get the combination data.
     * @return int The graph data.
     */
    public int[] getCombinationData() {
        return numberOfRights;
    }
}
