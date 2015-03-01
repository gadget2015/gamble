package org.robert.sharenote.webgui.dialog;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import org.robert.sharenote.webgui.session.UserSession;
import org.robert.sharenote.webgui.session.UserSessionFactoryImpl;

@ManagedBean
@RequestScoped
public class ErrorDialogManagedBean {
	private UserSession userSession = new UserSessionFactoryImpl()
			.getUserSession();

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public String gotoStartDialog() {
		BrowserCheck checker = new BrowserCheck();

		if (checker.canViewShowInputRichText() == false) {
			return "startdialog_safaribrowser";
		} else {
			return "startdialog_goodbrowser";
		}
	}
}
