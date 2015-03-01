package org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.tips.stryktips.finder.desktopclient.Main;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.Algorithm;
import org.robert.tips.stryktips.finder.desktopclient.dialog.Controller;
import org.robert.tips.stryktips.finder.desktopclient.dialog.Model;
import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBDMock;

public class StopFindSystemControllerTest {
	StartFindSystemControllerTest util = new StartFindSystemControllerTest();
	StryktipsFinderBDMock bd = new StryktipsFinderBDMock();
	SearchCallbackHandler callbackHandler = new SearchCallbackHandler(
			new SearchLogView(), new SystemInfoModel());

	@Test
	public void shouldStopSystemSearchThread() throws Throwable {
		// Given
		Main.model = new Model();
		Main.model.threadPriority = Thread.NORM_PRIORITY;
		Runnable runnable = new FindStryktipsSystemRunnable(bd, 1967,
				callbackHandler, Algorithm.RANDOM);
		Thread t = new Thread(runnable);
		t.start();

		Main.model.currentSearch = (FindStryktipsSystemRunnable) runnable;
		SystemInfoModel systemInfoModel = new SystemInfoModel();
		Controller controller = new StopFindSystemController(
				new SearchLogView(), systemInfoModel);

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should be ok", "success", outcome);
		Assert.assertNull(Main.model.currentSearch);
		Assert.assertEquals("Should change state of start/stop-button.", Boolean.FALSE,
				systemInfoModel.isSearching);
	}
}
