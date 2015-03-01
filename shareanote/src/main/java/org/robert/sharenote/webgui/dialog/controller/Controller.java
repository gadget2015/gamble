package org.robert.sharenote.webgui.dialog.controller;

/**
 * All controller should implements this interface.
 * 
 * @author Robert Georen.
 * 
 */
public interface Controller {

	/**
	 * Execute the logic in the controller.
	 * 
	 * @return outcome used to nagivate to next view (see faces-config.xml).
	 */
	public String perform();
}

