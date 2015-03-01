package org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.tips.stryktips.finder.desktopclient.Main;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.dialog.Controller;
import org.robert.tips.stryktips.finder.desktopclient.dialog.Model;
import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBDMock;

public class StartFindSystemControllerTest {

	@Test
	public void shouldStartFindingSystem() throws TechnicalException {
		// Given
		StryktipsFinderBD bd = new StryktipsFinderBDMock();
		SystemInfoModel systemInfoModel = new SystemInfoModel();
		systemInfoModel.searchInfo = bd.getSystemInfoWithOnlyLatestSystem(1967);
		Controller controller = new StartFindSystemController(
				new SearchLogView(), bd, systemInfoModel);
		int threadsBefore = numberOfThread();
		Main.model = new Model();
		Main.model.systemId = 1967;

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should be ok", "success", outcome);
		int threadsAfter = numberOfThread();
		Assert.assertEquals("Should start a new thread.", threadsBefore + 1,
				threadsAfter);
		Assert.assertEquals("Should change state of start/stop-button.", Boolean.TRUE, systemInfoModel.isSearching);
	}

	public int numberOfThread() {
		ThreadGroup tg = Thread.currentThread().getThreadGroup();
		while (tg.getParent() != null) {
			tg = tg.getParent();
		}

		Thread[] t = new Thread[tg.activeCount()];
		int numberOfThreads = tg.enumerate(t);

		return numberOfThreads;
	}
}
