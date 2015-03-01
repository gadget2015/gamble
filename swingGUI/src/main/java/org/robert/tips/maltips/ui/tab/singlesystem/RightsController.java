package org.robert.tips.maltips.ui.tab.singlesystem;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import org.robert.tips.util.TextMessages;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.maltips.types.MaltipsTextMessages;
import org.robert.tips.maltips.MaltipsDocument;

/**
 * This is where the number of rights are defined from.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class RightsController extends JPanel implements MaltipsTextMessages, 
                                                 MaltipsConstants,
                                                 ChangeListener
 {
    private MaltipsDocument maltipsDocument;
    private JComboBox minComboBox;
    private JComboBox maxComboBox;
    
    public RightsController( MaltipsDocument maltipsDocument ) throws GeneralApplicationException
    {
        this.maltipsDocument = maltipsDocument;
     
        setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();
        TextMessages textMessages = TextMessages.getInstance();
        
        // Heading
        constraints.gridwidth = 2;
        constraints.gridx = GridBagConstraints.REMAINDER;
        JLabel label = new JLabel( textMessages.getText( NUMBER_OF_RIGHTS_IN_SINGLEROW_SYSTEM ) );
        add( label, constraints );
        
        // minimum combobox.
        minComboBox = new JComboBox();
        maxComboBox = new JComboBox();
        MinRightsControllerHandler minRightsControllerHandler = new MinRightsControllerHandler( maltipsDocument );
        MaxRightsControllerHandler maxRightsControllerHandler = new MaxRightsControllerHandler( maltipsDocument );
        
        for ( int i = 0; i <= NUMBER_OF_GAMES; i++ )
        {
            minComboBox.addItem( String.valueOf( i ) );    
            maxComboBox.addItem( String.valueOf( i ) );    
        }
        
        minComboBox.addActionListener( minRightsControllerHandler );
        maxComboBox.addActionListener( maxRightsControllerHandler );        
        
        // min
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 1;
        add( new JLabel( textMessages.getText( SINGLE_SYSTEM_MIN_RIGHTS ) ), constraints );        
        constraints.gridx = 1;
        add( minComboBox, constraints );
        
        // max 
        constraints.gridx = 0;
        constraints.gridy = 2;
        add( new JLabel( textMessages.getText( SINGLE_SYSTEM_MAX_RIGHTS ) ), constraints );
        constraints.gridx = 1;
        add( maxComboBox, constraints );
        
        maltipsDocument.addChangeListener( this );
    }
    
    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged( ChangeEvent event )
    {
        // Update view with extended system games selected in the Single row system.
        int minRights = maltipsDocument.getMaltipsSystem().getSingleSystem().getMinInSingleRow();
        minComboBox.setSelectedIndex( minRights );
        
        int maxRights = maltipsDocument.getMaltipsSystem().getSingleSystem().getMaxInSingleRow();
        maxComboBox.setSelectedIndex( maxRights );
    }   
 }