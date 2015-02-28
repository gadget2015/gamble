package org.robert.tipsbolag.webgui.session;

import java.io.Serializable;
import java.util.Date;

import org.openid4java.discovery.DiscoveryInformation;
import org.robert.tipsbolag.webgui.domain.model.Spelare;

/**
 * Defines a session storage for an actor, e.g associated with a specific
 * client.
 * 
 * @author Robert Georen.
 * 
 */
public class UserSession implements Serializable {
	private static final long serialVersionUID = 1L;
	private DiscoveryInformation discoveryInformation;
	private String email;
	private String errorMessage;
	private Spelare spelare;

	/**
	 * Used when authenticate with Google, openId.
	 */
	private String assocHandle;
	private Date createdDate;

	public UserSession() {
		this.setCreatedDate(new Date(System.currentTimeMillis()));
	}

	public DiscoveryInformation getOpenIdDiscoveryInformation() {
		return this.discoveryInformation;
	}

	public void setOpenIdDiscoveryInformation(DiscoveryInformation discoveryInfo) {
		this.discoveryInformation = discoveryInfo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public boolean isAuthenticated() {
		if (email != null && email.length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setAssocHandle(String assocHandle) {
		this.assocHandle = assocHandle;
	}

	public String getAssocHandle() {
		return assocHandle;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setSpelare(Spelare spelare) {
		this.spelare = spelare;
	}

	public Spelare getSpelare() {
		return spelare;
	}
}
