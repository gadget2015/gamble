package org.robert.tips.stryktipsfinder.webgui.dialog.startdialog;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;
import org.robert.tips.stryktipsfinder.webgui.businessdelegate.StryktipsSystemBusinessDelegate;
import org.robert.tips.stryktipsfinder.webgui.businessdelegate.mock.StryktipsSystemMockBD;
import org.robert.tips.stryktipsfinder.webgui.dialog.controller.Controller;
import org.robert.tips.stryktipsfinder.webgui.dialog.startdialog.controller.InitStartDialogController;
import org.robert.tips.stryktipsfinder.webgui.session.UserSessionFactory;
import org.robert.tips.stryktipsfinder.webgui.session.UserSessionFactoryImpl;

/**
 * Defines the JSF backing bean.
 * 
 * @author Robert
 * 
 */
@ManagedBean
@RequestScoped
public class StartDialogManagedBean implements StartDialogView {
	private List<RSystemSearchInfo> systems;
	private String init;

	@ManagedProperty(value = "#{param.systemName}")
	private String systemName;
	
	@EJB
	StryktipsSystemBusinessDelegate stryktipsBD = new StryktipsSystemMockBD();

	public void init() {
		UserSessionFactory userSessionFactory = new UserSessionFactoryImpl();
		Controller controller = new InitStartDialogController(stryktipsBD,
				this, userSessionFactory);

		controller.perform();
	}

	@Override
	public void setSystems(List<RSystemSearchInfo> systems) {
		this.systems = systems;
	}

	public List<RSystemSearchInfo> getSystems() {
		return systems;
	}

	public void setInit(String init) {
		this.init = init;
	}

	public String getInit() {
		init();
		return init;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;

		if (systemName != null) {
			// Update session with selected system

			// Find select system from list of system.
			long id = 0;
			UserSessionFactory userSessionFactory = new UserSessionFactoryImpl();
			for (RSystemSearchInfo systemInfo : userSessionFactory
					.getUserSession().getAllSystems()) {
				if (systemName.equals(systemInfo.getName())) {
					// Found system actor selected.
					id = systemInfo.getId();

					break;
				}
			}
			userSessionFactory.getUserSession().setSelectedSystem(id);

			// Redirect actor to dialog.
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();

			try {
				ec.redirect("randomsystemdialog.xhtml");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public String getSystemName() {
		return systemName;
	}
}
