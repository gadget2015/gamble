package org.robert.sharenote.webgui.dialog.viewnote;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.businessdelegate.mock.NoteMockBD;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.session.UserSessionFactory;
import org.robert.sharenote.webgui.session.UserSessionFactoryMock;

public class InitViewNoteDialogControllerTest {
	NoteBusinessDelegate businessDelegate;
	UserSessionFactory userSessionFactory;

	@Before
	public void init() {
		businessDelegate = new NoteMockBD();
		this.userSessionFactory = new UserSessionFactoryMock();
	}

	@Test
	public void shouldBeAbleToViewDialog() {
		// Given
		ViewNoteDialogView view = mock(ViewNoteDialogView.class);
		Controller controller = new InitViewNoteDialogController(
				businessDelegate, view, userSessionFactory);
		when(view.getSelectedNoteId()).thenReturn(555);

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should be return a success.", "success", outcome);
		Assert.assertNotNull(
				"Should set note in actors session so view can show it.",
				userSessionFactory.getUserSession().getNote());
	}

	@Test
	public void shouldNotFindNoteToView() {
		// Given
		ViewNoteDialogView view = mock(ViewNoteDialogView.class);
		Controller controller = new InitViewNoteDialogController(
				businessDelegate, view, userSessionFactory);
		when(view.getSelectedNoteId()).thenReturn(3434);

		// When
		controller.perform();

		// Then
		Assert.assertNull(
				"Should NOT set note in actors session so view can show it.",
				userSessionFactory.getUserSession().getNote());
		Assert.assertEquals("The given note can't be found. Note Id = 3434.",
				userSessionFactory.getUserSession().getErrorMessage());
		verify(view).redirectToErrorDialog();
	}

	@Test
	public void shouldNotHavePermissionToViewNote() {
		// Given
		ViewNoteDialogView view = mock(ViewNoteDialogView.class);
		Controller controller = new InitViewNoteDialogController(
				businessDelegate, view, userSessionFactory);
		when(view.getSelectedNoteId()).thenReturn(8392);

		// When
		controller.perform();

		// Then
		Assert.assertNull(
				"Should NOT set note in actors session so view can show it.",
				userSessionFactory.getUserSession().getNote());
		Assert.assertEquals(
				"You are note authorized to view this note (note Id = 8392).",
				userSessionFactory.getUserSession().getErrorMessage());
		verify(view).redirectToErrorDialog();
	}
}
