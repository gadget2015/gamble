package org.robert.tips.stryktips.ui.desktop.rsystem.parameters;

import javax.swing.JPanel;
import javax.swing.JLabel;
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
 * Contains all components that is used to show the R-system data.
 * @author Robert Siwerz.
 */
public class ParametersDataContainer extends JPanel implements StryktipsTextMessages
{
    public ParametersDataContainer( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException
    {
        setLayout( new GridBagLayout() );
        
        GridBagConstraints constraints = new GridBagConstraints();
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder( etchedBorder );
        
        TextMessages textMessages = TextMessages.getInstance();
        titledBorder.setTitle( textMessages.getText( RSYSTEM_SYSTEM_DATA_TITLE ) );
        setBorder( titledBorder );        
 
       // The system type controller.
        SystemTypeController systemTypeController = new SystemTypeController( stryktipsDocument );
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        add( systemTypeController, constraints );
        
        // add a line separator
        constraints.gridy = 1;
        add( new JLabel( "-----------------------------------------------------------" ), constraints );
        
        // The view that show number of combinations.
        ParametersView parametersView = new ParametersView( stryktipsDocument );
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        add( parametersView, constraints );               
    }
}