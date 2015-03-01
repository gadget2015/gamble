package org.robert.sharenote.webgui.dialog.search;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.sharenote.domain.model.Note;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.session.UserSessionFactory;
import org.robert.sharenote.webgui.session.UserSessionFactoryMock;

public class SearchForNoteControllerTest {
	NoteBusinessDelegate businessDelegate;
	UserSessionFactory userSessionFactory;

	@Before
	public void init() {
		this.businessDelegate = mock(NoteBusinessDelegate.class);
		this.userSessionFactory = new UserSessionFactoryMock();
	}

	@Test
	public void shouldFindOneNoteByContent() {
		// Given
		SearchView view = mock(SearchView.class);
		Controller controller = new SearchForNoteController(businessDelegate,
				userSessionFactory, view);
		when(view.getSearchCriteria()).thenReturn("todo list");
		List<Note> searchResult = new ArrayList<Note>();
		Note theOne = new Note();
		theOne.setId(33);
		theOne.setText("This is my todo list.");
		searchResult.add(theOne);
		when(businessDelegate.searchForNote("todo list", null)).thenReturn(searchResult);
		
		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);
		verify(view).setSearchResult(searchResult);
	}
	
	@Test
	public void shouldNotFindAnyNoteAndThenReturnNoNotesFound() {
		// Given
		SearchView view = mock(SearchView.class);
		Controller controller = new SearchForNoteController(businessDelegate,
				userSessionFactory, view);
		when(view.getSearchCriteria()).thenReturn("todo list");
		List<Note> searchResult = new ArrayList<Note>();
		when(businessDelegate.searchForNote("todo list", null)).thenReturn(searchResult);
		
		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should run ok.", "success", outcome);
		verify(view).showNoNotesFoundMessage();
	}
}
