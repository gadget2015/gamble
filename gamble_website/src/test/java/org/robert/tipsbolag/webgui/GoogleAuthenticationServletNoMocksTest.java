package org.robert.tipsbolag.webgui;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.eclipse.jetty.testing.ServletTester;
import org.junit.Before;
import org.junit.Test;
import org.openid4java.discovery.DiscoveryException;
import org.robert.tipsbolag.webgui.businessdelegate.AuthenticationResponse;
import org.robert.tipsbolag.webgui.businessdelegate.SpelbolagBusinessDelegate;
import org.robert.tipsbolag.webgui.businessdelegate.impl.SpelbolagBusinessDelegateImpl;
import org.robert.tipsbolag.webgui.session.UserSessionFactory;

public class GoogleAuthenticationServletNoMocksTest {
	ServletTester tester;
	String baseUrl;
	String TEST_WEB_CONTEXT = "/context/googleauthentication";
	HttpClient httpClient;
	PostMethod post;

	UserSessionFactory userSessionFactoryMock;

	@Before
	public void initJettyContainer() throws Exception {
		// Start jetty container
		tester = new ServletTester();
		tester.setContextPath("/context");
		tester.addServlet(GoogleAuthenticationServletMock.class,
				"/googleauthentication");
		baseUrl = tester.createSocketConnector(true);
		tester.start();

		// init HTTP request.
		this.httpClient = new HttpClient();
		this.post = new PostMethod(baseUrl + TEST_WEB_CONTEXT);

		this.userSessionFactoryMock = GoogleAuthenticationServletMock.userSessionFactoryMock;
	}

	@Test
	public void dummyTest(){
		
	}
	
	//@Test
	public void shouldSimulateAGoogleAuthenticationProcess() throws HttpException,
			IOException, DiscoveryException {
		// Call google to create a hand-shake
		SpelbolagBusinessDelegate businessDelegate = new SpelbolagBusinessDelegateImpl();
		String returnUrl = "83.180.211.130:49360" + TEST_WEB_CONTEXT;
		AuthenticationResponse authenticationRequest = businessDelegate
				.createAuthenticateRequest(returnUrl);

		// Update actors session.
		userSessionFactoryMock.getUserSession().setOpenIdDiscoveryInformation(
				authenticationRequest.discoveryInformation);
		userSessionFactoryMock.getUserSession().setAssocHandle(
				authenticationRequest.getOpenidAssocHandle());

		// Go to google login page.
		System.out.println("Vanligtvis så anropas denna URL så att google kan autentisera användaren:" + authenticationRequest.authenticationRequest);
		HttpMethod httpget = new GetMethod(authenticationRequest.authenticationRequest);
		int status = httpClient.executeMethod(httpget);
		System.out.println("Authentication request:"+ status);
		
		// Veriy authentication response.			

	}
}
