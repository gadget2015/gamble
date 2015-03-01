package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough;

import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;
import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchCallbackHandler;
import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchLogView;

public class SearchCallbackHandlerMock extends SearchCallbackHandler {

	public SearchCallbackHandlerMock(SearchLogView searchLogView,
			SystemInfoModel model) {
		super(searchLogView, model);
	}

	@Override
	public void callback(String message) {
		System.out.println("Callback: " + message);
	}
}
