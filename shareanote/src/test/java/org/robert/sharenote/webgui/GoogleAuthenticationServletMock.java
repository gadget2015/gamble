package org.robert.sharenote.webgui;

import javax.servlet.http.HttpServletRequest;

import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.businessdelegate.mock.NoteMockBD;
import org.robert.sharenote.webgui.session.UserSessionFactory;
import org.robert.sharenote.webgui.session.UserSessionFactoryMock;

public class GoogleAuthenticationServletMock extends
		GoogleAuthenticationServlet {
	private static final long serialVersionUID = 1L;
	public static final NoteBusinessDelegate noteBd = new NoteMockBD();
	public static final UserSessionFactory userSessionFactoryMock = new UserSessionFactoryMock();

	public GoogleAuthenticationServletMock() {		
		super.noteBusinessDelegate = noteBd;
		super.userSessionFactory = userSessionFactoryMock;
	}

	protected UserSessionFactory getUserSessionFactory(
			HttpServletRequest request, String assoc_handle) {

		return userSessionFactory;
	}
}
