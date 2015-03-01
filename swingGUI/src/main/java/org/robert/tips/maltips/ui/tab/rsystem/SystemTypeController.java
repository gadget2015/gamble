package org.robert.tips.maltips.ui.tab.rsystem;

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
import org.robert.tips.types.GambleApplicationConstants;
import org.robert.tips.maltips.MaltipsDocument;
import org.robert.tips.maltips.types.MaltipsTextMessages;
import org.robert.tips.maltips.types.RSystemType;
import org.robert.tips.maltips.types.RSystem;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;

/**
 * This is where the r-system type are defined.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
class SystemTypeController extends JPanel implements ChangeListener,
                                                     GambleApplicationConstants,
                                                     MaltipsTextMessages
 {
    private MaltipsDocument maltipsDocument;
    private JComboBox systemTypeComboBox;
    
    public SystemTypeController( MaltipsDocument maltipsDocument ) throws GeneralApplicationException
    {
        this.maltipsDocument = maltipsDocument;
     
        setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();
        TextMessages textMessages = TextMessages.getInstance();
        
        // Heading
        constraints.gridx = 0;
        JLabel label = new JLabel( textMessages.getText( RSYSTEM_TYPE_LABEL ) );
        add( label, constraints );
        
        // system type combobox.
        RSystem rSystem = new RSystem();
        PropertyResourceBundle rsystems = rSystem.loadRSystems();
        Enumeration enumeration = rsystems.getKeys();
        
        systemTypeComboBox = new JComboBox();
        SystemTypeControllerHandler systemTypeControllerHandler = new SystemTypeControllerHandler( maltipsDocument );
        
        // sort the r-systems
        TreeMap treeMap = new TreeMap();

        while ( enumeration.hasMoreElements() )
        {
            String system = ( String ) enumeration.nextElement();
            treeMap.put( system, system );             
        }
        
        Set systemTypes = treeMap.keySet();
        Iterator iterator = systemTypes.iterator();
        
        // add system types
        while ( iterator.hasNext() )
        {
            String system = ( String ) iterator.next();
            systemTypeComboBox.addItem( system ); 
        }

        systemTypeComboBox.addActionListener( systemTypeControllerHandler );
        constraints.gridx = 2;
        add( systemTypeComboBox, constraints );
        
        maltipsDocument.addChangeListener( this );
    }
    
    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged( ChangeEvent event )
    {
        // Update view with R-system system.
        RSystemType systemType = maltipsDocument.getMaltipsSystem().getRsystem().getRSystemType();
        
        // set selected system type
        int numberOfItems = systemTypeComboBox.getItemCount();
        
        for  ( int i = 0; i < numberOfItems; i++ )
        {
            String currentItem = ( String ) systemTypeComboBox.getItemAt( i );
            
            if ( currentItem != null && systemType != null && currentItem.equals( systemType.getSystemType() ) )
            {
                systemTypeComboBox.setSelectedIndex( i );
                break;
            }
        }
    }  
 }