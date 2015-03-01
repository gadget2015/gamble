package org.robert.sharenote.webgui.dialog;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.session.UserSessionFactory;
import org.robert.sharenote.webgui.session.UserSessionFactoryMock;

public class InitHeaderViewTest {
	NoteBusinessDelegate businessDelegate;
	UserSessionFactory userSessionFactory;

	@Before
	public void init() {
		this.businessDelegate = mock(NoteBusinessDelegate.class);
		this.userSessionFactory = new UserSessionFactoryMock();
	}

	@Test
	public void shouldInitHeadViewWhenActorIsAuthenticated() {
		// Given
		HeaderView view = mock(HeaderView.class);
		Controller controller = new InitHeaderController(userSessionFactory,
				view);
		userSessionFactory.getUserSession().setEmail("robert.georen@gmail.com");

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);
		verify(view).showLoginId();
	}

	@Test
	public void shouldInitHeadViewWhenActorIsNotAuthenticated() {
		// Given
		HeaderView view = mock(HeaderView.class);
		Controller controller = new InitHeaderController(userSessionFactory,
				view);

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);
		verify(view).showLoginControl();
	}
}
