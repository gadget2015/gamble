package org.robert.tips.maltips.ui.tab.bankers;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import org.robert.tips.util.TextMessages;
import org.robert.tips.util.TextMessageNotFoundException;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.maltips.types.MaltipsTextMessages;
import org.robert.tips.maltips.MaltipsDocument;
import org.robert.tips.maltips.ui.general.ReducedSystemView;

/**
 * Contains all components that builds up the bankers system.
 * @author Robert Siwerz.
 */
public class BankersSystemContainer extends JPanel implements MaltipsTextMessages
{
    /**
     * Creates an instance of the bankers tab.
     * @param maltipsDocument The document, e.g the model.
     */
    public BankersSystemContainer( MaltipsDocument maltipsDocument ) throws GeneralApplicationException
    {
        setLayout( new GridBagLayout() );
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder( etchedBorder );
        
        TextMessages textMessages = TextMessages.getInstance();
        titledBorder.setTitle( textMessages.getText( BANKERS_SYSTEM_TITLE ) );
        setBorder( titledBorder );
        
        GridBagConstraints constraints = new GridBagConstraints();
        
        // The button in the single system
        ButtonViewController buttonViewController = new ButtonViewController( maltipsDocument );
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;        
        add( buttonViewController, constraints );
        
        // bankers parameters.
        BankersParametersContainer bankersParametersContainer = new BankersParametersContainer( maltipsDocument );
        constraints.gridx = 1;
        constraints.gridy = 0;
        add( bankersParametersContainer, constraints );        
    }
}