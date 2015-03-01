package org.robert.tips.stryktips.ui.desktop.odds.oddsinput;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import org.robert.tips.stryktips.types.StryktipsOddsField;

/**
 * This is the focus listener added to all odds input fields.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class OddsInputFocusListener implements FocusListener
{
    private StryktipsOddsField stryktipsOddsField;
    
    /**
     * Creates a new instance.
     * @param stryktipsOddsField Field to add focus listener to.
     */
    public OddsInputFocusListener( StryktipsOddsField stryktipsOddsField )
    {
        this.stryktipsOddsField = stryktipsOddsField;
    }
    
    /**
     * Implemented from interface.
     */
    public void focusGained(FocusEvent e)
    { 
        stryktipsOddsField.selectAll();
    }
    
    /**
     * Implementef from interface.
     */
    public void focusLost(FocusEvent e)
    {
    }
}