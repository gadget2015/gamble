package org.robert.tips.stryktips.ui.desktop.playedpercentage.parameters;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.stryktips.ui.desktop.odds.parameters.ReducedView;
import org.robert.tips.util.TextMessages;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Contains all components that is used to set parameters when reducing the percentage system.
 *
 * Use MVC pattern to update the percent domain model object (PlayedPercentage).
 *
 * @author Robert Siwerz.
 */
public class PercentageParametersContainer extends JPanel implements StryktipsTextMessages {

    public PercentageParametersContainer(StryktipsDocument stryktipsDocument ) throws GeneralApplicationException {
        setLayout( new GridBagLayout() );
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder( etchedBorder );

        TextMessages textMessages = TextMessages.getInstance();
        titledBorder.setTitle( textMessages.getText( PERCENTAGE_SYSTEM_PARAMETERS_TITLE ) );
        setBorder( titledBorder );

        GridBagConstraints constraints = new GridBagConstraints();

        // Add parameters
        RevenueInputContainer revenueInputContainer = new RevenueInputContainer(stryktipsDocument);
        constraints.anchor = GridBagConstraints.NORTHWEST;
        add(revenueInputContainer, constraints);

        // add a line separator
        constraints.gridy = 1;
        add( new JLabel( "-----------------------------------------------------------" ), constraints );

        // Show number of rows in reduced system
        ReducedView reducedView = new ReducedView( stryktipsDocument );
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        add( reducedView, constraints );
    }
}
