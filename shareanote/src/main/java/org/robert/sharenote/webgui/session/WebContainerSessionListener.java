package org.robert.sharenote.webgui.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Lyssnar på sessionshanteringen i en webcontainer.
 */
public class WebContainerSessionListener implements HttpSessionListener {
	private static Map<String, Object> allActiveSessions = new HashMap<String, Object>();

	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("Session Created: " + event.getSession().getId());

		allActiveSessions.put(event.getSession().getId(), event.getSession());
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("Session Destroyed: " + event.getSession().getId());

		allActiveSessions.remove(event.getSession().getId());
	}

	public static List<HttpSession> getActiveSessions() {
		List<HttpSession> activeSessions = new ArrayList<HttpSession>();

		for (Object value : allActiveSessions.values()) {
			activeSessions.add((HttpSession) value);
		}

		return activeSessions;
	}

}