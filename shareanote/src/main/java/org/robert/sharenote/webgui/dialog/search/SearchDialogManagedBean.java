package org.robert.sharenote.webgui.dialog.search;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.robert.sharenote.domain.model.Note;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.businessdelegate.mock.NoteMockBD;
import org.robert.sharenote.webgui.dialog.BrowserCheck;
import org.robert.sharenote.webgui.dialog.Navigation;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.session.UserSessionFactory;
import org.robert.sharenote.webgui.session.UserSessionFactoryImpl;

@ManagedBean
@RequestScoped
public class SearchDialogManagedBean implements SearchView {
	private String searchCriteria;
	private List<Note> searchResult;
	private String searchResultMessage;
	@ManagedProperty(value = "#{param.noteId}")
	private String noteId;

	@EJB
	NoteBusinessDelegate stryktipsBD = new NoteMockBD();

	@Override
	public String getSearchCriteria() {
		return searchCriteria;
	}

	@Override
	public void setSearchResult(List<Note> searchResult) {
		this.searchResult = searchResult;
	}

	@Override
	public void showNoNotesFoundMessage() {
		this.searchResultMessage = "Inga anteckningar funna.";
	}

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;

		UserSessionFactory userSessionFactory = new UserSessionFactoryImpl();
		Controller controller = new SearchForNoteController(stryktipsBD,
				userSessionFactory, this);

		controller.perform();
	}

	public List<Note> getSearchResult() {
		return searchResult;
	}

	public void setSearchResultMessage(String searchResultMessage) {
		this.searchResultMessage = searchResultMessage;
	}

	public String getSearchResultMessage() {
		return searchResultMessage;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;

		if (noteId != null) {
			// Redirect actor to start dialog with note Id as parameter.
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();

			try {
				BrowserCheck checker = new BrowserCheck();

				if (checker.canViewShowInputRichText() == false) {
					ec.redirect(Navigation.START_SAFARI + "?noteId=" + noteId);
				} else {
					ec.redirect(Navigation.START_GOOD + "?noteId=" + noteId);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public String getNoteId() {
		return noteId;
	}

	public String gotoStartDialog() {
		BrowserCheck checker = new BrowserCheck();

		if (checker.canViewShowInputRichText() == false) {
			return "startdialog_safaribrowser";
		} else {
			return "startdialog_goodbrowser";
		}
	}
}
