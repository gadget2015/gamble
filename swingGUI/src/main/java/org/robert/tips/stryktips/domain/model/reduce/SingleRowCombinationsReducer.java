package org.robert.tips.stryktips.domain.model.reduce;

import org.robert.tips.stryktips.SingleRowCombinationsCallbackImpl;
import org.robert.tips.stryktips.domain.model.StryktipsSystem;
import javax.swing.JProgressBar;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.stryktips.ui.desktop.dialogs.combinationgraph.CombinationGraphDocument;
import org.robert.tips.types.ReducerIF;
import org.robert.tips.stryktips.exceptions.GameNotSetException;

/**
 * This class is used to reduce with the 'n over r' algorithm on the
 * single row system. This is a utility class.
 * @author Robert Siwerz.
 */
public class SingleRowCombinationsReducer implements StryktipsErrorMessages,
        ErrorMessages,
        StryktipsConstants,
        ReducerIF {

    private Thread reducerThread;
    private StryktipsSystem stryktipsSystem;
    private SingleRowCombinationsCallback callback;

    /**
     * Create an instance of this BankerReducer.
     */
    public SingleRowCombinationsReducer(StryktipsSystem stryktipsSystem, SingleRowCombinationsCallback callback) {
        this.stryktipsSystem = stryktipsSystem;
        this.callback = callback;
    }

    /**
     * Creates all possible combinations for the singlerow system.
     * <pre>
     * Flow:
     * 1. Check so that min < max number of rights in singlerow system
     * 2. Check so that there are set game options result for all rows in the single system.
     * 3. Create all possible combinations.
     * </pre>
     */
    public void reduce() throws ReducingParametersNotSetException, GameNotSetException, GeneralApplicationException {
        checkMinMaxParameters();
        checkSingleRowSystem();

        SingleRowCombinationsReducerRunnable reducerRunnable = new SingleRowCombinationsReducerRunnable(stryktipsSystem, callback);
        reducerThread = new Thread(reducerRunnable);
        getReducerThread().start();
    }

    /**
     * Check so that the min value is less or equal to max value.
     * @return boolean true=ok.
     */
    private boolean checkMinMaxParameters() throws ReducingParametersNotSetException {
        int min = stryktipsSystem.getCombination().getMinInSingleRow();
        int max = stryktipsSystem.getCombination().getMaxInSingleRow();

        if (min > max) {
            throw new ReducingParametersNotSetException(INVALID_RIGHTS_IN_SINGLEROW);
        }

        return true;
    }

    /**
     * Check so that all rows are set in the single row system.
     * @return boolean true=ok.
     */
    private boolean checkSingleRowSystem() throws GameNotSetException {
        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            char singleRowCharacter = stryktipsSystem.getCombination().getSingleRow(i);

            if (singleRowCharacter == 0) {
                throw new GameNotSetException(NOT_ALL_ROWS_SET_IN_SINGLESYSTEM);
            }
        }

        return true;
    }

    /**
     * Get the combination graph. This calculates the number of rows in the reduced system
     * that are valid for every number of rights that can occur in the singlerow system, e.g
     * 0 - 13.
     * @param combinationGraphModel Model to put graph data into.
     */
    public void getCombinationGraphData(CombinationGraphDocument combinationGraphModel) throws GeneralApplicationException {
        // The reducing can take a while, so change status bar.
        JProgressBar statusBar = new JProgressBar(0, NUMBER_OF_GAMES);
        statusBar.setStringPainted(true);
        SingleRowCombinationsGraphRunnable reducerRunnable = new SingleRowCombinationsGraphRunnable(stryktipsSystem, combinationGraphModel, callback);
        Thread t = new Thread(reducerRunnable);
        t.start();
    }

    public Thread getReducerThread() {
        return reducerThread;
    }
}

