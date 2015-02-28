package org.robert.tipsbolag.webgui.dialog;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.robert.tipsbolag.webgui.businessdelegate.AuthenticationResponse;
import org.robert.tipsbolag.webgui.businessdelegate.SpelbolagBusinessDelegate;
import org.robert.tipsbolag.webgui.businessdelegate.mock.SpelbolagBusinessDelegateMock;
import org.robert.tipsbolag.webgui.session.UserSession;
import org.robert.tipsbolag.webgui.session.UserSessionFactory;
import org.robert.tipsbolag.webgui.session.UserSessionFactoryImpl;

@ManagedBean
@RequestScoped
public class HeaderViewModel {
	UserSessionFactory usrFactory = new UserSessionFactoryImpl();
	@EJB
	SpelbolagBusinessDelegate businessDelegate = new SpelbolagBusinessDelegateMock();
	private boolean renderAnvandarIdLabel;
	private UserSession model;

	/**
	 * JSF 2.0 default constuctor.
	 */
	public HeaderViewModel() {
		this.setModel(usrFactory.getUserSession());
	}

	public HeaderViewModel(UserSessionFactory usrFactory) {
		this.usrFactory = usrFactory;
		this.setModel(usrFactory.getUserSession());
	}

	public void loggaInMedGoogleKonto() {
		// Create authentication service request.
		String retrunUrl = getReturnUrl();
		AuthenticationResponse authenticationRequest = businessDelegate
				.createAuthenticateRequest(retrunUrl);

		// Update actors session.
		usrFactory.getUserSession().setOpenIdDiscoveryInformation(
				authenticationRequest.discoveryInformation);
		usrFactory.getUserSession().setAssocHandle(
				authenticationRequest.getOpenidAssocHandle());

		// Go to google login page.
		redirectToGoogleAuthenticationDialog(authenticationRequest);
	}

	public void loggaInMedAOuth2TillGoogle() {
		// Create authentication service request.
		String sessionID = UUID.randomUUID().toString();
		AuthenticationResponse authenticationRequest = businessDelegate
				.createAOuth2AuthenticateRequest("http://www.stryktipsbolag.se/oauth2callback", sessionID);

		// Update actors session with an unique identifier so servlet can find
		// actors session.
		usrFactory.getUserSession().setAssocHandle(sessionID);

		// Go to google login page.
		redirectToGoogleAuthenticationDialog(authenticationRequest);
	}

	protected void redirectToGoogleAuthenticationDialog(
			AuthenticationResponse authenticationRequest) {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.redirect(authenticationRequest.authenticationRequest);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	protected String getReturnUrl() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		String serverNameFromRequest = ec.getRequestServerName();
		// return "83.189.167.74:1967/tips-website";
		return serverNameFromRequest;
	}

	@PostConstruct
	public void initDialog() {
		// 1. Kolla ifall användaren är inloggad.
		UserSession session = usrFactory.getUserSession();

		if (session.isAuthenticated()) {
			this.setRenderAnvandarIdLabel(true);
		} else {
			this.setRenderAnvandarIdLabel(false);
		}
	}

	public boolean getRenderAnvandarIdLabel() {
		return renderAnvandarIdLabel;
	}

	public void setRenderAnvandarIdLabel(boolean renderAnvandarIdLabel) {
		this.renderAnvandarIdLabel = renderAnvandarIdLabel;
	}

	public void setModel(UserSession model) {
		this.model = model;
	}

	public UserSession getModel() {
		return model;
	}
}
