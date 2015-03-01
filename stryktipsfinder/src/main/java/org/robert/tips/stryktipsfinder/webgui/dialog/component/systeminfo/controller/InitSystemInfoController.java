package org.robert.tips.stryktipsfinder.webgui.dialog.component.systeminfo.controller;

import java.util.List;

import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;
import org.robert.tips.stryktipsfinder.webgui.dialog.component.systeminfo.SystemInfoView;
import org.robert.tips.stryktipsfinder.webgui.dialog.controller.Controller;
import org.robert.tips.stryktipsfinder.webgui.session.UserSessionFactory;

/**
 * Load selected system info from actor session and set in view.
 * 
 * @author Robert
 * 
 */
public class InitSystemInfoController implements Controller {
	SystemInfoView view;
	UserSessionFactory userSessionFactory;

	public InitSystemInfoController(SystemInfoView view,
			UserSessionFactory userSessionFactory) {
		this.view = view;
		this.userSessionFactory = userSessionFactory;
	}

	@Override
	public String perform() {
		// Get system info for given id from actors session.
		long systemInfoId =userSessionFactory.getUserSession().getSelectedSystem();
		List<RSystemSearchInfo> allSystems = userSessionFactory
				.getUserSession().getAllSystems();

		for (RSystemSearchInfo systemInfo : allSystems) {
			if (systemInfo.getId() == systemInfoId) {
				// found system, then set it in view.
				view.setSystemInfo(systemInfo);

				return "success";
			}
		}

		return "failure";
	}

}
