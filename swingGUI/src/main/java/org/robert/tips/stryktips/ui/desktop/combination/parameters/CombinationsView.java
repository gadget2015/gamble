package org.robert.tips.stryktips.ui.desktop.combination.parameters;

import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.util.TextMessages;
import org.robert.tips.util.TextMessageNotFoundException;
import org.robert.tips.types.GameTextMessages;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * This is where the number of combinations are displayed.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class CombinationsView extends JPanel implements ChangeListener, 
                                                 StryktipsTextMessages,
                                                 GameTextMessages
 {
    private StryktipsDocument stryktipsDocument;
    private JTextField numberOfCombinations;
    
    public CombinationsView( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException
    {
        this.stryktipsDocument = stryktipsDocument;

        setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();
        
        // label
        TextMessages textMessages = TextMessages.getInstance();
        add( new JLabel( textMessages.getText( NUMBER_IN_REDUCED_SYSTEM ) ), constraints );
        
        // text field.
        numberOfCombinations = new JTextField( 5 );
        numberOfCombinations.setEditable( false );
        add( numberOfCombinations, constraints );
        
        stryktipsDocument.addChangeListener( this );        
    }
    
    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged( ChangeEvent event )
    {
        int value = stryktipsDocument.getStryktipsSystem().getReducedSystem().size();
        numberOfCombinations.setText( String.valueOf( value ) );      
    }
 }