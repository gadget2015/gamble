package org.robert.tips.stryktips.ui.desktop.odds.oddsinput;

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
 * Contains all components that builds up the odds input fields.
 * @author Robert Siwerz.
 */
public class OddsInputContainer extends JPanel implements StryktipsTextMessages
{
    public OddsInputContainer( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException
    {
        setLayout( new GridBagLayout() );
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder( etchedBorder );
        
        TextMessages textMessages = TextMessages.getInstance();
        titledBorder.setTitle( textMessages.getText( ODDS_INPUT_SYSTEM_TITLE ) );
        setBorder( titledBorder );
        
        GridBagConstraints constraints = new GridBagConstraints();
        
        // The button in the single system
        OddsInputController oddsInputController = new OddsInputController( stryktipsDocument );
        constraints.gridy = 0;
        constraints.gridx = 0;
        add( oddsInputController, constraints );
    }
}