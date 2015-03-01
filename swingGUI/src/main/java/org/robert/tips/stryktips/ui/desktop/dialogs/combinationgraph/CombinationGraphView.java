package org.robert.tips.stryktips.ui.desktop.dialogs.combinationgraph;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import org.robert.tips.stryktips.ui.desktop.dialogs.GraphView;

/**
 * This class creates a combination graph with the given data. The dimension
 * for this graph is fixed and number of rows is also fixed.
 * @author Robert Siwerz.
 */
class CombinationGraphView extends GraphView implements ChangeListener
{    
    /**
     * The document.
     */
    private CombinationGraphDocument combinationGraphDocument;
   
    /**
     * Creates a new instance of this graph.
     * @param combinationGraphDocument The model.
     * @param title The title to show.
     */
    public CombinationGraphView( String title,
                                 int width,
                                 int height,
                                 CombinationGraphDocument combinationGraphDocument,
                                 CombinationGraphDialog parentDialog )
    {
       super.title = title;
       super.width = width;
       super.height = height;
       super.parentDialog = parentDialog;
       this.combinationGraphDocument = combinationGraphDocument;
       
       combinationGraphDocument.addChangeListener( this );      
    }
    
    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged( ChangeEvent event )
    {
        data = combinationGraphDocument.getCombinationGraph();
        
        invalidate();
        parentDialog.validate();
        parentDialog.doLayout();
        repaint();
    }    
}
