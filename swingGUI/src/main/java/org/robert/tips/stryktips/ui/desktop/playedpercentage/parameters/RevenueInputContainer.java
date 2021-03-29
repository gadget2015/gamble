package org.robert.tips.stryktips.ui.desktop.playedpercentage.parameters;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.util.TextMessages;

import javax.swing.*;
import java.awt.*;

/**
 * Container for the revenue parameter.
 *
 * Use MVC pattern to update the percent domain model object (PlayedPercentage).
 *
 * @author Robert Siwerz.
 */
public class RevenueInputContainer extends JPanel implements StryktipsTextMessages {

    public RevenueInputContainer(StryktipsDocument stryktipsDocument ) throws GeneralApplicationException {
        // Layout stuff
        setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();
        TextMessages textMessages = TextMessages.getInstance();

        // Label
        JLabel label = new JLabel( textMessages.getText( PERCENTAGE_REVENUE_PARAMETER_LABEL ) );
        add( label, constraints );

        // input
        JTextField revenueInputField = new JTextField( 7 );
        revenueInputField.setText(String.valueOf(stryktipsDocument.getStryktipsSystem().getPlayed().revenue));
        RevenueInputFieldHandler revenueInputFieldHandler = new RevenueInputFieldHandler(stryktipsDocument, revenueInputField);
        revenueInputField.getDocument().addDocumentListener(revenueInputFieldHandler);
        add( revenueInputField, constraints );
    }
}
