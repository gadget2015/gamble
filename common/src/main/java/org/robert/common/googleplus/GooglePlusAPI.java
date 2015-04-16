package org.robert.common.googleplus;

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

	/**
	 * Get accesstoken from Google given authorication code.
	 */
	public String getAccessToken(String authorizationCode, String clientId,
			String redirectUri, String clientSecret) {
		try {
			HttpClient httpClient = new HttpClient();
			PostMethod postRequest = new PostMethod(
					"https://accounts.google.com/o/oauth2/token");
			NameValuePair[] data = {
					new NameValuePair("grant_type", "authorization_code"),
					new NameValuePair("client_id", clientId),
					new NameValuePair("code", authorizationCode),
					new NameValuePair("redirect_uri", redirectUri),
					new NameValuePair("client_secret", clientSecret) };

			postRequest.setRequestBody(data);

			postRequest.addRequestHeader("content-type",
					"application/x-www-form-urlencoded");
			int result = httpClient.executeMethod(postRequest);
			logger.debug("HTTP response status code:" + result);

			String response = postRequest.getResponseBodyAsString();
			GoogleToken gt = new GoogleToken(response);

			if (gt.getAccess_token() == null) {
				logger.debug("No access_token from Google." + gt.toString());
				throw new RuntimeException("No access_token from Google.");
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