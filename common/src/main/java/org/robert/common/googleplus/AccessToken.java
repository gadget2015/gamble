package org.robert.common.googleplus;

public class AccessToken {

	private String token;

	public AccessToken(String access_token) {
		this.token = access_token;
	}

	public String getValue() {
		return token;
	}
}
