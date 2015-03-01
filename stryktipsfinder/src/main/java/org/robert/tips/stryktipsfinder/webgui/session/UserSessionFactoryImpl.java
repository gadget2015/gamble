package org.robert.tips.stryktipsfinder.webgui.session;

import javax.faces.context.FacesContext;

public class UserSessionFactoryImpl implements UserSessionFactory {
	private static String SESSION_ATTRIBUTE_FOR_MANAGED_BEAN = "userSessionImpl";

	/**
	 * Get a user session object from JSF, e.g from web container. If none
	 * already exists, create one.
	 */
	@Override
	public UserSession getUserSession() {
		UserSessionImpl userSessionStorage = (UserSessionImpl) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get(SESSION_ATTRIBUTE_FOR_MANAGED_BEAN);

		if (userSessionStorage == null) {
			userSessionStorage = new UserSessionImpl();
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getSessionMap()
					.put(SESSION_ATTRIBUTE_FOR_MANAGED_BEAN, userSessionStorage);
		}

		return userSessionStorage;
	}
}
