package org.robert.tipsbolag.webgui;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.robert.common.googleplus.AccessToken;
import org.robert.common.googleplus.AuthorizationCode;
import org.robert.common.googleplus.ClientId;
import org.robert.common.googleplus.ClientSecret;
import org.robert.common.googleplus.GooglePlusAPI;
import org.robert.tipsbolag.webgui.dialog.Navigation;
import org.robert.tipsbolag.webgui.session.UserSessionFactory;
import org.robert.tipsbolag.webgui.session.UserSessionFactoryServletImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleAOuth2CallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory
			.getLogger(GoogleAOuth2CallbackServlet.class);

	UserSessionFactory userSessionFactory;
	GooglePlusAPI googleAPI = new GooglePlusAPI();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		@SuppressWarnings("unchecked")
		Map<String, String[]> parameters = request.getParameterMap();

		Object error = parameters.get("error");
		if (error != null) {
			// User isn't authenticated.
			logger.error("Actor isn't authenticated:" + error + ".");
			response.sendRedirect(Navigation.ERROR_MESSAGE_DIALOG);
		} else {
			// User is authenticated and now will the authorization flow
			// continue
			logger.info("Actor is authenticated.");

			// continue with getting an access_token.
			String[] authorizationCodeTmp = parameters.get("code");
			AuthorizationCode authCode = new AuthorizationCode(
					authorizationCodeTmp[0]);

			try {
				ClientId clientId = new ClientId(
						"607736284212-t8iejp7vq3pf853r88ncspgreb7fvtgo.apps.googleusercontent.com");
				URL redirectURL = new URL(
						"http://www.stryktipsbolag.se/oauth2callback");
				ClientSecret password = new ClientSecret(
						"uqOcN4UXUPhhFc53_xWSt4dF");
				AccessToken access_token = googleAPI.getAccessToken(authCode,
						clientId, redirectURL, password);

				// Call google and get email-adress.
				org.robert.common.googleplus.UserInfo email = googleAPI.getEmailAdress(access_token);
				logger.info("Actor identifies as " + email.getEmail() + ".");

				// Update actors session with email-adress and go to html-page.
				String[] assoc_handles = parameters.get("state");
				String assoc_handle = assoc_handles[0];
				this.userSessionFactory = getUserSessionFactory(request,
						assoc_handle);
				this.userSessionFactory.getUserSession().setEmail(
						email.getEmail());
				response.sendRedirect(Navigation.MITT_SALDO);
			} catch (RuntimeException e) {
				response.sendRedirect(Navigation.ERROR_MESSAGE_DIALOG);
			}
		}
	}

	/**
	 * This approach is used because of unit test requirements.
	 * 
	 * @param assoc_handle
	 */
	protected UserSessionFactory getUserSessionFactory(
			HttpServletRequest request, String assoc_handle) {

		this.userSessionFactory = new UserSessionFactoryServletImpl(request,
				getServletContext(), assoc_handle);

		return userSessionFactory;
	}
}
