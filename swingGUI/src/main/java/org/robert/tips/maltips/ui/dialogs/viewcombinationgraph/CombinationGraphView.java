package org.robert.tips.maltips.ui.dialogs.viewcombinationgraph;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JDialog;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.maltips.ui.dialogs.MaltipsGraphView;

/**
 * This class a combination graph with data in the MVC document. The dimension
 * for this graph is fixed and number of rows are also fixed.
 * @author Robert Siwerz.
 */
class CombinationGraphView extends MaltipsGraphView implements ChangeListener
{       
    /**
     * The document.
     */
    private CombinationGraphDocument combinationGraphDocument;
    
    /**
     * Creates a new instance of this graph.
     * @param data Data to display.
     */
    public CombinationGraphView( String title,
                                 int width,
                                 int height,
                                 CombinationGraphDocument combinationGraphDocument,
                                 JDialog parentDialog
                               )
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
        data = combinationGraphDocument.getCombinationData();
        
        invalidate();
        parentDialog.validate();
        parentDialog.doLayout();
        repaint();
    }    
}
