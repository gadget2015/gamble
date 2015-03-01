package org.robert.tips.stryktips.types;

import javax.swing.JTextField;

/**
 * This is a stryktips odds text field.
 * @author Robert Siwerz.
 */
public class StryktipsOddsField extends JTextField
{
    /**
     * the rownumber that this button is for.
     */
    private int rownumber;  
    
    /**
     * Constructs a stryktips button.
     * @param rownumber The row that this button is for.
     */
    public StryktipsOddsField( int rownumber )
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