package org.robert.tips.stryktips.ui.desktop.dialogs;

import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import org.robert.tips.stryktips.StryktipsGameType;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsGame;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.util.TextMessageNotFoundException;
import org.robert.tips.util.TextMessages;
import org.robert.tips.stryktips.domain.model.reduce.OddsReducer;

/**
 * This is a dialog box that shows the reduced systems odds graph. 
 * @author Robert Siwerz.
 */
public class ReducedOddsGraphDialog extends JDialog implements StryktipsTextMessages {

    /**
     * Creates an instance of this dialog.
     * @param stryktipsGameType The game type.
     */
    public ReducedOddsGraphDialog(StryktipsGameType stryktipsGameType) throws GeneralApplicationException {
        super(stryktipsGameType.getMainFrame(), true);
        setSize(new Dimension(550, 400));

        TextMessages textMessages = TextMessages.getInstance();
        setTitle(textMessages.getText(REDUCED_ODDS_GRAPH_DIALOG_TITLE));

        // the odds graph canvas.        
        OddsReducer oddsReducer = new OddsReducer(((StryktipsDocument) stryktipsGameType.getDocument()).getStryktipsSystem());
        int[] data = oddsReducer.getReducedOddsGraphData();

        OddsGraph oddsGraph = new OddsGraph(data, textMessages.getText(REDUCED_ODDS_GRAPH_TITLE));
        JScrollPane graphPane = new JScrollPane(oddsGraph);

        getContentPane().add(graphPane);
    }
}