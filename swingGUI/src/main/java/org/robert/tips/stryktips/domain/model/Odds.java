package org.robert.tips.stryktips.domain.model;

import org.robert.tips.stryktips.domain.model.reduce.OddsReducer;
import org.robert.tips.stryktips.exceptions.GameNotSetException;
import static org.robert.tips.stryktips.types.StryktipsConstants.*;

/**
 * This entity defines data used when reducing a the system with respect of odds.
 * @author Robert Georen.
 */
public class Odds {

    /**
     * contains the odds system.
     */
    private float[][] oddsSystem = new float[NUMBER_OF_GAMES][NUMBER_OF_GAMEOPTIONS];
    /**
     * referens to the stryktipssystem.
     */
    private StryktipsSystem stryktipsSystem;
    /**
     * Minimum odds for the odds reducing.
     */
    private float minimumOdds;
    /**
     * Maximum odds for the odds reducing.
     */
    private float maximumOdds;

    /** 
     * Creates a new instance of the odds reducer system.
     * @param strykyipsSystem
     */
    public Odds(StryktipsSystem strykyipsSystem) {
        this.stryktipsSystem = strykyipsSystem;
    }

    /**
     * Get the odds for the given position.
     * @param position The position for the odds, e.g { 0 - 38 }.
     * @return float The odds.
     */
    public float getOddsSystem(int position) {
        int rowNumber = (position - position % NUMBER_OF_GAMEOPTIONS) / NUMBER_OF_GAMEOPTIONS;
        int colNumber = position % NUMBER_OF_GAMEOPTIONS;

        return oddsSystem[rowNumber][colNumber];
    }

    /**
     * Set the odds for the given position.
     * @param rowNumber The game number to change.
     * @param value Value to set, e.g the odds.
     */
    public void setOddsSystem(int position, float value) {
        int rowNumber = (position - position % NUMBER_OF_GAMEOPTIONS) / NUMBER_OF_GAMEOPTIONS;
        int colNumber = position % NUMBER_OF_GAMEOPTIONS;

        oddsSystem[rowNumber][colNumber] = value;
    }

    /**
     * Set minimum odds.
     * @param value Minimum odds.
     */
    public void setMinimumOdds(float value) {
        minimumOdds = value;
    }

    /**
     * Get minimum odds.
     * @return float Minimum odds value.
     */
    public float getMinimumOdds() {
        return minimumOdds;
    }

    /**
     * Set maximum odds.
     * @param value Maximum odds.
     */
    public void setMaximumOdds(float value) {
        maximumOdds = value;
    }

    /**
     * Get maximum odds.
     * @return float Maximum odds value.
     */
    public float getMaximumOdds() {
        return maximumOdds;
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
        OddsReducer reducer = new OddsReducer(stryktipsSystem);
        reducer.reduce();
    }
}
