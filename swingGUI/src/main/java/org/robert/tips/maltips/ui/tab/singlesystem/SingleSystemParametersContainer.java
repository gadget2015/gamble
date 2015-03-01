package org.robert.tips.maltips.ui.tab.singlesystem;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import org.robert.tips.util.TextMessages;
import org.robert.tips.util.TextMessageNotFoundException;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.maltips.types.MaltipsTextMessages;
import org.robert.tips.maltips.MaltipsDocument;
import org.robert.tips.maltips.ui.general.ReducedSystemView;

/**
 * Contains all components that builds up the single system parameters container
 * for the Maltips game type.
 * @author Robert Siwerz.
 */
class SingleSystemParametersContainer extends JPanel implements MaltipsTextMessages
{
    /**
     * Creates a new instance of the single system parameters container.
     * @param maltipsDocument Reference to the parent document.
     */
    public SingleSystemParametersContainer( MaltipsDocument maltipsDocument ) throws GeneralApplicationException
    {
        setLayout( new GridBagLayout() );
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder( etchedBorder );
        
        TextMessages textMessages = TextMessages.getInstance();
        titledBorder.setTitle( textMessages.getText( SINGLESYSTEM_PARAMETERS_TITLE ) );
        setBorder( titledBorder );
        
        GridBagConstraints constraints = new GridBagConstraints();

        // The input fields for number of rights in the singlerow system.
        RightsController rightsController = new RightsController( maltipsDocument );
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        add( rightsController, constraints );
        
        // add a line separator
        constraints.gridy = 1;
        add( new JLabel( "-----------------------------------------------------------" ), constraints );
                
        // The reduced rows view
        ReducedSystemView reducedSystemView = new ReducedSystemView( maltipsDocument );
        constraints.gridy = 2;
        constraints.gridx = 0;
        add( reducedSystemView, constraints );
    }
}