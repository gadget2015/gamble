package org.robert.sharenote.webgui.session;

import javax.servlet.http.Cookie;

public class CookieUtil {
	public String getJavaSessionId(Cookie[] cookies) {
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if ("JSESSIONID".equals(cookie.getName())) {
					String id = cookie.getValue();
					return id;
				}
			}
		}

		return "";
	}
	
	public void printCookies(Cookie[] cookies) {
		System.out.println("Print cookies:");
		if (cookies != null) {
			System.out.println("Number of cookes = " + cookies.length);
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				System.out.println("Cookie: domain=" + cookie.getDomain()
						+ ", path=" + cookie.getPath() + ", name="
						+ cookie.getName() + ", value=" + cookie.getValue());
			}
		}
	}
}
