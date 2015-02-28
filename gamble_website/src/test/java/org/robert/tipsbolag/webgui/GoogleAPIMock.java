package org.robert.tipsbolag.webgui;

/**
 * Mock the Google+ API.
 */
public class GoogleAPIMock extends GooglePlusAPI {

	public UserInfo getEmailAdress(String access_token) {
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail("robert.georen@gmail.com");

		return userInfo;
	}

	public String getAccessToken(String authorizationCode) {
		return "ya29.IwF1dXXS1PPAY_LJqw2tegoWn5TzIkPTeMJPN-0WgZ3TOOKQgES74SMdvr3mndfJIdE-QaP3bB3EjQ";
	}
}
