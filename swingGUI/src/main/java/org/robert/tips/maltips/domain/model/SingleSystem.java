package org.robert.tips.maltips.domain.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.exceptions.GameAlreadySetException;
import java.util.ArrayList;
import org.robert.tips.MainFrame;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.maltips.domain.model.reduce.SingleSystemCombinationReducerCallback;
import org.robert.tips.maltips.domain.model.reduce.SingleSystemCombinationsReducer;

/**
 * Contains the mathimatical document.
 * @author Robert Siwerz.
 */
public class SingleSystem implements MaltipsConstants {

    /**
     * contains the mathimatical system.
     */
    private int[] singleSystem = new int[NUMBER_OF_GAMES];
    /**
     * referens to the aggregateroot.
     */
    private MaltipsSystem maltipsSystem;
    /**
     * the minimum number of rights in the singlerow system.
     */
    private int minInSingleRow;
    /**
     *  the maximum number of rights in the singlerow system.
     */
    private int maxInSingleRow;

    /**
     * Creates a new instance of this document.
     * @param maltipsSystem The maltips document.
     */
    public SingleSystem(MaltipsSystem maltipsSystem) {
        this.maltipsSystem = maltipsSystem;
    }

    /**
     * Set a row in the single system.
     * @param position integer value between { 0-29 }.
     * @param value the game number, {1-30}.
     * @throws GamelAlreadySetException The game is already set the in the mathimatical system.
     */
    public void setSingleSystemRow(int position, int value) throws GameAlreadySetException {
        // First check so this game option isn't already set.
        if (singleSystem[position] != 0 && value != 0) {
            throw new GameAlreadySetException("position:" + position);
        }

        singleSystem[position] = value;
    }

    /**
     * Get the option for the given position in the
     * single system.
     * @param position integer value between { 0-29 }.
     * @return int The character/option.
     */
    public int getSingleSystemRow(int position) {
        int returnValue = singleSystem[position];

        return returnValue;
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
     * Get all selected games from the single system.
     * @return int[] Contains all games that are selected.
     */
    public int[] getSelectedRows() {
        ArrayList tmp = new ArrayList(NUMBER_OF_GAMES);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            if (singleSystem[i] != 0) {
                tmp.add(new Integer(singleSystem[i]));
            }
        }

        // fix upp return value, e.g as a int[].
        int size = tmp.size();
        int[] returnValue = new int[size];

        for (int i = 0; i < size; i++) {
            returnValue[i] = ((Integer) tmp.get(i)).intValue();
        }

        return returnValue;
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
    public void reduce(SingleSystemCombinationReducerCallback callback) throws GeneralApplicationException, ReducingParametersNotSetException {
        SingleSystemCombinationsReducer reducer = new SingleSystemCombinationsReducer(maltipsSystem, callback);
        reducer.reduce();        
    }
}
