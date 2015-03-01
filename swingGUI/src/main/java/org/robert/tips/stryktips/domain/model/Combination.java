package org.robert.tips.stryktips.domain.model;

import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.stryktips.domain.model.reduce.SingleRowCombinationsCallback;
import org.robert.tips.stryktips.domain.model.reduce.SingleRowCombinationsReducer;
import org.robert.tips.stryktips.domain.model.reduce.SingleRowCombinationsReducerRunnable;
import org.robert.tips.stryktips.exceptions.GameNotSetException;
import static org.robert.tips.stryktips.types.StryktipsConstants.*;

/**
 * Defines the combination system.
 * @author Robert
 */
public class Combination {

    private char[] singleRow = new char[NUMBER_OF_GAMES]; // contains the single row used for 'n over r' reducing.

    private int minInSingleRow;             // the minimum number of rights in the singlerow system.

    private int maxInSingleRow;             // the maximum number of rights in the singlerow system.

    private StryktipsSystem stryktipsSystem;    // referens to the main document


    public Combination(StryktipsSystem stryktipsSystem) {
        this.stryktipsSystem = stryktipsSystem;
    }

    /**
     * Set a value in the single row.
     * @param rownumber The game number to change, 0 - NUMBER_OF_GAMES.
     * @param value Value to set, e.g '1', 'X' or '2'.
     * @throws GameAlreadySetException The game result is already set in the single row system.
     */
    public void setSingleRow(int rownumber, char value) throws GameAlreadySetException {
        // check so that this result isn't selected in the single system.

        if (singleRow[rownumber] != value &&
                singleRow[rownumber] != 0 &&
                value != 0) // if value=0 it's going to be unselected.
        {
            throw new GameAlreadySetException("rownumber:" + rownumber + ", value:" + value);
        }

        // all it's ok, set new value
        singleRow[rownumber] = value;
    }

    /**
     * Get the value for the given single row number.
     * @param rownumber The rownumber to get the value for, 0 - NUMBER_OF_GAMES.
     */
    public char getSingleRow(int rownumber) {
        return singleRow[rownumber];
    }

    /**
     * Set minimum number of rights in the singlerow system.
     * @param value The new minimum value.
     */
    public void setMinInSingleRow(int value) {
        minInSingleRow = value;
    }

    /**
     * Get minimum number of rights in the singlerow system.
     * @return int The minimum value.
     */
    public int getMinInSingleRow() {
        return minInSingleRow;
    }

    /**
     * Set maximum number of rights in the singlerow system.
     * @param value The new maximum value.
     */
    public void setMaxInSingleRow(int value) {
        maxInSingleRow = value;
    }

    /**
     * Get maximum number of rights in the singlerow system.
     * @return int The maximum value.
     */
    public int getMaxInSingleRow() {
        return maxInSingleRow;
    }

    /**
     * Get the Single row.
     * @return char[].
     */
    char[] getSingleRow() {
        return singleRow;
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
    public void reduce(SingleRowCombinationsCallback callback) throws ReducingParametersNotSetException, GameNotSetException, GeneralApplicationException {
        SingleRowCombinationsReducer reducer = new SingleRowCombinationsReducer(stryktipsSystem, callback);
        reducer.reduce();
    }
}
