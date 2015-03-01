package org.robert.tips.maltips.domain.model.reduce;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.maltips.domain.model.MaltipsSystem;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.maltips.types.MaltipsErrorMessages;
import org.robert.tips.maltips.types.MaltipsGame;
import java.util.ArrayList;
import java.util.Iterator;
import org.robert.tips.exceptions.ReducingParametersNotSetException;

/**
 * This class is used to create a bankers reduced system with 
 * the games set in the bankers tab.This is a utility class.
 * @author Robert Siwerz.
 */
public class BankersSystemReducer implements ErrorMessages,
        MaltipsErrorMessages,
        MaltipsConstants {

    private MaltipsSystem maltipsSystem;

    /**
     * Create an instance of this class.
     * @param maltipsSystem The maltipssystem.
     */
    public BankersSystemReducer(MaltipsSystem maltipsSystem) {
        this.maltipsSystem = maltipsSystem;
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
        // check
        checkBankersSystemSet();
 
        ArrayList reducedSystem = reduceWithBankers();

        // set reduced system.
        maltipsSystem.setReducedSystem(reducedSystem);
    }

    /**
     * Check so that are at least one game set in the bankers system.
     * @throws GeneralApplicationException Major error.     
     */
    private void checkBankersSystemSet() throws ReducingParametersNotSetException {
        int numberOfGames = getNumberOfSelectedGames();

        if (numberOfGames < 1) {
            throw new ReducingParametersNotSetException(MUST_SELECT_ATLEAST_ONE_GAME);
        }
    }

    /**
     * Get number of selected games in the Bankers system.
     * @return int Number of selected games.
     */
    private int getNumberOfSelectedGames() {
        int numberOfGames = 0;

        for (int i = 0; i < NUMBER_OF_GAMES; i++) // iterates over all rows.
        {
            int game = maltipsSystem.getBankers().getBankersRow(i);

            if (game != 0) {
                numberOfGames++;
            }
        }

        return numberOfGames;
    }

    /**
     * Reduce with the Bankers set in the bankers system.
     * @return ArrayList The reduced system.
     */
    private ArrayList reduceWithBankers() {
        ArrayList reducedSystem = new ArrayList();
        Iterator iterator = maltipsSystem.getReducedSystem().iterator();

        // Iterate over all reduced maltips games
        while (iterator.hasNext()) {
            MaltipsGame currentGame = (MaltipsGame) iterator.next();

            int[] game = currentGame.getMaltipsRow();
            boolean currentGameValid = false;

            for (int i = 0; i < NUMBER_OF_GAMES; i++) {
                int banker = maltipsSystem.getBankers().getBankersRow(i);

                if (banker != 0) {
                    boolean bankerValid = false;

                    // A bankers is set, check so it's in the current game
                    for (int j = 0; j < NUMBER_GAMES_IN_MALTIPS; j++) {
                        if (banker == game[j]) {
                            // bankers found, current row is ok.
                            bankerValid = true;
                        }
                    }

                    if (bankerValid) {
                        currentGameValid = true;
                    } else {
                        currentGameValid = false;
                        break;  // skip testing more

                    }
                }
            }

            if (currentGameValid) {
                // add row to the reduced system
                reducedSystem.add(currentGame);
            }
        }

        return reducedSystem;
    }
}

