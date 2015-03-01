package org.robert.sharenote.webgui.dialog.startdialog;

import org.robert.sharenote.domain.model.Note;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.dialog.Navigation;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.session.UserSessionFactory;

/**
 * Get all stryktips systems from database.
 * 
 * @author Robert
 * 
 */
public class InitStartDialogController implements Controller {
	private NoteBusinessDelegate businessDelegate;
	private StartDialogView view;
	UserSessionFactory userSessionFactory;

	public InitStartDialogController(NoteBusinessDelegate bd,
			StartDialogView view, UserSessionFactory userSessionFactory) {
		this.businessDelegate = bd;
		this.view = view;
		this.userSessionFactory = userSessionFactory;
	}

	@Override
	public String perform() {
		// Get note from datastorage
		String id = view.getNoteId();
		if (id == null) {
			Note newNote = new Note();
			newNote.setText("Enter your note text here.");
			userSessionFactory.getUserSession().setNote(newNote);
		} else {
			Note note = businessDelegate.getNote(Long.valueOf(id));
			userSessionFactory.getUserSession().setNote(note);

			String permaLink = Navigation.DOMAIN_NAME;
			permaLink += (view.canViewShowInputRichText()) ? Navigation.START_GOOD
					: Navigation.START_SAFARI;
			permaLink += "?noteId=" + id;
			view.setPermalink(permaLink);

			String permalinkView = Navigation.DOMAIN_NAME
					+ Navigation.VIEWNOTE_DIALOG + "?noteId=" + id;
			view.setPermalinkView(permalinkView);
		}

		// Check if actor have access to view this note.
		if (userSessionFactory.getUserSession().hasAccessToCurrentNote() == false) {
			view.showErrorMessage("You are not authorized to view this note. Contact user '"
					+ userSessionFactory.getUserSession().getNote()
							.getAdminUserId()
					+ "' if you want to view the note.");
			return "error";
		}

		// Make GUI responsive to actors browser.
		boolean goodView = view.canViewShowInputRichText();
		if (goodView) {
			view.setRichTextEnabled(true);
		} else {
			view.setRichTextEnabled(false);
		}

		// Set if actor has admin access to view.
		if (userSessionFactory.getUserSession().hasAdminAccessToCurrentNote()) {
			view.setAdminAccess(true);
		} else {
			view.setAdminAccess(false);
		}

		return "success";
	}
}