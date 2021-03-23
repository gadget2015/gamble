package org.robert.tips.stryktips.domain.model;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.NoReducedRowsException;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.stryktips.domain.model.reduce.PercentageReducer;
import org.robert.tips.stryktips.exceptions.GameNotSetException;
import org.robert.tips.types.ReducerIF;

import static org.robert.tips.stryktips.types.StryktipsConstants.NUMBER_OF_GAMEOPTIONS;
import static org.robert.tips.stryktips.types.StryktipsConstants.NUMBER_OF_GAMES;

/**
 * This entity defines data used when reducing a the system with respect of played percentage.
 * The played percentage can be retrieved live when logging in at SvenskaSpel.se.
 *
 * @author Robert Georen.
 */
public class PlayedPercentage {
    /**
     * reference to the stryktipssystem.
     */
    private StryktipsSystem stryktipsSystem;

    /**
     * contains the percentage for the system.
     */
    private float[][] percentageSystem = new float[NUMBER_OF_GAMES][NUMBER_OF_GAMEOPTIONS];

    /**
     * Minimum number of people that will get all rights, e.g. all 13 matches correct set.
     */
    public int minimumNumberOfPeopleWithFullPot;

    /**
     * Maximum number of peolple that will get all rights, e.g. all 13 matches correct set.
     */
    public int maxiumumNumberOfPeopleWithFullPot;

    /**
     * Total revenue for the current stryktips round.
     */
    public int revenue;


    public float koefficientMin;
    public float koefficientMax;

    public PlayedPercentage(StryktipsSystem strykyipsSystem) {
        this.stryktipsSystem = strykyipsSystem;
    }

    /**
     * Set the odds for the given position.
     * @param position The position for the value, e.g { 0 - 38 }.
     * @param value Value to set.
     */
    public void setPercentage(int position, float value) {
        int rowNumber = (position - position % NUMBER_OF_GAMEOPTIONS) / NUMBER_OF_GAMEOPTIONS;
        int colNumber = position % NUMBER_OF_GAMEOPTIONS;

        percentageSystem[rowNumber][colNumber] = value;
    }

    /**
     * Set the odds for the given position.
     * @param position The position for the value, e.g { 0 - 38 }.
     */
    public float getPercentage(int position) {
        int rowNumber = (position - position % NUMBER_OF_GAMEOPTIONS) / NUMBER_OF_GAMEOPTIONS;
        int colNumber = position % NUMBER_OF_GAMEOPTIONS;

        return percentageSystem[rowNumber][colNumber];
    }

    /**
     * Iterates over all rows in the reduced rows container and
     * only stores the rows that complies to the parameters (antal personer, omsattning)
     * <pre>
     * Flow:
     * 1. Check so that parameters are set.
     * 2. Iterates over all reduced rows and reduce with percentage reducer.
     * </pre>
     *
     */
    public void reduce() throws GameNotSetException, NoReducedRowsException, GeneralApplicationException, ReducingParametersNotSetException {
        // Check parameters
        if (minimumNumberOfPeopleWithFullPot < 0 ) {
            throw new ReducingParametersNotSetException("Min value must be more than 0.");
        }

        if (minimumNumberOfPeopleWithFullPot > maxiumumNumberOfPeopleWithFullPot) {
            throw new ReducingParametersNotSetException("Min number of people must be less than max number of people");
        }

        if (revenue < 1) {
            throw new ReducingParametersNotSetException("Missing revenue parameter.");
        }

        if (koefficientMin < 0 ) {
            throw new ReducingParametersNotSetException("Missing lower koefficeint.");
        }

        if (koefficientMax < koefficientMin) {
            throw new ReducingParametersNotSetException("The  koefficient Max must be more than Min value.");
        }

        // Reduce
        ReducerIF reducer = new PercentageReducer(stryktipsSystem);
        reducer.reduce();
    }
}
