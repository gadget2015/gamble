package org.robert.sharenote.webgui.businessdelegate.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.robert.common.openid.OpenIdClient;
import org.robert.common.openid.OpenIdClientImpl;
import org.robert.sharenote.webgui.businessdelegate.AuthenticationResponse;

public class NoteBDTest {

	@Test
	public void shouldResolveAuthenticationService() {
		// Given
		NoteBD bd = new NoteBD();

		// When
		AuthenticationResponse response = bd.createAuthenticateRequest("noterepo.com");
		String openid_assoc_handle = response.getOpenidAssocHandle(); 
		System.out.println(response.authenticationRequest);
		System.out.println(openid_assoc_handle);

		// Then
		boolean startsWith = response.authenticationRequest
				.startsWith("https://www.google.com/accounts/o8/ud?openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.return_to=http%3A%2F%2Fnoterepo.com");
		Assert.assertEquals("Should be Googles service endpoint.", true,
				startsWith);
		boolean contains = response.authenticationRequest
				.contains("openid.ext1.type.email=http%3A%2F%2Fschema.openid.net%2Fcontact%2Femail");
		Assert.assertEquals("Should contain email fetch request.", true,
				contains);
		Assert.assertNotNull("Should be an assoc_handle in discovery information.", openid_assoc_handle);
		
	}

	@Test
	public void shouldFailDiscoverAuthenticationService() {
		// Given
		NoteBD bd = new NoteBD();
		NoteBD.HTTPS_WWW_GOOGLE_COM_ACCOUNTS_O8_ID = "http://abc.com";

		// When
		try {
			bd.createAuthenticateRequest(null);
			Assert.fail("Should fail.");
		} catch (RuntimeException e) {
			// Then an exception should be thrown.
		}
	}

	/**
	 * Validate authentication response from Google. Response=
	 * 
	 * <pre>
	 * http://noterepo.com/?
	 * openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0
	 * &openid.mode=id_res 
	 * &openid.op_endpoint=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fud
	 * &openid.response_nonce=2012-08-10T09%3A36%3A32ZjtFfBxzt2qj1Pg
	 * &openid.return_to=http%3A%2F%2Fnoterepo.com
	 * &openid.assoc_handle=AMlYA9V_4RqO0wNnXXRDa6ow-BrBosqLe6D4s_O8WsJ5F6-e9rRBFRNqfWKQDmLKukJ-46st
	 * &openid.signed=op_endpoint%2Cclaimed_id%2Cidentity%2Creturn_to%2Cresponse_nonce%2Cassoc_handle%2Cns.ext1%2Cext1.mode%2Cext1.type.email%2Cext1.value.email
	 * &openid.sig=2YC2sG3ImjLkqGcYowl%2B7jCkz8an1arl0dfxJE0hqvo%3D
	 * &openid.identity=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid%3Fid%3DAItOawl4to1GypwVzLaDEUG2gi_32ANUWILjjxY
	 * &openid.claimed_id=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid%3Fid%3DAItOawl4to1GypwVzLaDEUG2gi_32ANUWILjjxY
	 * &openid.ns.ext1=http%3A%2F%2Fopenid.net%2Fsrv%2Fax%2F1.0
	 * &openid.ext1.mode=fetch_response
	 * &openid.ext1.type.email=http%3A%2F%2Fschema.openid.net%2Fcontact%2Femail
	 * &openid.ext1.value.email=robert.georen%40gmail.com
	 * </pre>
	 */
	@Test
	public void shouldBeAValidResponse() {
		// Given
		NoteBD bd = new NoteBD();
		NoteBD.HTTPS_WWW_GOOGLE_COM_ACCOUNTS_O8_ID = "https://www.google.com/accounts/o8/id";
		List<DiscoveryInformation> discoveries;
		DiscoveryInformation discovered = null;
		AuthRequest authReq = null;
		try {
			OpenIdClient openIdClient = new OpenIdClientImpl();
			discoveries = (List<DiscoveryInformation>) openIdClient
					.discover(NoteBD.HTTPS_WWW_GOOGLE_COM_ACCOUNTS_O8_ID);

			discovered = openIdClient.associate(discoveries);

			authReq = openIdClient.authenticate(discovered,
					"http://noterepo.com");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("openid.ns", "http://specs.openid.net/auth/2.0");
		parameters.put("openid.mode", "id_res");
		parameters.put("openid.op_endpoint",
				"https://www.google.com/accounts/o8/ud");
		parameters.put("openid.return_to", "http://noterepo.com");
		parameters.put("openid.assoc_handle", authReq.getHandle());
		parameters
				.put("openid.signed",
						"op_endpoint%2Cclaimed_id%2Cidentity%2Creturn_to%2Cresponse_nonce%2Cassoc_handle%2Cns.ext1%2Cext1.mode%2Cext1.type.email%2Cext1.value.email");
		parameters.put("openid.sig",
				"2YC2sG3ImjLkqGcYowl%2B7jCkz8an1arl0dfxJE0hqvo%3D");
		parameters
				.put("openid.identity",
						"https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid%3Fid%3DAItOawl4to1GypwVzLaDEUG2gi_32ANUWILjjxY");
		parameters
				.put("openid.claimed_id",
						"https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid%3Fid%3DAItOawl4to1GypwVzLaDEUG2gi_32ANUWILjjxY");
		parameters.put("openid.response_nonce",
				"2012-08-10T09:36:32ZjtFfBxzt2qj1Pg");

		// When
		try {
			bd.verifyGoogleResponse("http://noterepo.com", parameters,
					discovered);
		} catch (RuntimeException e) {
			// Then should fail due to 'return_to must be signed'.
		}
	}
}
