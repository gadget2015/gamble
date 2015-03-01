package org.robert.tips.stryktips.ui.desktop.mathimatical;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import org.robert.tips.types.TipsButton;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.util.TextMessages;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.GameAlreadySetException;

/**
  * This is an eventhandler class for the extended system buttons.
  * @author Robert Siwerz.
  */
class MathimaticalSystemButtonViewControllerHandler implements ActionListener, 
                                               StryktipsErrorMessages,
                                               ErrorMessages,
                                               StryktipsConstants
{
    private StryktipsDocument stryktipsDocument;
  
	/**
	 * Contructor.
	 * @param SingleSystemButtonViewHandler Reference to the current stryktips document.
	 */
	public MathimaticalSystemButtonViewControllerHandler( StryktipsDocument stryktipsDocument ) 
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
		TipsButton button = ( TipsButton ) event.getSource();
		
		int rowNumber = button.getRownumber();
		char buttonValue = ( char ) button.getCharacter();
		rowNumber *= NUMBER_OF_GAMEOPTIONS;
		rowNumber += ( buttonValue == GAMEVALUE_TIE ) ? 1: 0;
		rowNumber += ( buttonValue == GAMEVALUE_TWO ) ? 2: 0;
    		
		if( button.getState() == false ) 
		{		
		    // Button pressed at the n time
		    try
		    {
			    stryktipsDocument.getStryktipsSystem().getMathimatical().setMathimaticalRow( rowNumber, buttonValue );
			    stryktipsDocument.setDocumentIsDirty(true);
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
			    stryktipsDocument.getStryktipsSystem().getMathimatical().setMathimaticalRow( rowNumber, ( char )0 );
                            stryktipsDocument.setDocumentIsDirty(true);
			    button.setState( false );
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