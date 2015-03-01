package org.robert.tips.maltips.domain.model.reduce;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.maltips.domain.model.MaltipsSystem;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.maltips.types.MaltipsErrorMessages;
import org.robert.tips.maltips.types.MaltipsGame;
import org.robert.tips.util.Combination;
import java.util.ArrayList;
import java.util.Iterator;
import org.robert.tips.exceptions.ReducingParametersNotSetException;

/**
 * This class is used to create a mathimatical system with 
 * the game set.This is a utility class.
 * @author Robert Siwerz.
 */
public class MathimaticalSystemReducer implements ErrorMessages,
        MaltipsErrorMessages,
        MaltipsConstants {

    private MaltipsSystem maltipsSystem;
    
    /**
     * Create an instance of this class.
     * @param maltipsSystem
     */
    public MathimaticalSystemReducer(MaltipsSystem maltipsSystem) {
        this.maltipsSystem = maltipsSystem;
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
        // check
        checkMathimaticalSystemSet();

        int numberOfSelectedGames = getNumberOfSelectedGames();
        Combination combinations = new Combination(numberOfSelectedGames, NUMBER_GAMES_IN_MALTIPS);
        combinations.createCombinations();
        ArrayList combinationsContainer = combinations.getCombinations();

        // Map combinations to the mathimatical system
        ArrayList reducedSystem = new ArrayList();
        Iterator iterator = combinationsContainer.iterator();
        int[] selectedGames = maltipsSystem.getMathimatical().getSelectedRows();

        while (iterator.hasNext()) {
            int[] combination = (int[]) iterator.next();
            int[] game = new int[NUMBER_GAMES_IN_MALTIPS];

            for (int i = 0; i < NUMBER_GAMES_IN_MALTIPS; i++) {
                game[i] = selectedGames[combination[i]];
            }

            MaltipsGame maltipsGame = new MaltipsGame(game);
            reducedSystem.add(maltipsGame);
        }

        // set reduced system.
        maltipsSystem.setReducedSystem(reducedSystem);
    }

    /**
     * Check so that are at least 8 games set in the mathimatical system.
     * @throws GeneralApplicationException Major error.     
     */
    private void checkMathimaticalSystemSet() throws GeneralApplicationException, ReducingParametersNotSetException {
        int numberOfGames = getNumberOfSelectedGames();

        if (numberOfGames < NUMBER_GAMES_IN_MALTIPS) {
            throw new ReducingParametersNotSetException(MUST_SELECT_N_GAMES);
        }

    }

    /**
     * Get number of selected games in the mathimatical system.
     * @return int Number of selected games.
     */
    private int getNumberOfSelectedGames() {
        int numberOfGames = 0;

        for (int i = 0; i < NUMBER_OF_GAMES; i++) // iterates over all rows.
        {
            int game = maltipsSystem.getMathimatical().getMathimaticalRow(i);

            if (game != 0) {
                numberOfGames++;
            }
        }

        return numberOfGames;
    }
}

