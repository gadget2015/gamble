package org.robert.tips.stryktips.finder.desktopclient.dialog;

import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.FindStryktipsSystemRunnable;

/**
 * This is the master model, e.g the root model. Contains data about the current
 * session.
 * 
 * <pre>
 *    Pattern: MVC.
 * </pre>
 * 
 * @author Robert
 * 
 */
public class Model {
	/**
	 * Database id for the current search information object.
	 */
	public long systemId;

	/**
	 * The search runnable object.
	 */
	public FindStryktipsSystemRunnable currentSearch;

	/**
	 * Defines the thread priority the search thread will use.
	 */
	public int threadPriority;

	/**
	 * Set default values.
	 */
	public Model() {
		this.threadPriority = Thread.NORM_PRIORITY;
	}
}
