package org.robert.sharenote.webgui.dialog.search;

import java.util.Collections;
import java.util.List;

import org.robert.sharenote.domain.model.Note;
import org.robert.sharenote.domain.model.NoteComparator;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.session.UserSessionFactory;

public class SearchForNoteController implements Controller {
	private NoteBusinessDelegate businessDelegate;
	private SearchView view;
	UserSessionFactory userSessionFactory;

	public SearchForNoteController(NoteBusinessDelegate businessDelegate,
			UserSessionFactory userSessionFactory, SearchView view) {
		this.businessDelegate = businessDelegate;
		this.view = view;
		this.userSessionFactory = userSessionFactory;
	}

	@Override
	public String perform() {
		String searchCriteria = view.getSearchCriteria();
		List<Note> searchResult = businessDelegate.searchForNote(
				searchCriteria, userSessionFactory.getUserSession().getEmail());

		if (searchResult.isEmpty()) {
			view.showNoNotesFoundMessage();
		} else {
			Collections.sort(searchResult, new NoteComparator());
			view.setSearchResult(searchResult);
		}

		return "success";
	}

}
