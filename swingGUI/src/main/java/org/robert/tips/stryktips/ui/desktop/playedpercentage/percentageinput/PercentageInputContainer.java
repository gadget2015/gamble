package org.robert.tips.stryktips.ui.desktop.playedpercentage.percentageinput;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.util.TextMessages;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static org.robert.tips.stryktips.types.StryktipsConstants.NUMBER_OF_GAMEOPTIONS;
import static org.robert.tips.stryktips.types.StryktipsConstants.NUMBER_OF_GAMES;

/**
 * Contains all components that builds up the percentage input fields table.
 *
 * Use MVC pattern to update the percent domain model object (PlayedPercentage).
 * @author Robert Siwerz.
 */
public class PercentageInputContainer extends JPanel implements StryktipsTextMessages {

    public PercentageInputContainer( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException {
        setLayout( new GridBagLayout() );
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder( etchedBorder );

        TextMessages textMessages = TextMessages.getInstance();
        titledBorder.setTitle( textMessages.getText( PLAYEDPERCENTAGE_TITLE ) );
        setBorder( titledBorder );

        // Input fields
        JPanel panel = new JPanel();
        panel.setLayout( new GridLayout( NUMBER_OF_GAMES, NUMBER_OF_GAMEOPTIONS ) );

        for ( int i = 0; i < NUMBER_OF_GAMES; i++ ) {
            // one result
            StryktipsPercentageField oneField = new StryktipsPercentageField(i * NUMBER_OF_GAMEOPTIONS);
            oneField.setText( Float.toString(stryktipsDocument.getStryktipsSystem().getPlayed().getPercentage( i *NUMBER_OF_GAMEOPTIONS)));
            PercentageInputFieldHandler oneFieldController = new PercentageInputFieldHandler(stryktipsDocument, oneField);
            oneField.getDocument().addDocumentListener(oneFieldController);
            oneField.addFocusListener(new PercentageFieldInputFocutListener(oneField));
            panel.add(oneField);

            // Tied/equal
            StryktipsPercentageField xField = new StryktipsPercentageField(i * NUMBER_OF_GAMEOPTIONS + 1 );
            xField.setText( Float.toString(stryktipsDocument.getStryktipsSystem().getPlayed().getPercentage( i *NUMBER_OF_GAMEOPTIONS + 1 )));
            PercentageInputFieldHandler xFieldController = new PercentageInputFieldHandler(stryktipsDocument, xField);
            xField.getDocument().addDocumentListener(xFieldController);
            xField.addFocusListener(new PercentageFieldInputFocutListener(xField));
            panel.add(xField);

            // Lose.
            StryktipsPercentageField looseField = new StryktipsPercentageField(i * NUMBER_OF_GAMEOPTIONS + 2 );
            looseField.setText( Float.toString(stryktipsDocument.getStryktipsSystem().getPlayed().getPercentage( i *NUMBER_OF_GAMEOPTIONS + 2 )));
            PercentageInputFieldHandler looseFieldController = new PercentageInputFieldHandler(stryktipsDocument, looseField);
            looseField.getDocument().addDocumentListener(looseFieldController);
            looseField.addFocusListener(new PercentageFieldInputFocutListener(looseField));
            panel.add(looseField);
        }

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 0;
        add(panel);
    }
}
