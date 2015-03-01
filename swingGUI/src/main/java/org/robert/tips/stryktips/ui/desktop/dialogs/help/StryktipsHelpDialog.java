package org.robert.tips.stryktips.ui.desktop.dialogs.help;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.net.URL;
import java.util.Locale;
import java.io.IOException;
import org.robert.tips.stryktips.StryktipsGameType;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsGame;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.util.TextMessageNotFoundException;
import org.robert.tips.util.TextMessages;
import org.robert.tips.types.GambleApplicationConstants;

/**
 * This is a dialog box that shows the all odds graph. This graph show
 * all odds that exists for the odds system.
 * @author Robert Siwerz.
 */
public class StryktipsHelpDialog extends JDialog implements StryktipsTextMessages,
                                                            GambleApplicationConstants
{  
    private static final String INDEX_HTML = "index";
    
    /**
     * Creates an instance of this dialog.
     * @param stryktipsGameType The game type.
     */
    public StryktipsHelpDialog( StryktipsGameType stryktipsGameType ) throws GeneralApplicationException
    {
        super( stryktipsGameType.getMainFrame(), true );
        setSize( new Dimension( 700, 450 ) );
        
        TextMessages textMessages = TextMessages.getInstance();        
        setTitle( textMessages.getText( STRYKTIPS_HELP_DIALOG_TITLE ) );
        
        // the .html pane
        URL resourceURL = null;
        
        try
        {
            Locale locale = Locale.getDefault();
            String language = locale.getLanguage();
            String country = locale.getCountry();
            String resource = DOCUMENTATION + STRYKTIPS_DOCUMENTATION + 
                              INDEX_HTML + 
                              "_" + language + "_" + country + 
                              HTML_SUFIX;
            
            ClassLoader cl = this.getClass().getClassLoader(); 
            resourceURL = cl.getResource( resource ); 
            
            JEditorPane htmlContent = new JEditorPane( resourceURL );
            htmlContent.setEditable( false );
            htmlContent.addHyperlinkListener( new HyperactiveHandler() );
            
            JScrollPane scrollPane = new JScrollPane( htmlContent );
            
            getContentPane().add( scrollPane );
        }
        catch ( IOException e )
        {
            throw new GeneralApplicationException( "Resource not found: " + resourceURL + "," + e.getMessage() );    
        }
  
    }
}