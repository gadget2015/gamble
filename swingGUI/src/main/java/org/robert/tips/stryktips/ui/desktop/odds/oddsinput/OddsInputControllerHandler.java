package org.robert.tips.stryktips.ui.desktop.odds.oddsinput;

import javax.swing.event.DocumentEvent;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentListener;
import org.robert.tips.types.TipsButton;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.util.TextMessages;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsOddsField;

/**
  * This is an eventhandler class for the main system buttons.
  * @author Robert Siwerz.
  */
class OddsInputControllerHandler implements DocumentListener, 
                                               StryktipsErrorMessages,
                                               ErrorMessages,
                                               StryktipsConstants
{
    private StryktipsDocument stryktipsDocument;
    private StryktipsOddsField stryktipsOddsField;
    
	/**
	 * Contructor.
	 * @param stryktipsDocument Reference to the current stryktips document.
	 * @param stryktipsOddsField The odds field.
	 */
	public OddsInputControllerHandler( StryktipsDocument stryktipsDocument, StryktipsOddsField stryktipsOddsField ) 
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
        int position = stryktipsOddsField.getRownumber();
        String tmpValue = stryktipsOddsField.getText();
        
        try
        {
            tmpValue = ( tmpValue.length() == 0 ) ? "0.0": tmpValue;
            Float value = Float.valueOf( tmpValue );
	
            stryktipsDocument.getStryktipsSystem().getOdds().setOddsSystem( position, value.floatValue() );                        
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