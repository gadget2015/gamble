package org.robert.sharenote.webgui.session;

import java.io.Serializable;
import java.util.Date;

import org.openid4java.discovery.DiscoveryInformation;
import org.robert.sharenote.domain.model.Note;

/**
 * Defines a session storage for an actor, e.g this class is associated with a specific
 * client.
 * 
 * @author Robert Georen.
 * 
 */
public class UserSession implements Serializable {
	private static final long serialVersionUID = 1L;
	private Note note;
	private DiscoveryInformation discoveryInformation;
	private String email;
	private String errorMessage;
	/**
	 * Used when authenticate with Google, openId.
	 */
	private String assocHandle;
	private Date createdDate;
	private String jSessionId;

	public UserSession() {
		this.setCreatedDate(new Date(System.currentTimeMillis()));
	}
	
	public void setNote(Note note) {
		this.note = note;
	}

	public Note getNote() {
		return note;
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

	public boolean hasAdminAccessToCurrentNote() {
		if (note.getAdminUserId() != null && email != null
				&& note.getAdminUserId().equals(email)) {
			return true;
		} else if (note.getAdminUserId() == null && email != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasAccessToCurrentNote() {
		if (note.getAdminUserId() != null && note.getPrivateAccess()
				&& email != null && note.getAdminUserId().equals(email)) {
			return true;
		} else if (note.getPrivateAccess() == false) {
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

	public void setjSessionId(String jSessionId) {
		this.jSessionId = jSessionId;
	}

	public String getjSessionId() {
		return jSessionId;
	}

}
