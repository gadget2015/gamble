package org.robert.tipsbolag.webgui;

import java.io.IOException;
import java.net.URL;

import junit.framework.Assert;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.eclipse.jetty.testing.ServletTester;
import org.junit.Before;
import org.junit.Test;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.robert.common.openid.OpenIdClient;
import org.robert.common.openid.OpenIdClientMock;
import org.robert.tipsbolag.webgui.session.UserSessionFactory;

public class GoogleAuthenticationServletTest {
	ServletTester tester;
	String baseUrl;
	String TEST_WEB_CONTEXT = "/context/googleauthentication";
	HttpClient httpClient;
	PostMethod post;
	OpenIdClient openIdClientMock;
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
		this.openIdClientMock = GoogleAuthenticationServletMock.openIdClientMock;
	}

	@Test
	public void shouldBeAValidGoogleAuthentication() throws HttpException,
			IOException, DiscoveryException {
		// Given
		DiscoveryInformation discoveryInfo = new DiscoveryInformation(new URL(
				"http://noterepo.com"));
		userSessionFactoryMock.getUserSession().setOpenIdDiscoveryInformation(
				discoveryInfo);
		userSessionFactoryMock.getUserSession()
				.setAssocHandle("KDASDF2342SDII");
		String request = baseUrl
				+ TEST_WEB_CONTEXT
				+ "?openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.mode=id_res&openid.op_endpoint=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fud&openid.response_nonce=2012-08-10T09%3A36%3A32ZjtFfBxzt2qj1Pg&openid.return_to=http%3A%2F%2Fnoterepo.com&openid.assoc_handle=AMlYA9V_4RqO0wNnXXRDa6ow-BrBosqLe6D4s_O8WsJ5F6-e9rRBFRNqfWKQDmLKukJ-46st&openid.signed=op_endpoint%2Cclaimed_id%2Cidentity%2Creturn_to%2Cresponse_nonce%2Cassoc_handle%2Cns.ext1%2Cext1.mode%2Cext1.type.email%2Cext1.value.email&openid.sig=2YC2sG3ImjLkqGcYowl%2B7jCkz8an1arl0dfxJE0hqvo%3D&openid.identity=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid%3Fid%3DAItOawl4to1GypwVzLaDEUG2gi_32ANUWILjjxY&openid.claimed_id=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid%3Fid%3DAItOawl4to1GypwVzLaDEUG2gi_32ANUWILjjxY&openid.ns.ext1=http%3A%2F%2Fopenid.net%2Fsrv%2Fax%2F1.0&openid.ext1.mode=fetch_response&openid.ext1.type.email=http%3A%2F%2Fschema.openid.net%2Fcontact%2Femail&openid.ext1.value.email=robert.georen%40gmail.com";
		HttpMethod httpget = new GetMethod(request);

		// When
		int status = httpClient.executeMethod(httpget);

		// Then
		Assert.assertEquals(
				"Should be a HTTP redirect error returned to client.", 404,
				status);
		Assert.assertEquals("Should call bd for verification.", true,
				((OpenIdClientMock) openIdClientMock).verificationCalled);
		Assert.assertEquals("Should set email in actors session.",
				"robert.georen@gmail.com", userSessionFactoryMock
						.getUserSession().getEmail());
	}

	@Test
	public void shouldBeAValidGoogleAuthenticationButMissingEmail()
			throws HttpException, IOException, DiscoveryException {
		// Given
		DiscoveryInformation discoveryInfo = new DiscoveryInformation(new URL(
				"http://noterepo.com"));
		userSessionFactoryMock.getUserSession().setOpenIdDiscoveryInformation(
				discoveryInfo);
		String request = baseUrl
				+ TEST_WEB_CONTEXT
				+ "?openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.mode=id_res&openid.op_endpoint=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fud&openid.response_nonce=2012-08-10T09%3A36%3A32ZjtFfBxzt2qj1Pg&openid.return_to=http%3A%2F%2Fnoterepo.com&openid.assoc_handle=AMlYA9V_4RqO0wNnXXRDa6ow-BrBosqLe6D4s_O8WsJ5F6-e9rRBFRNqfWKQDmLKukJ-46st&openid.signed=op_endpoint%2Cclaimed_id%2Cidentity%2Creturn_to%2Cresponse_nonce%2Cassoc_handle%2Cns.ext1%2Cext1.mode%2Cext1.type.email%2Cext1.value.email&openid.sig=2YC2sG3ImjLkqGcYowl%2B7jCkz8an1arl0dfxJE0hqvo%3D&openid.identity=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid%3Fid%3DAItOawl4to1GypwVzLaDEUG2gi_32ANUWILjjxY&openid.claimed_id=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid%3Fid%3DAItOawl4to1GypwVzLaDEUG2gi_32ANUWILjjxY&openid.ns.ext1=http%3A%2F%2Fopenid.net%2Fsrv%2Fax%2F1.0&openid.ext1.mode=fetch_response&openid.ext1.type.email=http%3A%2F%2Fschema.openid.net%2Fcontact%2Femail";
		GetMethod httpget = new GetMethod(request);
		userSessionFactoryMock.getUserSession().setEmail(null);

		// When
		int status = httpClient.executeMethod(httpget);

		// Then
		Assert.assertEquals(
				"Should be a HTTP redirect error returned to client.", 404,
				status);
		Assert.assertEquals("Should call bd for verification.", true,
				((OpenIdClientMock) openIdClientMock).verificationCalled);
		Assert.assertEquals("Should NOT set email in actors session.", null,
				userSessionFactoryMock.getUserSession().getEmail());

	}

	@Test
	public void shouldBeAnInvalidGoogleAuthentication() throws HttpException,
			IOException, DiscoveryException {
		// Given
		((OpenIdClientMock) openIdClientMock).verificationCalled = false;
		String SESSIONID = "djsfj232903ks";
		String request = baseUrl
				+ TEST_WEB_CONTEXT
				+ "?openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.mode=id_res&openid.op_endpoint=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fud&openid.response_nonce=2012-08-10T09%3A36%3A32ZjtFfBxzt2qj1Pg&openid.return_to=http%3A%2F%2Fnoterepo.com&openid.assoc_handle=AMlYA9V_4RqO0wNnXXRDa6ow-BrBosqLe6D4s_O8WsJ5F6-e9rRBFRNqfWKQDmLKukJ-46st&openid.signed=op_endpoint%2Cclaimed_id%2Cidentity%2Creturn_to%2Cresponse_nonce%2Cassoc_handle%2Cns.ext1%2Cext1.mode%2Cext1.type.email%2Cext1.value.email&openid.sig=2YC2sG3ImjLkqGcYowl%2B7jCkz8an1arl0dfxJE0hqvo%3D&openid.identity=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid%3Fid%3DAItOawl4to1GypwVzLaDEUG2gi_32ANUWILjjxY&openid.claimed_id=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid%3Fid%3DAItOawl4to1GypwVzLaDEUG2gi_32ANUWILjjxY&openid.ns.ext1=http%3A%2F%2Fopenid.net%2Fsrv%2Fax%2F1.0&openid.ext1.mode=fetch_response&openid.ext1.type.email=http%3A%2F%2Fschema.openid.net%2Fcontact%2Femail&openid.ext1.value.email=fail.auth%40gmail.com";
		GetMethod httpget = new GetMethod(request);
		httpget.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		httpget.setRequestHeader("Cookie", "JSESSIONID=" + SESSIONID);

		// When
		int status = httpClient.executeMethod(httpget);

		// Then
		Assert.assertEquals(
				"Should redirect actor and that will create a HTTP 404 error.",
				404, status);
		Assert.assertEquals(
				"Should call bd for verification and set value to false.",
				false,
				((OpenIdClientMock) openIdClientMock).verificationCalled);
	}

}
