package org.robert.sharenote.webgui.dialog.startdialog;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.sharenote.domain.model.Note;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.businessdelegate.mock.NoteMockBD;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.dialog.startdialog.InitStartDialogController;
import org.robert.sharenote.webgui.dialog.startdialog.StartDialogView;
import org.robert.sharenote.webgui.session.UserSessionFactory;
import org.robert.sharenote.webgui.session.UserSessionFactoryMock;

public class InitStartDialogControllerTest {
	NoteBusinessDelegate businessDelegate;
	UserSessionFactory userSessionFactory;

	@Before
	public void init() {
		businessDelegate = new NoteMockBD();
		this.userSessionFactory = new UserSessionFactoryMock();
	}

	@Test
	public void shouldInitDialog() {
		// Given
		StartDialogView view = mock(StartDialogView.class);
		Controller controller = new InitStartDialogController(businessDelegate,
				view, userSessionFactory);
		when(view.getNoteId()).thenReturn("323");
		when(view.canViewShowInputRichText()).thenReturn(true);

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);
		Assert.assertEquals("Should update model", "hello.", userSessionFactory
				.getUserSession().getNote().getText());
		verify(view).setPermalink(
				"http://www.noterepo.com/startdialog_goodbrowser.xhtml?noteId=323");
		verify(view).setPermalinkView(
				"http://www.noterepo.com/viewnotedialog.xhtml?noteId=323");
	}

	@Test
	public void shouldInitDialogWhenNoNoteIdIsSet() {
		// Given
		StartDialogView view = mock(StartDialogView.class);
		Controller controller = new InitStartDialogController(businessDelegate,
				view, userSessionFactory);
		when(view.canViewShowInputRichText()).thenReturn(false);

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);
		Assert.assertEquals("Should update model",
				"Enter your note text here.", userSessionFactory
						.getUserSession().getNote().getText());
		Assert.assertEquals("Should be no Id set", 0, userSessionFactory
				.getUserSession().getNote().getId());
		verify(view).setRichTextEnabled(false);
	}

	/**
	 * This should happen in Safari browser because they have a bug so they
	 * can't show inputrichtext JSF component correct.
	 */
	@Test
	public void shouldMakeViewToShowRichTextInputField() {
		// Given
		StartDialogView view = mock(StartDialogView.class);
		Controller controller = new InitStartDialogController(businessDelegate,
				view, userSessionFactory);
		when(view.getNoteId()).thenReturn("323");
		when(view.canViewShowInputRichText()).thenReturn(true);

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);
		verify(view).setRichTextEnabled(true);
	}

	/**
	 * When actor is an admin user on the current Note he/she should be able to
	 * define that this note is only viewable by him/her.
	 */
	@Test
	public void shouldShowPrivateAccessChooserWhenActorIsAnAdminUserAndIsDefinedAsAdminUserForTheNote() {
		// Given
		StartDialogView view = mock(StartDialogView.class);
		Controller controller = new InitStartDialogController(businessDelegate,
				view, userSessionFactory);
		userSessionFactory.getUserSession().setEmail("kalle.anka@swipnet.se");
		when(view.getNoteId()).thenReturn("8392");

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);
		verify(view).setAdminAccess(true);
	}

	/**
	 * When actor is an admin user and the current Note don't have an admin user
	 * set on the note then the actor shoudl be able to define that this note is
	 * only viewable by him/her.
	 */
	@Test
	public void shouldShowPrivateAccessChooserWhenActorIsAnAdminUserAndNoteDontHaveAdminUserSet() {
		// Given
		StartDialogView view = mock(StartDialogView.class);
		Controller controller = new InitStartDialogController(businessDelegate,
				view, userSessionFactory);
		userSessionFactory.getUserSession().setEmail("kalle.anka@swipnet.se");
		when(view.getNoteId()).thenReturn("1");

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);
		verify(view).setAdminAccess(true);
	}

	/**
	 * When actor is an admin user but not on the current Note he/she should NOT
	 * be able to choose the private access note.
	 */
	@Test
	public void shouldNotShowPrivateAccessChooserWhenActorIsAnAdminUserAndIsNotDefinedAsAdminUserForTheNote() {
		// Given
		StartDialogView view = mock(StartDialogView.class);
		Controller controller = new InitStartDialogController(businessDelegate,
				view, userSessionFactory);
		userSessionFactory.getUserSession().setEmail("robert.georen@gmail.com");
		when(view.getNoteId()).thenReturn("8393");

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);
		verify(view).setAdminAccess(false);
		verify(view, times(0)).showErrorMessage(
				"You are not authorized to view this note.");
	}

	@Test
	public void shouldNotShowPrivateAccessChooserWhenActorIsNotAnAdminUser() {
		// Given
		StartDialogView view = mock(StartDialogView.class);
		Controller controller = new InitStartDialogController(businessDelegate,
				view, userSessionFactory);
		userSessionFactory.getUserSession().setNote(
				new Note(6767, "hej hopp", "kalle.anka@swipnet.se", false));

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);
		verify(view).setAdminAccess(false);
	}

	@Test
	public void shouldShowNotAuthorizedMessageWhenActorDontHaveAccessToNote() {
		// Given
		StartDialogView view = mock(StartDialogView.class);
		Controller controller = new InitStartDialogController(businessDelegate,
				view, userSessionFactory);
		userSessionFactory.getUserSession().setEmail(
				"dont.have.access@fraud.com");
		when(view.getNoteId()).thenReturn("8392");

		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "error", outcome);
		verify(view)
				.showErrorMessage(
						"You are not authorized to view this note. Contact user 'kalle.anka@swipnet.se' if you want to view the note.");
	}
}
