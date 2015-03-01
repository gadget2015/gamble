package org.robert.tips.stryktips;

import javax.swing.JProgressBar;
import org.robert.tips.MainFrame;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.domain.model.StryktipsSystem;
import org.robert.tips.stryktips.domain.model.reduce.SingleRowCombinationsCallback;

/**
 * Implementation of callback used by single row systems.
 * @author Robert
 */
public class SingleRowCombinationsCallbackImpl implements SingleRowCombinationsCallback {

    private JProgressBar statusBar;
    private StryktipsSystem stryktipsSystem;
    private StryktipsDocument stryktipsDocument; 
    private MainFrame mainFrame;
    private int max;

    /**
     * Creates a new instance of this callback implementation.
     * @param stryktipsSystem
     * @param mainFrame
     * @param max of progressbar, if 0 then the number of rows in the reduced system is used as max value.
     */
    public SingleRowCombinationsCallbackImpl(StryktipsDocument stryktipsDocument, StryktipsSystem stryktipsSystem, MainFrame mainFrame, int max) {
        this.stryktipsDocument = stryktipsDocument;
        this.stryktipsSystem = stryktipsSystem;
        this.mainFrame = mainFrame;
        this.max = max;
    }

    public void updateProgressBar(int value) {
        statusBar.setValue(value);
    }

    public void initProgressBar() throws GeneralApplicationException {
        // The reducing can take a while, so change status bar.
        int endOfProgressBar = (max != 0) ? max : stryktipsSystem.getReducedSystem().size();
        statusBar = new JProgressBar(0, endOfProgressBar);
        statusBar.setStringPainted(true);
        mainFrame.changeStatusBar(statusBar);
    }

    public void resetProgressBar() throws GeneralApplicationException {
        // restore statusbar.
        mainFrame.changeStatusBar(null);

        // must wait for thread that creates combinations to finish before
        // inform that the model has changed.
        stryktipsDocument.setDocumentIsDirty(true);
    }
}
