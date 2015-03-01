package org.robert.tips.stryktips.ui.desktop.dialogs;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;

/**
 * This class creates a graph with the given data. The dimension
 * of the graph is calculated from the data. This implies that if
 * the given data has 500 positions, e.g the array size is of 500.
 * Then the x-axis goes from 0 to 500. The high of the y-axis is 
 * calculated from the highets integer in the given data.
 * @author Robert Siwerz.
 */
class OddsGraph extends Canvas
{    
    /**
     * Should be array of 500 integers.
     */
    private int[]data;
    private String title;
    private int width;
    private int height;
    
    /**
     * Creates a new instance of this graph.
     * @param data Data to display.
     */
    public OddsGraph( int[] data, String title )
    {
       this.data = data; 
       width = getXaxis() + 60;     // extra space on left and right of graph
       height = getYaxis() + 60;    // extra space above an beneift of graph.
       this.title = title;
       setSize( width, height );       
    }
    /**
     * Paint this canvas.
     */
    public void paint(Graphics g)
    {
        // plot graph data
        int x = 30;
        int y = height - 30;
        g.setColor( Color.gray );
        
        for ( int i = 0; i < getXaxis(); i++ )
        {
            g.drawLine( ( x + i ), y, ( x + i ), ( y - data[ i ] ) ); 
        }
        
        // draw x-axis with labels
        g.setColor( Color.black );
        g.drawLine( 29, height - 29, width - 28, height - 29 );
       
        for ( int i = 0; i < getXaxis(); i += 50 )
        {
            g.drawString( String.valueOf( i ), 30 + i, height - 12 );    // 50 i intervall
        }

        // y-axis with labels
        g.drawLine( 29, height - 29, 29 , 25 );
        
        g.drawLine( 29, height - 29, width - 28, height - 29 );
       
        for ( int i = 0; i < getYaxis(); i += 50 )
        {
            g.drawString( String.valueOf( i ), 5, ( height - i -29 ));    // 50 i intervall
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
}
