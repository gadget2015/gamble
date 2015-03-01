package org.robert.tips.stryktips.ui.desktop.extended.parameters;

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
 * Contains all components that is used to show the extended system data.
 * @author Robert Siwerz.
 */
public class ParametersDataContainer extends JPanel implements StryktipsTextMessages
{
    public ParametersDataContainer( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException
    {
        setLayout( new GridBagLayout() );
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder( etchedBorder );
        
        TextMessages textMessages = TextMessages.getInstance();
        titledBorder.setTitle( textMessages.getText( EXTENDED_SYSTEM_PARAMETERS ) );
        setBorder( titledBorder );
        
        GridBagConstraints constraints = new GridBagConstraints();
        
        // The input fields for min and max odds boundaries.
        ReducedView reducedView = new ReducedView( stryktipsDocument );
        constraints.gridx = 0;
        constraints.gridy = 0;
        add( reducedView, constraints );
        
        add( reducedView, constraints );               
    }
}