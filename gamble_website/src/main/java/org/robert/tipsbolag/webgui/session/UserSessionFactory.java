package org.robert.tipsbolag.webgui.session;


/**
 * Creates a session factory that creates session scopes.
 * 
 * @author Robert Georen.
 * 
 */
public interface UserSessionFactory {
	public UserSession getUserSession();
}
