package org.robert.tipsbolag.webgui.domain.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userId;

	public UserId() {
	}

	public UserId(String userId) {
		this.userId = userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
}
