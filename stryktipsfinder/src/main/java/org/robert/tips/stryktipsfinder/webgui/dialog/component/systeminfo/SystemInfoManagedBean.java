package org.robert.tips.stryktipsfinder.webgui.dialog.component.systeminfo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;
import org.robert.tips.stryktipsfinder.webgui.dialog.component.systeminfo.controller.InitSystemInfoController;
import org.robert.tips.stryktipsfinder.webgui.dialog.controller.Controller;
import org.robert.tips.stryktipsfinder.webgui.session.UserSessionFactory;
import org.robert.tips.stryktipsfinder.webgui.session.UserSessionFactoryImpl;

/**
 * Defines the JSF backing bean for the system info component.
 * 
 * @author Robert G.
 * 
 */
@ManagedBean
@RequestScoped
public class SystemInfoManagedBean implements SystemInfoView {
	private RSystemSearchInfo selectedInfo;
	private String init;

	/**
	 * Init backing bean.
	 */
	public void init() {
		UserSessionFactory userSessionFactory = new UserSessionFactoryImpl();
		Controller controller = new InitSystemInfoController(this,
				userSessionFactory);

		controller.perform();
	}

	@Override
	public void setSystemInfo(RSystemSearchInfo selectedInfo) {
		this.setSelectedInfo(selectedInfo);
	}

	public void setSelectedInfo(RSystemSearchInfo selectedInfo) {
		this.selectedInfo = selectedInfo;
	}

	public RSystemSearchInfo getSelectedInfo() {
		return selectedInfo;
	}

	public void setInit(String init) {
		this.init = init;
	}

	public String getInit() {
		init();
		return init;
	}

	public String joinSearching() {
		return "success";
	}

	public String backToStartDialog() {
		return "success";
	}
}
