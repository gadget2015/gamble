package org.robert.tips.maltips.ui.tab.bankers;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.robert.tips.types.TipsButton;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.util.TextMessages;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.maltips.MaltipsDocument;

/**
  * This is an eventhandler class for the bankers system buttons.
  * @author Robert Siwerz.
  */
class BankersSystemButtonControllerHandler implements ActionListener,
                                                      ErrorMessages
{
    private MaltipsDocument maltipsDocument;
  
	/**
	 * Create a new instance of this handler.
	 * @param maltipsDocument Reference to the current maltips document.
	 */
	public BankersSystemButtonControllerHandler( MaltipsDocument maltipsDocument ) 
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
		TipsButton button = ( TipsButton ) event.getSource();

		int rowNumber = button.getRownumber();
    		
		if( button.getState() == false ) 
		{		
		    // Button pressed at the n time
		    try
		    {
			    maltipsDocument.getMaltipsSystem().getBankers().setBankersRow( rowNumber, rowNumber + 1 );
			    button.setState( true );
                            maltipsDocument.setDocumentIsDirty(true);
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
			    maltipsDocument.getMaltipsSystem().getBankers().setBankersRow( rowNumber, 0 );
			    button.setState( false );
                            maltipsDocument.setDocumentIsDirty(true);
			}
			catch ( GameAlreadySetException e )
			{
                // This should never happen!            
                e.printStackTrace();
                System.exit(0); 
			}
		}	
    }    
} 