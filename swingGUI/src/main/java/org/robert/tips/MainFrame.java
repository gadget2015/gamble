/**
 * Main frame for the application. This frame cantains the menu,
 * statusbar.
 * @author Robert Geor�n
 */
package org.robert.tips;

import java.util.Map;
import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Permission;
import java.util.logging.Logger;
import org.robert.tips.types.MenuChoices;
import org.robert.tips.util.TextMessages;
import org.robert.tips.types.GameTextMessages;
import org.robert.tips.exceptions.GeneralApplicationException;

/**
 * The main frame for this application.
 */
public class MainFrame extends JFrame implements MenuChoices,
        GameTextMessages {

    private JPanel centerPanel = new JPanel();
    private JPanel statusBarPanel = new JPanel(new GridLayout());

    /**
     * Default constructor
     */
    public MainFrame() throws GeneralApplicationException {
        super();

        getContentPane().setLayout(new BorderLayout());
        setSize(new Dimension(600, 530));

        initMainFrame();
        show();
    }

    /**
     * Test constructor.
     */
    public MainFrame(String message) {
        System.out.println("unittest: " + message);
    }

    /**
     * Changes the content in the MainFrame
     * @param JPanel The new JPanel
     * @deprecated Use changeGameType.
     */
    public void changeContent(JPanel p) {
        centerPanel.removeAll();
        centerPanel.add(p);
        centerPanel.validate();
    }

    /**
     * Change the game type.
     * @param newType The new game type.
     */
    public void changeGameType(GameIF newType) throws GeneralApplicationException {
        JMenuBar menuSystem = newType.getMenuSystem();
        setJMenuBar(menuSystem);    // set menu bar
        centerPanel.removeAll();
        JPanel content = newType.getFrameContent();
        centerPanel.add(content); // change content
        changeStatusBar(null);    // reset statusbars
        validate();
    }

    /**
     * Change the status bar.
     * @param statusBar The new Java Swing component. If null, restore statusbar.
     */
    public void changeStatusBar(JComponent statusBar) throws GeneralApplicationException {
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

        JMenuBar menuBar = new JMenuBar();  // contains all menus
        MainFrameMenuHandler menuHandler = new MainFrameMenuHandler(this);
        getContentPane().removeAll();
        centerPanel = new JPanel();

        // Put the menu and menu items in the menubar
        // File menu
        JMenu menuFile = new JMenu();
        menuFile.setText(textMessages.getText(FILE));

        // New menu item
        JMenu menuFileNew = new JMenu();
        menuFileNew.setText(textMessages.getText(FILE_NEW));

        // Maltips game type
        JMenuItem menuFileNewMaltips = new JMenuItem();
        menuFileNewMaltips.setText(textMessages.getText(FILE_NEW_MALTIPS));
        menuFileNewMaltips.setActionCommand(FILE_NEW_MALTIPS);
        menuFileNewMaltips.addActionListener(menuHandler);
        menuFileNew.add(menuFileNewMaltips);

        // Stryktips game type
        JMenuItem menuFileNewStryktips = new JMenuItem();
        menuFileNewStryktips.setText(textMessages.getText(FILE_NEW_STRYKTIPS));
        menuFileNewStryktips.setActionCommand(FILE_NEW_STRYKTIPS);
        menuFileNewStryktips.addActionListener(menuHandler);
        menuFileNew.add(menuFileNewStryktips);

        menuFile.add(menuFileNew);

        // Open menu item
        JMenuItem menuFileOpen = new JMenu();
        menuFileOpen.setText(textMessages.getText(FILE_OPEN));

        // stryktips game type
        JMenuItem menuFileOpenStryktips = new JMenuItem();
        menuFileOpenStryktips.setText(textMessages.getText(FILE_OPEN_STRYKTIPS));
        menuFileOpenStryktips.setActionCommand(FILE_OPEN_STRYKTIPS);
        menuFileOpenStryktips.addActionListener(menuHandler);
        menuFileOpen.add(menuFileOpenStryktips);

        // maltips game type
        JMenuItem menuFileOpenMaltips = new JMenuItem();
        menuFileOpenMaltips.setText(textMessages.getText(FILE_OPEN_MALTIPS));
        menuFileOpenMaltips.setActionCommand(FILE_OPEN_MALTIPS);
        menuFileOpenMaltips.addActionListener(menuHandler);
        menuFileOpen.add(menuFileOpenMaltips);

        menuFile.add(menuFileOpen);

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
        changeStatusBar(null);   // reset statusbar

        getContentPane().add(statusBarPanel, BorderLayout.SOUTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        validate();
    }
}
