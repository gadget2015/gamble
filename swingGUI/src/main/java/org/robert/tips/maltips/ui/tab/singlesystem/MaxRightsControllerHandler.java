package org.robert.tips.maltips.ui.tab.singlesystem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import org.robert.tips.maltips.MaltipsDocument;

/**
  * This is an eventhandler class for the comboboxes that handles the maximum number of
  * rights in the singlerow system.
  * @author Robert Siwerz.
  */
class MaxRightsControllerHandler implements ActionListener
{
    private MaltipsDocument maltipsDocument;
  
	/**
	 * Contructor.
	 * @param maltipsDocument Reference to the current stryktips document.
	 */
	public MaxRightsControllerHandler( MaltipsDocument maltipsDocument ) 
	{
        this.maltipsDocument = maltipsDocument;
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
        
        maltipsDocument.getMaltipsSystem().getSingleSystem().setMaxInSingleRow( value );
        maltipsDocument.setDocumentIsDirty(true);
    }    
} 