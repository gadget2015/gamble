package org.robert.sharenote.webgui.dialog.search;

import java.util.List;

import org.robert.sharenote.domain.model.Note;

public interface SearchView {

	String getSearchCriteria();

	void setSearchResult(List<Note> searchResult);

	void showNoNotesFoundMessage();

}
