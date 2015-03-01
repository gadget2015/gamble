package org.robert.tips.maltips.ui.dialogs;

import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Dimension;
import org.robert.tips.util.TextMessageNotFoundException;
import org.robert.tips.util.TextMessages;
import org.robert.tips.maltips.types.MaltipsTextMessages;
import org.robert.tips.maltips.MaltipsGameType;
import org.robert.tips.maltips.MaltipsDocument;
import org.robert.tips.maltips.types.MaltipsGame;
import org.robert.tips.exceptions.GeneralApplicationException;

/**
 * This is a dialog box that shows the reduced rows.
 * @author Robert Siwerz
 */
public class ViewReducedSystemDialog extends JDialog implements MaltipsTextMessages
{
    private JTextArea textArea;
    
    /**
     * Creates an instance of this dialog.
     * @param maltipsGameType The game type.
     */
    public ViewReducedSystemDialog( MaltipsGameType maltipsGameType ) throws GeneralApplicationException
    {
        super( maltipsGameType.getMainFrame(), true );
        
        textArea = new JTextArea();
        textArea.setEditable( false );
        MaltipsDocument maltipsDocument = ( MaltipsDocument ) maltipsGameType.getDocument();
        ArrayList rows = maltipsDocument.getMaltipsSystem().getReducedSystem();
        Iterator iterator = rows.iterator();
        
        while ( iterator.hasNext() )
        {
            MaltipsGame game = ( MaltipsGame ) iterator.next();
            textArea.append( game.toString() + "\n");
        }
        
        getContentPane().add( new JScrollPane( textArea ) );
        setSize( new Dimension( 150, 300 ) );
        
        TextMessages textMessages = TextMessages.getInstance();        
        setTitle( textMessages.getText( VIEW_REDUCEDSYSTEM_TITLE ) );
    }
}