package org.robert.tips.maltips.domain.model.reduce;

import org.robert.tips.maltips.domain.model.MaltipsSystem;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.types.ReducerIF;
import org.robert.tips.maltips.types.MaltipsErrorMessages;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.maltips.ui.dialogs.viewcombinationgraph.CombinationGraphDocument;
import org.robert.tips.exceptions.ReducingParametersNotSetException;

/**
 * This class is used to reduce with the 'n over r' algorithm on the
 * single row system. This is a utility class.
 * @author Robert Siwerz.
 */
public class SingleSystemCombinationsReducer implements MaltipsErrorMessages,
        ErrorMessages,
        MaltipsConstants,
        ReducerIF {

    private Thread reducerThread;
    private MaltipsSystem maltipsSystem;
    private SingleSystemCombinationReducerCallback callback;

    /**
     * Create an instance of this SingleSystemCombinationsReducer.
     * @param maltipsSystem
     */
    public SingleSystemCombinationsReducer(MaltipsSystem maltipsSystem, SingleSystemCombinationReducerCallback callback) {
        this.maltipsSystem = maltipsSystem;
        this.callback = callback;
    }

    /**
     * Creates all possible combinations for the singlerow system.
     * <pre>
     * Flow:
     * 1. Check so that min < max number of rights is set in singlerow system parameters.
     * 2. Check so that there are at least 8 games set in the single system.
     * 3. Create all possible combinations.
     * </pre>
     */
    public void reduce() throws GeneralApplicationException, ReducingParametersNotSetException {
        checkMinMaxParameters();
        checkSingleSystem();

        SingleSystemCombinationsReducerRunnable reducerRunnable = new SingleSystemCombinationsReducerRunnable(maltipsSystem, callback);
        reducerThread = new Thread(reducerRunnable);
        getReducerThread().start();
    }

    /**
     * Check so that the min value is less or equal to max value.
     * @return boolean true=ok.
     */
    private void checkMinMaxParameters() throws GeneralApplicationException, ReducingParametersNotSetException {
        int min = maltipsSystem.getSingleSystem().getMinInSingleRow();
        int max = maltipsSystem.getSingleSystem().getMaxInSingleRow();

        if (min > max || max == 0) {
            // minimum number shall be equal or less than maximum.
            throw new ReducingParametersNotSetException(INVALID_RIGHTS_IN_SINGLESYSTEM);
        }
    }

    /**
     * Check so that there are at least 8 games set in the single system.
     * @return boolean true=ok.
     */
    private void checkSingleSystem() throws GeneralApplicationException, ReducingParametersNotSetException {
        int numberOfSelectedGames = maltipsSystem.getSingleSystem().getSelectedRows().length;

        if (numberOfSelectedGames < 8) {
            throw new ReducingParametersNotSetException(MUST_SELECT_MORE_GAMES);
        }
    }

    /**
     * Populates the given MVC document model with graph data. This is done
     * in another thread. The graph data is created by setting 0 ... 8 rights
     * in the single system.
     * @param combinationGraphDocument The graph model.
     */
    public void populateCombinationGraphData(CombinationGraphDocument combinationGraphDocument) throws GeneralApplicationException, ReducingParametersNotSetException {
        checkSingleSystem();

        SingleSystemCombinationsGraphRunnable graphRunnable = new SingleSystemCombinationsGraphRunnable(maltipsSystem, callback, combinationGraphDocument);
        reducerThread = new Thread(graphRunnable);
        reducerThread.start();
    }

    /**
     * @return the reducerThread
     */
    public Thread getReducerThread() {
        return reducerThread;
    }
}

