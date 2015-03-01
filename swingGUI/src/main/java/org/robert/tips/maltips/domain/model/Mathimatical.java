package org.robert.tips.maltips.domain.model;

import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.exceptions.GameAlreadySetException;
import java.util.ArrayList;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.maltips.domain.model.reduce.MathimaticalSystemReducer;

/**
 * Contains the mathimatical document.
 * @author Robert Siwerz.
 */
public class Mathimatical implements MaltipsConstants {

    /**
     * contains the mathimatical system.
     */
    private int[] mathimaticalSystem = new int[NUMBER_OF_GAMES];
    /**
     * referens to the aggregateroot.
     */
    private MaltipsSystem maltipsSystem;

    /**
     * Creates a new instance of this document.
     * @param maltipsDocument The maltips system.
     */
    public Mathimatical(MaltipsSystem maltipsSystem) {
        this.maltipsSystem = maltipsSystem;
    }

    /**
     * Set a row in the mathimatical system.
     * @param position integer value between { 0-29 }.
     * @param the value to set, e.g the match numbe {1-30}.
     * @throws GamelAlreadySetException The game is already set the in the mathimatical system.
     */
    public void setMathimaticalRow(int position, int value) throws GameAlreadySetException {
        // First check so this game option isn't already set.
        if (mathimaticalSystem[position] != 0 && value != 0) {
            throw new GameAlreadySetException("position:" + position);
        }

        mathimaticalSystem[position] = value;
    }

    /**
     * Get the option for the given position in the
     * mathimatical system.
     * @param position integer value between { 0-29 }.
     * @return int The character/option.
     */
    public int getMathimaticalRow(int position) {
        int returnValue = mathimaticalSystem[position];

        return returnValue;
    }

    /**
     * Get all selected games from the mathimatical system.
     * @return int[] Contains all games that are selected.
     */
    public int[] getSelectedRows() {
        ArrayList tmp = new ArrayList(NUMBER_OF_GAMES);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            if (mathimaticalSystem[i] != 0) {
                tmp.add(new Integer(mathimaticalSystem[i]));
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
     * Create a mathimatical system from the Mathimatical document model.
     * <pre>
     * Flow:
     * 1. Check so that are at least 8 games set in the mathimatical system.
     * 3. Create the mathimatical system.
     * </pre>
     * @throws GeneralApplicationException Major error.
     */
    public void reduce() throws GeneralApplicationException, ReducingParametersNotSetException {
        MathimaticalSystemReducer reducer = new MathimaticalSystemReducer(maltipsSystem);
        reducer.reduce();
    }
}
