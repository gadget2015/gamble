package org.robert.tips.stryktips.ui.desktop.odds.parameters;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import org.robert.tips.util.TextMessages;
import org.robert.tips.util.TextMessageNotFoundException;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.stryktips.types.StryktipsConstants;

/**
 * This is where the minimum and maximum odds are defined.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class MinMaxController extends JPanel implements StryktipsTextMessages, StryktipsConstants
 {
    private StryktipsDocument stryktipsDocument;
    
    public MinMaxController( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException
    {
        this.stryktipsDocument = stryktipsDocument;
     
        setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();
        TextMessages textMessages = TextMessages.getInstance();
        
        // Heading
        constraints.gridwidth = 2;
        constraints.gridx = GridBagConstraints.REMAINDER;
        JLabel label = new JLabel( textMessages.getText( ODDS_BOUNDARIES_HEADING ) );
        add( label, constraints );
        
        // minimun odds input field
        JTextField minInputField = new JTextField( 5 );
        minInputField.setText( Float.toString( stryktipsDocument.getStryktipsSystem().getOdds().getMinimumOdds() ) );
        MinOddsControllerHandler minOddsControllerHandler = new MinOddsControllerHandler( stryktipsDocument, minInputField );
        minInputField.getDocument().addDocumentListener( minOddsControllerHandler );
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 1;
        add( new JLabel( textMessages.getText( MIN_ODDS_BOUNDARY ) ), constraints );        
        constraints.gridx = 1;
        add( minInputField, constraints );
        
        // max 
        JTextField maxInputField = new JTextField( 5 );
        maxInputField.setText( Float.toString( stryktipsDocument.getStryktipsSystem().getOdds().getMaximumOdds() ) );
        MaxOddsControllerHandler maxOddsControllerHandler = new MaxOddsControllerHandler( stryktipsDocument, maxInputField );
        maxInputField.getDocument().addDocumentListener( maxOddsControllerHandler );
           
        constraints.gridx = 0;
        constraints.gridy = 2;
        add( new JLabel( textMessages.getText( MAX_ODDS_BOUNDARY ) ), constraints );
        constraints.gridx = 1;
        add( maxInputField, constraints );
    }
 }