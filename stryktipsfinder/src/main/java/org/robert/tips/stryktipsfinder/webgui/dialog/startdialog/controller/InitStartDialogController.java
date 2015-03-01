package org.robert.tips.stryktipsfinder.webgui.dialog.startdialog.controller;

import java.util.List;

import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;
import org.robert.tips.stryktipsfinder.webgui.businessdelegate.StryktipsSystemBusinessDelegate;
import org.robert.tips.stryktipsfinder.webgui.dialog.controller.Controller;
import org.robert.tips.stryktipsfinder.webgui.dialog.startdialog.StartDialogView;
import org.robert.tips.stryktipsfinder.webgui.session.UserSessionFactory;

/**
 * Get all stryktips systems from database.
 * 
 * @author Robert
 * 
 */
public class InitStartDialogController implements Controller {
	private StryktipsSystemBusinessDelegate businessDelegate;
	private StartDialogView view;
	UserSessionFactory userSessionFactory;
	
	public InitStartDialogController(StryktipsSystemBusinessDelegate bd,
			StartDialogView view, UserSessionFactory userSessionFactory) {
		this.businessDelegate = bd;
		this.view = view;
		this.userSessionFactory = userSessionFactory;
	}

	@Override
	public String perform() {
		List<RSystemSearchInfo> systems = businessDelegate.getAllRSystems();

		view.setSystems(systems);
		userSessionFactory.getUserSession().setAllSystems(systems);

		
		return "success";
	}

}
