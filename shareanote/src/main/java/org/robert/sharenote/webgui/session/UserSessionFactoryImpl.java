package org.robert.sharenote.webgui.session;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserSessionFactoryImpl implements UserSessionFactory {
	Logger logger = LoggerFactory.getLogger(UserSessionFactoryImpl.class);
	public static String SESSION_ATTRIBUTE_FOR_MANAGED_BEAN = "userSessionImpl";

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
			logger.debug("Create a new session for actor.");
		}

		return userSessionStorage;
	}
}
