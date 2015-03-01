package org.robert.tips.stryktips.domain.model.reduce;

import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.types.StryktipsGame;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.types.ReducerIF;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import org.robert.tips.stryktips.domain.model.Mathimatical;
import org.robert.tips.stryktips.domain.model.StryktipsSystem;
import org.robert.tips.stryktips.exceptions.GameNotSetException;
import org.robert.tips.stryktips.types.StryktipsGameComparator;

/**
 * This class is used to create a extended system with 
 * the game set. 
 * @author Robert Siwerz.
 */
public class ExtendedSystemReducer implements StryktipsErrorMessages,
        ErrorMessages,
        StryktipsConstants,
        ReducerIF {

    private StryktipsSystem stryktipsSystem;

    /**
     * Create an instance of this class.
     * @param stryktipsSystem The stryktips model.
     */
    public ExtendedSystemReducer(StryktipsSystem stryktipsSystem) {
        this.stryktipsSystem = stryktipsSystem;
    }

    /**
     * Create a mathimatical system from the Mathimatical document model.
     * <pre>
     * Flow:
     * 1. Check so that any row are set in the extended system.
     * 3. Create the extended system.
     * </pre>
     * @throws GameNotSetException Missing game in extended system.
     */
    public void reduce() throws GameNotSetException {
        // check
        checkExtendedSystemSet();
        ArrayList extendedSystem = createExtendedSystem();

        // Set the extended system rows in the document
        stryktipsSystem.setReducedSystem(extendedSystem);
    }

    /**
     * Check so that any row are set in the extended system.
     * @throws GameNotSetException No row set in extended system.     
     */
    private boolean checkExtendedSystemSet() throws GameNotSetException {
        // Check so that there are game options set for any row.
        boolean valid = false;

        for (int i = 0; i < NUMBER_OF_GAMES; i++) // iterates over all rows.
        {
            char one = stryktipsSystem.getExtended().getExtendedRow(i * NUMBER_OF_GAMEOPTIONS);
            char tie = stryktipsSystem.getExtended().getExtendedRow(i * NUMBER_OF_GAMEOPTIONS + 1);
            char two = stryktipsSystem.getExtended().getExtendedRow(i * NUMBER_OF_GAMEOPTIONS + 2);

            if (one != 0 || tie != 0 || two != 0) {
                // a game is set.
                valid = true;
                break;
            }
        }

        if (!valid) {
            throw new GameNotSetException("No game set in extended system");
        } else {
            return true;
        }
    }

    /**
     * Get extended system from document.
     * @return char[][] The extended system
     */
    private char[][] getExtendedSystem() {
        char[][] extendedSystem = new char[NUMBER_OF_GAMES][NUMBER_OF_GAMEOPTIONS];

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            char one = stryktipsSystem.getExtended().getExtendedRow(i * NUMBER_OF_GAMEOPTIONS);
            char tie = stryktipsSystem.getExtended().getExtendedRow(i * NUMBER_OF_GAMEOPTIONS + 1);
            char two = stryktipsSystem.getExtended().getExtendedRow(i * NUMBER_OF_GAMEOPTIONS + 2);

            extendedSystem[i][ 0] = one;
            extendedSystem[i][ 1] = tie;
            extendedSystem[i][ 2] = two;
        }

        return extendedSystem;
    }

    /**
     * Create extended system. Iterates over all rows in the
     * reduced system and add the extended system to each row.
     */
    private ArrayList createExtendedSystem() {
        TreeSet extendedSystemRows = new TreeSet(new StryktipsGameComparator());
        Iterator iterator = stryktipsSystem.getReducedSystem().iterator();

        // Iterate over all reduced system rows
        while (iterator.hasNext()) {
            StryktipsGame game = (StryktipsGame) iterator.next();

            // first create a mathimatical system for the current
            // reduced row.
            char[] row = game.getSingleRow();
            char[][] mathimaticalSystem = getExtendedSystem();

            for (int i = 0; i < NUMBER_OF_GAMES; i++) {
                mathimaticalSystem[i][ 0] = (row[i] == GAMEVALUE_ONE) ? row[i] : mathimaticalSystem[i][ 0];
                mathimaticalSystem[i][ 1] = (row[i] == GAMEVALUE_TIE) ? row[i] : mathimaticalSystem[i][ 1];
                mathimaticalSystem[i][ 2] = (row[i] == GAMEVALUE_TWO) ? row[i] : mathimaticalSystem[i][ 2];
            }

            // create the mathimatical system rows.
            Mathimatical mSystem = new Mathimatical();
            mSystem.setMathimaticalSystem(mathimaticalSystem);
            ArrayList rows = mSystem.createsSingleRowsFromMathimaticalSystem();

            Iterator mIterator = rows.iterator();

            while (mIterator.hasNext()) {
                char[] mRow = (char[]) mIterator.next();
                StryktipsGame mGame = new StryktipsGame(mRow);
                extendedSystemRows.add(mGame);
            }
        }

        // fix up a nice return value
        ArrayList ret = new ArrayList(extendedSystemRows.size());
        iterator = extendedSystemRows.iterator();

        while (iterator.hasNext()) {
            ret.add(iterator.next());
        }

        return ret;
    }
}

