package org.robert.tipsbolag.webgui;

import javax.servlet.http.HttpServletRequest;

import org.robert.tipsbolag.webgui.session.UserSessionFactory;
import org.robert.tipsbolag.webgui.session.UserSessionFactoryMock;

public class GoogleAOuth2CallbackServletMock extends
		GoogleAOuth2CallbackServlet {
	public static final UserSessionFactory userSessionFactoryMock = new UserSessionFactoryMock();
	private static final long serialVersionUID = 1L;
	public static int MOCK_TYPE;

	public GoogleAOuth2CallbackServletMock() {
		if (MOCK_TYPE == 1) {
			super.googleAPI = new GoogleAPIMock();
			super.userSessionFactory = userSessionFactoryMock;
		} else if (MOCK_TYPE == 2) {
			// Call Google API
			super.userSessionFactory = userSessionFactoryMock;
		}
	}

	protected UserSessionFactory getUserSessionFactory(
			HttpServletRequest request, String assoc_handle) {

		return userSessionFactory;
	}
}
