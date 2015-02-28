package org.robert.tipsbolag.webgui.dialog;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import org.robert.tipsbolag.webgui.businessdelegate.SpelbolagBusinessDelegate;
import org.robert.tipsbolag.webgui.businessdelegate.mock.SpelbolagBusinessDelegateMock;
import org.robert.tipsbolag.webgui.domain.model.Spelare;
import org.robert.tipsbolag.webgui.domain.model.UserId;
import org.robert.tipsbolag.webgui.session.UserSession;
import org.robert.tipsbolag.webgui.session.UserSessionFactory;
import org.robert.tipsbolag.webgui.session.UserSessionFactoryImpl;

@ManagedBean
@RequestScoped
public class MittSaldoViewModel {
	private boolean renderAuthenticationRequired;
	private boolean renderMittSaldo;
	UserSessionFactory usrFactory = new UserSessionFactoryImpl();
	private UserSession model;

	@EJB
	SpelbolagBusinessDelegate businessDelegate = new SpelbolagBusinessDelegateMock();
	private String init;

	public MittSaldoViewModel() {
		initDialog();
		this.setModel(usrFactory.getUserSession());
	}

	public MittSaldoViewModel(UserSessionFactory usrFactory) {
		this.usrFactory = usrFactory;
		this.setModel(usrFactory.getUserSession());
		initDialog();
	}

	public void initDialog() {
		// 1. Kolla ifall användaren är inloggad.
		UserSession session = usrFactory.getUserSession();

		if (session.isAuthenticated()) {
			// Visa saldo
			this.setRenderAuthenticationRequired(false);
			this.setRenderMittSaldo(true);
		} else {
			// Visa inloggningslänk.
			this.setRenderAuthenticationRequired(true);
			this.setRenderMittSaldo(false);
		}

		// 2. Om inloggad så ska den spelarens data läsas upp.
		if (session.isAuthenticated()) {
			UserId userId = new UserId(session.getEmail());
			Spelare spelare = businessDelegate.hamtaSpelare(userId);
			session.setSpelare(spelare);
		}
	}

	public void setInit(String init) {
		this.init = init;
	}

	public String getInit() {
		initDialog();
		return init;
	}

	public void setModel(UserSession model) {
		this.model = model;
	}

	public UserSession getModel() {
		return model;
	}

	public void setRenderMittSaldo(boolean renderMittSaldo) {
		this.renderMittSaldo = renderMittSaldo;
	}

	public boolean getRenderMittSaldo() {
		return renderMittSaldo;
	}

	public void setRenderAuthenticationRequired(boolean renderAuthenticationRequired) {
		this.renderAuthenticationRequired = renderAuthenticationRequired;
	}

	public boolean getRenderAuthenticationRequired() {
		return renderAuthenticationRequired;
	}
}
