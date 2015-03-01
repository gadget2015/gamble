package org.robert.sharenote.webgui.dialog.startdialog;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.sharenote.domain.model.Note;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.businessdelegate.mock.NoteMockBD;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.dialog.startdialog.SaveNoteController;
import org.robert.sharenote.webgui.session.UserSessionFactory;
import org.robert.sharenote.webgui.session.UserSessionFactoryMock;

public class SaveNoteControllerTest {
	NoteBusinessDelegate businessDelegate;
	UserSessionFactory userSessionFactory;

	@Before
	public void init() {
		this.businessDelegate = mock(NoteBusinessDelegate.class);
		this.userSessionFactory = new UserSessionFactoryMock();
	}

	@Test
	public void shouldUpdateNote() {
		// Given
		Controller controller = new SaveNoteController(businessDelegate,
				userSessionFactory);
		Note note = new Note();
		note.setId(33);
		note.setText("Hej hopp");
		userSessionFactory.getUserSession().setNote(note);

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);
		verify(businessDelegate).saveNote(note);
	}

	@Test
	public void shouldSaveANewNote() {
		// Given
		NoteBusinessDelegate businessDelegate = new NoteMockBD();
		Controller controller = new SaveNoteController(businessDelegate,
				userSessionFactory);
		Note note = new Note();
		note.setText("Hej hopp");
		userSessionFactory.getUserSession().setNote(note);

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);
		Assert.assertEquals("Should have got an Id for the note.", 10,
				userSessionFactory.getUserSession().getNote().getId());
	}
}