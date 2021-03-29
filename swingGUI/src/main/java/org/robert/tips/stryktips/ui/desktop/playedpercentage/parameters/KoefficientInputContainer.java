package org.robert.tips.stryktips.ui.desktop.playedpercentage.parameters;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.util.TextMessages;

import javax.swing.*;
import java.awt.*;

/**
 * Container for the koefficient parameters (min and max.
 *
 * Use MVC pattern to update the percent domain model object (PlayedPercentage).
 *
 * @author Robert Siwerz.
 */
public class KoefficientInputContainer extends JPanel implements StryktipsTextMessages {

    public KoefficientInputContainer(StryktipsDocument stryktipsDocument) throws GeneralApplicationException {
        // Layout stuff
        setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();
        TextMessages textMessages = TextMessages.getInstance();

        // Label
        JLabel label = new JLabel( textMessages.getText( PERCENTAGE_KOEFFICIENT_PARAMETER_LABEL ) + " { ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add( label, constraints );

        // input
        JTextField lowRangeInputField = new JTextField( 4 );
        lowRangeInputField.setText(String.valueOf(stryktipsDocument.getStryktipsSystem().getPlayed().koefficientMin));
        LowRangeInputFieldHandler lowRangeInputFieldHandler = new LowRangeInputFieldHandler(stryktipsDocument, lowRangeInputField);
        lowRangeInputField.getDocument().addDocumentListener(lowRangeInputFieldHandler);
        constraints.gridx = 1;
        add( lowRangeInputField, constraints );

        label = new JLabel( " , " );
        constraints.gridx = 2;
        constraints.gridy = 0;
        add( label, constraints );

        JTextField highRangeInputField = new JTextField( 4 );
        highRangeInputField.setText(String.valueOf(stryktipsDocument.getStryktipsSystem().getPlayed().koefficientMax));
        HighRangeInputFieldHandler highRangeInputFieldHandler = new HighRangeInputFieldHandler(stryktipsDocument, highRangeInputField);
        highRangeInputField.getDocument().addDocumentListener(highRangeInputFieldHandler);
        constraints.gridx = 3;
        add( highRangeInputField, constraints );

        label = new JLabel( " }" );
        constraints.gridx = 4;
        constraints.gridy = 0;
        add( label, constraints );

    }
}
