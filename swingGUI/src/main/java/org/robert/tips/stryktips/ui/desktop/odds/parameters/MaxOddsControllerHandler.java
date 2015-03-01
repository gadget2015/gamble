package org.robert.tips.stryktips.ui.desktop.odds.parameters;

import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.util.TextMessages;
import org.robert.tips.util.TextMessageNotFoundException;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.exceptions.GeneralApplicationException;

/**
  * This is an eventhandler class for the min odds input text field.
  * @author Robert Siwerz.
  */
class MaxOddsControllerHandler implements DocumentListener,
                                          StryktipsErrorMessages,
                                          StryktipsTextMessages,
                                          ErrorMessages
{
    private StryktipsDocument stryktipsDocument;
  
    private JTextField stryktipsOddsField;
    
	/**
	 * Contructor.
	 * @param stryktipsDocument Reference to the current stryktips document.
	 * @param stryktipsOddsField The odds field.
	 */
	public MaxOddsControllerHandler( StryktipsDocument stryktipsDocument, JTextField stryktipsOddsField ) 
	{
        this.stryktipsDocument = stryktipsDocument;
        this.stryktipsOddsField = stryktipsOddsField;
    }
    
    /**
     * Interface method implementation.
     */
    public void removeUpdate(DocumentEvent e)
    {
        updateOddsDocument();
    }
    
    /**
     * Interface method implementation.
     */    
    public void insertUpdate(DocumentEvent e) 
    {
        updateOddsDocument();
    }
    
    /**
     * Interface method implementation.
     */
    public void changedUpdate(DocumentEvent e)
    {	
        updateOddsDocument();
    }
    
    /**
     * update odds document
     */
    private void updateOddsDocument()
    {
        String tmpValue = stryktipsOddsField.getText();
        
        try
        {
            tmpValue = ( tmpValue.length() == 0 ) ? "0.0": tmpValue;
            Float value = Float.valueOf( tmpValue );
        		
            stryktipsDocument.getStryktipsSystem().getOdds().setMaximumOdds( value.floatValue() ); 
            stryktipsDocument.setDocumentIsDirty(true);
        }
        catch ( NumberFormatException e )
        {
            try
            {
                TextMessages textMessages = TextMessages.getInstance();
                    
                JOptionPane.showMessageDialog( null, 
										        textMessages.getText( INVALID_ODDS_FROMAT ), 
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
} 