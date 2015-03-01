package org.robert.tips.maltips.domain.model.reduce;

import org.robert.tips.maltips.domain.model.MaltipsSystem;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.maltips.types.MaltipsGame;
import org.robert.tips.maltips.ui.dialogs.viewcombinationgraph.CombinationGraphDocument;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This runnable object is used when create the graph for the combinations.
 * @author Robert Siwerz.
 */
class SingleSystemCombinationsGraphRunnable implements Runnable,
        MaltipsConstants {

    private MaltipsSystem maltipsSystem;
    private CombinationGraphDocument combinationGraphDocument;
    private SingleSystemCombinationReducerCallback callback;

    /**
     * Create a new instance of this runnable class.
     */
    SingleSystemCombinationsGraphRunnable(MaltipsSystem maltipsSystem, SingleSystemCombinationReducerCallback callback, CombinationGraphDocument combinationGraphModel) {
        this.maltipsSystem = maltipsSystem;
        this.callback = callback;
        this.combinationGraphDocument = combinationGraphModel;
    }

    @SuppressWarnings("static-access")
    public void run() {
        // The reducing can take a while, so change status bar.
        SingleSystemCombinationReducerUtility utility = SingleSystemCombinationReducerUtility.newInstance();

        try {
            callback.initProgressBar();
            Thread.currentThread().sleep(1000);

            // This is the main part for this thread to do.
            // creates all possible combinations that can occur between 0 ... 8 rights in the singel system.
            for (int i = 0; i <= NUMBER_GAMES_IN_MALTIPS; i++) {
                int numberOfSelectedGamesInSingleSystem = maltipsSystem.getSingleSystem().getSelectedRows().length;
                ArrayList combinations = utility.createCombinations(numberOfSelectedGamesInSingleSystem, i, i);

                // Iterate over all the reduced system rows and check if each row is valid 
                // againts the combinations.
                ArrayList reducedSystemRows = createReducedSystem(combinations);
                int numberOfRows = reducedSystemRows.size();

                combinationGraphDocument.setCombinationValue(numberOfRows, i);

                Thread.currentThread().sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();    // Do noting.
        } catch (GeneralApplicationException e) {
            e.printStackTrace();    // Do noting.
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
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return newReducedSystem;
    }
}
