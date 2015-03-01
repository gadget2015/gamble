package org.robert.tips.stryktipsfinder.webgui.dialog.startdialog.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.tips.stryktipsfinder.webgui.businessdelegate.StryktipsSystemBusinessDelegate;
import org.robert.tips.stryktipsfinder.webgui.businessdelegate.mock.StryktipsSystemMockBD;
import org.robert.tips.stryktipsfinder.webgui.dialog.controller.Controller;
import org.robert.tips.stryktipsfinder.webgui.dialog.startdialog.StartDialogView;
import org.robert.tips.stryktipsfinder.webgui.dialog.startdialog.controller.InitStartDialogController;
import org.robert.tips.stryktipsfinder.webgui.session.UserSessionFactory;
import org.robert.tips.stryktipsfinder.webgui.session.UserSessionFactoryMock;

public class InitStartDialogControllerTest {
	StryktipsSystemBusinessDelegate businessDelegate;
	UserSessionFactory userSessionFactory;
	
	@Before
	public void init() {
		businessDelegate = new StryktipsSystemMockBD();
		this.userSessionFactory = new UserSessionFactoryMock();
	}

	@Test
	public void shouldInitDialog() {
		// Given
		StartDialogView view = mock(StartDialogView.class);
		Controller controller = new InitStartDialogController(businessDelegate,
				view, userSessionFactory);

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);		
		verify(view).setSystems(((StryktipsSystemMockBD) businessDelegate).getlAllRSystems);
	}
}
