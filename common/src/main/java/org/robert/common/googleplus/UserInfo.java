package org.robert.common.googleplus;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserInfo {
	private String id;
	private String email;
	private String verified_email;
	private String name;
	private String given_name;
	private String family_name;
	private String link;
	private String picture;
	private String gender;
	private String error;

	public UserInfo(String JSON) {
		JSONParser parser = new JSONParser();
		this.email = parser.getJSON_Attribute(JSON, "email");
		this.error = parser.getJSON_Attribute(JSON, "error");
		this.name = parser.getJSON_Attribute(JSON, "name");
		this.given_name = parser.getJSON_Attribute(JSON, "given_name");
	}

	public UserInfo() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVerified_email() {
		return verified_email;
	}

	public void setVerified_email(String verified_email) {
		this.verified_email = verified_email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id:" + getId());
		sb.append(System.getProperty("line.separator"));
		sb.append("email:" + getEmail());
		sb.append(System.getProperty("line.separator"));
		sb.append("verified_email:" + getVerified_email());
		sb.append(System.getProperty("line.separator"));
		sb.append("name:" + getName());
		sb.append(System.getProperty("line.separator"));
		sb.append("given_name:" + getGiven_name());
		sb.append(System.getProperty("line.separator"));
		sb.append("family_name:" + getFamily_name());
		sb.append(System.getProperty("line.separator"));
		sb.append("link:" + getLink());
		sb.append(System.getProperty("line.separator"));
		sb.append("picture:" + getPicture());
		sb.append(System.getProperty("line.separator"));
		sb.append("gender:" + getGender());
		sb.append("error:" + getError());

		return sb.toString();
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
