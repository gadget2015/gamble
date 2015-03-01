package org.robert.tips.stryktips.ui.desktop.rsystem;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.types.TipsButton;

/**
 * This is where the R system are defined.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class ButtonViewController extends JPanel implements StryktipsConstants,
        ChangeListener {

    private StryktipsDocument stryktipsDocument;
    private TipsButton[] stryktipsButtons = new TipsButton[NUMBER_OF_GAMES * NUMBER_OF_GAMEOPTIONS];

    public ButtonViewController(StryktipsDocument stryktipsDocument) {
        this.stryktipsDocument = stryktipsDocument;
        setLayout(new GridLayout(NUMBER_OF_GAMES, NUMBER_OF_GAMEOPTIONS + 1));
        RSystemButtonViewControllerHandler rSystemButtonViewControllerHandler = new RSystemButtonViewControllerHandler(stryktipsDocument);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            add(new JLabel(String.valueOf(i + 1) + "."));

            stryktipsButtons[i * NUMBER_OF_GAMEOPTIONS] = new TipsButton(GAMEVALUE_ONE, i);
            stryktipsButtons[i * NUMBER_OF_GAMEOPTIONS].addActionListener(rSystemButtonViewControllerHandler);
            add(stryktipsButtons[i * NUMBER_OF_GAMEOPTIONS]);

            stryktipsButtons[i * NUMBER_OF_GAMEOPTIONS + 1] = new TipsButton(GAMEVALUE_TIE, i);
            stryktipsButtons[i * NUMBER_OF_GAMEOPTIONS + 1].addActionListener(rSystemButtonViewControllerHandler);
            add(stryktipsButtons[i * NUMBER_OF_GAMEOPTIONS + 1]);

            stryktipsButtons[i * NUMBER_OF_GAMEOPTIONS + 2] = new TipsButton(GAMEVALUE_TWO, i);
            stryktipsButtons[i * NUMBER_OF_GAMEOPTIONS + 2].addActionListener(rSystemButtonViewControllerHandler);
            add(stryktipsButtons[i * NUMBER_OF_GAMEOPTIONS + 2]);
        }

        stryktipsDocument.addChangeListener(this);
    }

    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged(ChangeEvent event) {
        // Update the view with data from the mathimatical system.

        for (int i = 0; i < (NUMBER_OF_GAMEOPTIONS * NUMBER_OF_GAMES); i++) {
            char value = stryktipsDocument.getStryktipsSystem().getRsystem().getRSystemRow(i);

            if (value != 0) {
                stryktipsButtons[i].setState(true);
            }
        }
    }
}
