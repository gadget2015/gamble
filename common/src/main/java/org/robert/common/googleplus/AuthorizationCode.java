package org.robert.common.googleplus;

public class AuthorizationCode {
	public AuthorizationCode(String string) {
		this.authorization_code = string;
	}

	public String getValue() {
		return authorization_code;
	}

	private String authorization_code;

}
