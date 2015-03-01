package org.robert.tips.stryktips.finder.desktopclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.finder.desktopclient.dialog.settings.SettingsView;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.types.MenuChoices;
import org.robert.tips.util.TextMessages;

/**
 * This is an eventhandler class for the menubar in the MainFrame class.
 * 
 * @author Robert Geor�n
 */
class MainFrameMenuHandler implements ActionListener, MenuChoices,
		FinderMenuChoices, ErrorMessages {

	private MainFrame mainFrame;

	/**
	 * Constructor.
	 */
	public MainFrameMenuHandler(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	/**
	 * This method is called everytime an actionevent is fired.
	 * 
	 * @param ActionEvent
	 *            The event object
	 */
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		if (command.equals(FILE_EXIT)) {
			System.exit(0);
		} else if (command.equals(HELP_ABOUT)) {
			try {
				TextMessages textMessages = TextMessages.getInstance();
				AboutDialog mainf = new AboutDialog();
				JOptionPane.showMessageDialog(mainFrame, mainf,
						textMessages.getText(HELP_ABOUT),
						JOptionPane.PLAIN_MESSAGE);
			} catch (GeneralApplicationException e) {
				e.printStackTrace();
				System.exit(0);
			}
		} else if (command.equals(MENU_SETTINGS)) {
			try {
				SettingsView view = new SettingsView(mainFrame);
				view.setVisible(true);
			} catch (GeneralApplicationException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
}
