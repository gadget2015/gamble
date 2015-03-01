package org.robert.tips.stryktipsfinder.webgui.dialog.component.systeminfo.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;
import org.robert.tips.stryktipsfinder.webgui.businessdelegate.StryktipsSystemBusinessDelegate;
import org.robert.tips.stryktipsfinder.webgui.businessdelegate.mock.StryktipsSystemMockBD;
import org.robert.tips.stryktipsfinder.webgui.dialog.component.systeminfo.SystemInfoView;
import org.robert.tips.stryktipsfinder.webgui.dialog.component.systeminfo.controller.InitSystemInfoController;
import org.robert.tips.stryktipsfinder.webgui.dialog.controller.Controller;
import org.robert.tips.stryktipsfinder.webgui.session.UserSessionFactory;
import org.robert.tips.stryktipsfinder.webgui.session.UserSessionFactoryMock;

public class InitSystemInfoControllerTest {
	StryktipsSystemBusinessDelegate businessDelegate;
	UserSessionFactory userSessionFactory;
	
	@Before
	public void init() {
		businessDelegate =new StryktipsSystemMockBD();
		this.userSessionFactory = new UserSessionFactoryMock();
	}

	@Test
	public void shouldLoadSystem() {
		// Given
		SystemInfoView view = mock(SystemInfoView.class);		
		Controller controller = new InitSystemInfoController(view,
				userSessionFactory);
		List<RSystemSearchInfo> allSystems = businessDelegate.getAllRSystems();
		userSessionFactory.getUserSession().setAllSystems(allSystems);
		userSessionFactory.getUserSession().setSelectedSystem(1);
		RSystemSearchInfo selectedInfo = null;

		for (RSystemSearchInfo info : allSystems) {
			if (info.getId() == 1) {
				selectedInfo = info;
			}
		}

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);
		verify(view).setSystemInfo(selectedInfo);
	}
	
	@Test
	public void shouldNotFoundSystemToLoad() {
		// Given
		SystemInfoView view = mock(SystemInfoView.class);		
		Controller controller = new InitSystemInfoController(view,
				userSessionFactory);
		List<RSystemSearchInfo> allSystems = businessDelegate.getAllRSystems();
		userSessionFactory.getUserSession().setAllSystems(allSystems);
		userSessionFactory.getUserSession().setSelectedSystem(9);

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should fail load system.", "failure", outcome);
	}
}
