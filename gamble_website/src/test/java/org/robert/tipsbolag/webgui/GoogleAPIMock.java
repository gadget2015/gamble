package org.robert.tipsbolag.webgui;

import java.net.URL;

import org.robert.common.googleplus.AccessToken;
import org.robert.common.googleplus.AuthorizationCode;
import org.robert.common.googleplus.ClientId;
import org.robert.common.googleplus.ClientSecret;

/**
 * Mock the Google+ API.
 */
public class GoogleAPIMock extends org.robert.common.googleplus.GooglePlusAPI {
	@Override
	public org.robert.common.googleplus.UserInfo getEmailAdress(
			AccessToken access_token) {
		org.robert.common.googleplus.UserInfo userInfo = new org.robert.common.googleplus.UserInfo();
		userInfo.setEmail("robert.georen@gmail.com");

		return userInfo;
	}

	@Override
	public AccessToken getAccessToken(AuthorizationCode authorizationCode,
			ClientId clientId, URL redirectUri, ClientSecret clientSecret) {
		return new AccessToken(
				"ya29.IwF1dXXS1PPAY_LJqw2tegoWn5TzIkPTeMJPN-0WgZ3TOOKQgES74SMdvr3mndfJIdE-QaP3bB3EjQ");
	}
}
