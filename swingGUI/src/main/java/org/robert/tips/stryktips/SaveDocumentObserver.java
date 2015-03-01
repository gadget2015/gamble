package org.robert.tips.stryktips;

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
    private StryktipsDocument stryktipsDocument;
    private JMenuItem menuItem;
    
    /**
     * creates an instance of this observer class.
     * @param stryktipsDocument The document.
     * @param menuItem The menu item.
     */
    public SaveDocumentObserver( StryktipsDocument stryktipsDocument, JMenuItem menuItem ) throws GeneralApplicationException
    {
        this.stryktipsDocument = stryktipsDocument;
        this.menuItem = menuItem;
        
        stryktipsDocument.addChangeListener( this );   
    }
    
    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged( ChangeEvent event )
    { 
        boolean isDirty = stryktipsDocument.getIsDocumentDirty();

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