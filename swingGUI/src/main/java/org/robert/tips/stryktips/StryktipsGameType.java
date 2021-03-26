package org.robert.tips.stryktips;

import org.robert.tips.GameIF;
import org.robert.tips.stryktips.ui.desktop.playedpercentage.PlayedPercentageContainer;
import org.robert.tips.util.TextMessages;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import org.robert.tips.stryktips.domain.model.StryktipsSystem;
import org.robert.tips.types.MenuChoices;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsMenuChoices;
import org.robert.tips.stryktips.ui.desktop.combination.CombinationSystemContainer;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.stryktips.ui.desktop.bankers.BankersSystemContainer;
import org.robert.tips.stryktips.ui.desktop.odds.OddsSystemContainer;
import org.robert.tips.stryktips.ui.desktop.mathimatical.MathimaticalSystemContainer;
import org.robert.tips.stryktips.ui.desktop.rsystem.RSystemContainer;
import org.robert.tips.stryktips.ui.desktop.extended.ExtendedSystemContainer;

/**
 * This is the content for the Stryktips game type. This is the main entry.
 * @author Robert Siwerz.
 */
public class StryktipsGameType implements GameIF,
        MenuChoices,
        StryktipsMenuChoices,
        StryktipsTextMessages {

    private JFrame mainFrame;                   // reference to the main fram, top-level frame.

    private StryktipsDocument currentDocument;  // The current active/open document.

    /**
     * Creates a new Stryktips game type with a new empty document.
     * @param mainFrame The mainframe.
     */
    public StryktipsGameType(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        currentDocument = new StryktipsDocument();
    }

    /**
     * Creates a new Stryktips game type with the given document.
     * @param mainFrame The mainframe.
     * @param document The stryktips document
     */
    public StryktipsGameType(JFrame mainFrame, StryktipsDocument stryktipsDocument) {
        this.mainFrame = mainFrame;
        currentDocument = stryktipsDocument;
    }

    /**
     * Implemented method from GameIF.
     */
    public JMenuBar getMenuSystem() throws GeneralApplicationException {
        JMenuBar menuBar = new JMenuBar();  // contains all menus

        TextMessages textMessages = TextMessages.getInstance();
        MenuHandler menuHandler = new MenuHandler(this);

        // Put the menu and menu items in the menubar
        // File menu
        JMenu menuFile = new JMenu();
        menuFile.setText(textMessages.getText(FILE));

        // Close document
        JMenuItem menuFileClose = new JMenuItem();
        menuFileClose.setText(textMessages.getText(FILE_CLOSE_DOCUMENT));
        menuFileClose.setActionCommand(FILE_CLOSE_DOCUMENT);
        menuFileClose.addActionListener(menuHandler);
        menuFile.add(menuFileClose);

        // save document
        JMenuItem menuFileSave = new JMenuItem();
        menuFileSave.setText(textMessages.getText(FILE_SAVE));
        menuFileSave.setActionCommand(FILE_SAVE);
        menuFileSave.addActionListener(menuHandler);
        menuFile.add(menuFileSave);
        SaveDocumentObserver saveDocumentObserver = new SaveDocumentObserver(currentDocument, menuFileSave);

        menuFile.addSeparator();

        JMenuItem menuFileExit = new JMenuItem();
        menuFileExit.setText(textMessages.getText(FILE_EXIT));
        menuFileExit.setActionCommand(FILE_EXIT);
        menuFileExit.addActionListener(menuHandler);
        menuFile.add(menuFileExit);

        // R-system menu
        JMenu menuRsystem = new JMenu();
        menuRsystem.setText(textMessages.getText(MENU_RSYSTEM));

        // create R system
        JMenuItem menuCreateRSystem = new JMenuItem();
        menuCreateRSystem.setText(textMessages.getText(MENU_CREATE_RSYSTEM));
        menuCreateRSystem.setActionCommand(MENU_CREATE_RSYSTEM);
        menuCreateRSystem.addActionListener(menuHandler);
        menuRsystem.add(menuCreateRSystem);

        // Mathimatical menu
        JMenu menuMathimatical = new JMenu();
        menuMathimatical.setText(textMessages.getText(MENU_MATHIMATICAL));

        // create mathimatical system
        JMenuItem menuCreateMathimaticalSystem = new JMenuItem();
        menuCreateMathimaticalSystem.setText(textMessages.getText(CREATE_MATHIMATICAL_SYSTEM));
        menuCreateMathimaticalSystem.setActionCommand(CREATE_MATHIMATICAL_SYSTEM);
        menuCreateMathimaticalSystem.addActionListener(menuHandler);
        menuMathimatical.add(menuCreateMathimaticalSystem);

        // Extended system menu
        JMenu menuExtendedSystem = new JMenu();
        menuExtendedSystem.setText(textMessages.getText(EXTENDED_SYSTEM));

        // create extended system
        JMenuItem menuCreateExtendedSystem = new JMenuItem();
        menuCreateExtendedSystem.setText(textMessages.getText(EXTENDED_CREATE_SYSTEM));
        menuCreateExtendedSystem.setActionCommand(EXTENDED_CREATE_SYSTEM);
        menuCreateExtendedSystem.addActionListener(menuHandler);
        menuExtendedSystem.add(menuCreateExtendedSystem);

        // Combination menu
        JMenu menuCombination = new JMenu();
        menuCombination.setText(textMessages.getText(MENU_COMBINATION));

        // create combinations
        JMenuItem menuCreateCombinations = new JMenuItem();
        menuCreateCombinations.setText(textMessages.getText(CREATE_COMBINATIONS));
        menuCreateCombinations.setActionCommand(CREATE_COMBINATIONS);
        menuCreateCombinations.addActionListener(menuHandler);
        menuCombination.add(menuCreateCombinations);

        // show combinations graph
        JMenuItem menuCreateCombinationsGraph = new JMenuItem();
        menuCreateCombinationsGraph.setText(textMessages.getText(MENU_COMBINATION_GRAPH));
        menuCreateCombinationsGraph.setActionCommand(MENU_COMBINATION_GRAPH);
        menuCreateCombinationsGraph.addActionListener(menuHandler);
        menuCombination.add(menuCreateCombinationsGraph);

        // Bakers menu
        JMenu menuBankers = new JMenu();
        menuBankers.setText(textMessages.getText(MENU_BANKERS));
        // reduce with from the combinations with the bankers.
        JMenuItem menuReduceWithBankers = new JMenuItem();
        menuReduceWithBankers.setText(textMessages.getText(REDUCE_WTIH_BANKERS));
        menuReduceWithBankers.setActionCommand(REDUCE_WTIH_BANKERS);
        menuReduceWithBankers.addActionListener(menuHandler);
        menuBankers.add(menuReduceWithBankers);

        // Odds menu
        JMenu menuOdds = new JMenu();
        menuOdds.setText(textMessages.getText(MENU_ODDS));

        // Reduce with odds system
        JMenuItem menuReducedWithOdds = new JMenuItem();
        menuReducedWithOdds.setText(textMessages.getText(REDUCE_WITH_ODDS));
        menuReducedWithOdds.setActionCommand(REDUCE_WITH_ODDS);
        menuReducedWithOdds.addActionListener(menuHandler);
        menuOdds.add(menuReducedWithOdds);

        // show odds graph for all odds in the odds system.
        JMenuItem menuAllOddsGraph = new JMenuItem();
        menuAllOddsGraph.setText(textMessages.getText(ALL_ODDS_GRAPH));
        menuAllOddsGraph.setActionCommand(ALL_ODDS_GRAPH);
        menuAllOddsGraph.addActionListener(menuHandler);
        menuOdds.add(menuAllOddsGraph);

        // show odds graph over the odds for the reduced system.
        JMenuItem menuReducedOddsGraph = new JMenuItem();
        menuReducedOddsGraph.setText(textMessages.getText(REDUCED_ODDS_GRAPH));
        menuReducedOddsGraph.setActionCommand(REDUCED_ODDS_GRAPH);
        menuReducedOddsGraph.addActionListener(menuHandler);
        menuOdds.add(menuReducedOddsGraph);

        // Help reduce      
        JMenu menuReduce = new JMenu();
        menuReduce.setText(textMessages.getText(REDUCE));

        // view reduced rows.
        JMenuItem menuViewReducedRows = new JMenuItem();
        menuViewReducedRows.setText(textMessages.getText(VIEW_REDUCED_ROWS));
        menuViewReducedRows.setActionCommand(VIEW_REDUCED_ROWS);
        menuViewReducedRows.addActionListener(menuHandler);
        menuReduce.add(menuViewReducedRows);

        // export reduced rows to svenskaspel compliant format.
        JMenuItem menuExportReducedSystem = new JMenuItem();
        menuExportReducedSystem.setText(textMessages.getText(EXPORT_REDUCED_SYSTEM));
        menuExportReducedSystem.setActionCommand(EXPORT_REDUCED_SYSTEM);
        menuExportReducedSystem.addActionListener(menuHandler);
        menuReduce.add(menuExportReducedSystem);

        // Check the rows, e.g check how many right you have in the reduced system
        JMenuItem menuCheckReducedSystem = new JMenuItem();
        menuCheckReducedSystem.setText(textMessages.getText(CHECK_REDUCED_SYSTEM));
        menuCheckReducedSystem.setActionCommand(CHECK_REDUCED_SYSTEM);
        menuCheckReducedSystem.addActionListener(menuHandler);
        menuReduce.add(menuCheckReducedSystem);

        // Help menu      
        JMenu menuHelp = new JMenu();
        menuHelp.setText(textMessages.getText(HELP));

        // stryktips help.
        JMenuItem menuHelpStryktips = new JMenuItem();
        menuHelpStryktips.setText(textMessages.getText(MENU_HELP_STRYKTIPS));
        menuHelpStryktips.setActionCommand(MENU_HELP_STRYKTIPS);
        menuHelpStryktips.addActionListener(menuHandler);
        menuHelp.add(menuHelpStryktips);

        // about
        JMenuItem menuHelpAbout = new JMenuItem();
        menuHelpAbout.setText(textMessages.getText(HELP_ABOUT));
        menuHelpAbout.setActionCommand(HELP_ABOUT);
        menuHelpAbout.addActionListener(menuHandler);
        menuHelp.add(menuHelpAbout);

        // Add menu's and menubar
        menuBar.add(menuFile);
        menuBar.add(menuRsystem);
        menuBar.add(menuMathimatical);
        menuBar.add(menuExtendedSystem);
        menuBar.add(menuCombination);
        menuBar.add(menuBankers);
        menuBar.add(menuOdds);
        menuBar.add(menuReduce);
        menuBar.add(menuHelp);

        return menuBar;
    }

    /**
     * implemented method from GameIF.
     */
    public JPanel getFrameContent() throws GeneralApplicationException {
        JTabbedPane tabbedPane = new JTabbedPane();
        GridBagConstraints constraints = new GridBagConstraints();

        TextMessages textMessages = TextMessages.getInstance();

        // add R-system tab
        JPanel rsystemContent = new JPanel(new GridBagLayout());
        RSystemContainer rSystemContainer = new RSystemContainer(currentDocument);
        rsystemContent.add(rSystemContainer, constraints);
        tabbedPane.add(textMessages.getText(TAB_RSYSTEM), rsystemContent);

        // add mathimatical tab
        JPanel mathimaticalContent = new JPanel(new GridBagLayout());
        MathimaticalSystemContainer mathimaticalSystemContainer = new MathimaticalSystemContainer(currentDocument);
        mathimaticalContent.add(mathimaticalSystemContainer, constraints);
        tabbedPane.add(textMessages.getText(TAB_MATHIMATICAL), mathimaticalContent);

        // add extended system tab
        ExtendedSystemContainer extendedSystemContainer = new ExtendedSystemContainer(currentDocument);
        tabbedPane.add(textMessages.getText(TAB_EXTENDED_SYSTEM), extendedSystemContainer);

        // add combination tab.
        CombinationSystemContainer combinationSystemContainer = new CombinationSystemContainer(currentDocument);
        tabbedPane.add(textMessages.getText(TAB_COMBINATION), combinationSystemContainer);

        // add bankers tab
        JPanel bankersContent = new JPanel(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        BankersSystemContainer bankersSystemContainer = new BankersSystemContainer(currentDocument);
        bankersContent.add(bankersSystemContainer, constraints);
        tabbedPane.add(textMessages.getText(TAB_BANKERS), bankersContent);

        // add the odds tab
        OddsSystemContainer oddsSystemContainer = new OddsSystemContainer(currentDocument);
        tabbedPane.add(textMessages.getText(TAB_ODDS), oddsSystemContainer);

        // Add the percentage tag
        PlayedPercentageContainer playedPercentageContainer = new PlayedPercentageContainer(currentDocument);
        tabbedPane.add(textMessages.getText(TAB_PLAYEDPERCENTAGE), playedPercentageContainer);

        // fix up the return content
        JPanel content = new JPanel();
        content.add(tabbedPane);

        return content;
    }

    /**
     * Implemented method from GameIF.
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * Implemented method from GameUF.
     */
    public Object getDocument() {
        return currentDocument;
    }
}