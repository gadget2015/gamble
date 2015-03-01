package org.robert.tips.maltips.ui.dialogs.checkreducesystem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.robert.tips.types.TipsButton;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.util.TextMessages;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.maltips.types.MaltipsErrorMessages;
import org.robert.tips.maltips.types.MaltipsConstants;

/**
  * This is an eventhandler class for the right row buttons.
  * @author Robert Siwerz.
  */
class RightRowControllerHandler implements ActionListener, 
                                           MaltipsErrorMessages,
                                           MaltipsConstants,
                                           ErrorMessages
{
    private CheckReducedSystemDocument checkReducedSystemDocument;
  
	/**
	 * Contructor.
	 * @param checkReducedSystemDocument Reference to the CheckReducedSystemDocument document.
	 */
	public RightRowControllerHandler( CheckReducedSystemDocument checkReducedSystemDocument ) 
	{
        this.checkReducedSystemDocument = checkReducedSystemDocument;
    }
    
    /**
    * This method is called everytime an action event is fired.
    * @param ActionEvent The event object
    */
    public void actionPerformed(ActionEvent event ) 
    {
		String command = event.getActionCommand();
		TipsButton button = ( TipsButton ) event.getSource();
		
		if( button.getState() == false ) 
		{		
		    // Button pressed at the n time
		    int rownumber = button.getRownumber();	    

            try
            {
			    checkReducedSystemDocument.setRightRow( rownumber, rownumber + 1 );
			    button.setState( true );
			}
			catch ( GameAlreadySetException e )
			{
			    try
			    {
			        TextMessages textMessages = TextMessages.getInstance();
        			    
			        JOptionPane.showMessageDialog( null, 
											    textMessages.getText( GAME_ALREADY_SET ), 
											    textMessages.getText( DIALOG_ALERT_TITLE ), 
											    JOptionPane.ERROR_MESSAGE ); 
                }
                catch ( GeneralApplicationException e2 )
                {
                    e2.printStackTrace();
                    System.exit(0);
                }
			}
		}
		else 
		{		
		    // Button pressed at the n+1 time
		    try
		    {
			    int rownumber = button.getRownumber();
			    checkReducedSystemDocument.setRightRow( rownumber, 0 );
			    button.setState( false );
			}
			catch ( GameAlreadySetException e )
			{
			    // this happens when actor tries to uncheck the row and that row is already
			    //unhecked by the extended system... but this can never occur.
			    e.printStackTrace();
			    System.exit(0);
			}			
		}		
    }    
} 