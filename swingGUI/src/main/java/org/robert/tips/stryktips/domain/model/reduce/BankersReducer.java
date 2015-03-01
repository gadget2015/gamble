package org.robert.tips.stryktips.domain.model.reduce;

import org.robert.tips.stryktips.domain.model.StryktipsSystem;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.types.StryktipsGame;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.types.ReducerIF;
import java.util.ArrayList;
import java.util.Iterator;
import org.robert.tips.exceptions.NoReducedRowsException;
import org.robert.tips.stryktips.exceptions.GameNotSetException;

/**
 * This class is used to reduce with combinations rows with
 * the bankers set.This is a utility class.
 * @author Robert Siwerz.
 */
public class BankersReducer implements StryktipsErrorMessages,
        ErrorMessages,
        StryktipsConstants,
        ReducerIF {

    private StryktipsSystem stryktipsSystem;

    /**
     * Create an instance of this BankerReducer.
     * @param stryktipsDocument The stryktips model.
     */
    public BankersReducer(StryktipsSystem stryktipsSystem) {
        this.stryktipsSystem = stryktipsSystem;
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
        checkBankersSet();
        checkThatThereAreAnyReducedRows();

        Iterator iterator = stryktipsSystem.getReducedSystem().iterator();
        ArrayList reducedRows = new ArrayList();

        while (iterator.hasNext()) {
            StryktipsGame game = (StryktipsGame) iterator.next();
            char[] singleRow = game.getSingleRow();
            boolean validRow = false;

            for (int i = 0; i < NUMBER_OF_GAMES; i++) {
                char banker = stryktipsSystem.getBanker().getBankersRow(i);

                if (banker != 0) {
                    if (singleRow[i] == banker) {
                        validRow = true;
                    } else {
                        validRow = false;
                        break;
                    }
                }
            }

            if (validRow) {
                reducedRows.add(game);
            }
        }

        // Set the reduced row in the document
        stryktipsSystem.setReducedSystem(reducedRows);
    }

    /**
     * Check so that any bankers are set. 
     * @throws GameNotSetException No bankers set.
     */
    private boolean checkBankersSet() throws GameNotSetException {
        // Check so that there are bankers set.
        boolean found = false;

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            char bankerCharacter = stryktipsSystem.getBanker().getBankersRow(i);

            if (bankerCharacter != 0) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new GameNotSetException("No banker set.");
        } else {
            return true;
        }
    }

    /**
     * Check so that are any combination rows to reduce
     * @throws NoReducedRowsException No reduced rows.     
     */
    private boolean checkThatThereAreAnyReducedRows() throws NoReducedRowsException {
        int numberOfReducedRows = stryktipsSystem.getReducedSystem().size();

        if (numberOfReducedRows < 1) {
            throw new NoReducedRowsException("There are no reduced rows so there is no need to set a banker.");
        } else {
            return true;
        }
    }
}

