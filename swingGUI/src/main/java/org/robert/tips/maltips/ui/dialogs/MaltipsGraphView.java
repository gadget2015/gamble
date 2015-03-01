package org.robert.tips.maltips.ui.dialogs;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JDialog;
import org.robert.tips.maltips.types.MaltipsConstants;

/**
 * This class creates a ordinary maltips graph. This paint a graph where the
 * x-axis is from 0 - NUMBER_GAMES_IN_MALTIPS and the y-axis shows an integer
 * value. The height is dynamic and takes the form of the highets number
 * in the graph data.
 * @author Robert Siwerz.
 */
public abstract class MaltipsGraphView extends Canvas implements MaltipsConstants
{
    /**
     * The witdh of this graph.
     */
    protected int width;
    
    /**
     * The hight of this graph.
     */
    protected int height;
    
    /**
     * Should be array of 9 integers, e.g 0 - 8 rights.
     */
    protected int[] data;
    
    /**
     * Title for this graph.
     */
    protected String title;  
    
    /**
     * The parent dialog.
     */
    protected JDialog parentDialog;
    
    /**
     * Paint this canvas.
     */
    public void paint(Graphics g)
    {
        // plot graph data
        int x = 30;
        int y = height - 30;
        
        // plot 0 - 4 rights
  
        for ( int i = 0; i < ( NUMBER_GAMES_IN_MALTIPS - 3 ); i++ )
        {
            g.setColor( Color.gray );
            g.fill3DRect( ( x + ( i * 30 ) ), ( y - data[ i ] ), 10, data[ i ] + 1, true ); 
            g.setColor( Color.black );
            g.drawString( String.valueOf( data[ i ] ), ( x + ( i * 30 ) ), ( y - data[ i ] - 10 ) );
        }
        
        // plot 5 - 8 rights       
        for ( int i = ( NUMBER_GAMES_IN_MALTIPS - 3 ); i <= NUMBER_GAMES_IN_MALTIPS; i++ )
        {
            g.setColor( Color.green );
            g.fill3DRect( ( x + ( i*30 ) ), ( y - data[ i ] ), 10, data[ i ] + 1, true ); 
            g.setColor( Color.black );
            g.drawString( String.valueOf( data[ i ] ), ( x + ( i * 30 ) ), ( y - data[ i ] - 10 ) );
        }
        
        // draw x-axis with labels
        g.setColor( Color.black );
        g.drawLine( 29, height - 29, width - 28, height - 29 );
       
        for ( int i = 0; i <= NUMBER_GAMES_IN_MALTIPS; i ++ )
        {
            g.drawString( String.valueOf( i ), 30 + i*30, height - 12 );    // label for x-axis
        }

        // y-axis with labels      
        g.drawLine( 29, height - 29, 29, height - getYaxis() - 39 );
       
        for ( int i = 0; i < getYaxis(); i += 10 )
        {
            g.drawString( String.valueOf( i ), 5, ( height - i -29 ));    // 20 i intervall
        }
        
        // draw title for graph
        g.setColor( Color.black );
        g.drawString( title, 50, 20 );
    }
    
    /**
     * Get number of items in the data array.
     * @return int The number of data.
     */
    private int getXaxis()
    {
        return data.length;
    }
    
    /**
     * Get the maximum number in the data array.
     * @return int The highest data in the data array.
     */
    private int getYaxis()
    {
        int highest = 0;
        for ( int i = 0; i < getXaxis(); i++ )
        {
            int current = data[ i ];
            
            highest = ( current > highest ) ? current: highest;
        }
               
        return highest;
    }        
    
    /**
     * Overloaded method from java.awt.Component.
     */
    public Dimension getPreferredSize()    
    {
        width = getXaxis()* 30 + 60;
        height = getYaxis() + 80;
        
        return new Dimension( width, height );
    }
}