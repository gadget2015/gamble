package org.robert.tips.maltips;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import org.robert.tips.types.MenuChoices;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.GameIF;
import org.robert.tips.util.TextMessages;
import org.robert.tips.maltips.types.MaltipsTextMessages;
import org.robert.tips.maltips.ui.tab.mathimatical.MathimaticalSystemContainer;
import org.robert.tips.maltips.types.MaltipsMenuChoices;
import org.robert.tips.maltips.ui.tab.singlesystem.SingleSystemContainer;
import org.robert.tips.maltips.ui.tab.rsystem.RSystemContainer;
import org.robert.tips.maltips.ui.tab.bankers.BankersSystemContainer;

/**
 * This is the content for the Maltips game type.
 * @author Robert Siwerz.
 */
public class MaltipsGameType implements GameIF, 
                                        MenuChoices,
                                        MaltipsMenuChoices,
                                        MaltipsTextMessages
{
    private JFrame mainFrame;                   // reference to the main fram, top-level frame.
    private MaltipsDocument currentDocument;  // The current active/open document.
    
    /**
     * Creates a new Maltips game type with a new empty document.
     * @param mainFrame The mainframe.
     */
    public MaltipsGameType( JFrame mainFrame )
    {
        this.mainFrame = mainFrame;
        currentDocument = new MaltipsDocument();
    }

    /**
     * Creates a new Maltips game type with the given document.
     * @param mainFrame The mainframe.
     * @param document The maltips document
     */
    public MaltipsGameType( JFrame mainFrame, MaltipsDocument maltipsDocument )
    {
        this.mainFrame = mainFrame;
        currentDocument = maltipsDocument;
    }
   
    /**
     * Implemented method from GameIF.
     */
    public JMenuBar getMenuSystem() throws GeneralApplicationException
    {
        JMenuBar menuBar = new JMenuBar();  // contains all menus
        TextMessages textMessages = TextMessages.getInstance();
        MaltipsMenuHandler menuHandler = new MaltipsMenuHandler( this );
        
        // Put the menu and menu items in the menubar
        // File menu
        JMenu menuFile = new JMenu();
        menuFile.setText( textMessages.getText( FILE  ) );
        
        // Close document
        JMenuItem menuFileClose = new JMenuItem();
        menuFileClose.setText( textMessages.getText( FILE_CLOSE_DOCUMENT ) );
        menuFileClose.setActionCommand( FILE_CLOSE_DOCUMENT );
        menuFileClose.addActionListener( menuHandler );
        menuFile.add( menuFileClose );
        
        // save document
        JMenuItem menuFileSave = new JMenuItem();
        menuFileSave.setText( textMessages.getText( FILE_SAVE ) );
        menuFileSave.setActionCommand( FILE_SAVE );
        menuFileSave.addActionListener( menuHandler );
        menuFile.add( menuFileSave );
        SaveDocumentObserver saveDocumentObserver = new SaveDocumentObserver( currentDocument, menuFileSave );        
        
        menuFile.addSeparator();
                
        JMenuItem menuFileExit = new JMenuItem();      	
        menuFileExit.setText( textMessages.getText( FILE_EXIT ) );
        menuFileExit.setActionCommand( FILE_EXIT );
        menuFileExit.addActionListener( menuHandler );
        menuFile.add( menuFileExit );
 
        // Mathimatical menu
        JMenu menuMathimatical = new JMenu();
        menuMathimatical.setText( textMessages.getText( MENU_MATHIMATICAL  ) );       
        
        // create mathimatical system
        JMenuItem menuMathimaticalCreate = new JMenuItem();      	
        menuMathimaticalCreate.setText( textMessages.getText( MENU_MATHIMATICAL_CREATE ) );
        menuMathimaticalCreate.setActionCommand( MENU_MATHIMATICAL_CREATE );
        menuMathimaticalCreate.addActionListener( menuHandler );
        menuMathimatical.add( menuMathimaticalCreate );        
        
        
        // Rsystem menu
        JMenu menuRSystem = new JMenu();
        menuRSystem.setText( textMessages.getText( MENU_RSYSTEM  ) );       
        
        // create r-system
        JMenuItem menuRSystemCreate = new JMenuItem();      	
        menuRSystemCreate.setText( textMessages.getText( MENU_RSYSTEM_CREATE ) );
        menuRSystemCreate.setActionCommand( MENU_RSYSTEM_CREATE );
        menuRSystemCreate.addActionListener( menuHandler );
        menuRSystem.add( menuRSystemCreate );          
        
        // Single system menu
        JMenu menuSingleSystem = new JMenu();
        menuSingleSystem.setText( textMessages.getText( MENU_SINGLESYSTEM  ) );       
        
        // create combination system from single system
        JMenuItem menuSingleSystemCreate = new JMenuItem();      	
        menuSingleSystemCreate.setText( textMessages.getText( MENU_SINGLESYSTEM_CREATE ) );
        menuSingleSystemCreate.setActionCommand( MENU_SINGLESYSTEM_CREATE );
        menuSingleSystemCreate.addActionListener( menuHandler );
        menuSingleSystem.add( menuSingleSystemCreate ); 
        
        // create a graph of all possible combinations.
        JMenuItem menuSingleSystemCombinationGraph = new JMenuItem();      	
        menuSingleSystemCombinationGraph.setText( textMessages.getText( MENU_SINGLESYSTEM_COMBINATION_GRAPH ) );
        menuSingleSystemCombinationGraph.setActionCommand( MENU_SINGLESYSTEM_COMBINATION_GRAPH );
        menuSingleSystemCombinationGraph.addActionListener( menuHandler );
        menuSingleSystem.add( menuSingleSystemCombinationGraph );         
        
        // Bankers system menu
        JMenu menuBankersSystem = new JMenu();
        menuBankersSystem.setText( textMessages.getText( MENU_BANKERSSYSTEM  ) );       
        
        // Reduce with the bankers system
        JMenuItem menuBankersSystemReduce = new JMenuItem();      	
        menuBankersSystemReduce.setText( textMessages.getText( MENU_BANKERSSYSTEM_REDUCE ) );
        menuBankersSystemReduce.setActionCommand( MENU_BANKERSSYSTEM_REDUCE );
        menuBankersSystemReduce.addActionListener( menuHandler );
        menuBankersSystem.add( menuBankersSystemReduce ); 
                
        // Reduce menu
        JMenu menuReduce = new JMenu();
        menuReduce.setText( textMessages.getText( MENU_REDUCE  ) );       
        
        // show reduced system
        JMenuItem menuReduceView = new JMenuItem();      	
        menuReduceView.setText( textMessages.getText( MENU_REDUCE_VIEW ) );
        menuReduceView.setActionCommand( MENU_REDUCE_VIEW );
        menuReduceView.addActionListener( menuHandler );
        menuReduce.add( menuReduceView );    

        // export reduced rows to svenskaspel compliant format.
        JMenuItem menuExportReducedSystem = new JMenuItem();
        menuExportReducedSystem.setText( textMessages.getText( MENU_REDUCED_EXPORTSYSTEM) );
        menuExportReducedSystem.setActionCommand( MENU_REDUCED_EXPORTSYSTEM );
        menuExportReducedSystem.addActionListener( menuHandler );
        menuReduce.add( menuExportReducedSystem ); 
        
        // check the reduced system.
        JMenuItem menuReduceCheckSystem = new JMenuItem();      	
        menuReduceCheckSystem.setText( textMessages.getText( MENU_REDUCE_CHECKSYSTEM ) );
        menuReduceCheckSystem.setActionCommand( MENU_REDUCE_CHECKSYSTEM );
        menuReduceCheckSystem.addActionListener( menuHandler );
        menuReduce.add( menuReduceCheckSystem );          
        
        // Help menu      
        JMenu menuHelp = new JMenu();
        menuHelp.setText( textMessages.getText( HELP ) );        
        
        // about
        JMenuItem menuHelpAbout = new JMenuItem();
        menuHelpAbout.setText( textMessages.getText( HELP_ABOUT ) );
        menuHelpAbout.setActionCommand( HELP_ABOUT );
        menuHelpAbout.addActionListener( menuHandler );
        menuHelp.add( menuHelpAbout );
        
        // Add menu's and menubar
        menuBar.add( menuFile );
        menuBar.add( menuMathimatical );
        menuBar.add( menuRSystem );
        menuBar.add( menuSingleSystem );
        menuBar.add( menuBankersSystem );
        menuBar.add( menuReduce );
	    menuBar.add( menuHelp );
    	
	    return menuBar;
    }
    
    /**
     * implemented method from GameIF.
     */
    public JPanel getFrameContent() throws GeneralApplicationException
    {        
        JTabbedPane tabbedPane = new JTabbedPane();
        GridBagConstraints constraints = new GridBagConstraints();
        
        TextMessages textMessages = TextMessages.getInstance();     
        
        // add M-system tab
        MathimaticalSystemContainer mathimaticalSystemContainer = new MathimaticalSystemContainer( currentDocument );
        tabbedPane.add( textMessages.getText( TAB_MATHIMATICAL ), mathimaticalSystemContainer );
        
        // add R-system tab
        RSystemContainer rSystemContainer = new RSystemContainer( currentDocument );
        tabbedPane.add( textMessages.getText( TAB_RSYSTEM ), rSystemContainer );
         
        // add single system tab
        SingleSystemContainer singleSystemContainer = new SingleSystemContainer( currentDocument );
        tabbedPane.add( textMessages.getText( TAB_SINGLESYSTEM ), singleSystemContainer );
         
        // add banker system tab
        BankersSystemContainer bankersSystemContainer = new BankersSystemContainer( currentDocument );
        tabbedPane.add( textMessages.getText( TAB_BANKERSSYSTEM ), bankersSystemContainer );
        
        // fix up the return content
        JPanel content = new JPanel();
        content.add( tabbedPane );
        
        return content;
    }
    
    /**
     * Implemented method from GameIF.
     */
    public JFrame getMainFrame()
    {
        return mainFrame;
    }
    
    /**
     * Implemented method from GameUF.
     */
    public Object getDocument()
    {
        return currentDocument;
    }
}