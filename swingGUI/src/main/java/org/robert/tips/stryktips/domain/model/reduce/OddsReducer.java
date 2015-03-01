package org.robert.tips.stryktips.domain.model.reduce;

import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.types.StryktipsGame;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.types.ReducerIF;
import java.util.ArrayList;
import java.util.Iterator;
import org.robert.tips.stryktips.domain.model.Mathimatical;
import org.robert.tips.stryktips.domain.model.StryktipsSystem;
import org.robert.tips.stryktips.exceptions.GameNotSetException;

/**
 * This class is used to reduce with odds system. This is removing
 * the rows that not are between minimum and maximum odds boundaries.
 * @author Robert Siwerz.
 */
public class OddsReducer implements StryktipsErrorMessages,
        ErrorMessages,
        StryktipsConstants,
        ReducerIF {

    private StryktipsSystem stryktipsSystem;
    
    /**
     * Create an instance of this reducer.
     */
    public OddsReducer(StryktipsSystem stryktipsSystem) {
        this.stryktipsSystem = stryktipsSystem;
    }

    /**
     * Iterates over all rows in the reduced rows container and 
     * only stores the rows that are between the max and min boundaries.
     * <pre>
     * Flow:
     * 1. Check so that odds are set.
     * 2. Iterates over all reduced rows and reduce with the odds system.
     * </pre>
     * @throws GeneralApplicationException Major error.
     */
    public void reduce() throws GameNotSetException {
        if (!checkOddsSet()) {
            return;
        // Iterate over the reduced rows and reduce with the odds system.
        }
        ArrayList reducedSystem = stryktipsSystem.getReducedSystem();
        ArrayList reducedRows = new ArrayList();
        Iterator iterator = reducedSystem.iterator();
        float minimum = stryktipsSystem.getOdds().getMinimumOdds();
        float maximum = stryktipsSystem.getOdds().getMaximumOdds();

        while (iterator.hasNext()) {
            StryktipsGame game = (StryktipsGame) iterator.next();
            char[] singleRow = game.getSingleRow();
            float odds = 1;

            for (int i = 0; i < NUMBER_OF_GAMES; i++) {
                int pos = 0;
                pos = (singleRow[i] == GAMEVALUE_TIE) ? 1 : pos;
                pos = (singleRow[i] == GAMEVALUE_TWO) ? 2 : pos;

                float rowOdds = stryktipsSystem.getOdds().getOddsSystem(i * NUMBER_OF_GAMEOPTIONS + pos);
                odds *= rowOdds;
            }

            if (odds >= minimum && odds <= maximum) {
                // row is in the odds bondary, add it to result.
                reducedRows.add(game);
            }
        }

        // Set the reduced row in the document
        stryktipsSystem.setReducedSystem(reducedRows);
    }

    /**
     * Check so that odds are set for all games.
     * @throws GeneralApplicationException Major error.     
     */
    private boolean checkOddsSet() throws GameNotSetException {
        // Check so that there are bankers set.
        boolean valid = true;

        for (int i = 0; i < (NUMBER_OF_GAMES * NUMBER_OF_GAMEOPTIONS); i++) {
            float value = stryktipsSystem.getOdds().getOddsSystem(i);

            if (value < 1) {
                valid = false;
                break;
            }
        }

        if (!valid) {
            throw new GameNotSetException("Missing odds values.");
        } else {
            return true;
        }
    }

    /**
     * Get data used to create a graph. The data is an array with integers. The
     * integer data is number of occurens for a odds intervall. The data array
     * could look like this. This get odds for the mathimatical system, 3^13.
     * <pre>
     * data[0] = 50;    // 50 gamble rows that have between 0-1 000 in odds.
     * data[1] = 58;    // 58 gamble rows that have between 1 000 - 2000 in odds.
     * data[2] = 200;   // 200 gamble rows that have between 2 000 - 3000 in odds.
     * data[3] = 350;   // 350 gamble rows that have between 3 000 - 4000 in odds.
     * </pre>     
     * @return int[500] Data array used to plot a graph.
     */
    public int[] getAllOddsGraphData() {
        // First get all possible combination in this gamle, e.g create
        // a mathimatical system with 3^13 number of single rows.
        char[][] M_13_0 = new char[NUMBER_OF_GAMES][NUMBER_OF_GAMEOPTIONS];

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            M_13_0[i][0] = GAMEVALUE_ONE;
            M_13_0[i][1] = GAMEVALUE_TIE;
            M_13_0[i][2] = GAMEVALUE_TWO;
        }

        Mathimatical mathimaticalSystem = new Mathimatical();
        mathimaticalSystem.setMathimaticalSystem(M_13_0);
        ArrayList rows = mathimaticalSystem.createsSingleRowsFromMathimaticalSystem();

        // calculates the odds for all single rows in the mathimatical system.
        int[] data = new int[500];    // max 500 data points, e.g 0 - 500 000

        Iterator iterator = rows.iterator();

        while (iterator.hasNext()) {
            char[] singleRow = (char[]) iterator.next();
            float odds = 1;

            for (int i = 0; i < NUMBER_OF_GAMES; i++) {
                int pos = 0;
                pos = (singleRow[i] == GAMEVALUE_TIE) ? 1 : pos;
                pos = (singleRow[i] == GAMEVALUE_TWO) ? 2 : pos;

                float rowOdds = stryktipsSystem.getOdds().getOddsSystem(i * NUMBER_OF_GAMEOPTIONS + pos);
                odds *= rowOdds;
            }

            int intervall = ((int) (odds - (odds % 1000))) / 1000;

            if (intervall < 500) {
                data[intervall]++;
            }
        }

        return data;
    }

    /**
     * Get data used to create a graph. The data is an array with integers. The
     * integer data is number of occurens for a odds intervall. The data array
     * could look like this. This method gets the odds for the reduced system.
     * <pre>
     * data[0] = 50;    // 50 gamble rows that have between 0-1 000 in odds.
     * data[1] = 58;    // 80 gamble rows that have between 1 000 - 2000 in odds.
     * data[2] = 200;   // 200 gamble rows that have between 2 000 - 3000 in odds.
     * data[3] = 350;   // 350 gamble rows that have between 3 000 - 4000 in odds.
     * </pre>
     * @return int[500] Data array used to plot a graph.
     */
    public int[] getReducedOddsGraphData() {
        // First get the reduced system.
        ArrayList reducedSystem = stryktipsSystem.getReducedSystem();

        // calculates the odds for all single rows in the mathimatical system.
        int[] data = new int[500];    // max 500 data points, e.g 0 - 500 000

        Iterator iterator = reducedSystem.iterator();

        while (iterator.hasNext()) {
            StryktipsGame game = (StryktipsGame) iterator.next();
            char[] singleRow = game.getSingleRow();
            float odds = 1;

            for (int i = 0; i < NUMBER_OF_GAMES; i++) {
                int pos = 0;
                pos = (singleRow[i] == GAMEVALUE_TIE) ? 1 : pos;
                pos = (singleRow[i] == GAMEVALUE_TWO) ? 2 : pos;

                float rowOdds = stryktipsSystem.getOdds().getOddsSystem(i * NUMBER_OF_GAMEOPTIONS + pos);
                odds *= rowOdds;
            }

            int intervall = ((int) (odds - (odds % 1000))) / 1000;

            if (intervall < 500) {
                data[intervall]++;
            }
        }

        return data;
    }
}

