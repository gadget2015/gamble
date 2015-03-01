package org.robert.tips.types;

import javax.swing.JButton;
import java.awt.Color;
import org.robert.tips.maltips.types.MaltipsConstants;

/**
 * This is a tips button. The button have an initial value, and when the
 * button is pressed, it gets a green background with the initial value in black.
 * When the button is pressed the second time ( when the button is green ) the
 * green background is removed and replaced with the gray ( as initially ).
 * @author Robert Siwerz.
 */
public class TipsButton extends JButton implements MaltipsConstants
{
    private char character; // character on the button.
    private int rownumber;  // the rownumber that this button is for.
    private boolean state;  // state for the button, true=selected, false= not selected.
    
    /**
     * Constructs a stryktips button.
     * @param character Text to display on the button.
     * @param rownumber The row that this button is for.
     */
    public TipsButton( char character, int rownumber )
    {
        super( String.valueOf( character ) );
        this.character = character;
        this.rownumber = rownumber;
    }

    /**
     * Constructs a maltips button.
     * @param rownumber The game that this button is for.
     */
    public TipsButton( int rownumber )
    {
        super( String.valueOf( rownumber ) );
        this.character = ( char ) ( rownumber - 1 );
        this.rownumber = rownumber - 1;
    }
    
    /**
     * Set the state of this button.
     * @param state New state for this button.
     */
    public void setState( boolean state )
    {
        this.state = state;
        
        if ( state )
        {
            setBackground( Color.green );
        }
        else
        {
            setBackground( Color.lightGray );
        }
    }
    
    /**
     * Get this buttons state.
     * @return boolean The state of this button.
     */
    public boolean getState()
    {
        return state;
    }
     
    /**
     * Get the rownumber this button is for.
     * @return int The rownumber this button is for.
     */
    public int getRownumber()
    {
        return rownumber;
    }
    
    /**
     * Get the character for this button.
     * @return char The character on this button.
     */
    public char getCharacter()
    {
        return character;
    }
}