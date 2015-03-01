package org.robert.tips.maltips.ui.dialogs.checkreducesystem;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JDialog;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.maltips.ui.dialogs.MaltipsGraphView;

/**
 * This class creates a check graph with the given data. The dimension
 * for this graph is fixed and number of rows are also fixed.
 * @author Robert Siwerz.
 */
class CheckGraphView extends MaltipsGraphView implements ChangeListener
{       
    /**
     * The document.
     */
    private CheckReducedSystemDocument checkReducedSystemDocument;
    
    /**
     * Creates a new instance of this graph.
     * @param data Data to display.
     */
    public CheckGraphView(  String title,
                            int width,
                            int height,
                            CheckReducedSystemDocument checkReducedSystemDocument,
                            JDialog parentDialog
                          )
    {
       super.title = title;
       super.width = width;
       super.height = height;
       super.parentDialog = parentDialog;
       this.checkReducedSystemDocument = checkReducedSystemDocument;
       
       checkReducedSystemDocument.addChangeListener( this );   
    }
 
    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged( ChangeEvent event )
    {
        data = checkReducedSystemDocument.getNumberOfRights();
        
        invalidate();
        parentDialog.validate();
        parentDialog.doLayout();
        repaint();
    }    
}
