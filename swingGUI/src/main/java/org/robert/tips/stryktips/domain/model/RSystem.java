package org.robert.tips.stryktips.domain.model;

import org.robert.tips.stryktips.types.RSystemType;
import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.domain.model.reduce.RSystemReducer;
import org.robert.tips.stryktips.exceptions.GameNotSetException;
import static org.robert.tips.stryktips.types.StryktipsConstants.*;

/**
 * Defines the R system entity.
 * @author Robert
 */
public class RSystem {

    /**
     * contains the Rsystem.
     */
    private char[][] rSystem = new char[NUMBER_OF_GAMES][NUMBER_OF_GAMES];
    /**
     * R-system type. 
     */
    private RSystemType systemType;
    /**
     * referens to the main document
     */
    private StryktipsSystem stryktipsSystem;

    /**
     * Creates a new instance of this document.
     * @param stryktipsDocument The stryktips document.
     */
    public RSystem(StryktipsSystem stryktipsSystem) {
        this.stryktipsSystem = stryktipsSystem;
        systemType = new RSystemType("");
    }

    /**
     * Set a row in the R system.
     * @param position integer value between { 0-38 }. 0-2=first row, 3-5=second row.
     * @param character The game character/option.
     * @throws GamelAlreadySetException The game is already set the in the R system.
     */
    public void setRSystemRow(int position, char character) throws GameAlreadySetException {
        // calcualte row and colnumber
        int rowNumber = (position - position % NUMBER_OF_GAMEOPTIONS) / NUMBER_OF_GAMEOPTIONS;
        int colNumber = position % NUMBER_OF_GAMEOPTIONS;

        // get the character in the r-system
        char rRowCharacter = 0;
        rRowCharacter = (colNumber == 0) ? GAMEVALUE_ONE : rRowCharacter;
        rRowCharacter = (colNumber == 1) ? GAMEVALUE_TIE : rRowCharacter;
        rRowCharacter = (colNumber == 2) ? GAMEVALUE_TWO : rRowCharacter;

        // First check so this game option isn't already set.
        if (rSystem[rowNumber][colNumber] == character) {
            throw new GameAlreadySetException("position:" + position + ", character:" + character);
        }

        rSystem[rowNumber][colNumber] = character;
    }

    /**
     * Get the character/option for the given position in the
     * R system.
     * @param position integer value between { 0-38 }. 0-2=first row, 3-5=second row.
     * @return char The character/option.
     */
    public char getRSystemRow(int position) {
        int rowNumber = (position - position % NUMBER_OF_GAMEOPTIONS) / NUMBER_OF_GAMEOPTIONS;
        int colNumber = position % NUMBER_OF_GAMEOPTIONS;
        char returnValue = rSystem[rowNumber][colNumber];

        return returnValue;
    }

    /**
     * Get the R-System.
     */
    public char[][] getRSystem() {
        return rSystem;
    }

    /**
     * Get system type.
     * @return String System type.
     */
    public RSystemType getSystemType() {
        return systemType;
    }

    /**
     * Set system type.
     * @param systemType New system type.
     */
    public void setSystemType(RSystemType systemType) {
        this.systemType = systemType;
    }

    /**
     * Create a R-system from the Rsystem document model.
     * <pre>
     * Flow:
     * 1. Check so that all rows are set in the R-system and matches the selected systemtype.
     * 2. Create the R-system.
     * </pre>
     * @throws GeneralApplicationException Major error.
     */
    public void reduce() throws GeneralApplicationException, GameNotSetException {
        RSystemReducer reducer = new RSystemReducer(stryktipsSystem);
        reducer.reduce();
    }
}
