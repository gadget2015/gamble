package org.robert.tips.stryktips.finder.desktopclient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchSystemView;
import org.robert.tips.types.GameTextMessages;
import org.robert.tips.types.MenuChoices;

/**
 * The main frame for this application.
 */
public class MainFrame extends JFrame implements MenuChoices, GameTextMessages,
		FinderMenuChoices {

	private static final long serialVersionUID = 1L;
	private JPanel centerPanel = new JPanel();
	private JPanel statusBarPanel = new JPanel(new GridLayout());

	/**
	 * Default constructor
	 */
	public MainFrame() throws GeneralApplicationException {
		super();

		getContentPane().setLayout(new BorderLayout());
		setSize(new Dimension(400, 360));

		initMainFrame();
		setVisible(true);
	}

	/**
	 * Test constructor.
	 */
	public MainFrame(String message) {
		System.out.println("unittest: " + message);
	}

	/**
	 * Changes the content in the MainFrame
	 * 
	 * @param JPanel
	 *            The new JPanel
	 */
	public void changeContent(JPanel p) {
		centerPanel.removeAll();
		centerPanel.add(p);
		centerPanel.validate();
	}

	/**
	 * Change the status bar.
	 * 
	 * @param statusBar
	 *            The new Java Swing component. If null, restore statusbar.
	 */
	public void changeStatusBar(JComponent statusBar)
			throws GeneralApplicationException {
		statusBarPanel.removeAll();

		if (statusBar == null) {
			TextMessages textMessages = TextMessages.getInstance();
			statusBar = new JLabel();
			((JLabel) statusBar).setText(textMessages.getText(STATUSBAR_OK));
		}

		statusBarPanel.add(statusBar);
		validate();
	}

	/**
	 * Create the menu system for the main frame.
	 */
	public void initMainFrame() throws GeneralApplicationException {
		// set default title
		TextMessages textMessages = TextMessages.getInstance();
		setTitle(textMessages.getText(PROGRAM_TITLE));

		JMenuBar menuBar = new JMenuBar(); // contains all menus
		MainFrameMenuHandler menuHandler = new MainFrameMenuHandler(this);
		getContentPane().removeAll();
		centerPanel = new JPanel();

		// Put the menu and menu items in the menubar
		// File menu
		JMenu menuFile = new JMenu();
		menuFile.setText(textMessages.getText(FILE));

		// Settings
		JMenuItem menuFileSettings = new JMenuItem();
		menuFileSettings.setText(textMessages.getText(MENU_SETTINGS));
		menuFileSettings.setActionCommand(MENU_SETTINGS);
		menuFileSettings.addActionListener(menuHandler);
		menuFile.add(menuFileSettings);

		menuFile.addSeparator();

		// exit menu item
		JMenuItem menuFileExit = new JMenuItem();
		menuFileExit.setText(textMessages.getText(FILE_EXIT));
		menuFileExit.setActionCommand(FILE_EXIT);
		menuFileExit.addActionListener(menuHandler);
		menuFile.add(menuFileExit);

		// Help menu
		JMenu menuHelp = new JMenu();
		JMenuItem menuHelpAbout = new JMenuItem();
		menuHelp.setText(textMessages.getText(HELP));
		menuHelpAbout.setText(textMessages.getText(HELP_ABOUT));
		menuHelpAbout.setActionCommand(HELP_ABOUT);
		menuHelpAbout.addActionListener(menuHandler);
		menuHelp.add(menuHelpAbout);

		// Add menu's and menubar
		menuBar.add(menuFile);
		menuBar.add(menuHelp);

		// set menu bar
		setJMenuBar(menuBar);

		// set statusbar and toolbar
		changeStatusBar(null); // reset statusbar

		getContentPane().add(statusBarPanel, BorderLayout.SOUTH);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		validate();

		// Add default content
		changeContent(new SearchSystemView());
	}
}