package org.robert.tips.stryktips.ui.desktop.playedpercentage.percentageinput;


import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * This is the focus listener added to all percentage input fields, and
 * it selects the input when focuesed. This will then be easier for
 * the user to edit the input values.
 *
 * @author Robert Siwerz.
 */
public class PercentageFieldInputFocutListener implements FocusListener {
    private StryktipsPercentageField stryktipsPercentageField;

    public PercentageFieldInputFocutListener( StryktipsPercentageField stryktipsPercentageField )
    {
        this.stryktipsPercentageField = stryktipsPercentageField;
    }
    @Override
    public void focusGained(FocusEvent e) {
        stryktipsPercentageField.selectAll();
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
