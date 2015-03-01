package org.robert.tips;

import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.File;
import org.robert.tips.maltips.domain.model.MaltipsSystem;
import org.robert.tips.types.MenuChoices;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.util.TextMessages;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.StryktipsGameType;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.maltips.MaltipsGameType;
import org.robert.tips.maltips.MaltipsDocument;
import org.robert.tips.maltips.domain.model.MaltipsSystemRepository;
import org.robert.tips.maltips.domain.model.MaltipsSystemRepositoryIF;
import org.robert.tips.stryktips.domain.model.StryktipsSystem;
import org.robert.tips.stryktips.domain.model.StryktipsSystemRepository;
import org.robert.tips.stryktips.domain.model.StryktipsSystemRepositoryIF;

/**
 * This is an eventhandler class for the menubar in the MainFrame class.
 * @author Robert Geor�n
 */
class MainFrameMenuHandler implements ActionListener,
        MenuChoices,
        ErrorMessages {

    private MainFrame mainFrame;

    /**
     * Constructor.
     */
    public MainFrameMenuHandler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * This method is called everytime an actionevent is fired.
     * @param ActionEvent The event object
     */
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals(FILE_EXIT)) {
            System.exit(0);
        } else if (command.equals(FILE_NEW_MALTIPS)) {
            try {
                MaltipsGameType maltipsGameType = new MaltipsGameType(mainFrame);
                mainFrame.changeGameType(maltipsGameType);
            } catch (GeneralApplicationException e) {
                e.printStackTrace();
                System.exit(0);
            }
        } else if (command.equals(FILE_NEW_STRYKTIPS)) {
            try {
                StryktipsGameType stryktipsGameType = new StryktipsGameType(mainFrame);
                mainFrame.changeGameType(stryktipsGameType);
            } catch (GeneralApplicationException e) {
                e.printStackTrace();
                System.exit(0);
            }
        } else if (command.equals(FILE_OPEN_STRYKTIPS)) {
            openDocument();
        } else if (command.equals(FILE_OPEN_MALTIPS)) {
            openMaltipsDocument();
        } else if (command.equals(HELP_ABOUT)) {
            try {
                TextMessages textMessages = TextMessages.getInstance();
                AboutDialog mainf = new AboutDialog();
                JOptionPane.showMessageDialog(mainFrame,
                        mainf,
                        textMessages.getText(HELP_ABOUT),
                        JOptionPane.PLAIN_MESSAGE);
            } catch (GeneralApplicationException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    /**
     * Open a stryktips document.
     */
    private void openDocument() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            // Open document from disk

            try {
                StryktipsSystemRepositoryIF repository = new StryktipsSystemRepository();
                StryktipsSystem stryktipsSystem = repository.find(file);
                stryktipsSystem.setFileName(file);
                StryktipsDocument stryktipsDocument = new StryktipsDocument();
                stryktipsDocument.setStryktipsSystem(stryktipsSystem);                

                // change frame to new document.
                try {
                    StryktipsGameType stryktipsGameType = new StryktipsGameType(mainFrame, stryktipsDocument);
                    mainFrame.changeGameType(stryktipsGameType);

                    // update application title
                    TextMessages textMessages = TextMessages.getInstance();
                    String newTitle = textMessages.getText(PROGRAM_TITLE) + " - " + stryktipsDocument.getStryktipsSystem().getFileName().getAbsolutePath();
                    stryktipsGameType.getMainFrame().setTitle(newTitle);
                } catch (GeneralApplicationException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    TextMessages textMessages = TextMessages.getInstance();

                    JOptionPane.showMessageDialog(null,
                            textMessages.getText(ERROR_WHILE_OPENING + "\n" + file.getAbsolutePath()),
                            textMessages.getText(DIALOG_ALERT_TITLE),
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Open a maltips document.
     */
    private void openMaltipsDocument() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            // Open document from disk

            try {
                MaltipsSystemRepositoryIF repository = new MaltipsSystemRepository();
                MaltipsSystem foundSystem = repository.find(file);
                foundSystem.setFileName(file);
                MaltipsDocument maltipsDocument = new MaltipsDocument(foundSystem);

                // change frame to new document.
                try {
                    MaltipsGameType maltipsGameType = new MaltipsGameType(mainFrame, maltipsDocument);
                    mainFrame.changeGameType(maltipsGameType);

                    // update application title
                    TextMessages textMessages = TextMessages.getInstance();
                    String newTitle = textMessages.getText(PROGRAM_TITLE) + " - " + maltipsDocument.getMaltipsSystem().getFileName().getAbsolutePath();
                    maltipsGameType.getMainFrame().setTitle(newTitle);
                } catch (GeneralApplicationException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    TextMessages textMessages = TextMessages.getInstance();

                    JOptionPane.showMessageDialog(null,
                            textMessages.getText(ERROR_WHILE_OPENING + "\n" + file.getAbsolutePath()),
                            textMessages.getText(DIALOG_ALERT_TITLE),
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    System.exit(0);
                }
            }
        }
    }
} 