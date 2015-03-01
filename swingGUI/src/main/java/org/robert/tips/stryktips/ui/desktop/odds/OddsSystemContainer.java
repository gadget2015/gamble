package org.robert.tips.stryktips.ui.desktop.odds;

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
import org.robert.tips.stryktips.ui.desktop.odds.oddsinput.OddsInputContainer;
import org.robert.tips.stryktips.ui.desktop.odds.parameters.ParametersDataContainer;

/**
 * Contains all components that builds up the odds system.
 * @author Robert Siwerz.
 */
public class OddsSystemContainer extends JPanel implements StryktipsTextMessages
{
    /**
     * Creates an instance of the bankers tab.
     * @param stryktipsDocument The document, e.g the model.
     */
    public OddsSystemContainer( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException
    {
        setLayout( new GridBagLayout() );
        
        TextMessages textMessages = TextMessages.getInstance();
        
        GridBagConstraints constraints = new GridBagConstraints();
        
        // The input fields in the odds system
        OddsInputContainer oddsInputContainer = new OddsInputContainer( stryktipsDocument );
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;        
        add( oddsInputContainer, constraints );
        
        // The odds data paremeters
        ParametersDataContainer parametersDataContainer = new ParametersDataContainer( stryktipsDocument );
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;        
        add( parametersDataContainer, constraints );        
    }
}