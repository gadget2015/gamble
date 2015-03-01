package org.robert.sharenote.webgui.businessdelegate.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.FetchRequest;
import org.robert.common.openid.OpenIdClient;
import org.robert.common.openid.OpenIdClientImpl;
import org.robert.sharenote.domain.infrastructure.NoteNotFoundException;
import org.robert.sharenote.domain.infrastructure.NoteRepository;
import org.robert.sharenote.domain.model.Note;
import org.robert.sharenote.webgui.businessdelegate.AuthenticationResponse;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;

/**
 * Delegates call to repository.
 * 
 * @author Robert
 * 
 */
@Stateless
public class NoteBD implements NoteBusinessDelegate {
	static String HTTPS_WWW_GOOGLE_COM_ACCOUNTS_O8_ID = "https://www.google.com/accounts/o8/id";

	@EJB
	NoteRepository repository;

	/**
	 * EJB 3.x default constructor.
	 */
	public NoteBD() {
	}

	@Override
	public Note getNote(long id) {
		try {
			return repository.findNote(id);
		} catch (NoteNotFoundException e) {
			Note note = new Note();
			note.setText("The note is missing in datastorage.");
			return note;
		}
	}

	@Override
	public Note saveNote(Note note) {
		Note updatedNote = repository.update(note);

		return updatedNote;
	}

	@Override
	public List<Note> searchForNote(String searchCriteria, String userId) {
		return repository.searchForNote(searchCriteria, userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AuthenticationResponse createAuthenticateRequest(String returnURL) {
		OpenIdClient openIdClient = new OpenIdClientImpl();
		List<DiscoveryInformation> discoveries;

		try {
			discoveries = (List<DiscoveryInformation>) openIdClient
					.discover(HTTPS_WWW_GOOGLE_COM_ACCOUNTS_O8_ID);

			DiscoveryInformation discovered = openIdClient
					.associate(discoveries);
			AuthenticationResponse response = new AuthenticationResponse();
			response.discoveryInformation = discovered;
			AuthRequest authReq = openIdClient.authenticate(discovered,
					"http://" + returnURL + "/googleauthentication.servlet");

			FetchRequest fetch = FetchRequest.createFetchRequest();
			fetch.addAttribute("email",
					"http://schema.openid.net/contact/email", // type URI
					true); // required

			// attach the extension to the authentication request
			authReq.addExtension(fetch);

			// Create response
			String serviceURL = authReq.getDestinationUrl(true);
			// Special fix for Google openId usage, see
			// https://developers.google.com/accounts/docs/OpenID
			// serviceURL = serviceURL.replace("ext1", "ax");

			response.authenticationRequest = serviceURL;

			return response;

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public boolean verifyGoogleResponse(String string,
			Map<String, String> parameters, DiscoveryInformation discovered) {

		try {
			OpenIdClient openIdClient = new OpenIdClientImpl();
			boolean result = openIdClient.verifyGoogleResponse(string, parameters,
					discovered);

			return result;
		} catch (Exception e) {
			// An verification exception has occurred.
			return false;
		}
	}
}
