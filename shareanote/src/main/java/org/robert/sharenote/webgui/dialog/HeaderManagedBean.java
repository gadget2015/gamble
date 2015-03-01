package org.robert.sharenote.webgui.dialog;

import java.io.IOException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.businessdelegate.impl.NoteBD;
import org.robert.sharenote.webgui.businessdelegate.mock.NoteMockBD;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.session.UserSessionFactory;
import org.robert.sharenote.webgui.session.UserSessionFactoryImpl;

@ManagedBean
@RequestScoped
public class HeaderManagedBean implements HeaderView {
	private boolean showLoginId;
	private boolean showLoginControl;

	@EJB
	NoteBusinessDelegate noteBD = new NoteMockBD();

	/**
	 * Called each time requesting the dialog because of @RequestScope.
	 */
	public HeaderManagedBean() {
		init();
	}

	public void init() {
		UserSessionFactory userSessionFactory = new UserSessionFactoryImpl();
		Controller controller = new InitHeaderController(userSessionFactory,
				this);

		controller.perform();
	}

	@Override
	public void redirectToGoogleAuthentication(String toURL) {
		System.out.println("redirectToGoogleAuthentication:" + toURL);
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.redirect(toURL);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void showLoginId() {
		this.setShowLoginId(true);
	}

	@Override
	public void showLoginControl() {
		this.setShowLoginControl(true);
	}

	public void setShowLoginId(boolean showLoginId) {
		this.showLoginId = showLoginId;
	}

	public boolean getShowLoginId() {
		return showLoginId;
	}

	public String authenticateWithGoogleAccount() {
		UserSessionFactory userSessionFactory = new UserSessionFactoryImpl();
		Controller controller = new AuthenticateByOpenIdController(this,
				noteBD, userSessionFactory);

		return controller.perform();
	}

	public void setShowLoginControl(boolean showLoginControl) {
		this.showLoginControl = showLoginControl;
	}

	public boolean getShowLoginControl() {
		return showLoginControl;
	}

	public String getEmail() {
		UserSessionFactory userSessionFactory = new UserSessionFactoryImpl();

		return userSessionFactory.getUserSession().getEmail();
	}

	@Override
	public String getReturnUrl() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		String serverNameFromRequest = ec.getRequestServerName();

		return serverNameFromRequest;
	}
}
