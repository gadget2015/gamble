package org.robert.tips.stryktips.ui.desktop.playedpercentage.parameters;

/**
 * This is an event handler for the number of rights low range input text field.
 *
 * Use the MVC pattern: This class i the C = Controller.
 *
 * @author Robert Siwerz.
 */

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.util.TextMessages;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This is an event handler for the number of rights low range input text field.
 *
 * Use the MVC pattern: This class i the C = Controller.
 *
 * @author Robert Siwerz.
 */
public class LowNumberOfRightsRangeInputFieldHandler implements DocumentListener,
        StryktipsErrorMessages,
        StryktipsTextMessages,
        ErrorMessages {

    private StryktipsDocument stryktipsDocument;
    private JTextField lowRangeInputField;

    public LowNumberOfRightsRangeInputFieldHandler(StryktipsDocument stryktipsDocument, JTextField lowRangeInputField) {
        this.stryktipsDocument = stryktipsDocument;
        this.lowRangeInputField = lowRangeInputField;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateModel();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateModel();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateModel();
    }

    /**
     * Update tho Model.
     */
    private void updateModel()
    {
        String tmpValue = lowRangeInputField.getText();

        try
        {
            tmpValue = ( tmpValue.length() == 0 ) ? "0": tmpValue;
            int value = Integer.valueOf( tmpValue );

            stryktipsDocument.getStryktipsSystem().getPlayed().minimumNumberOfPeopleWithFullPot = value;
            stryktipsDocument.setDocumentIsDirty(true);
        }
        catch ( NumberFormatException e )
        {
            try
            {
                TextMessages textMessages = TextMessages.getInstance();

                JOptionPane.showMessageDialog( null,
                        textMessages.getText( INVALID_NUMBER_OF_RIGHTS_FORMAT ),
                        textMessages.getText( DIALOG_ALERT_TITLE ),
                        JOptionPane.ERROR_MESSAGE );
            }
            catch ( GeneralApplicationException e2 )
            {
                e2.printStackTrace();
                System.exit(0);
            }
        }
    }
}
