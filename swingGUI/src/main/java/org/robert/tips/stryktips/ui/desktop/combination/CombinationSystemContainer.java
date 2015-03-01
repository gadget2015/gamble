package org.robert.tips.stryktips.ui.desktop.combination;

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
import org.robert.tips.stryktips.ui.desktop.combination.singlesystem.SingleSystemContainer;
import org.robert.tips.stryktips.ui.desktop.combination.parameters.ParametersDataContainer;

/**
 * Contains all components that builds up the combination system.
 * @author Robert Siwerz.
 */
public class CombinationSystemContainer extends JPanel implements StryktipsTextMessages
{
    /**
     * Creates an instance of the bankers tab.
     * @param stryktipsDocument The document, e.g the model.
     */
    public CombinationSystemContainer( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException
    {
        setLayout( new GridBagLayout() );     
        GridBagConstraints constraints = new GridBagConstraints();
        
        // single row system sf2FuEqJ
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        SingleSystemContainer singleSystemContainer = new SingleSystemContainer( stryktipsDocument );
        add( singleSystemContainer, constraints );
        
        // combination system data
        constraints.gridx = 1;
        ParametersDataContainer combinationSystemDataContainer = new ParametersDataContainer( stryktipsDocument );
        add( combinationSystemDataContainer, constraints );
    }
}