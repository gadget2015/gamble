package org.robert.tips.stryktips.ui.desktop.playedpercentage.percentageinput;

import javax.swing.*;

/**
 * This is a stryktips percentage text field.
 *
 * @author Robert Siwerz.
 */
public class StryktipsPercentageField extends JTextField {
    /**
     * the rownumber that this button is for.
     */
    private int rownumber;

    /**
     * Constructs a percentage input text field..
     * @param rownumber The row that this filed is for, 0-13.
     */
    public StryktipsPercentageField( int rownumber )
    {
        super( 3 );
        this.rownumber = rownumber;
    }

    /**
     * Get the rownumber this button is for.
     * @return int The rownumber this button is for.
     */
    public int getRownumber()
    {
        return rownumber;
    }
}
