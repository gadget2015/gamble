package org.robert.tips.stryktips;

import java.io.Serializable;
import javax.swing.event.EventListenerList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.types.StryktipsXMLConstants;
import org.robert.tips.stryktips.domain.model.StryktipsSystem;

/**
 * Defines the Stryktips document. This class contains all data that a
 * stryktips document contains.
 * Pattern: MVC.
 * @author Robert Siwerz.
 */
public class StryktipsDocument implements StryktipsConstants,
        StryktipsXMLConstants,
        Serializable {

    protected EventListenerList changeListenerList = new EventListenerList();  // Used by MVC pattern

    protected ChangeEvent changeEvent;  // used by MVC pattern.

    private boolean documentIsDirty;		// true=document shall is dirty, e.g data has changed after previous save.

    private StryktipsSystem stryktipsSystem;    // Model

    /**
     * Creates an empty/clean instance of this document.
     */
    public StryktipsDocument() {
        stryktipsSystem = new StryktipsSystem();
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

    public void setStryktipsSystem(StryktipsSystem stryktipsSystem) {
        this.stryktipsSystem = stryktipsSystem;
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
     * Get if document is dirty, e.g data hase changed in the document model.
     * @return boolen true=document is dirty.
     */
    public boolean getIsDocumentDirty() {
        return documentIsDirty;
    }

    /**
     * Set if document is dirty. 
     * @param isDirty true= document is dirty.
     */
    public void setDocumentIsDirty(boolean isDirty) {
        documentIsDirty = isDirty;
        fireStateChanged();
    }

    public StryktipsSystem getStryktipsSystem() {
        return stryktipsSystem;
    }
}