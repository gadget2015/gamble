package org.robert.tips.stryktips.ui.desktop.bankers;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.robert.tips.types.TipsButton;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.domain.model.StryktipsSystem;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.util.TextMessages;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.exceptions.GameNotSetException;

/**
 * This is an eventhandler class for the main system buttons.
 * @author Robert Siwerz.
 */
class BankersSystemButtonControllerHandler implements ActionListener,
        StryktipsErrorMessages,
        ErrorMessages,
        StryktipsConstants {

    private StryktipsDocument stryktipsDocument;
    private StryktipsSystem stryktipsSystem;

    /**
     * Contructor.
     * @param SingleSystemButtonViewHandler Reference to the current stryktips document.
     */
    public BankersSystemButtonControllerHandler(StryktipsDocument stryktipsDocument) {
        this.stryktipsDocument = stryktipsDocument;
        this.stryktipsSystem = stryktipsDocument.getStryktipsSystem();
    }

    /**
     * This method is called everytime an action event is fired.
     * @param ActionEvent The event object
     */
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        TipsButton button = (TipsButton) event.getSource();


        if (button.getState() == false) {
            // Button pressed at the n time

            try {
                int rowNumber = button.getRownumber();
                char buttonValue = button.getCharacter();
                stryktipsSystem.getBanker().setBankersRow(rowNumber, buttonValue);
                button.setState(true);
                stryktipsDocument.setDocumentIsDirty(true);
            } catch (GameNotSetException e) {
                // actor haven't set this game option in the single row system.
                // It must be set in the single row system to be a banker.
                try {
                    TextMessages textMessages = TextMessages.getInstance();

                    JOptionPane.showMessageDialog(null,
                            textMessages.getText(GAME_NOT_SET_IN_SINGLEROWSYSTEM),
                            textMessages.getText(DIALOG_ALERT_TITLE),
                            JOptionPane.ERROR_MESSAGE);
                } catch (GeneralApplicationException e2) {
                    e2.printStackTrace();
                    System.exit(0);
                }
            }
        } else {
            // Button pressed at the n+1 time
            try {
                int rownumber = button.getRownumber();
                stryktipsSystem.getBanker().setBankersRow(rownumber, (char) 0);
                button.setState(false);
                stryktipsDocument.setDocumentIsDirty(true);
            } catch (GameNotSetException e) {
                // this happens when actor tries to uncheck the row and that row is already
                //unhecked ... but this can never occur.
                e.printStackTrace();
                System.exit(0);
            }
        }


    }
} 