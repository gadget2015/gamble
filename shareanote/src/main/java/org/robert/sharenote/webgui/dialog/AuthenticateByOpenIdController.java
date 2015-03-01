package org.robert.sharenote.webgui.dialog;

import org.robert.sharenote.webgui.businessdelegate.AuthenticationResponse;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.session.UserSessionFactory;

public class AuthenticateByOpenIdController implements Controller {
	private HeaderView view;
	NoteBusinessDelegate businessDelegate;
	UserSessionFactory userSessionFactory;

	public AuthenticateByOpenIdController(HeaderView view,
			NoteBusinessDelegate businessDelegate,
			UserSessionFactory userSessionFactory) {
		this.view = view;
		this.businessDelegate = businessDelegate;
		this.userSessionFactory = userSessionFactory;
	}

	@Override
	public String perform() {
		// Create authentication service request.
		String retrunUrl = view.getReturnUrl();
		AuthenticationResponse authenticationRequest = businessDelegate
				.createAuthenticateRequest(retrunUrl);
		view.redirectToGoogleAuthentication(authenticationRequest.authenticationRequest);
		userSessionFactory.getUserSession().setOpenIdDiscoveryInformation(
				authenticationRequest.discoveryInformation);
		userSessionFactory.getUserSession().setAssocHandle(
				authenticationRequest.getOpenidAssocHandle());
		System.out.println("Controller, add assoc_handle="
				+ userSessionFactory.getUserSession().getAssocHandle() + ".");
		return "success";
	}

}
