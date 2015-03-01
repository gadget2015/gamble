package org.robert.tips.stryktips.ui.desktop.combination.parameters;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import org.robert.tips.stryktips.StryktipsDocument;
/**
  * This is an eventhandler class for the comboboxes that handles the minimum number of
  * rights in the singlerow system.
  * @author Robert Siwerz.
  */
class MinRightsControllerHandler implements ActionListener
{
    private StryktipsDocument stryktipsDocument;
  
	/**
	 * Contructor.
	 * @param SingleSystemButtonViewHandler Reference to the current stryktips document.
	 */
	public MinRightsControllerHandler( StryktipsDocument stryktipsDocument ) 
	{
        this.stryktipsDocument = stryktipsDocument;
    }
    
    /**
    * This method is called everytime an action event is fired.
    * @param ActionEvent The event object
    */
    public void actionPerformed(ActionEvent event ) 
    {
		String command = event.getActionCommand();
		Object tmpValue = ( ( JComboBox )event.getSource() ).getSelectedItem();
        int value = Integer.parseInt( ( String ) tmpValue );
        
        stryktipsDocument.getStryktipsSystem().getCombination().setMinInSingleRow( value );
    }    
} 