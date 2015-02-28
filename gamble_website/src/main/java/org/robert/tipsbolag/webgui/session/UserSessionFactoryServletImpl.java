package org.robert.tipsbolag.webgui.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User session factory used from within Servlets.
 * 
 * @author Robert
 */
public class UserSessionFactoryServletImpl implements UserSessionFactory {
	HttpServletRequest request;
	Logger logger = LoggerFactory
			.getLogger(UserSessionFactoryServletImpl.class);
	ServletContext servletContext;
	String assoc_handle;

	public UserSessionFactoryServletImpl(HttpServletRequest request,
			ServletContext servletContext, String assoc_handle) {
		this.request = request;
		this.servletContext = servletContext;
		this.assoc_handle = assoc_handle;
	}

	/**
	 * Get a session associated with given assoc_handle. There could be more
	 * assoc_handles in actors session because they are not unique per
	 * authentication request to google.
	 */
	@Override
	public UserSession getUserSession() {
		// 1. Add all user session objects with same assoc_handle to list.
		List<UserSession> userSessions = new ArrayList<UserSession>();
		logger.info("UserSessionFactory: # active HTTP sessions:"
				+ WebContainerSessionListener.getActiveSessions().size() + ".");
		for (HttpSession webSession : WebContainerSessionListener
				.getActiveSessions()) {
			Object userSessionStorage = webSession
					.getAttribute(UserSessionFactoryImpl.SESSION_ATTRIBUTE_FOR_MANAGED_BEAN);
			logger.info("Found correct type of session:"
					+ userSessionStorage);
			if (userSessionStorage instanceof UserSessionImpl) {
				// It's a session object.
				UserSession userSession = (UserSession) userSessionStorage;
				String toCompareAssoc_handle = userSession.getAssocHandle();

				if (toCompareAssoc_handle == null) {
					logger.debug("assoc_handle is null.");
				} else if (this.assoc_handle.equals(toCompareAssoc_handle)) {
					// Found a candidate session.
					userSessions.add(userSession);
				} else {
					logger.debug("Not equal:" + this.assoc_handle + "!="
							+ toCompareAssoc_handle);
				}
			}
		}

		// 2. Check so that at least one session is found.
		if (userSessions.size() == 0) {
			logger.error("Can't find any user session associated with assoc_handler = "
					+ assoc_handle + ".");
			return new UserSessionImpl();
		} else {
			// 2. sort list of user sessions and return last one.
			Collections.sort(userSessions, new UserSessionComparator());
			UserSession foundUserSession = userSessions.get(0);
			logger.debug("Found latest user session: assoc_handle = "
					+ foundUserSession.getAssocHandle());
			return foundUserSession;
		}

	}
}
