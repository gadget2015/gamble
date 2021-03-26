package org.robert.tips.stryktips.ui.desktop.playedpercentage;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.stryktips.ui.desktop.playedpercentage.percentageinput.PercentageInputContainer;
import org.robert.tips.util.TextMessages;

import javax.swing.*;
import java.awt.*;

/**
 * Contains all UI components that builds up the played percentage system.
 *
 * @author Robert Siwerz.
 */
public class PlayedPercentageContainer extends JPanel implements StryktipsTextMessages {

    public PlayedPercentageContainer( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException {
        setLayout( new GridBagLayout() );
        TextMessages textMessages = TextMessages.getInstance();
        GridBagConstraints constraints = new GridBagConstraints();

        // The input fields for the percentage values
        PercentageInputContainer inputFields = new PercentageInputContainer(stryktipsDocument);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        add(inputFields, constraints);

    }
}
