package org.robert.tipsbolag.webgui;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines operations that this application uses in the Google + API.
 */
public class GooglePlusAPI {
	private Logger logger = LoggerFactory.getLogger(GooglePlusAPI.class);

	public UserInfo getEmailAdress(String access_token) {
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(
				"https://www.googleapis.com/oauth2/v1/userinfo?access_token="
						+ access_token);

		try {
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				logger.error("HTTP GET failed when getting email from Google: "
						+ method.getStatusLine());
			}

			String response = method.getResponseBodyAsString();
			UserInfo ui = new UserInfo(response);
			method.releaseConnection();

			return ui;
		} catch (IOException e) {
			logger.error("Failed to get email for authenticated actor."
					+ e.getMessage());
			throw new RuntimeException(
					"Failed to get email for authenticated actor."
							+ e.getMessage());
		}

	}

	public String getAccessToken(String authorizationCode) {
		try {
			HttpClient httpClient = new HttpClient();
			PostMethod postRequest = new PostMethod(
					"https://accounts.google.com/o/oauth2/token");
			NameValuePair[] data = {
					new NameValuePair("grant_type", "authorization_code"),
					new NameValuePair("client_id",
							"607736284212-t8iejp7vq3pf853r88ncspgreb7fvtgo.apps.googleusercontent.com"),
					new NameValuePair("code", authorizationCode),
					new NameValuePair("redirect_uri",
							"http://www.stryktipsbolag.se/oauth2callback"),
					new NameValuePair("client_secret",
							"uqOcN4UXUPhhFc53_xWSt4dF") };

			postRequest.setRequestBody(data);

			postRequest.addRequestHeader("content-type",
					"application/x-www-form-urlencoded");
			int result = httpClient.executeMethod(postRequest);
			logger.debug("HTTP response status code:" + result);

			String response = postRequest.getResponseBodyAsString();
			GoogleToken gt = new GoogleToken(response);

			if (gt.getAccess_token() == null) {
				logger.debug("No access_token from Google."
						+ gt.toString());
				throw new RuntimeException(
						"No access_token from Google.");
			}

			postRequest.releaseConnection();
			return gt.getAccess_token();
		} catch (Exception e) {
			logger.error("Error calling Google+ API for getting an accessToken."
					+ e.getMessage());
			throw new RuntimeException(
					"Error calling Google+ API for getting an accessToken."
							+ e.getMessage());
		}
	}
}
/*
 * public String getAccessTokenOLD(String authorizationCode) { ClientConfig cc =
 * new ClientConfig().register(new JacksonFeature()); Client client =
 * ClientBuilder.newClient(cc); WebTarget webTarget = client
 * .target("https://accounts.google.com/o/oauth2"); WebTarget tokenWebTarget =
 * webTarget.path("token"); Form form = new Form(); form.param("grant_type",
 * "authorization_code"); form.param("client_id",
 * "607736284212-t8iejp7vq3pf853r88ncspgreb7fvtgo.apps.googleusercontent.com");
 * form.param("code", authorizationCode); form.param("redirect_uri",
 * "http://www.stryktipsbolag.se/oauth2callback"); form.param("client_secret",
 * "uqOcN4UXUPhhFc53_xWSt4dF");
 * 
 * Invocation.Builder invocationBuilder = tokenWebTarget
 * .request(MediaType.APPLICATION_FORM_URLENCODED_TYPE);
 * 
 * // Do the actually HTTP POST Response postResponse = invocationBuilder
 * .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE),
 * Response.class);
 * 
 * // get access_token from response. GoogleToken output =
 * postResponse.readEntity(GoogleToken.class); logger.debug(output.toString());
 * 
 * return output.getAccess_token(); }
 */

/*
 * public UserInfo getEmailAdress_OLD(String access_token) { ClientConfig cc =
 * new ClientConfig().register(new JacksonFeature()); Client client =
 * ClientBuilder.newClient(cc); WebTarget emailWebTarget = client
 * .target("https://www.googleapis.com/oauth2/v1/userinfo"); emailWebTarget =
 * emailWebTarget .queryParam("access_token", access_token);
 * 
 * Invocation.Builder invocationBuilder = emailWebTarget
 * .request(MediaType.TEXT_PLAIN_TYPE);
 * 
 * // Do the actually HTTP GET Response postResponse = invocationBuilder.get();
 * 
 * // get access_token from response. try { UserInfo userInfo =
 * postResponse.readEntity(UserInfo.class); logger.debug(userInfo.toString());
 * 
 * return userInfo; } catch (ProcessingException e) {
 * logger.warn("Error processning response from Google+ API.userinfo. " +
 * e.getMessage()); throw new RuntimeException("Error in service"); } }
 */