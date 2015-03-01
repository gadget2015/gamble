package org.robert.tips.stryktips.ui.desktop.odds.parameters;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.types.GameTextMessages;
import org.robert.tips.util.TextMessages;

/**
 * This is where the number of reduced rows are displayed.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class ReducedView extends JPanel implements ChangeListener, 
                                            StryktipsTextMessages,
                                            GameTextMessages
 {
    private StryktipsDocument stryktipsDocument;
    private JTextField numberInReducedSystem;
    
    public ReducedView( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException
    {
        this.stryktipsDocument = stryktipsDocument;
        setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();
        
        TextMessages textMessages = TextMessages.getInstance();
        
        // label
        add( new JLabel( textMessages.getText( NUMBER_IN_REDUCED_SYSTEM ) ), constraints );
        
        // text field.
        numberInReducedSystem = new JTextField( 5 );
        numberInReducedSystem.setEditable( false );
        add( numberInReducedSystem, constraints );
        
        stryktipsDocument.addChangeListener( this );        
    }
    
    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged( ChangeEvent event )
    {
        int value = stryktipsDocument.getStryktipsSystem().getReducedSystem().size();
        
        numberInReducedSystem.setText( String.valueOf( value ) );      
    }
 }