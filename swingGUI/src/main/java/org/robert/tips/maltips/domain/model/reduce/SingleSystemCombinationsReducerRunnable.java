package org.robert.tips.maltips.domain.model.reduce;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.maltips.types.MaltipsGame;
import java.util.ArrayList;
import java.util.Iterator;
import org.robert.tips.maltips.domain.model.MaltipsSystem;

/**
 * This runnable object is used when create the combination reducing system.
 * @author Robert Siwerz.
 */
class SingleSystemCombinationsReducerRunnable implements Runnable,
        MaltipsConstants {

    private MaltipsSystem maltipsSystem;
    private SingleSystemCombinationReducerCallback callback;

    /**
     * Create a new instance of this class.
     * @param maltipsSystem
     * @param callback implementation to use.
     */
    public SingleSystemCombinationsReducerRunnable(MaltipsSystem maltipsSystem, SingleSystemCombinationReducerCallback callback) {
        this.maltipsSystem = maltipsSystem;
        this.callback = callback;
    }

    public void run() {
        SingleSystemCombinationReducerUtility utility = SingleSystemCombinationReducerUtility.newInstance();

        try {
            // The reducing can take a while, so change status bar.
            callback.initProgressBar();

            // This is the main part for this thread to do.
            // creates all possible combinations that can occur between min and max.
            int min = maltipsSystem.getSingleSystem().getMinInSingleRow();
            int max = maltipsSystem.getSingleSystem().getMaxInSingleRow();
            int numberOfSelectedGamesInSingleSystem = maltipsSystem.getSingleSystem().getSelectedRows().length;
            ArrayList combinations = utility.createCombinations(numberOfSelectedGamesInSingleSystem, min, max);

            // Iterate over all the reduced system rows and check if each row is valid 
            // againts the combinations.
            ArrayList reducedSystemRows = createReducedSystem(combinations);

            maltipsSystem.setReducedSystem(reducedSystemRows);
        } catch (GeneralApplicationException e) {
            // Do noting.
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
    @SuppressWarnings("static-access")
    private ArrayList createReducedSystem(ArrayList combinations) {
        ArrayList newReducedSystem = new ArrayList();   // return reduced system
        ArrayList reducedSystem = (ArrayList) maltipsSystem.getReducedSystem().clone(); // clone the reduced system
        Iterator iterator = reducedSystem.iterator();

        // The single system in the singlesystem document
        int[] singleSystem = maltipsSystem.getSingleSystem().getSelectedRows();

        SingleSystemCombinationReducerUtility utility = SingleSystemCombinationReducerUtility.newInstance();
        int rowNumber = 0;

        // iterate over current reduced system rows.
        while (iterator.hasNext()) {
            try {
                MaltipsGame currentGame = (MaltipsGame) iterator.next();
                int[] currentGameRow = currentGame.getMaltipsRow();

                boolean validRow = utility.isCurrentReducedRowValid(currentGameRow, combinations, singleSystem);

                if (validRow) {
                    newReducedSystem.add(currentGame);
                }

                // update statusbar
                callback.updateProgressBar(++rowNumber);

                // sleep this thread for some milliseconds.
                if (rowNumber % 5 == 0) {
                    Thread.currentThread().sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return newReducedSystem;
    }
}
