package org.robert.sharenote.webgui.businessdelegate;

import java.util.List;
import java.util.Map;

import org.openid4java.discovery.DiscoveryInformation;
import org.robert.sharenote.domain.model.Note;

/**
 * Defines operations on business methods.
 * 
 * @author Robert
 * 
 */
public interface NoteBusinessDelegate {
	public Note getNote(long id);

	public Note saveNote(Note note);

	public List<Note> searchForNote(String searchCriteria, String userId);

	public AuthenticationResponse createAuthenticateRequest(String returnURL);

	public boolean verifyGoogleResponse(String string,
			Map<String, String> parameters, DiscoveryInformation discovered);
}
