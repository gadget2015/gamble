package org.robert.sharenote.webgui.dialog.startdialog;

import java.util.Calendar;
import java.util.Date;

import org.robert.sharenote.domain.model.Note;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.session.UserSessionFactory;

public class SaveNoteController implements Controller {
	private NoteBusinessDelegate businessDelegate;
	UserSessionFactory userSessionFactory;

	public SaveNoteController(NoteBusinessDelegate businessDelegate,
			UserSessionFactory userSessionFactory) {
		this.businessDelegate = businessDelegate;
		this.userSessionFactory = userSessionFactory;
	}

	@Override
	public String perform() {
		// Set current time as last saved time.
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		userSessionFactory.getUserSession().getNote().setLastSaved(now);

		// Call business tier to save note.
		Note updateNote = businessDelegate.saveNote(userSessionFactory
				.getUserSession().getNote());
		userSessionFactory.getUserSession().setNote(updateNote);

		return "success";
	}

}
