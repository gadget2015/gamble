package org.robert.tips.stryktips.domain.model;

import org.robert.tips.exceptions.NoReducedRowsException;
import org.robert.tips.stryktips.domain.model.reduce.BankersReducer;
import org.robert.tips.stryktips.exceptions.GameNotSetException;
import static org.robert.tips.stryktips.types.StryktipsConstants.*;

/**
 * Defines a banker system.
 * @author Robert
 */
public class Banker {

    /**
     * contains the bankers system row.
     */
    private char[] bankersRow = new char[NUMBER_OF_GAMES];
    /**
     * referens to the stryktipssystem.
     */
    private StryktipsSystem stryktipsSystem;

    /**
     * Creates a new instance of the bankers document.
     * @param stryktipssystem Owner for this document.
     */
    public Banker(StryktipsSystem stryktipssystem) {
        this.stryktipsSystem = stryktipssystem;
    }

    /**
     * Get a game option in the bankers system.
     * @param row The row to get the bankers for.
     */
    public char getBankersRow(int row) {
        return bankersRow[row];
    }

    /**
     * Set a game option in the bankers system.
     * @param rowNumber The game number to change.
     * @param value Value to set, e.g '1', 'X' or '2'.
     * @throws GameNotSetException The game is not set in single row system.
     */
    public void setBankersRow(int rowNumber, char value) throws GameNotSetException {
        char singleSystemRow = stryktipsSystem.getCombination().getSingleRow(rowNumber);

        if (singleSystemRow != value && value != 0) {
            throw new GameNotSetException("The game is not set in single row system (combination system) for: rowNumber=" + rowNumber + ", value=" + value);
        } else {
            bankersRow[rowNumber] = value;

        }

    }

    /**
     * Iterates over all rows in the reduced system container and
     * only stores the rows that have the bankers set.
     * <pre>
     * Flow:
     * 1. Check so that any bankers are set.
     * 2. Check so that are any combination rows to reduce.
     * 3. Iterates over all combinations and store the valid rows.
     * </pre>
     * @throws GeneralApplicationException Major error.
     */
    public void reduce() throws GameNotSetException, NoReducedRowsException {
        BankersReducer bankerReducer = new BankersReducer(stryktipsSystem);
        bankerReducer.reduce();
    }
}
