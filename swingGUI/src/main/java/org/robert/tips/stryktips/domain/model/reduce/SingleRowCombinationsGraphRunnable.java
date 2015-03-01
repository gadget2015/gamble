package org.robert.tips.stryktips.domain.model.reduce;

import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsGame;
import org.robert.tips.stryktips.ui.desktop.dialogs.combinationgraph.CombinationGraphDocument;
import java.util.ArrayList;
import java.util.Iterator;
import org.robert.tips.stryktips.domain.model.StryktipsSystem;

/**
 * This runnable object is used when creating the combination graph.
 * Creates graph data, e.g int[ 0 - NUMBER_OF_GAMES ].
 * @author Robert Siwerz.
 */
public class SingleRowCombinationsGraphRunnable implements Runnable,
        StryktipsConstants {

    private CombinationGraphDocument combinationGraphDocument;
    private SingleRowCombinationsCallback callback;
    private StryktipsSystem stryktipsSystem;

    /**
     * Create a new instance of this runnable class.
     * @param combinationGraphModel Model to put graph data into.   
     */
    public SingleRowCombinationsGraphRunnable(StryktipsSystem stryktipsSystem, CombinationGraphDocument combinationGraphModel, SingleRowCombinationsCallback callback) {
        this.stryktipsSystem = stryktipsSystem;
        this.combinationGraphDocument = combinationGraphModel;
        this.callback = callback;
    }

    public void run() {
        SingleRowCombinationReducerUtility utility = SingleRowCombinationReducerUtility.newInstance();

        try {
            callback.initProgressBar();

            for (int i = 0; i <= NUMBER_OF_GAMES; i++) {
                // This is the main part for this thread to do.
                // creates all possible combinations that can occur between min and max.
                ArrayList combinations = utility.createCombinations(i, i);

                // Iterate over all the reduced system rows and check if each row is valid 
                // againts the combinations.
                ArrayList reducedSystemRows = createReducedSystem(combinations);
                combinationGraphDocument.setCombinationGraphValue(reducedSystemRows.size(), i);

                // update statusbar
                callback.updateProgressBar(i);
            }
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
    private ArrayList createReducedSystem(ArrayList combinations) {
        ArrayList newReducedSystem = new ArrayList();   // return reduced system
        ArrayList reducedSystem = (ArrayList) stryktipsSystem.getReducedSystem().clone(); // clone the reduced system
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

                // sleep this thread for some milliseconds.
                if (++rowNumber % 5 == 0) {
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return newReducedSystem;
    }
}
