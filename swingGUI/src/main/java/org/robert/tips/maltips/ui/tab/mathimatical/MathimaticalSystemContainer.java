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

/**
 * Contains all components that builds up the mathimatical system frame
 * for the Maltips game type.
 * @author Robert Siwerz.
 */
public class MathimaticalSystemContainer extends JPanel implements MaltipsTextMessages
{
    /**
     * Creates a new instance of the mathimatical system.
     * @param maltipsDocument Reference to the parent document.
     */
    public MathimaticalSystemContainer( MaltipsDocument maltipsDocument ) throws GeneralApplicationException
    {
        setLayout( new GridBagLayout() );
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder( etchedBorder );
        
        TextMessages textMessages = TextMessages.getInstance();
        titledBorder.setTitle( textMessages.getText( MATHIMATICAL_SYSTEM_TITLE ) );
        setBorder( titledBorder );
        
        GridBagConstraints constraints = new GridBagConstraints();
        
        // The button in the mathimatical system
        ButtonViewController buttonController = new ButtonViewController( maltipsDocument );
        constraints.gridy = 0;
        constraints.gridx = 0;
        add( buttonController, constraints );
        
        // The mathimatical system parameters.
        MathimaticalParametersContainer mathimaticalParametersContainer = new MathimaticalParametersContainer( maltipsDocument );
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        add( mathimaticalParametersContainer, constraints );        
    }
}