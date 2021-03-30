package org.robert.tips.stryktips.ui.desktop.playedpercentage.parameters;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.util.TextMessages;

import javax.swing.*;
import java.awt.*;

/**
 * Container for the number of rights for 13 rights parameters (min and max).
 *
 * Use MVC pattern to update the percent domain model object (PlayedPercentage).
 *
 * @author Robert Siwerz.
 */
public class NumberOfRightsInputContainer extends JPanel implements StryktipsTextMessages {

    public NumberOfRightsInputContainer(StryktipsDocument stryktipsDocument) throws GeneralApplicationException {
        // Layout stuff
        setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();
        TextMessages textMessages = TextMessages.getInstance();

        // Label
        JLabel label = new JLabel( textMessages.getText( PERCENTAGE_NUMBER_OF_RIGHTS_PARAMETER_LABEL ) + " { ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add( label, constraints );

        // Input range
        JTextField lowRangeInputField = new JTextField( 4 );
        lowRangeInputField.setText(String.valueOf(stryktipsDocument.getStryktipsSystem().getPlayed().minimumNumberOfPeopleWithFullPot));
        LowNumberOfRightsRangeInputFieldHandler lowRangeInputFieldHandler = new LowNumberOfRightsRangeInputFieldHandler(stryktipsDocument, lowRangeInputField);
        lowRangeInputField.getDocument().addDocumentListener(lowRangeInputFieldHandler);
        constraints.gridx = 1;
        add( lowRangeInputField, constraints );

        label = new JLabel( " , " );
        constraints.gridx = 2;
        constraints.gridy = 0;
        add( label, constraints );

        JTextField highRangeInputField = new JTextField( 4 );
        highRangeInputField.setText(String.valueOf(stryktipsDocument.getStryktipsSystem().getPlayed().minimumNumberOfPeopleWithFullPot));
        HighNumberOfRightsRangeInputFieldHandler highRangeInputFieldHandler = new HighNumberOfRightsRangeInputFieldHandler(stryktipsDocument, highRangeInputField);
        highRangeInputField.getDocument().addDocumentListener(highRangeInputFieldHandler);
        constraints.gridx = 3;
        add( highRangeInputField, constraints );

        label = new JLabel( " }" );
        constraints.gridx = 4;
        constraints.gridy = 0;
        add( label, constraints );
    }
}
