package org.robert.tips.maltips.domain.model;

import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.maltips.domain.model.reduce.BankersSystemReducer;
import org.robert.tips.maltips.types.MaltipsConstants;

/**
 * Contains the maltips bankers document.
 * @author Robert Siwerz.
 */
public class Bankers implements MaltipsConstants {

    /**
     * contains the bankers system row.
     */
    private int[] bankersRow = new int[NUMBER_OF_GAMES];
    /**
     * referens to the aggregateroot.
     */
    private MaltipsSystem maltipsSystem;

    /**
     * Creates a new instance of the bankers document.
     * @param maltipsDocument Owner for this document.
     */
    public Bankers(MaltipsSystem maltipsSystem) {
        this.maltipsSystem = maltipsSystem;
    }

    /**
     * Get a game option in the bankers system.
     * @param row The row to get the bankers for.
     */
    public int getBankersRow(int row) {
        return bankersRow[row];
    }

    /**
     * Set a game option in the bankers system.
     * @param rowNumber The game number to change.
     * @param value Value to set, e.g number between 1 -30 or 0=reset game value.
     * @throws GameAlreadySetException The game result is already set in the extended row system.
     */
    public void setBankersRow(int rowNumber, int value) throws GameAlreadySetException {
        if (bankersRow[rowNumber] != 0 && value != 0) {
            throw new GameAlreadySetException("The game is already set: rowNumber=" + rowNumber + ", value=" + value);
        } else {
            bankersRow[rowNumber] = value;
        }
    }

    /**
     * Create a bankers system from the Bankers document model.
     * <pre>
     * Flow:
     * 1. Check so that are at least one game set in the bankers system.
     * 3. Create the reduced system from the bankers system.
     * </pre>
     * @throws GeneralApplicationException Major error.
     */
    public void reduce() throws GeneralApplicationException, ReducingParametersNotSetException {
        BankersSystemReducer reducer = new BankersSystemReducer(maltipsSystem);
        reducer.reduce();
    }
}
