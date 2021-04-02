package org.robert.tips.stryktips;

import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import org.robert.tips.stryktips.domain.model.StryktipsSystemRepository;
import org.robert.tips.types.MenuChoices;
import org.robert.tips.util.TextMessages;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.MainFrame;
import org.robert.tips.AboutDialog;
import org.robert.tips.exceptions.NoReducedRowsException;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.stryktips.domain.model.StryktipsSystemRepositoryIF;
import org.robert.tips.stryktips.types.StryktipsMenuChoices;
import org.robert.tips.stryktips.domain.model.reduce.SingleRowCombinationsCallback;
import org.robert.tips.stryktips.ui.desktop.dialogs.ReducedRowsDialog;
import org.robert.tips.stryktips.ui.desktop.dialogs.AllOddsGraphDialog;
import org.robert.tips.stryktips.ui.desktop.dialogs.ReducedOddsGraphDialog;
import org.robert.tips.stryktips.ui.desktop.dialogs.checkreducedsystem.CheckReducedSystemDialog;
import org.robert.tips.stryktips.ui.desktop.dialogs.help.StryktipsHelpDialog;
import org.robert.tips.stryktips.ui.desktop.dialogs.combinationgraph.CombinationGraphDialog;
import org.robert.tips.stryktips.exceptions.GameNotSetException;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.stryktips.types.StryktipsGame;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.types.GameTextMessages;

/**
 * This is an eventhandler class for the menubar in the MainFrame class.
 * @author Robert Geor�n
 */
class MenuHandler implements ActionListener,
        MenuChoices,
        StryktipsMenuChoices,
        ErrorMessages,
        StryktipsErrorMessages,
        StryktipsConstants,
        GameTextMessages {

    private StryktipsGameType stryktipsGameType;

    /**
     * Constructor.
     */
    public MenuHandler(StryktipsGameType mainFrame) {
        this.stryktipsGameType = mainFrame;
    }

    /**
     * This method is called everytime an actionevent is fired.
     * @param ActionEvent The event object
     */
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals(FILE_EXIT)) {
            System.exit(0);
        } else if (command.equals(FILE_CLOSE_DOCUMENT)) {
            closeDocument();
        } else if (command.equals(FILE_SAVE)) {
            saveDocument();
        } else if (command.equals(CREATE_COMBINATIONS)) {
            createCombinationReducing();
        } else if (command.equals(MENU_COMBINATION_GRAPH)) {
            viewCombinationGraph();
        } else if (command.equals(REDUCE_WTIH_BANKERS)) {
            reduceWithBankers();
        } else if (command.equals(VIEW_REDUCED_ROWS)) {
            viewReducedRows();
        } else if (command.equals(REDUCE_WITH_ODDS)) {
            reduceWithOdds();
        } else if (command.equals(ALL_ODDS_GRAPH)) {
            viewAllOddsGraph();
        } else if (command.equals(REDUCED_ODDS_GRAPH)) {
            viewReducedOddsGraph();
        } else if (command.equals(EXPORT_REDUCED_SYSTEM)) {
            exportReducedSystem();
        } else if (command.equals(CHECK_REDUCED_SYSTEM)) {
            showCheckReducedSystemDialog();
        } else if (command.equals(CREATE_MATHIMATICAL_SYSTEM)) {
            createMathimaticalSystem();
        } else if (command.equals(EXTENDED_CREATE_SYSTEM)) {
            createExtendedSystem();
        } else if (command.equals(MENU_CREATE_RSYSTEM)) {
            createRSystem();
        } else if (command.equals(MENU_HELP_STRYKTIPS)) {
            showStryktipsHelp();
        } else if (command.equals(HELP_ABOUT)) {
            showAboutDialog();
        } else if (command.equals(REDUCE_PLAYED_PERCENTAGE_SYSTEM)) {
            reducePlayedPercentageSystem();
        }
    }

    /**
     * save document.
     */
    private void saveDocument() {
        StryktipsDocument stryktipsDocument = (StryktipsDocument) stryktipsGameType.getDocument();

        if (stryktipsDocument.getStryktipsSystem().getFileName() == null) {
            // First time this document is saved.
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showSaveDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                stryktipsDocument.getStryktipsSystem().setFileName(file);
            } else {
                return; // don't save document.

            }
        }

        // Save av stryktips document to disk.
        try {
            // save as XML document
            StryktipsSystemRepositoryIF repository = new StryktipsSystemRepository();
            repository.save(stryktipsDocument.getStryktipsSystem());

            stryktipsDocument.setDocumentIsDirty(false);

            // update application title
            TextMessages textMessages = TextMessages.getInstance();
            String newTitle = textMessages.getText(PROGRAM_TITLE) + " - " + stryktipsDocument.getStryktipsSystem().getFileName().getAbsolutePath();
            stryktipsGameType.getMainFrame().setTitle(newTitle);
        } catch (Exception e) {
            e.printStackTrace();
            // error while saving document.
            try {
                TextMessages textMessages = TextMessages.getInstance();

                JOptionPane.showMessageDialog(null,
                        textMessages.getText(ERROR_WHILE_SAVING),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e2) {
                e2.printStackTrace();
                System.exit(0);
            }
        }
    }

    /** 
     * show all odds graph.
     */
    private void viewAllOddsGraph() {
        try {
            AllOddsGraphDialog dialog = new AllOddsGraphDialog(stryktipsGameType);
            dialog.show();
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /** 
     * show reduced odds graph.
     */
    private void viewReducedOddsGraph() {
        try {
            ReducedOddsGraphDialog dialog = new ReducedOddsGraphDialog(stryktipsGameType);
            dialog.show();
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Export the reduced system to a svenskaspel compliant format.
     */
    private void exportReducedSystem() {
        StryktipsDocument stryktipsDocument = (StryktipsDocument) stryktipsGameType.getDocument();

        if (stryktipsDocument.getStryktipsSystem().getFileName() == null) {
            // actor must save the document first.
            try {
                TextMessages textMessages = TextMessages.getInstance();

                JOptionPane.showMessageDialog(null,
                        textMessages.getText(DOCUMENT_MUST_FIRST_BE_SAVED),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e2) {
                e2.printStackTrace();
                System.exit(0);
            }
        }

        // export file.
        String tmpExportFilename = stryktipsDocument.getStryktipsSystem().getFileName().getAbsolutePath() + ".export.txt";
        File exportFileName = new File(tmpExportFilename);

        try {
            FileOutputStream ostream = new FileOutputStream(exportFileName);
            PrintWriter writer = new PrintWriter(ostream);
            writer.println("Stryktipset");


            ArrayList reducedSystem = stryktipsDocument.getStryktipsSystem().getReducedSystem();
            Iterator iterator = reducedSystem.iterator();

            while (iterator.hasNext()) {
                StryktipsGame game = (StryktipsGame) iterator.next();
                char[] singleRow = game.getSingleRow();
                writer.print("E");    // indicates a single row

                for (int i = 0; i < NUMBER_OF_GAMES; i++) {
                    writer.write("," + singleRow[i]);
                }

                writer.println();
            }

            writer.close();
        } catch (FileNotFoundException e) {
            try {
                TextMessages textMessages = TextMessages.getInstance();

                JOptionPane.showMessageDialog(null,
                        textMessages.getText(DOCUMENT_MUST_FIRST_BE_SAVED),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e2) {
                e2.printStackTrace();
                System.exit(0);
            }
        }
    }

    /**
     * Show check reduced system dialog.
     */
    private void showCheckReducedSystemDialog() {
        try {
            CheckReducedSystemDialog dialog = new CheckReducedSystemDialog(stryktipsGameType);
            dialog.show();
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Create mathimatical system.
     */
    private void createMathimaticalSystem() {
        try {
            StryktipsDocument stryktipsDocument = (StryktipsDocument) stryktipsGameType.getDocument();
            stryktipsDocument.getStryktipsSystem().getMathimatical().reduce();

            stryktipsDocument.setDocumentIsDirty(true);
        } catch (GameNotSetException e) {
            TextMessages textMessages;
            try {
                textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        textMessages.getText(MUST_SELECT_MATHIMATICALROWS),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);

            } catch (GeneralApplicationException ex) {
                Logger.getLogger(MenuHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.exit(0);
        }
    }

    /**
     * Create combination reduced system.
     */
    private void createCombinationReducing() {
        try {
            MainFrame mainFrame = (MainFrame) stryktipsGameType.getMainFrame();
            StryktipsDocument stryktipsDocument = (StryktipsDocument) stryktipsGameType.getDocument();
            SingleRowCombinationsCallback callback = new SingleRowCombinationsCallbackImpl(stryktipsDocument, stryktipsDocument.getStryktipsSystem(), mainFrame, 0);
            stryktipsDocument.getStryktipsSystem().getCombination().reduce(callback);
        } catch (ReducingParametersNotSetException e) {
            // minimum number shall be equal or less than maximum.
            TextMessages textMessages;
            try {
                textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        textMessages.getText(INVALID_RIGHTS_IN_SINGLEROW),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } catch (GeneralApplicationException ex) {
                Logger.getLogger(MenuHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (GameNotSetException e) {
            TextMessages textMessages;
            try {
                textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        textMessages.getText(NOT_ALL_ROWS_SET_IN_SINGLESYSTEM),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } catch (GeneralApplicationException ex) {
                Logger.getLogger(MenuHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Create R-System.
     */
    private void createRSystem() {
        try {
            StryktipsDocument stryktipsDocument = (StryktipsDocument) stryktipsGameType.getDocument();
            stryktipsDocument.getStryktipsSystem().getRsystem().reduce();

            stryktipsDocument.setDocumentIsDirty(true);
        } catch (GameNotSetException e) {
            TextMessages textMessages;
            try {
                textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        textMessages.getText(INVALID_RSYSTEM_SET),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } catch (GeneralApplicationException ex) {
                Logger.getLogger(MenuHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Show about dialog.
     */
    private void showAboutDialog() {
        try {
            TextMessages textMessages = TextMessages.getInstance();
            AboutDialog mainf = new AboutDialog();
            JOptionPane.showMessageDialog(stryktipsGameType.getMainFrame(),
                    mainf,
                    textMessages.getText(HELP_ABOUT),
                    JOptionPane.PLAIN_MESSAGE);
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Close the current document.
     */
    private void closeDocument() {
        try {
            boolean isDirty = ((StryktipsDocument) stryktipsGameType.getDocument()).getIsDocumentDirty();

            if (isDirty) {
                TextMessages textMessages = TextMessages.getInstance();
                int choice = JOptionPane.showConfirmDialog(null,
                        textMessages.getText(DOCUMENT_NOT_SAVED),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    saveDocument();
                } else {
                    // don't save                   
                }
            }

            // change frame content
            ((MainFrame) stryktipsGameType.getMainFrame()).initMainFrame();
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Reduce system with bankers set.
     */
    private void reduceWithBankers() {
        try {
            StryktipsDocument stryktipsDocument = (StryktipsDocument) stryktipsGameType.getDocument();
            stryktipsDocument.getStryktipsSystem().getBanker().reduce();

            stryktipsDocument.setDocumentIsDirty(true);
        } catch (GameNotSetException e) {
            TextMessages textMessages;
            try {
                textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        textMessages.getText(MUST_SELECT_BANKER),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } catch (GeneralApplicationException ex) {
                Logger.getLogger(MenuHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoReducedRowsException e) {
            TextMessages textMessages;
            try {
                textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        textMessages.getText("TODO"),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } catch (GeneralApplicationException ex) {
                Logger.getLogger(MenuHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * View reduced rows.
     */
    private void viewReducedRows() {
        try {
            ReducedRowsDialog dialog = new ReducedRowsDialog(stryktipsGameType);
            dialog.show();
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reduce system with odds system.
     */
    private void reduceWithOdds() {
        try {
            StryktipsDocument stryktipsDocument = (StryktipsDocument) stryktipsGameType.getDocument();
            stryktipsDocument.getStryktipsSystem().getOdds().reduce();

            stryktipsDocument.setDocumentIsDirty(true);
        } catch (GameNotSetException e) {

            TextMessages textMessages;
            try {
                textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        textMessages.getText(MUST_SET_ODDS_FOR_ALL_GAMES),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);

            } catch (GeneralApplicationException ex) {
                Logger.getLogger(MenuHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Show the stryktips help dialog.
     */
    private void showStryktipsHelp() {
        try {
            StryktipsHelpDialog dialog = new StryktipsHelpDialog(stryktipsGameType);
            dialog.show();
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * View combination graph.
     */
    private void viewCombinationGraph() {
        try {
            CombinationGraphDialog dialog = new CombinationGraphDialog(stryktipsGameType);
            dialog.show();
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Create extended system.
     */
    private void createExtendedSystem() {
        try {
            StryktipsDocument stryktipsDocument = (StryktipsDocument) stryktipsGameType.getDocument();
            stryktipsDocument.getStryktipsSystem().getExtended().reduce();
            stryktipsDocument.setDocumentIsDirty(true);
        } catch (GameNotSetException e) {
            TextMessages textMessages;
            try {
                textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        textMessages.getText(MUST_SELECT_EXTENDEDROW),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);

            } catch (GeneralApplicationException ex) {
                Logger.getLogger(MenuHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * Reduce the played percentage system.
     */
    private void reducePlayedPercentageSystem() {
        System.out.println("Reducera played percentage system...");
        try {
            StryktipsDocument stryktipsDocument = (StryktipsDocument) stryktipsGameType.getDocument();
            stryktipsDocument.getStryktipsSystem().getPlayed().reduce();
            stryktipsDocument.setDocumentIsDirty(true);
        } catch (ReducingParametersNotSetException e) {
            TextMessages textMessages;
            try {
                textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        e.getMessage(),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);

            } catch (GeneralApplicationException ex) {
                Logger.getLogger(MenuHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            try {
                TextMessages textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        "Game not set with message = " + e.getMessage(),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } catch (GeneralApplicationException ex) {
                Logger.getLogger(MenuHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
