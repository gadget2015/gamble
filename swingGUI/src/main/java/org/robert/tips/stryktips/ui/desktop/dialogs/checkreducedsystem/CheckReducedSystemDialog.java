package org.robert.tips.stryktips.ui.desktop.dialogs.checkreducedsystem;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import org.robert.tips.stryktips.StryktipsGameType;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.util.TextMessages;
import org.robert.tips.stryktips.types.StryktipsConstants;

/**
 * This is a dialog that is used to check the number of rights in the
 * reduced system.
 * @author Robert Siwerz.
 */
public class CheckReducedSystemDialog extends JDialog implements StryktipsTextMessages,
                                                                 StryktipsConstants
{  
    /**
     * Creates an instance of this dialog.
     * @param stryktipsGameType The game type.
     */
    public CheckReducedSystemDialog( StryktipsGameType stryktipsGameType ) throws GeneralApplicationException
    {
        super( stryktipsGameType.getMainFrame(), false );
        setSize( new Dimension( 700, 450 ) );
        getContentPane().setLayout( new FlowLayout() );
        TextMessages textMessages = TextMessages.getInstance();        
        setTitle( textMessages.getText( CHECK_REDUCEDSYSTEM_DIALOG_TITLE ) );
        
        JPanel content = new JPanel();
        content.setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();
        
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder( etchedBorder );
        titledBorder.setTitle( textMessages.getText( RIGHT_ROW_TITLE ) );
        content.setBorder( titledBorder );
        
        CheckReducedSystemDocument checkReducedSystemDocument = new CheckReducedSystemDocument( stryktipsGameType );
        
        // add botton to chech the right row.
        RightRowController rightRowController = new RightRowController( checkReducedSystemDocument );
        constraints.gridx = 0;
        constraints.gridy = 0;
        content.add( rightRowController, constraints );
        
        // add check graph
        CheckGraphView checkGraphView = new CheckGraphView( textMessages.getText( RIGHTS_REDUCEDSYSTEM_GRAPH ), 470, 200, checkReducedSystemDocument, this );
        constraints.gridx = 1;
        constraints.gridy = 0;
        content.add( checkGraphView, constraints );        
        
        getContentPane().add( content );
    }
}