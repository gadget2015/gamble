package org.robert.tips.maltips.domain.model;

import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.maltips.types.RSystemType;
import java.util.ArrayList;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.maltips.domain.model.reduce.RSystemReducer;

/**
 * Contains the R-system domain object.
 * @author Robert Siwerz.
 */
public class RSystem implements MaltipsConstants {

    /**
     * contains the R-system. A value in the array that are '0' defines that
     * the game isn't set.
     */
    private int[] rSystem = new int[NUMBER_OF_GAMES];
    /**
     * Defines the r-system type.
     */
    private RSystemType rSystemType;
    /**
     * referens to the aggregateroot.
     */
    private MaltipsSystem maltipsSystem;

    /**
     * Creates a new instance of this document.
     * @param maltipsSystem The maltips domain object.
     */
    public RSystem(MaltipsSystem maltipsSystem) {
        this.maltipsSystem = maltipsSystem;
    }

    /**
     * Set a row in the R-system.
     * @param position integer value between { 0-29 }.
     * @param value is 0 to reset selection oterwise game # = {1-30 }
     * @throws GamelAlreadySetException The game is already set the in the R-system.
     */
    public void setRSystemRow(int position, int value) throws GameAlreadySetException {
        // First check so this game option isn't already set.
        if (rSystem[position] != 0 && value != 0) {
            throw new GameAlreadySetException("position:" + position);
        }

        rSystem[position] = value;
    }

    /**
     * Get the option for the given position in the
     * single system.
     * @param position integer value between { 0-29 }.
     * @return int The character/option.
     */
    public int getRSystemRow(int position) {
        int returnValue = rSystem[position];

        return returnValue;
    }

    /**
     * Get all selected games from the R-system.
     * @return int[] Contains all games that are selected.
     */
    public int[] getSelectedRows() {
        ArrayList tmp = new ArrayList(NUMBER_OF_GAMES);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            if (rSystem[i] != 0) {
                tmp.add(new Integer(rSystem[i]));
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
     * Get the r-system type.
     * @return RSystemType The system type.
     */
    public RSystemType getRSystemType() {
        return rSystemType;
    }

    /**
     * Set r-system type.
     * @param systemType The system type.
     */
    public void setRSystemType(RSystemType systemType) {
        this.rSystemType = systemType;
    }

        /**
     * Create a R-system from the Rsystem document model.
     * <pre>
     * Flow:
     * 1. Check so that all rows are set in the R-system and matches the selected systemtype.
     * 2. Create the R-system.
     * </pre>
     * @throws GeneralApplicationException Major error.
     */
    public void reduce() throws GeneralApplicationException, ReducingParametersNotSetException {
        RSystemReducer reducer = new RSystemReducer(maltipsSystem);
        reducer.reduce();
    }
}
