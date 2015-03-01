package org.robert.tips.stryktips.domain.model;

import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.stryktips.domain.model.reduce.ExtendedSystemReducer;
import org.robert.tips.stryktips.exceptions.GameNotSetException;
import static org.robert.tips.stryktips.types.StryktipsConstants.*;

/**
 * Contains the extended system data.
 * @author Robert Siwerz.
 */
public class Extended {

    /**
     * contains the extended system.
     */
    private char[][] extendedSystem = new char[NUMBER_OF_GAMES][NUMBER_OF_GAMES];
    /**
     * referens to the stryktips system.
     */
    private StryktipsSystem stryktipsSystem;

    public Extended(StryktipsSystem stryktipsSystem) {
        this.stryktipsSystem = stryktipsSystem;
    }

    /**
     * Set a row in the mathimatical system.
     * @param position integer value between { 0-38 }. 0-2=first row, 3-5=second row.
     * @param character The game character/option.
     * @throws GameAlreadySetException The game have already this game option.
     */
    public void setExtendedRow(int position, char character) throws GameAlreadySetException {
        // calcualte row and colnumber
        int rowNumber = (position - position % NUMBER_OF_GAMEOPTIONS) / NUMBER_OF_GAMEOPTIONS;
        int colNumber = position % NUMBER_OF_GAMEOPTIONS;

        // get the character in the extended system that the column represent
        char extendedRowCharacter = 0;
        extendedRowCharacter = (colNumber == 0) ? GAMEVALUE_ONE : extendedRowCharacter;
        extendedRowCharacter = (colNumber == 1) ? GAMEVALUE_TIE : extendedRowCharacter;
        extendedRowCharacter = (colNumber == 2) ? GAMEVALUE_TWO : extendedRowCharacter;

        // First check so this game option isn't already set.
        if (extendedSystem[rowNumber][colNumber] == character) {
            throw new GameAlreadySetException("position:" + position + ", character:" + character);
        }

        extendedSystem[rowNumber][colNumber] = character;
    }

    /**
     * Get the character/option for the given position in the
     * extended system.
     * @param position integer value between { 0-38 }. 0-2=first row, 3-5=second row.
     * @return char The character/option.
     */
    public char getExtendedRow(int position) {
        int rowNumber = (position - position % NUMBER_OF_GAMEOPTIONS) / NUMBER_OF_GAMEOPTIONS;
        int colNumber = position % NUMBER_OF_GAMEOPTIONS;
        char returnValue = extendedSystem[rowNumber][colNumber];

        return returnValue;
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
        ExtendedSystemReducer reducer = new ExtendedSystemReducer(stryktipsSystem);
        reducer.reduce();

    }
}
