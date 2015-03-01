package org.robert.tips.maltips.ui.dialogs.viewcombinationgraph;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import org.robert.tips.MainFrame;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.util.TextMessages;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.maltips.MaltipsDocument;
import org.robert.tips.maltips.types.MaltipsTextMessages;
import org.robert.tips.maltips.MaltipsGameType;
import org.robert.tips.maltips.SingleSystemCombinationReducerCallbackImpl;
import org.robert.tips.maltips.domain.model.MaltipsSystem;
import org.robert.tips.maltips.domain.model.reduce.SingleSystemCombinationReducerCallback;
import org.robert.tips.maltips.domain.model.reduce.SingleSystemCombinationsReducer;

/**
 * This is a dialog that is used to view the combination graph.
 * @author Robert Siwerz.
 */
public class ViewCombinationGraphDialog extends JDialog implements MaltipsTextMessages {

    /**
     * Creates an instance of this dialog.
     * @param maltipsGameType The game type.
     */
    public ViewCombinationGraphDialog(MaltipsGameType maltipsGameType) throws GeneralApplicationException, ReducingParametersNotSetException {
        super(maltipsGameType.getMainFrame(), false);
        setSize(new Dimension(500, 450));
        getContentPane().setLayout(new FlowLayout());
        TextMessages textMessages = TextMessages.getInstance();
        setTitle(textMessages.getText(VIEW_COMBINATION_GRAPH_DIALOG_TITLE));

        // create the dialog content with a border
        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder(etchedBorder);
        titledBorder.setTitle(textMessages.getText(VIEW_COMBINATION_GRAPH_BORDER_TITLE));
        content.setBorder(titledBorder);

        CombinationGraphDocument combinationGraphDocument = new CombinationGraphDocument();
        MaltipsDocument maltipsDocument = (MaltipsDocument) maltipsGameType.getDocument();
        MaltipsSystem maltipsSystem = maltipsDocument.getMaltipsSystem();
        MainFrame mainFrame = (MainFrame) maltipsGameType.getMainFrame();
        SingleSystemCombinationReducerCallback callback = new SingleSystemCombinationReducerCallbackImpl(maltipsSystem, mainFrame, maltipsDocument);
        SingleSystemCombinationsReducer singleSystemCombinationsReducer = new SingleSystemCombinationsReducer(maltipsSystem, callback);
        singleSystemCombinationsReducer.populateCombinationGraphData(combinationGraphDocument);

        // add check graph
        CombinationGraphView combinationGraphView = new CombinationGraphView(textMessages.getText(VIEW_COMBINATION_GRAPH_LABEL),
                250,
                200,
                combinationGraphDocument,
                this);
        constraints.gridx = 0;
        constraints.gridy = 0;
        content.add(combinationGraphView, constraints);

        getContentPane().add(content);

    }
}
