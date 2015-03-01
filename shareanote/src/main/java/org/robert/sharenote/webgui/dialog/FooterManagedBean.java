package org.robert.sharenote.webgui.dialog;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@RequestScoped
public class FooterManagedBean {

	public String gotoHelpDialog() {
			return "helpdialog";
	}
}
