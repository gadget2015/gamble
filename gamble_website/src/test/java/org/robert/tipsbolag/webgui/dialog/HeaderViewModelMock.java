package org.robert.tipsbolag.webgui.dialog;

import org.robert.tipsbolag.webgui.businessdelegate.AuthenticationResponse;
import org.robert.tipsbolag.webgui.session.UserSessionFactoryMock;

public class HeaderViewModelMock extends HeaderViewModel{
	public boolean redirectToGoogle;
	
	public HeaderViewModelMock(UserSessionFactoryMock mock) {
		super(mock);
	}
	@Override
	protected String getReturnUrl() {
		return "http://www.tipsbolag.se";
	}

	@Override
	protected void redirectToGoogleAuthenticationDialog(
			AuthenticationResponse authenticationRequest) {
		redirectToGoogle = true;
	}

}
