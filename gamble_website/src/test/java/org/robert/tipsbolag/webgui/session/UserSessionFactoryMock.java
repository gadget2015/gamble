package org.robert.tipsbolag.webgui.session;


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
