package org.robert.tipsbolag.webgui;

import javax.servlet.http.HttpServletRequest;

import org.robert.common.openid.OpenIdClient;
import org.robert.common.openid.OpenIdClientMock;
import org.robert.tipsbolag.webgui.session.UserSessionFactory;
import org.robert.tipsbolag.webgui.session.UserSessionFactoryMock;

public class GoogleAuthenticationServletMock extends
		GoogleAuthenticationServlet {
	private static final long serialVersionUID = 1L;
	public static final OpenIdClient openIdClientMock = new OpenIdClientMock();
	public static final UserSessionFactory userSessionFactoryMock = new UserSessionFactoryMock();

	public GoogleAuthenticationServletMock() {		
		super.openIdClient = openIdClientMock;
		super.userSessionFactory = userSessionFactoryMock;
	}

	protected UserSessionFactory getUserSessionFactory(
			HttpServletRequest request, String assoc_handle) {

		return userSessionFactory;
	}
}
