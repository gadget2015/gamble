package org.robert.tips.stryktips.finder.desktopclient.dialog;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.RSystemSearchInfo;

/**
 * Defines the model for the system info view.
 * 
 * @author Robert
 * 
 */
public class SystemInfoModel {
	// Used by MVC pattern
	protected EventListenerList changeListenerList = new EventListenerList();
	protected ChangeEvent changeEvent;

	public String name;
	public int numberOfMathematicalRows;
	public long systemId;
	public RSystemSearchInfo searchInfo;
	public long currentSpeed;
	public Boolean isSearching = Boolean.FALSE;
	public long rowsInSystemUnderTest;

	/**
	 * Add a listener for this model. Used by the MVC pattern.
	 * 
	 * @param listener
	 *            Listener to add for this model.
	 */
	public void addChangeListener(ChangeListener cl) {
		changeListenerList.add(ChangeListener.class, cl);
		cl.stateChanged(new ChangeEvent(this));
	}

	/**
	 * Remove a listener for this model. Used by MVC pattern.
	 * 
	 * @param listener
	 *            Listener to remove.
	 */
	public void removeChangeListener(ChangeListener cl) {
		changeListenerList.remove(ChangeListener.class, cl);
	}

	/**
	 * Notify all listeners that have registered interest for notification on
	 * this event type. Used by MVC pattern.
	 */
	public void fireStateChanged() {
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
}
