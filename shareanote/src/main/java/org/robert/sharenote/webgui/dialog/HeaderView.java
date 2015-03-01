package org.robert.sharenote.webgui.dialog;

public interface HeaderView {

	void redirectToGoogleAuthentication(String string);

	void showLoginId();

	void showLoginControl();

	/**
	 * Return the URL actor have entered in browser, e.g either www.noterepo.com
	 * or noterepo.com. This is used as return URL when authenticate actor and
	 * is important to be able to get same JSF session.
	 */
	String getReturnUrl();
}
