package org.robert.tips.stryktipsfinder.webgui.session;

/**
 * Creates a session factory that creates session scopes.
 * 
 * @author Robert Georen.
 * 
 */
public interface UserSessionFactory {

    /**
     * Get a session scope associated with actor. If none exist create one.
     */
    public UserSession getUserSession();
}
