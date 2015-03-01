package org.robert.tips;

import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import org.robert.tips.exceptions.GeneralApplicationException;

/**
 *Defines interface for a type of game.
 */
public interface GameIF
{
    /**
     * Get the menu system that are valid for this Game type.
     * @return JMenuBar This it the menu system that are valid for this type of game.
     */
    public JMenuBar getMenuSystem() throws GeneralApplicationException;
    
    /**
     * Get content for this type of game. This is the data shown in the main frame dialog.
     * @return JPanel The content that are shown in the main frame.
     */
    public JPanel getFrameContent() throws GeneralApplicationException;
    
    /**
     * Get the main frame for this game type.
     */
    public JFrame getMainFrame();
    
    /**
     * Get the document for this type of game.
     * @return Object The document.
     */
    public Object getDocument();
}