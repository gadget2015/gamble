package org.robert.sharenote.webgui.dialog.startdialog;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.businessdelegate.mock.NoteMockBD;
import org.robert.sharenote.webgui.dialog.BrowserCheck;
import org.robert.sharenote.webgui.dialog.Navigation;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.session.UserSession;
import org.robert.sharenote.webgui.session.UserSessionFactory;
import org.robert.sharenote.webgui.session.UserSessionFactoryImpl;

/**
 * Defines the JSF backing bean.
 * 
 * @author Robert
 * 
 */
@ManagedBean
@RequestScoped
public class StartDialogManagedBean implements StartDialogView {
	private String init;
	@ManagedProperty(value = "#{param.noteId}")
	private String noteId;

	@EJB
	NoteBusinessDelegate noteBD = new NoteMockBD();

	private boolean richTextEnabled;
	private String permalink;
	private String permalinkView;
	private boolean adminAccess;
	private UserSession model = new UserSessionFactoryImpl().getUserSession();

	public void init() {
		Controller controller = new InitStartDialogController(noteBD, this,
				new UserSessionFactoryImpl());

		controller.perform();
	}

	public void setInit(String init) {
		this.init = init;
	}

	public String getInit() {
		init();
		return init;
	}

	@Override
	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public void setText(String text) {
		// Replace Swedish characters
		if (text != null) {
			text = StringEscapeUtils.unescapeHtml(text);
		}

		// Update model
		UserSessionFactory userSessionFactory = new UserSessionFactoryImpl();
		userSessionFactory.getUserSession().getNote().setText(text);
		// set admin id if not one already exists
		userSessionFactory.getUserSession().getNote()
				.setAdminUserId(userSessionFactory.getUserSession().getEmail());

		// Perform save action.
		Controller controller = new SaveNoteController(noteBD,
				userSessionFactory);

		controller.perform();

		// If this is a new Note then redirect actor to same
		// dialog but with noteId in URL.
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();

		if (true) {

			try {
				ec.redirect("?noteId="
						+ userSessionFactory.getUserSession().getNote().getId());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public String getText() {
		// get text from model
		UserSessionFactory userSessionFactory = new UserSessionFactoryImpl();
		String text = userSessionFactory.getUserSession().getNote().getText();

		return text;
	}

	@Override
	public boolean canViewShowInputRichText() {
		BrowserCheck checker = new BrowserCheck();

		return checker.canViewShowInputRichText();
	}

	public String gotoSearchDialog() {
		return "search";
	}

	public void setRichTextEnabled(boolean richTextEnabled) {
		this.richTextEnabled = richTextEnabled;
	}

	public boolean isRichTextEnabled() {
		return richTextEnabled;
	}

	/**
	 * Called by Safari browser.
	 */
	public String saveNote() {
		return "success";
	}

	public String getRedirectPage() {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();

			if (canViewShowInputRichText() == false) {
				ec.redirect(Navigation.START_SAFARI);
			} else {
				ec.redirect(Navigation.START_GOOD);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return "success";
	}

	@Override
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public String getPermalink() {
		return permalink;
	}

	@Override
	public void setAdminAccess(boolean hasAccess) {
		this.adminAccess = hasAccess;
	}

	public boolean getAdminAccess() {
		// Must be done in the view due to JSF phases.
		if (getModel().hasAdminAccessToCurrentNote()) {
			this.adminAccess = true;
		} else {
			this.adminAccess = false;
		}

		return this.adminAccess;
	}

	@Override
	public void showErrorMessage(String message) {
		try {
			// put message in session context.
			getModel().setErrorMessage(message);

			// Redirect to error dialog.
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();

			ec.redirect(Navigation.ERROR_MESSAGE_DIALOG);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void setPrivateAccess(boolean privateAccess) {
		getModel().getNote().setPrivateAccess(privateAccess);
	}

	public boolean getPrivateAccess() {
		return getModel().getNote().getPrivateAccess();
	}

	public void setModel(UserSession model) {
		this.model = model;
	}

	public UserSession getModel() {
		return model;
	}

	public void setPermalinkView(String permalinkView) {
		this.permalinkView = permalinkView;
	}

	public String getPermalinkView() {
		return permalinkView;
	}
}
