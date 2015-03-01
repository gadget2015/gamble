package org.robert.sharenote.webgui.dialog.startdialog;

/**
 * Defines operations on the view.
 * 
 * @author Robert
 * 
 */
public interface StartDialogView {

	String getNoteId();

	boolean canViewShowInputRichText();

	/**
	 * Enable richtext input fields.
	 */
	void setRichTextEnabled(boolean isReadOnly);

	/**
	 * Permalink used when editing a note.
	 */
	void setPermalink(String string);

	/**
	 * Define if actor has admin access to this view.
	 */
	void setAdminAccess(boolean hasAccess);

	void showErrorMessage(String message);

	/**
	 * Permalink that is used when only viewing the note.
	 */
	void setPermalinkView(String string);
}