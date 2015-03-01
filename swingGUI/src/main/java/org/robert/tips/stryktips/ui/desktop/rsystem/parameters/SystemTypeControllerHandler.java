package org.robert.tips.stryktips.ui.desktop.rsystem.parameters;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.RSystemType;

/**
  * This is an eventhandler class for the comboboxes that handles the system type.
  * @author Robert Siwerz.
  */
class SystemTypeControllerHandler implements ActionListener
{
    private StryktipsDocument stryktipsDocument;
  
	/**
	 * Contructor.
	 * @param stryktipsDocument Reference to the current stryktips document.
	 */
	public SystemTypeControllerHandler( StryktipsDocument stryktipsDocument ) 
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
		Object value = ( ( JComboBox )event.getSource() ).getSelectedItem();
        RSystemType type = new RSystemType( ( String ) value );
        stryktipsDocument.getStryktipsSystem().getRsystem().setSystemType( type );
    }    
} 