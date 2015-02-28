package org.robert.tipsbolag.webgui;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.eclipse.jetty.testing.ServletTester;
import org.junit.Before;
import org.junit.Test;
import org.openid4java.discovery.DiscoveryException;
import org.robert.common.openid.OpenIdClient;
import org.robert.tipsbolag.webgui.session.UserSessionFactory;

public class GoogleAOuth2CallbackServletTest {
	ServletTester tester;
	String baseUrl;
	String TEST_WEB_CONTEXT = "/context/oauth2callback";
	HttpClient httpClient;
	PostMethod post;
	OpenIdClient openIdClientMock;
	UserSessionFactory userSessionFactoryMock;

	// Get a new Code from Google and insert here.
	String code = "4/aRdzlcUmD5bOff-skVKhi8-q3m1kc5osLHCZn5-ZFhU.UubbkfUYA8gXrjMoGjtSfTow1--VlwI";

	@Before
	public void initJettyContainer() throws Exception {
		// Start jetty container
		tester = new ServletTester();
		tester.setContextPath("/context");
		tester.addServlet(GoogleAOuth2CallbackServletMock.class,
				"/oauth2callback");
		baseUrl = tester.createSocketConnector(true);
		tester.start();

		// init HTTP request.
		this.httpClient = new HttpClient();
		this.post = new PostMethod(baseUrl + TEST_WEB_CONTEXT);

		// set mock values
		this.userSessionFactoryMock = GoogleAOuth2CallbackServletMock.userSessionFactoryMock;
	}

	@Test
	public void shouldBeAValidGoogleAuthentication() throws HttpException,
			IOException, DiscoveryException {
		// Given
		GoogleAOuth2CallbackServletMock.MOCK_TYPE = 1;
		String request = baseUrl + TEST_WEB_CONTEXT + "?state=superhero&code="
				+ code;
		HttpMethod httpget = new GetMethod(request);

		// When
		int status = httpClient.executeMethod(httpget);

		// Then
		Assert.assertEquals("Should be a HTTP redirect  returned to client.",
				404, status);

		// Clean-up
		userSessionFactoryMock.getUserSession().setEmail("");
	}

	@Test
	public void shouldBeAValidGoogleAuthenticationAndGetEmail()
			throws HttpException, IOException, DiscoveryException {
		// Given
		String request = baseUrl + TEST_WEB_CONTEXT + "?state=KDASDF2342SDII&code="
				+ code;
		userSessionFactoryMock.getUserSession()
		.setAssocHandle("KDASDF2342SDII");
		GoogleAOuth2CallbackServletMock.MOCK_TYPE = 1;
		HttpMethod httpget = new GetMethod(request);

		// When
		httpClient.executeMethod(httpget);

		// Then
		Assert.assertEquals("Should set email in actors session.",
				"robert.georen@gmail.com", userSessionFactoryMock
						.getUserSession().getEmail());

		// Clean-up
		userSessionFactoryMock.getUserSession().setEmail("");
	}

	@Test
	public void shouldBeAnUnauthenticatedUser() throws IOException {
		// Given
		String request = baseUrl + TEST_WEB_CONTEXT
				+ "?error=access_denied&state=superhero";
		GoogleAOuth2CallbackServletMock.MOCK_TYPE = 1;
		HttpMethod httpget = new GetMethod(request);

		// When
		int statusCode = httpClient.executeMethod(httpget);

		// Then
		Assert.assertEquals(
				"Should redirect actor and that will create a HTTP 404 error.",
				404, statusCode);
	}

	@Test
	public void shouldBeErrorWhileCallingGoogleAPIBecauseCodeIsInvalid()
			throws Throwable, IOException {
		// Given
		String request = baseUrl + TEST_WEB_CONTEXT + "?state=superhero&code="
				+ code;
		GoogleAOuth2CallbackServletMock.MOCK_TYPE = 2;
		HttpMethod httpget = new GetMethod(request);

		// When
		int status = httpClient.executeMethod(httpget);

		// Then
		Assert.assertEquals("Should be a HTTP redirect  returned to client.",
				404, status);
	}
}
