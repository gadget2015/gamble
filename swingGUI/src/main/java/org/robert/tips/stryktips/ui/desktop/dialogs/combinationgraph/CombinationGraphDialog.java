package org.robert.tips.stryktips.ui.desktop.dialogs.combinationgraph;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import org.robert.tips.MainFrame;
import org.robert.tips.stryktips.StryktipsGameType;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.SingleRowCombinationsCallbackImpl;
import org.robert.tips.stryktips.types.StryktipsTextMessages;
import org.robert.tips.util.TextMessages;
import org.robert.tips.stryktips.domain.model.reduce.SingleRowCombinationsReducer;
import org.robert.tips.stryktips.types.StryktipsConstants;

/**
 * This is a dialog box that shows the kombination graph. 
 * @author Robert Siwerz.
 */
public class CombinationGraphDialog extends JDialog implements StryktipsTextMessages {

    /**
     * Creates an instance of this dialog.
     * @param stryktipsGameType The game type.
     */
    public CombinationGraphDialog(StryktipsGameType stryktipsGameType) throws GeneralApplicationException {
        super(stryktipsGameType.getMainFrame(), true);
        final int WIDTH = 550;
        final int HEIGHT = 400;
        setSize(new Dimension(WIDTH, HEIGHT));

        TextMessages textMessages = TextMessages.getInstance();
        setTitle(textMessages.getText(COMBINATION_GRAPH_TITLE));

        // the odds graph canvas.
        CombinationGraphDocument graphModel = new CombinationGraphDocument();
        StryktipsDocument stryktipsDocument = (StryktipsDocument) stryktipsGameType.getDocument();
        SingleRowCombinationsCallbackImpl callback = new SingleRowCombinationsCallbackImpl(stryktipsDocument, stryktipsDocument.getStryktipsSystem(),
                (MainFrame) stryktipsGameType.getMainFrame(),
                StryktipsConstants.NUMBER_OF_GAMES);

        SingleRowCombinationsReducer combinationReducer = new SingleRowCombinationsReducer(stryktipsDocument.getStryktipsSystem(), callback);
        combinationReducer.getCombinationGraphData(graphModel);

        CombinationGraphView combinationGraphView = new CombinationGraphView(textMessages.getText(COMBINATION_GRAPH_LABEL), WIDTH - 10, HEIGHT - 40, graphModel, this);
        JScrollPane graphPane = new JScrollPane(combinationGraphView);

        getContentPane().add(graphPane);
    }
}
