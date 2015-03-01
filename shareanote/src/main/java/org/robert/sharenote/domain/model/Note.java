package org.robert.sharenote.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.robert.sharenote.domain.infrastructure.InfrastructureNamedQuery;

/**
 * Defines a reduced Stryktips system.
 * 
 * @author Robert Georen.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = InfrastructureNamedQuery.SEARCH_FOR_NOTE, query = "SELECT note FROM Note note WHERE lower(note.text) LIKE :searchCriteria AND (note.privateAccess = false OR (note.adminUserId = :userId))") })
public class Note implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

	/**
	 * Text in note.
	 */
	@Column(columnDefinition = "VARCHAR(4000)")
	private String text;

	private String adminUserId;
	private boolean privateAccess;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastSaved;
	
	public Note() {
	}

	public Note(long id, String text) {
		this.id = id;
		this.text = text;
	}

	public Note(long id, String text, String adminUserId, boolean privateAccess) {
		this.id = id;
		this.text = text;
		this.adminUserId = adminUserId;
		this.privateAccess = privateAccess;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String toSting() {
		return "Id:" + id + ", text = " + text;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setPrivateAccess(boolean privateAccess) {
		this.privateAccess = privateAccess;
	}

	public boolean getPrivateAccess() {
		return privateAccess;
	}

	public void setLastSaved(Date lastSaved) {
		this.lastSaved = lastSaved;
	}

	public Date getLastSaved() {
		return lastSaved;
	}
}
