package org.robert.sharenote.webgui.session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Defines storage for JSF session data associated with a specific client/actor.
 * 
 * @author Robert Georen.
 * 
 */
@ManagedBean
@SessionScoped
public class UserSessionImpl extends UserSession {

	private static final long serialVersionUID = 1L;

	public UserSessionImpl() {
		super();
	}
}
