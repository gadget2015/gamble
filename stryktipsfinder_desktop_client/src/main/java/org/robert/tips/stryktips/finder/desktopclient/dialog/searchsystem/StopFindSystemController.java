package org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem;

import org.robert.tips.stryktips.finder.desktopclient.Main;
import org.robert.tips.stryktips.finder.desktopclient.dialog.Controller;
import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;

/**
 * Stop the current ongoing search of reduced system.
 * 
 * @author Robert
 * 
 */
public class StopFindSystemController implements Controller {
	SearchLogView searchLogView;
	SystemInfoModel systemInfoModel;

	public StopFindSystemController(SearchLogView searchLogView,
			SystemInfoModel systemInfoModel) {
		this.searchLogView = searchLogView;
		this.systemInfoModel = systemInfoModel;
	}

	@Override
	public String perform() {
		Main.model.currentSearch.stopSearch();
		Main.model.currentSearch.cancel(false);
		Main.model.currentSearch = null;
		searchLogView.textArea.insert(NEW_LINE, 0);
		searchLogView.textArea.insert("End search for reduced system.", 0);
		systemInfoModel.isSearching = Boolean.FALSE;
		
		systemInfoModel.fireStateChanged();
		
		return "success";
	}

}
