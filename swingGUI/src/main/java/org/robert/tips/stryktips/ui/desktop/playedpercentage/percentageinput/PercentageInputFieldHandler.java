package org.robert.tips.stryktips.ui.desktop.playedpercentage.percentageinput;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.stryktips.types.StryktipsOddsField;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.util.TextMessages;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Input event handler for the percentage input field. It checks that the input
 * conforms to a valid float value (exampel 0.21 or 0.35).
 * Use the MVC pattern: This class i the C = Controller.
 *
 * @author Robert Siwerz.
 */
public class PercentageInputFieldHandler implements DocumentListener,
        StryktipsErrorMessages,
        ErrorMessages,
        StryktipsConstants {
    private StryktipsDocument stryktipsDocument;
    private StryktipsPercentageField stryktipsPercentageField;

    public PercentageInputFieldHandler(StryktipsDocument stryktipsDocument, StryktipsPercentageField stryktipsPercentageField) {
        this.stryktipsDocument = stryktipsDocument;
        this.stryktipsPercentageField = stryktipsPercentageField;
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

    private void updateModel() {
        int position = stryktipsPercentageField.getRownumber();
        String tmpValue = stryktipsPercentageField.getText();
        System.out.println("Update model: position = " + position + ", value = " + tmpValue);

        try
        {
            tmpValue = ( tmpValue.length() == 0 ) ? "0.0": tmpValue;
            Float value = Float.valueOf( tmpValue );

            stryktipsDocument.getStryktipsSystem().getPlayed().setPercentage( position, value.floatValue() );
            stryktipsDocument.setDocumentIsDirty(true);
        }
        catch ( NumberFormatException e )
        {
            try
            {
                TextMessages textMessages = TextMessages.getInstance();

                JOptionPane.showMessageDialog( null,
                        textMessages.getText( INVALID_PERCENTAGE_FROMAT ),
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
