package org.robert.tips.stryktips.ui.desktop.mathimatical;

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
 * Contains all components that builds up the mathimatical system frame.
 * @author Robert Siwerz.
 */
public class MathimaticalSystemContainer extends JPanel implements StryktipsTextMessages
{
    /**
     * Creates a new instance of the mathimatical system.
     * @param stryktipsDocument Reference to the parent document.
     */
    public MathimaticalSystemContainer( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException
    {
        setLayout( new GridBagLayout() );
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder( etchedBorder );
        
        TextMessages textMessages = TextMessages.getInstance();
        titledBorder.setTitle( textMessages.getText( MATHIMATICAL_SYSTEM_TITLE ) );
        setBorder( titledBorder );
        
        GridBagConstraints constraints = new GridBagConstraints();
        
        // The button in the mathimatical system
        ButtonViewController buttonController = new ButtonViewController( stryktipsDocument );
        constraints.gridy = 0;
        constraints.gridx = 0;
        add( buttonController, constraints );
        
        // The mathimatical system parameters.
        MathimaticalView mathimaticalView = new MathimaticalView( stryktipsDocument );
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        add( mathimaticalView, constraints );        
    }
}