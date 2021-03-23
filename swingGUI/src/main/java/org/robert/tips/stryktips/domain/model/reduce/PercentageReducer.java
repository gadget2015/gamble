package org.robert.tips.stryktips.domain.model.reduce;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.NoReducedRowsException;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.stryktips.domain.model.StryktipsSystem;
import org.robert.tips.stryktips.exceptions.GameNotSetException;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.stryktips.types.StryktipsGame;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.types.ReducerIF;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Formula: number of rows = P * revenue * koefficient;
 *
 * Example:
 *  revenue = 28200278 SEK.
 *  P = 0.0000156708.
 *  number of rows = 266.
 *  koefficient = 0.601918951
 */
public class PercentageReducer implements StryktipsErrorMessages,
        ErrorMessages,
        StryktipsConstants,
        ReducerIF {
    private StryktipsSystem stryktipsSystem;

    /**
     * Create an instance of this reducer.
     */
    public PercentageReducer(StryktipsSystem stryktipsSystem) {
        this.stryktipsSystem = stryktipsSystem;
    }

    @Override
    public void reduce() throws GeneralApplicationException, GameNotSetException, NoReducedRowsException, ReducingParametersNotSetException {
        // Get parameters
        ArrayList currentRows = stryktipsSystem.getReducedSystem();
        int minimumNumberOfPeopleWithFullPot = stryktipsSystem.getPlayed().minimumNumberOfPeopleWithFullPot;
        int maxiumumNumberOfPeopleWithFullPot = stryktipsSystem.getPlayed().maxiumumNumberOfPeopleWithFullPot;
        float koefficientMin = stryktipsSystem.getPlayed().koefficientMin;
        float koefficientMax = stryktipsSystem.getPlayed().koefficientMax;
        int revenue = stryktipsSystem.getPlayed().revenue;

        ArrayList newReducedRows = new ArrayList();
        Iterator iterator = currentRows.iterator();

        while (iterator.hasNext()) {
            int row = 0;
            StryktipsGame game = (StryktipsGame) iterator.next();
            char[] singleRow = game.getSingleRow();

            // Calculate the P for the row.
            float P = 1;

            for (int i = 0; i < NUMBER_OF_GAMES; i++) {
                int pos = 0;
                pos = (singleRow[i] == GAMEVALUE_TIE) ? 1 : pos;
                pos = (singleRow[i] == GAMEVALUE_TWO) ? 2 : pos;

                float rowPercentage = stryktipsSystem.getPlayed().getPercentage(i * NUMBER_OF_GAMEOPTIONS + pos);
                P *= rowPercentage;
            }

            // Execute the formula with min and max for the given koefficient.
            int numberOfRowsMin = Math.round(P*koefficientMin*revenue);
            int numberOfRowsMax = Math.round(P*koefficientMax*revenue);

            // Decide if the row should be saved in the new reduced system.
            // 1. Check the low value of number of people that will get full pot.
            // 2. Check the high value of number of people that will get full pot.
            if (minimumNumberOfPeopleWithFullPot >= numberOfRowsMin &&
                minimumNumberOfPeopleWithFullPot <= numberOfRowsMax) {
                // This row is OK, and should be saved/stored/used.
                newReducedRows.add(game);
            }

        }

        stryktipsSystem.setReducedSystem(newReducedRows);
    }
}