package org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem;

import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.RSystemSearchInfo;
import org.robert.tips.stryktips.finder.desktopclient.dialog.Controller;
import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;

/**
 * Init the search info view. Calls business tier and get system information
 * data.
 * 
 * @author Robert
 * 
 */
public class InitSystemInfoViewController implements Controller {
	StryktipsFinderBD businessDelegate;
	SystemInfoModel model;
	SystemInfoSubView view;

	public InitSystemInfoViewController(StryktipsFinderBD businessDelegate,
			SystemInfoModel model, SystemInfoSubView view) {
		this.businessDelegate = businessDelegate;
		this.model = model;
		this.view = view;
	}

	@Override
	public String perform() {
		RSystemSearchInfo searchInfo;
		try {
			searchInfo = businessDelegate
					.getSystemInfoWithOnlyLatestSystem(model.systemId);
			
			model.searchInfo = searchInfo;
			model.fireStateChanged();

			return "success";
		} catch (TechnicalException e) {
			view.showTechnicalException(e.getMessage());
			
			return "fail";
		}
	}
}
