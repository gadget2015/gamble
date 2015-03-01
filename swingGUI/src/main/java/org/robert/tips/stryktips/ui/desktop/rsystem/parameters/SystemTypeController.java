package org.robert.tips.stryktips.ui.desktop.rsystem.parameters;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import org.robert.tips.util.TextMessages;
import org.robert.tips.util.TextMessageNotFoundException;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.types.RSystemType;
import org.robert.tips.types.GambleApplicationConstants;
import java.util.Enumeration;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.PropertyResourceBundle;

/**
 * This is where the number of rights are defined from.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class SystemTypeController extends JPanel implements StryktipsTextMessages, 
                                                     StryktipsConstants,
                                                     ChangeListener,
                                                     GambleApplicationConstants
 {
    private StryktipsDocument stryktipsDocument;
    private JComboBox systemTypeComboBox;
    
    public SystemTypeController( StryktipsDocument stryktipsDocument ) throws GeneralApplicationException
    {
        this.stryktipsDocument = stryktipsDocument;
     
        setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();
        TextMessages textMessages = TextMessages.getInstance();
        
        // Heading
        constraints.gridx = 0;
        JLabel label = new JLabel( textMessages.getText( RSYSTEM_TYPE_LABEL ) );
        add( label, constraints );
        
        // system type combobox.
        PropertyResourceBundle rsystems = loadRSystems();
        Enumeration enumeration = rsystems.getKeys();
        
        systemTypeComboBox = new JComboBox();
        SystemTypeControllerHandler systemTypeControllerHandler = new SystemTypeControllerHandler( stryktipsDocument );
        
        while ( enumeration.hasMoreElements() )
        {
            String system = ( String ) enumeration.nextElement();
            systemTypeComboBox.addItem( system ); 
        }

        systemTypeComboBox.addActionListener( systemTypeControllerHandler );
        constraints.gridx = 2;
        add( systemTypeComboBox, constraints );
        
        stryktipsDocument.addChangeListener( this );
    }
    
    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged( ChangeEvent event )
    {
        // Update view with R-system system.
        RSystemType systemType = stryktipsDocument.getStryktipsSystem().getRsystem().getSystemType();
        
        // set selected system type
        int numberOfItems = systemTypeComboBox.getItemCount();
        
        for  ( int i = 0; i < numberOfItems; i++ )
        {
            String currentItem = ( String ) systemTypeComboBox.getItemAt( i );
            
            if ( currentItem != null && currentItem.equals( systemType.getSystemType() ) )
            {
                systemTypeComboBox.setSelectedIndex( i );
                break;
            }
        }
    }  
    
    /**
     * Load all R system from resource.
     */
    private PropertyResourceBundle loadRSystems() throws GeneralApplicationException
    {
        URL resourceURL = null;
        
        try
        {
            String resource = RESOURCES_PATH + RSYSTEM_RESOURCENAME;
            
            ClassLoader cl = this.getClass().getClassLoader(); 
            resourceURL = cl.getResource( resource ); 
            InputStream is = resourceURL.openStream();
            
            PropertyResourceBundle resourceBoundle = new PropertyResourceBundle( is );    
            
            return resourceBoundle;
        }
        catch ( IOException e )
        {
            throw new GeneralApplicationException( "Resource not found: " + resourceURL + "," + e.getMessage() );    
        }
    }
 }