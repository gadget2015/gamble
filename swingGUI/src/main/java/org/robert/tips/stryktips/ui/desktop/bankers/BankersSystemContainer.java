package org.robert.tips.stryktips.ui.desktop.bankers;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.util.TextMessages;
import org.robert.tips.util.TextMessageNotFoundException;
import org.robert.tips.exceptions.GeneralApplicationException;

/**
 * Contains all components that builds up the bankers system.
 * @author Robert Siwerz.
 */
public class BankersSystemContainer extends JPanel implements StryktipsTextMessages
{
    /**
     * Creates an instance of the bankers tab.
     * @param stryktipsDocument The document, e.g the model.
     */
    public BankersSystemContainer( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException
    {
        setLayout( new GridBagLayout() );
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder( etchedBorder );
        
        TextMessages textMessages = TextMessages.getInstance();
        titledBorder.setTitle( textMessages.getText( BANKERS_SYSTEM_TITLE ) );
        setBorder( titledBorder );
        
        GridBagConstraints constraints = new GridBagConstraints();
        
        // The button in the single system
        ButtonController buttonController = new ButtonController( stryktipsDocument );
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;        
        add( buttonController, constraints );
        
        // Bankers view.
        BankersView bankersView = new BankersView( stryktipsDocument );
        constraints.gridx = 1;
        constraints.gridy = 0;
        add( bankersView, constraints );
        
    }
}