package org.robert.sharenote.webgui.dialog;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class BrowserCheck {
	public boolean canViewShowInputRichText() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		return canViewShowInputRichText(request);
	}

	public boolean canViewShowInputRichText(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		int isSafari = userAgent.indexOf("Safari");

		if (isSafari == -1) {
			// It's NOT a safari browser
			return true;
		} else {
			return false;
		}
	}
}
