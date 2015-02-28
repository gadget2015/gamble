package org.robert.tipsbolag.webgui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.discovery.DiscoveryInformation;
import org.robert.common.openid.OpenIdClient;
import org.robert.common.openid.OpenIdClientImpl;
import org.robert.tipsbolag.webgui.businessdelegate.AuthenticationResponse;
import org.robert.tipsbolag.webgui.dialog.Navigation;
import org.robert.tipsbolag.webgui.session.UserSessionFactory;
import org.robert.tipsbolag.webgui.session.UserSessionFactoryServletImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleAuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory
			.getLogger(GoogleAuthenticationServlet.class);

	OpenIdClient openIdClient;
	UserSessionFactory userSessionFactory;

	public GoogleAuthenticationServlet() {
		logger.info("Create new instance of GoogleAuthenticationServlet");
		this.openIdClient = new OpenIdClientImpl();
		//this.openIdClient = new OpenIdClientMock();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {

		@SuppressWarnings("unchecked")
		Map<String, String> parameters = (Map<String, String>) request
				.getParameterMap();

		// extract the receiving URL from the HTTP request
		String receivingURL = getReceivingURL(request);
		String assoc_handle = AuthenticationResponse
				.getOpenidAssocHandle(receivingURL);

		this.userSessionFactory = getUserSessionFactory(request, assoc_handle);
		DiscoveryInformation discoverInfo = userSessionFactory.getUserSession()
				.getOpenIdDiscoveryInformation();

		boolean verificationResult = openIdClient.verifyGoogleResponse(
				receivingURL, parameters, discoverInfo);

		if (verificationResult == true) {
			logger.debug("Actor authenticated by Google.");

			try {
				// Set data retreived from Google into actors session.
				Object emailsTmp = parameters.get("openid.ext1.value.email");
				String[] emails = (String[]) emailsTmp;
				if (emails == null) {
					logger.error("Authentication response from Google is missing email.");

					response.sendRedirect(Navigation.ERROR_MESSAGE_DIALOG);
				} else {
					logger.debug("Google email = " + emails[0] + ". length="
							+ emails.length);
					userSessionFactory.getUserSession().setEmail(emails[0]);

					// Redirect actor to start dialog.
					response.sendRedirect(Navigation.MITT_SALDO);
				}
			} catch (IOException e) {
				sendErrorMessageToActor(response);
			}
		} else {
			logger.info("Actor is NOT authenticated by Google.");
			logger.info("Receiving URL = " + receivingURL);
			logger.info("DiscoveryInformation = " + discoverInfo);

			try {
				response.sendRedirect(Navigation.ERROR_MESSAGE_DIALOG);
			} catch (IOException e) {
				sendErrorMessageToActor(response);
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

	private void sendErrorMessageToActor(HttpServletResponse response) {
		try {
			logger.error("Send error to client due to redirect error.");

			response.sendError(1967);
		} catch (IOException e1) {
			response.setContentType("text/html");
			PrintWriter pw;

			try {
				pw = response.getWriter();
				pw.println("<html>");
				pw.println("<head><title>Authentication error</title></title>");
				pw.println("<body>");
				pw.println("<h1>Major error while authenticate Google account</h1>");
				pw.println("</body></html>");
				pw.close();
			} catch (IOException e2) {
				logger.error("Major error while redirect an authenticated Google user.");
			}
		}
	}

	private String getReceivingURL(HttpServletRequest request) {
		StringBuffer receivingURL = request.getRequestURL();
		String queryString = request.getQueryString();

		if (queryString != null && queryString.length() > 0) {
			receivingURL.append("?").append(request.getQueryString());
		}

		return receivingURL.toString();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		logger.info("Google is making a HTTP POST request when as response for authentication.");
		doGet(request, response);
	}
}
