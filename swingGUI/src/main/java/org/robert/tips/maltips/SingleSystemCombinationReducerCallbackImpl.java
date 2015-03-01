package org.robert.tips.maltips;

import javax.swing.JProgressBar;
import org.robert.tips.MainFrame;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.maltips.domain.model.MaltipsSystem;
import org.robert.tips.maltips.domain.model.reduce.SingleSystemCombinationReducerCallback;

/**
 * Implementation of the SingleSystemCombinationReducerCallback interface.
 * Used to show progress of reducing process.
 * @author Robert
 */
public class SingleSystemCombinationReducerCallbackImpl implements SingleSystemCombinationReducerCallback {

    private JProgressBar statusBar;
    private MaltipsSystem maltipsSystem;
    private MainFrame mainFrame;
    private MaltipsDocument maltipsDocument;

    public SingleSystemCombinationReducerCallbackImpl(MaltipsSystem maltipsSystem, MainFrame mainFrame, MaltipsDocument maltipsDocument) {
        this.maltipsSystem = maltipsSystem;
        this.mainFrame = mainFrame;
        this.maltipsDocument = maltipsDocument;
    }

    public void updateProgressBar(int value) {
        statusBar.setValue(value);
    }

    public void initProgressBar() throws GeneralApplicationException {
        // The reducing can take a while, so change status bar.
        statusBar = new JProgressBar(0, maltipsSystem.getReducedSystem().size());
        statusBar.setStringPainted(true);
        mainFrame.changeStatusBar(statusBar);
    }

    public void resetProgressBar() throws GeneralApplicationException {
        // restore statusbar.
        mainFrame.changeStatusBar(null);
        maltipsDocument.setDocumentIsDirty(true);
    }
}
