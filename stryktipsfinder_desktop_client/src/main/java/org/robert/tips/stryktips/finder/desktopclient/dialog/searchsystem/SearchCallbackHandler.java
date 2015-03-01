package org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem;

import org.robert.tips.stryktips.finder.desktopclient.dialog.Controller;
import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;

/**
 * Used to catch messages sent from algorithm.
 * 
 * @author Robert
 * 
 */
public class SearchCallbackHandler {
	public String message = "";
	SearchLogView searchLogView;
	SystemInfoModel model;
	public AlgorithmContext context;

	public SearchCallbackHandler(SearchLogView searchLogView,
			SystemInfoModel model) {
		this.searchLogView = searchLogView;
		this.model = model;
	}

	public void callback(String message) {
		this.message = message;

		if (searchLogView.textArea.getLineCount() > 100) {
			// Reset log view.
			searchLogView.textArea.setText("");
		}

		searchLogView.textArea.insert(Controller.NEW_LINE, 0);
		searchLogView.textArea.insert(message, 0);

		model.searchInfo = context.systemAtServer;
		model.rowsInSystemUnderTest = (context.systemUnderVerification != null)? context.systemUnderVerification.size():0;
		model.fireStateChanged();
	}

	public void setSearchSpeed(int searchSpeed) {
		model.currentSpeed = searchSpeed;
		model.fireStateChanged();
	}

	public void endSearch() {
		Controller controller = new StopFindSystemController(searchLogView,
				model);
		controller.perform();
	}
}
