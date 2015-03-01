package org.robert.sharenote.webgui.dialog;

import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.session.UserSessionFactory;

public class InitHeaderController implements Controller {

	private HeaderView view;
	UserSessionFactory userSessionFactory;

	public InitHeaderController(UserSessionFactory userSessionFactory,
			HeaderView view) {
		this.view = view;
		this.userSessionFactory = userSessionFactory;
	}

	@Override
	public String perform() {
		// Check if actor is authenticated
		boolean isAuthenticated = userSessionFactory.getUserSession()
				.isAuthenticated();

		if (isAuthenticated) {
			// Actor is authenticated
			view.showLoginId();
		} else {
			view.showLoginControl();
		}

		return "success";
	}

}
