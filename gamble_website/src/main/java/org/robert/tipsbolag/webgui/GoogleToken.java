package org.robert.tipsbolag.webgui;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GoogleToken implements Serializable {
	private static final long serialVersionUID = 1L;
	private String access_token;
	private String refresh_token;
	private String expires_in;
	private String token_type;
	private String error;
	private String error_description;
	private String id_token;

	public GoogleToken() {
	}

	public GoogleToken(String JSON) {
		JSONParser parser = new JSONParser();
		this.access_token = parser.getJSON_Attribute(JSON, "access_token");
		this.error = parser.getJSON_Attribute(JSON, "error");
		this.error_description = parser.getJSON_Attribute(JSON,
				"error_description");
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("access_token:");
		sb.append(getAccess_token());
		sb.append(System.getProperty("line.separator"));
		sb.append("error:");
		sb.append(error);
		sb.append(System.getProperty("line.separator"));
		sb.append("error_description:");
		sb.append(error_description);
		sb.append(System.getProperty("line.separator"));
		sb.append("token_type:");
		sb.append(token_type);
		sb.append(System.getProperty("line.separator"));
		sb.append("expires_in:");
		sb.append(expires_in);
		sb.append(System.getProperty("line.separator"));
		sb.append("id_token:");
		sb.append(id_token);
		sb.append(System.getProperty("line.separator"));
		sb.append("reresh_token:");
		sb.append(refresh_token);

		return sb.toString();
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError_description() {
		return error_description;
	}

	public void setError_description(String error_description) {
		this.error_description = error_description;
	}

	public String getId_token() {
		return id_token;
	}

	public void setId_token(String id_token) {
		this.id_token = id_token;
	}
}
