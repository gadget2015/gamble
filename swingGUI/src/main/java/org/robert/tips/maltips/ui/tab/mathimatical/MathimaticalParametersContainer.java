package org.robert.tips.maltips.ui.tab.mathimatical;

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
 * Contains all components that builds up the mathimatical parameters container
 * for the Maltips game type.
 * @author Robert Siwerz.
 */
class MathimaticalParametersContainer extends JPanel implements MaltipsTextMessages
{
    /**
     * Creates a new instance of the mathimatical parameters container.
     * @param maltipsDocument Reference to the parent document.
     */
    public MathimaticalParametersContainer( MaltipsDocument maltipsDocument ) throws GeneralApplicationException
    {
        setLayout( new GridBagLayout() );
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder( etchedBorder );
        
        TextMessages textMessages = TextMessages.getInstance();
        titledBorder.setTitle( textMessages.getText( MATHIMATICAL_PARAMETERS_TITLE ) );
        setBorder( titledBorder );
        
        GridBagConstraints constraints = new GridBagConstraints();
        
        // The reduced rows view
        ReducedSystemView reducedSystemView = new ReducedSystemView( maltipsDocument );
        constraints.gridy = 0;
        constraints.gridx = 0;
        add( reducedSystemView, constraints );
    }
}