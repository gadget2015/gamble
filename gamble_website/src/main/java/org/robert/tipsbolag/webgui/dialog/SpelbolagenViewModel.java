package org.robert.tipsbolag.webgui.dialog;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.robert.tipsbolag.webgui.businessdelegate.SpelbolagBusinessDelegate;
import org.robert.tipsbolag.webgui.businessdelegate.mock.SpelbolagBusinessDelegateMock;
import org.robert.tipsbolag.webgui.domain.model.Spelbolag;
import org.robert.tipsbolag.webgui.domain.model.Transaktion;
import org.robert.tipsbolag.webgui.session.UserSession;
import org.robert.tipsbolag.webgui.session.UserSessionFactory;
import org.robert.tipsbolag.webgui.session.UserSessionFactoryImpl;

@ManagedBean
@CustomScoped(value = "#{window}")
public class SpelbolagenViewModel {
	private UserSession model;
	UserSessionFactory usrFactory = new UserSessionFactoryImpl();
	private String init;
	private DataModel<Spelbolag> spelbolagModel;
	private boolean renderSpelbolagInformation;
	private List<Transaktion> allaTransaktioner;
	private Spelbolag valtSpelbolag;
	
	@EJB
	SpelbolagBusinessDelegate businessDelegate = new SpelbolagBusinessDelegateMock();

	public SpelbolagenViewModel() {
		this.model = usrFactory.getUserSession();
	}

	public SpelbolagenViewModel(UserSessionFactory userSessionFactory) {
		this.usrFactory = userSessionFactory;
		this.model = userSessionFactory.getUserSession();
	}

	public void initDialog() {
		List<Spelbolag> spelbolag = businessDelegate.hamtaAllaSpelbolag();
		spelbolagModel = new ListDataModel<Spelbolag>(spelbolag);
	}

	public void setModel(UserSession model) {
		this.model = model;
	}

	public UserSession getModel() {
		return model;
	}

	public void setInit(String init) {
		this.init = init;
	}

	public String getInit() {
		initDialog();
		return init;
	}

	public void valjEttSpelbolag() {
		this.renderSpelbolagInformation = true;
		this.valtSpelbolag = getSpelbolagModel().getRowData();
		this.allaTransaktioner = valtSpelbolag.getKonto()
				.getSummeratransaktion();
	}

	public void setSpelbolagModel(DataModel<Spelbolag> spelbolagModel) {
		this.spelbolagModel = spelbolagModel;
	}

	public DataModel<Spelbolag> getSpelbolagModel() {
		return spelbolagModel;
	}

	public boolean getRenderSpelbolagInformation() {
		return renderSpelbolagInformation;
	}

	public List<Transaktion> getAllaTransaktioner() {
		return allaTransaktioner;
	}

	public void setValtSpelbolag(Spelbolag valtSpelbolag) {
		this.valtSpelbolag = valtSpelbolag;
	}

	public Spelbolag getValtSpelbolag() {
		return valtSpelbolag;
	}
}
