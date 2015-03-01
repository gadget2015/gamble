package org.robert.sharenote.webgui.businessdelegate.mock;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.robert.sharenote.domain.model.Note;
import org.robert.sharenote.webgui.businessdelegate.AuthenticationResponse;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;

public class NoteMockBD implements NoteBusinessDelegate {
	static HashMap<String, Note> notes;
	public static long nextId = 10;
	public Object verificationCalled;

	public NoteMockBD() {
		if (notes == null) {
			notes = new HashMap<String, Note>();

			notes.put("323", createNote(323, "hello."));
			notes.put("555", createNote(555, "Sunge mangs."));
			notes.put(
					"1",
					createNote(1,
							"Yes man.Yes man.Yes man.Yes man.Yes man.Yes man.Yes man.Yes man."));
			notes.put(
					"2",
					createNote(
							2,
							"Notes is simple.Notes is simple.Notes is simple.Notes is simple.Notes is simple.Notes is simple.Notes is simple.Notes is simple.Notes is simple.Notes is simple.Notes is simple.Notes is simple.Notes is simple."));
			notes.put("3", createNote(3, "<b>Rubrik</b>Star.star.<br/>star.<br/><br/>star.<br/>star.<br/>star.<br/>star.<br/>star.<br/>star.<br/>star.<br/>star.<br/>star.<br/>star.<br/>star.<br/>star.<br/>star.star.<br/>star.<br/>star.<br/>star.<br/>star.<br/>star."));
			notes.put(
					"4",
					createNote(4,
							"Augusti, Ausgusti, Ausgusti, Ausgusti, <h1>hhhh1<h1>"));
			notes.put(
					"8392",
					new Note(
							8392,
							"Privat anteckning tillhörande kalle.anka@swipnet.se, matlista.",
							"kalle.anka@swipnet.se", true));
			notes.put(
					"8393",
					new Note(
							8393,
							"Anteckning ägs av kalle.anka@swipnet.se men är inte privat.",
							"kalle.anka@swipnet.se", false));
		}
	}

	private Note createNote(long id, String text) {
		Note note;
		note = new Note();
		note.setId(id);
		note.setText(text);
		return note;
	}

	@Override
	public Note getNote(long id) {
		Note note = notes.get(Long.toString(id));

		return note;
	}

	@Override
	public Note saveNote(Note note) {
		if (note.getId() == 0) {
			note.setId(nextId++);
		}

		System.out.println("NoteMockBD.save():" + note.getId() + ", text = "
				+ note.getText());
		notes.put(Long.toString(note.getId()), note);

		return note;
	}

	@Override
	public List<Note> searchForNote(String searchCriteria, String userId) {
		List<Note> searchResult = new ArrayList<Note>();

		for (Note note : notes.values()) {
			if (note.getText().contains(searchCriteria)) {
				Note copy = new Note(note.getId(), note.getText());
				searchResult.add(copy);
			}
		}

		return searchResult;
	}

	@Override
	public AuthenticationResponse createAuthenticateRequest(String returnURL) {
		AuthenticationResponse response = new AuthenticationResponse();
		// String request =
		// "https://www.google.com/accounts/o8/ud?openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.return_to=http%3A%2F%2Fnoterepo.com%2Fgoogleauthentication.servlet&openid.realm=http%3A%2F%2Fnoterepo.com%2Fgoogleauthentication.servlet&openid.assoc_handle=AMlYA9Uy5NifwsuIoNZsWa4CHftxG1P-sNNahC1fz2U6GpOJfDT7eFqyFhvpycRCPbZmoAvD&openid.mode=checkid_setup&openid.ns.ext1=http%3A%2F%2Fopenid.net%2Fsrv%2Fax%2F1.0&openid.ext1.mode=fetch_request&openid.ext1.type.email=http%3A%2F%2Fschema.openid.net%2Fcontact%2Femail&openid.ext1.required=email";
		String request = "http://localhost:1967/sharenote/googlelogin_mock.xhtml?openid.assoc_handle=DSFKJSDK323sDSD&openid.ui=popup";
		// String request = "http://localhost:1967/sharenote/tesd.header";
		response.authenticationRequest = request;
		try {
			response.discoveryInformation = new DiscoveryInformation(new URL(
					"http://noterepo.com"));

		} catch (DiscoveryException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public boolean verifyGoogleResponse(String url,
			Map<String, String> parameters, DiscoveryInformation discovered) {

		if (url.contains("fail.auth")) {
			this.verificationCalled = false;

			return false;
		} else if (url != null && parameters.isEmpty() == false
				&& discovered != null) {
			this.verificationCalled = true;

			return true;
		} else {
			this.verificationCalled = false;

			return false;
		}
	}
}
