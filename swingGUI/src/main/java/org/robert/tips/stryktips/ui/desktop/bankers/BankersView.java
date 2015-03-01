package org.robert.tips.stryktips.ui.desktop.bankers;

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
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * This is where the number of reduced rows are displayed.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class BankersView extends JPanel implements ChangeListener, 
                                            StryktipsTextMessages,
                                            GameTextMessages
 {
    private StryktipsDocument stryktipsDocument;
    private JTextField numberInReducedSystem;
    
    public BankersView( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException
    {
        this.stryktipsDocument = stryktipsDocument;
        setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder( etchedBorder );
        
        TextMessages textMessages = TextMessages.getInstance();
        titledBorder.setTitle( textMessages.getText( BANKERS_VIEW_TITLE ) );
        setBorder( titledBorder );
        
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