package org.robert.sharenote.webgui.dialog.help;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import org.robert.sharenote.webgui.dialog.BrowserCheck;

@ManagedBean
@RequestScoped
public class HelpDialogManagedBean {

	public String gotoStartDialog() {
		BrowserCheck checker = new BrowserCheck();

		if (checker.canViewShowInputRichText() == false) {
			return "startdialog_safaribrowser";
		} else {
			return "startdialog_goodbrowser";
		}
	}
}
