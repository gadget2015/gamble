package org.robert.tipsbolag.webgui.dialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.robert.tipsbolag.webgui.businessdelegate.SpelbolagBusinessDelegate;
import org.robert.tipsbolag.webgui.businessdelegate.mock.SpelbolagBusinessDelegateMock;
import org.robert.tipsbolag.webgui.domain.model.Spelare;
import org.robert.tipsbolag.webgui.domain.model.Spelbolag;
import org.robert.tipsbolag.webgui.domain.model.Transaktion;
import org.robert.tipsbolag.webgui.domain.model.UserId;
import org.robert.tipsbolag.webgui.session.UserSession;
import org.robert.tipsbolag.webgui.session.UserSessionFactory;
import org.robert.tipsbolag.webgui.session.UserSessionFactoryImpl;

@ManagedBean
@SessionScoped
public class AdministreraSpelbolagViewModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean renderAuthenticationRequired;
	UserSessionFactory usrFactory = new UserSessionFactoryImpl();
	private UserSession model;
	@EJB
	SpelbolagBusinessDelegate businessDelegate = new SpelbolagBusinessDelegateMock();
	private DataModel<Spelare> spelareUIModel;
	private Spelare valdSpelare;
	private boolean renderValdSpelaresAllaTransaktioner;
	private int debit;
	private int kredit;
	private String beskrivning;
	private Date transaktionTid;
	private String init;
	private boolean authenticatedAsAdmin;

	public AdministreraSpelbolagViewModel() {
		this.model = usrFactory.getUserSession();
	}

	public AdministreraSpelbolagViewModel(
			UserSessionFactory userSessionFactoryMock) {
		this.usrFactory = userSessionFactoryMock;
		this.model = usrFactory.getUserSession();
	}

	public void initDialog() {
		// 1. Kolla ifall användaren är inloggad.
		UserSession session = usrFactory.getUserSession();

		if (session.isAuthenticated()) {
			this.setRenderAuthenticationRequired(false);
			this.setAuthenticatedAsAdmin(true);
		} else {
			this.setRenderAuthenticationRequired(true);
		}

		// 2. Om inloggad så ska den spelarens data läsas upp.
		if (session.isAuthenticated()) {
			Spelare spelare = businessDelegate.hamtaSpelare(new UserId(session
					.getEmail()));
			session.setSpelare(spelare);
			if (spelare.isAdminUser()) {
				this.setAuthenticatedAsAdmin(true);
			} else {
				this.setAuthenticatedAsAdmin(false);
				return;
			}

			// Fixar till GUI model för lista med spelare som ingår i
			// spelbolaget.
			ArrayList<Spelare> allaSpelare = new ArrayList<Spelare>();
			allaSpelare.addAll(spelare.getAdministratorForSpelbolag()
					.getSpelare());
			spelareUIModel = new ListDataModel<Spelare>(allaSpelare);
		}
	}

	public boolean getRenderAuthenticationRequired() {
		return renderAuthenticationRequired;
	}

	public void setRenderAuthenticationRequired(
			boolean renderAuthenticationRequired) {
		this.renderAuthenticationRequired = renderAuthenticationRequired;
	}

	public void setModel(UserSession model) {
		this.model = model;
	}

	public UserSession getModel() {
		return model;
	}

	public void setSpelareUIModel(DataModel<Spelare> spelareUIModel) {
		this.spelareUIModel = spelareUIModel;
	}

	public DataModel<Spelare> getSpelareUIModel() {
		return spelareUIModel;
	}

	public void valjEnSpelare() {
		Spelare valdSpelare = getSpelareUIModel().getRowData();
		this.valdSpelare = businessDelegate.hamtaSpelare(valdSpelare
				.getUserId());
		setRenderValdSpelaresAllaTransaktioner(true);
	}

	public void setValdSpelare(Spelare valdSpelare) {
		this.valdSpelare = valdSpelare;
	}

	public Spelare getValdSpelare() {
		return valdSpelare;
	}

	public boolean getRenderValdSpelaresAllaTransaktioner() {
		return this.renderValdSpelaresAllaTransaktioner;
	}

	public void setRenderValdSpelaresAllaTransaktioner(
			boolean renderValdSpelaresAllaTransaktioner) {
		this.renderValdSpelaresAllaTransaktioner = renderValdSpelaresAllaTransaktioner;
	}

	public void stangTransaktionlistan() {
		setRenderValdSpelaresAllaTransaktioner(false);
	}

	public void laggTillEnNyTransaktionForValdSpelare() {
		Transaktion nyTransaktion = new Transaktion(getDebit(), getKredit(),
				getBeskrivning(), getTransaktionTid());
		valdSpelare = businessDelegate.laggTillTransaktionForSpelare(
				valdSpelare.getUserId(), nyTransaktion);
		setRenderValdSpelaresAllaTransaktioner(false);
	}

	public void setDebit(int debit) {
		this.debit = debit;
	}

	public int getDebit() {
		return debit;
	}

	public void setKredit(int kredit) {
		this.kredit = kredit;
	}

	public int getKredit() {
		return kredit;
	}

	public void setBeskrivning(String beskrivning) {
		this.beskrivning = beskrivning;
	}

	public String getBeskrivning() {
		return beskrivning;
	}

	public void setTransaktionTid(Date transaktionTid) {
		this.transaktionTid = transaktionTid;
	}

	public Date getTransaktionTid() {
		return transaktionTid;
	}

	public void laggTilEnNyTransaktionForValtSpelbolag() {
		Transaktion nyTransaktion = new Transaktion(getDebit(), getKredit(),
				getBeskrivning(), getTransaktionTid());

		Spelbolag aktuelltSpelbolag = businessDelegate
				.laggTillTransaktionTillSpelbolag(model.getSpelare()
						.getAdministratorForSpelbolag().getId(), nyTransaktion);

		model.getSpelare().setAdministratorForSpelbolag(aktuelltSpelbolag);
	}

	public void taBetaltAvAllaSpelare() {
		Spelbolag aktuelltSpelbolag = businessDelegate.taBetaltAvAllaSpelare(
				model.getSpelare().getAdministratorForSpelbolag().getId(),
				transaktionTid);
		model.getSpelare().setAdministratorForSpelbolag(aktuelltSpelbolag);
	}

	public void setInit(String init) {
		this.init = init;
	}

	public String getInit() {
		initDialog();
		return init;
	}

	public void setAuthenticatedAsAdmin(boolean authenticatedAsAdmin) {
		this.authenticatedAsAdmin = authenticatedAsAdmin;
	}

	public boolean getAuthenticatedAsAdmin() {
		return authenticatedAsAdmin;
	}
}
