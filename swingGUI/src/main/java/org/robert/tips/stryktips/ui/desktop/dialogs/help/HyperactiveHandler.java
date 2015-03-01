package org.robert.tips.stryktips.ui.desktop.dialogs.help;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import javax.swing.text.html.HTMLDocument;
import javax.swing.JEditorPane;


/**
 * Handler class for HTML hyperlinks.
 * @author Robert Siwerz.
 */
class HyperactiveHandler implements HyperlinkListener 
{
    public void hyperlinkUpdate(HyperlinkEvent e) 
    {
 	    if ( e.getEventType() == HyperlinkEvent.EventType.ACTIVATED ) 
 	    {
 		    JEditorPane pane = ( JEditorPane ) e.getSource();
 		    
 		    if ( e instanceof HTMLFrameHyperlinkEvent ) 
 		    {
 		        HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
 		        HTMLDocument doc = (HTMLDocument)pane.getDocument();
 		        doc.processHTMLFrameHyperlinkEvent(evt);
 		    } 
 		    else 
 		    {
 		        try 
 		        {
 			        pane.setPage(e.getURL());
 		        } 
 		        catch (Throwable t) 
 		        {
 			        t.printStackTrace();
 		        }
 		    }
 	    }
 	}
}
