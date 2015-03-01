package org.robert.tips.maltips;

import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.exceptions.GeneralApplicationException;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JMenuItem;

/**
 * This is an observer object for the Save menu item.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class SaveDocumentObserver implements ChangeListener
{
    private MaltipsDocument maltipsDocument;
    private JMenuItem menuItem;
    
    /**
     * creates an instance of this observer class.
     * @param maltipsDocument The document.
     * @param menuItem The menu item.
     */
    public SaveDocumentObserver( MaltipsDocument maltipsDocument, JMenuItem menuItem ) throws GeneralApplicationException
    {
        this.maltipsDocument = maltipsDocument;
        this.menuItem = menuItem;
        
        maltipsDocument.addChangeListener( this );   
    }
    
    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged( ChangeEvent event )
    { 
        boolean isDirty = maltipsDocument.getIsDocumentDirty();

        if ( isDirty )
        {
            menuItem.setEnabled( true );
        }
        else
        {
            menuItem.setEnabled( false );
        }
    }
 }