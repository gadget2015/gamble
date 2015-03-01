package org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.RSystemSearchInfo;
import org.robert.tips.stryktips.finder.desktopclient.dialog.Controller;
import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBDMock;

public class InitSystemInfoViewControllerTest {

	@Test
	public void shouldInitView() {
		// Given
		StryktipsFinderBDMock bdMock = new StryktipsFinderBDMock();
		SystemInfoModel model = new SystemInfoModel();
		model.systemId=1967;
		model.searchInfo = new RSystemSearchInfo();
		SystemInfoSubView view = new SystemInfoSubView(model, bdMock);
		model.systemId = 1967;
		Controller controller = new InitSystemInfoViewController(bdMock, model,
				view);

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should get data.", "success", outcome);
		Assert.assertEquals("Should get name of system.", "R-4-2",
				model.searchInfo.getName());
	}
}
