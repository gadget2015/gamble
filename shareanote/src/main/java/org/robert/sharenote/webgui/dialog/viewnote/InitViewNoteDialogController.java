package org.robert.sharenote.webgui.dialog.viewnote;

import org.robert.sharenote.domain.model.Note;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.session.UserSessionFactory;

public class InitViewNoteDialogController implements Controller {
	NoteBusinessDelegate businessDelegate;
	ViewNoteDialogView view;
	UserSessionFactory userSessionFactory;

	public InitViewNoteDialogController(NoteBusinessDelegate businessDelegate,
			ViewNoteDialogView view, UserSessionFactory userSessionFactory) {
		this.businessDelegate = businessDelegate;
		this.view = view;
		this.userSessionFactory = userSessionFactory;
	}

	@Override
	public String perform() {
		Note note = businessDelegate.getNote(view.getSelectedNoteId());
		if (note == null) {
			userSessionFactory.getUserSession().setErrorMessage("The given note can't be found. Note Id = "
					+ view.getSelectedNoteId() + ".");
			view.redirectToErrorDialog();
		} else {
			userSessionFactory.getUserSession().setNote(note);
			
			if (userSessionFactory.getUserSession().hasAccessToCurrentNote() == false) {
				userSessionFactory.getUserSession().setErrorMessage("You are note authorized to view this note (note Id = " +
						+ view.getSelectedNoteId() + ").");
				userSessionFactory.getUserSession().setNote(null);
				view.redirectToErrorDialog();
			} 
		}

		return "success";
	}

}
