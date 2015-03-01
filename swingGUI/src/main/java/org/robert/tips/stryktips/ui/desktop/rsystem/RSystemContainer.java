package org.robert.tips.stryktips.ui.desktop.rsystem;

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
import org.robert.tips.stryktips.ui.desktop.rsystem.parameters.ParametersDataContainer;

/**
 * Contains all components that builds up the R system frame.
 * @author Robert Siwerz.
 */
public class RSystemContainer extends JPanel implements StryktipsTextMessages
{
    /**
     * Creates a new instance of the R system.
     * @param stryktipsDocument Reference to the parent document.
     */
    public RSystemContainer( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException
    {
        setLayout( new GridBagLayout() );
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder( etchedBorder );
        
        TextMessages textMessages = TextMessages.getInstance();
        titledBorder.setTitle( textMessages.getText( RSYSTEM_SYSTEM_TITLE ) );
        setBorder( titledBorder );
        
        GridBagConstraints constraints = new GridBagConstraints();
        
        // The button in the R system
        ButtonViewController buttonController = new ButtonViewController( stryktipsDocument );
        constraints.gridy = 0;
        constraints.gridx = 0;
        add( buttonController, constraints );
        
        // The R system parameters.
        ParametersDataContainer parametersDataContainer = new ParametersDataContainer( stryktipsDocument );
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        add( parametersDataContainer, constraints );                
    }
}