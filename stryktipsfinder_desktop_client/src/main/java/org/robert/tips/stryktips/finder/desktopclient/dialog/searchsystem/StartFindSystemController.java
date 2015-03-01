package org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem;

import javax.swing.SwingWorker;

import org.robert.tips.stryktips.finder.desktopclient.Main;
import org.robert.tips.stryktips.finder.desktopclient.dialog.Controller;
import org.robert.tips.stryktips.finder.desktopclient.dialog.Model;
import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;

public class StartFindSystemController implements Controller {
	SearchLogView searchLogView;
	StryktipsFinderBD bd;
	SystemInfoModel systemInfoModel;

	public StartFindSystemController(SearchLogView searchLogView,
			StryktipsFinderBD bd, SystemInfoModel systemInfoModel) {
		this.searchLogView = searchLogView;
		this.bd = bd;
		this.systemInfoModel = systemInfoModel;
	}

	@Override
	public String perform() {
		// Start a new thread.
		searchLogView.textArea.setText("Start searching ...");
		searchLogView.textArea.append(NEW_LINE);

		SearchCallbackHandler callbackHandler = new SearchCallbackHandler(
				searchLogView, systemInfoModel);

		Model theModel = Main.model;
		long systemID = theModel.systemId;

		// Create worker and start worker
		@SuppressWarnings("rawtypes")
		SwingWorker worker = new FindStryktipsSystemRunnable(bd, systemID,
				callbackHandler,
				systemInfoModel.searchInfo.getTypeOfAlgorithm());
		worker.execute();

		// Upadet model
		Main.model.currentSearch = (FindStryktipsSystemRunnable) worker;
		systemInfoModel.isSearching = Boolean.TRUE;
		systemInfoModel.fireStateChanged();
		
		return "success";
	}
}
