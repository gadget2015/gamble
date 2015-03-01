package org.robert.tips.stryktips.domain.model.reduce;

import org.robert.tips.stryktips.SingleRowCombinationsCallbackImpl;
import org.robert.tips.stryktips.domain.model.StryktipsSystem;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsGame;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This runnable object is used when create the combination reducing system.
 * @author Robert Siwerz.
 */
public class SingleRowCombinationsReducerRunnable implements Runnable,
        StryktipsConstants {

    private StryktipsSystem stryktipsSystem;
    private SingleRowCombinationsCallback callback;

    public SingleRowCombinationsReducerRunnable(StryktipsSystem stryktipsSystem, SingleRowCombinationsCallback callback) {
        this.stryktipsSystem = stryktipsSystem;
        this.callback = callback;
    }

    public void run() {
        SingleRowCombinationReducerUtility utility = SingleRowCombinationReducerUtility.newInstance();

        try {
            // The reducing can take a while, so change status bar.
            callback.initProgressBar();

            // This is the main part for this thread to do.
            // creates all possible combinations that can occur between min and max.
            int min = stryktipsSystem.getCombination().getMinInSingleRow();
            int max = stryktipsSystem.getCombination().getMaxInSingleRow();
            ArrayList combinations = utility.createCombinations(min, max);

            // Iterate over all the reduced system rows and check if each row is valid 
            // againts the combinations.
            ArrayList reducedSystemRows = createReducedSystem(combinations);
            stryktipsSystem.setReducedSystem(reducedSystemRows);
        } catch (GeneralApplicationException e) {
            // Do nothing.
            e.printStackTrace();
        } finally {
            try {
                // restore statusbar.
                callback.resetProgressBar();
            } catch (GeneralApplicationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Iterate over all the reduced system rows and check if each row is valid against
     * the combinations.
     * @param combinations Container with all combinations.
     */
    private ArrayList createReducedSystem(ArrayList combinations) {
        ArrayList newReducedSystem = new ArrayList();
        ArrayList reducedSystem = stryktipsSystem.getReducedSystem();
        Iterator iterator = reducedSystem.iterator();
        char[] singleRow = new char[NUMBER_OF_GAMES]; // The single row in the combination document
        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            singleRow[i] = stryktipsSystem.getCombination().getSingleRow(i);
        }

        SingleRowCombinationReducerUtility utility = SingleRowCombinationReducerUtility.newInstance();
        int rowNumber = 0;

        // iterate over current reduced system rows.
        while (iterator.hasNext()) {
            try {
                StryktipsGame currentGame = (StryktipsGame) iterator.next();
                char[] currentGameRow = currentGame.getSingleRow();

                boolean validRow = utility.isCurrentReducedRowValid(currentGameRow, combinations, singleRow);

                if (validRow) {
                    newReducedSystem.add(currentGame);
                }

                // update statusbar
                callback.updateProgressBar(++rowNumber);

                // sleep this thread for some milliseconds.
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return newReducedSystem;
    }
}
