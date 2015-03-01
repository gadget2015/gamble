package org.robert.tips.stryktips.ui.desktop.dialogs;

import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Dimension;
import org.robert.tips.stryktips.StryktipsGameType;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsGame;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.util.TextMessageNotFoundException;
import org.robert.tips.util.TextMessages;

/**
 * This is a dialog box that shows the mathimatical unreduced single rows that
 * are created from the singlerow system and the extended system.
 */
public class MathimaticalDialog extends JDialog implements StryktipsTextMessages
{
    private JTextArea textArea;
    
    /**
     * Creates an instance of this dialog.
     * @param stryktipsGameType The game type.
     */
    public MathimaticalDialog( StryktipsGameType stryktipsGameType ) throws GeneralApplicationException
    {
        super( stryktipsGameType.getMainFrame(), true );
        
        textArea = new JTextArea();
        textArea.setEditable( false );
        StryktipsDocument stryktipsDocument = ( StryktipsDocument ) stryktipsGameType.getDocument();
        ArrayList rows = stryktipsDocument.getStryktipsSystem().getReducedSystem();
        Iterator iterator = rows.iterator();
        
        while ( iterator.hasNext() )
        {
            StryktipsGame game = ( StryktipsGame ) iterator.next();
            textArea.append( game.toString() + "\n");
        }
        
        getContentPane().add( new JScrollPane( textArea ) );
        setSize( new Dimension( 150, 300 ) );
        
        TextMessages textMessages = TextMessages.getInstance();        
        setTitle( textMessages.getText( VIEW_UNREDUCEDROWS_TITLE ) );
    }
}