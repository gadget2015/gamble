package org.robert.tips.stryktipsfinder.webgui.session;

import org.robert.tips.stryktipsfinder.webgui.session.UserSession;
import org.robert.tips.stryktipsfinder.webgui.session.UserSessionFactory;

/**
 * Mock factory.
 * 
 * @author Robert Georen.
 * 
 */
public class UserSessionFactoryMock implements UserSessionFactory {
	private UserSessionMock userSessionMock;

	public UserSessionFactoryMock() {
		this.userSessionMock = new UserSessionMock();
	}

	@Override
	public UserSession getUserSession() {
		return userSessionMock;
	}
}
