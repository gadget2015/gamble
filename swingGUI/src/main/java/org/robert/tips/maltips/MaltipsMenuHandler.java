package org.robert.tips.maltips;

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
import org.robert.tips.types.MenuChoices;
import org.robert.tips.util.TextMessages;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.MainFrame;
import org.robert.tips.AboutDialog;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.types.GameTextMessages;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.maltips.domain.model.MaltipsSystem;
import org.robert.tips.maltips.domain.model.MaltipsSystemRepository;
import org.robert.tips.maltips.domain.model.MaltipsSystemRepositoryIF;
import org.robert.tips.maltips.types.MaltipsMenuChoices;
import org.robert.tips.maltips.ui.dialogs.ViewReducedSystemDialog;
import org.robert.tips.maltips.domain.model.reduce.SingleSystemCombinationReducerCallback;
import org.robert.tips.maltips.ui.dialogs.checkreducesystem.CheckReducedSystemDialog;
import org.robert.tips.maltips.ui.dialogs.viewcombinationgraph.ViewCombinationGraphDialog;
import org.robert.tips.maltips.types.MaltipsErrorMessages;
import org.robert.tips.maltips.types.MaltipsGame;

/**
 * This is an eventhandler class for the menubar in the Maltips game type.
 * @author Robert Geor�n
 */
class MaltipsMenuHandler implements ActionListener,
        MenuChoices,
        MaltipsMenuChoices,
        ErrorMessages,
        MaltipsErrorMessages,
        GameTextMessages {

    private MaltipsGameType maltipsGameType;

    /**
     * Constructor.
     */
    public MaltipsMenuHandler(MaltipsGameType mainFrame) {
        this.maltipsGameType = mainFrame;
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
        } else if (command.equals(MENU_MATHIMATICAL_CREATE)) {
            createMathimaticalSystem();
        } else if (command.equals(MENU_REDUCE_VIEW)) {
            viewReducedSystem();
        } else if (command.equals(MENU_SINGLESYSTEM_CREATE)) {
            createCombinationSingleSystem();
        } else if (command.equals(MENU_SINGLESYSTEM_COMBINATION_GRAPH)) {
            viewCombinationGraph();
        } else if (command.equals(MENU_RSYSTEM_CREATE)) {
            createRSystem();
        } else if (command.equals(MENU_REDUCE_CHECKSYSTEM)) {
            showCheckReducedSystemDialog();
        } else if (command.equals(MENU_BANKERSSYSTEM_REDUCE)) {
            reduceWithBankers();
        } else if (command.equals(MENU_REDUCED_EXPORTSYSTEM)) {
            exportReducedSystem();
        } else if (command.equals(HELP_ABOUT)) {
            showAboutDialog();
        }
    }

    /**
     * Show about dialog.
     */
    private void showAboutDialog() {
        try {
            TextMessages textMessages = TextMessages.getInstance();
            AboutDialog mainf = new AboutDialog();
            JOptionPane.showMessageDialog(maltipsGameType.getMainFrame(),
                    mainf,
                    textMessages.getText(HELP_ABOUT),
                    JOptionPane.PLAIN_MESSAGE);
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Create a mathimatical system.
     */
    private void createMathimaticalSystem() {
        try {
            MaltipsDocument maltipsDocument = (MaltipsDocument) maltipsGameType.getDocument();
            maltipsDocument.getMaltipsSystem().getMathimatical().reduce();

            maltipsDocument.setDocumentIsDirty(true);
        } catch (ReducingParametersNotSetException e) {
            try {
                TextMessages textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        textMessages.getText(MUST_SELECT_N_GAMES),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } catch (GeneralApplicationException ex) {
                Logger.getLogger(MaltipsMenuHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * View the reduced system.
     */
    private void viewReducedSystem() {
        try {
            ViewReducedSystemDialog dialog = new ViewReducedSystemDialog(maltipsGameType);
            dialog.show();
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Create combination system for the single system.
     */
    private void createCombinationSingleSystem() {
        try {
            MainFrame mainFrame = (MainFrame) maltipsGameType.getMainFrame();
            MaltipsDocument maltipsDocument = (MaltipsDocument) maltipsGameType.getDocument();
            MaltipsSystem maltipsSystem = maltipsDocument.getMaltipsSystem();
            SingleSystemCombinationReducerCallback callback = new SingleSystemCombinationReducerCallbackImpl(maltipsSystem, mainFrame, maltipsDocument);

            maltipsSystem.getSingleSystem().reduce(callback);   // Reduce
        } catch (ReducingParametersNotSetException e) {
            TextMessages textMessages;

            try {
                textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        textMessages.getText(INVALID_RIGHTS_IN_SINGLESYSTEM),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);

            } catch (GeneralApplicationException ex) {
                ex.printStackTrace();
                System.exit(0);
            }
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Create a r-system.
     */
    private void createRSystem() {
        try {
            MaltipsDocument maltipsDocument = (MaltipsDocument) maltipsGameType.getDocument();
            maltipsDocument.getMaltipsSystem().getRsystem().reduce();

            maltipsDocument.setDocumentIsDirty(true);
        } catch (ReducingParametersNotSetException e) {
            // Number of selected games isn't what the system type defines.
            // Actor must select/unselect some games.
            TextMessages textMessages;
            try {
                textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        textMessages.getText(INVALID_RSYSTEM_SET),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } catch (GeneralApplicationException ex) {
                e.printStackTrace();
                System.exit(0);
            }
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Show check reduced system dialog.
     */
    private void showCheckReducedSystemDialog() {
        try {
            CheckReducedSystemDialog dialog = new CheckReducedSystemDialog(maltipsGameType);
            dialog.show();
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Reduce with the bankers system.
     */
    private void reduceWithBankers() {
        try {
            MaltipsDocument maltipsDocument = (MaltipsDocument) maltipsGameType.getDocument();
            maltipsDocument.getMaltipsSystem().getBankers().reduce();
            maltipsDocument.setDocumentIsDirty(true);
        } catch (ReducingParametersNotSetException e) {
            try {
                TextMessages textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        textMessages.getText(MUST_SELECT_ATLEAST_ONE_GAME),
                        textMessages.getText(DIALOG_ALERT_TITLE), JOptionPane.ERROR_MESSAGE);
            } catch (GeneralApplicationException ex) {
                ex.printStackTrace();
                System.exit(0);
            }
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * save document.
     */
    private void saveDocument() {
        MaltipsDocument maltipsDocument = (MaltipsDocument) maltipsGameType.getDocument();

        if (maltipsDocument.getMaltipsSystem().getFileName() == null) {
            // First time this document is saved.
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showSaveDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                maltipsDocument.getMaltipsSystem().setFileName(file);
            } else {
                return; // don't save document.

            }
        }

        // Save maltips document to disk.
        try {
            // save as XML document
            MaltipsSystemRepositoryIF repository = new MaltipsSystemRepository();
            repository.save(maltipsDocument.getMaltipsSystem());

            // change dirty flag
            maltipsDocument.setDocumentIsDirty(false);

            // update application title
            TextMessages textMessages = TextMessages.getInstance();
            String newTitle = textMessages.getText(PROGRAM_TITLE) + " - " + maltipsDocument.getMaltipsSystem().getFileName().getAbsolutePath();
            maltipsGameType.getMainFrame().setTitle(newTitle);
        } catch (Exception e) {
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
     * Close the current document.
     */
    private void closeDocument() {
        try {
            boolean isDirty = ((MaltipsDocument) maltipsGameType.getDocument()).getIsDocumentDirty();

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
            ((MainFrame) maltipsGameType.getMainFrame()).initMainFrame();
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Export the reduced system to a svenskaspel compliant format.
     */
    private void exportReducedSystem() {
        MaltipsDocument maltipsDocument = (MaltipsDocument) maltipsGameType.getDocument();

        if (maltipsDocument.getMaltipsSystem().getFileName() == null) {
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

        // export reduced system to a .export file.
        MaltipsSystemRepositoryIF repository = new MaltipsSystemRepository();
        
        try {
            repository.exportReducedSystem(maltipsDocument.getMaltipsSystem());
        } catch (GeneralApplicationException ex) {
            Logger.getLogger(MaltipsMenuHandler.class.getName()).log(Level.SEVERE, null, ex);
            try {
                TextMessages textMessages = TextMessages.getInstance();

                JOptionPane.showMessageDialog(null,
                        textMessages.getText(DOCUMENT_MUST_FIRST_BE_SAVED),
                        textMessages.getText(DIALOG_ALERT_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /** 
     * show combination graph.
     */
    private void viewCombinationGraph() {
        try {
            ViewCombinationGraphDialog dialog = new ViewCombinationGraphDialog(maltipsGameType);
            dialog.show();
        } catch (ReducingParametersNotSetException e) {
            try {
                TextMessages textMessages = TextMessages.getInstance();
                JOptionPane.showMessageDialog(null,
                        textMessages.getText(e.getMessage()),
                        textMessages.getText(DIALOG_ALERT_TITLE), JOptionPane.ERROR_MESSAGE);
            } catch (GeneralApplicationException ex) {
                ex.printStackTrace();
                System.exit(0);
            }
        } catch (GeneralApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
