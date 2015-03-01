package org.robert.sharenote.webgui.dialog.viewnote;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.businessdelegate.mock.NoteMockBD;
import org.robert.sharenote.webgui.dialog.Navigation;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.session.UserSession;
import org.robert.sharenote.webgui.session.UserSessionFactoryImpl;

@ManagedBean
public class ViewNoteDialogManagedBean implements ViewNoteDialogView {
	@ManagedProperty(value = "#{param.noteId}")
	private String noteId;
	@EJB
	NoteBusinessDelegate noteBD = new NoteMockBD();
	private UserSession model = new UserSessionFactoryImpl().getUserSession();
	private String init;

	@Override
	public int getSelectedNoteId() {
		if (noteId == null) {
			return 0;
		} else {
			return Integer.valueOf(getNoteId());
		}
	}

	public void setInit(String init) {
	}

	public String getInit() {
		Controller controller = new InitViewNoteDialogController(noteBD, this,
				new UserSessionFactoryImpl());
		controller.perform();

		return init;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setModel(UserSession model) {
		this.model = model;
	}

	public UserSession getModel() {
		return model;
	}

	@Override
	public void redirectToErrorDialog() {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.redirect(Navigation.ERROR_MESSAGE_DIALOG);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
