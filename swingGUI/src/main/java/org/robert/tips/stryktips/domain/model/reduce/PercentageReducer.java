package org.robert.tips.stryktips.domain.model.reduce;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.NoReducedRowsException;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.stryktips.domain.model.StryktipsSystem;
import org.robert.tips.stryktips.exceptions.GameNotSetException;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.stryktips.types.StryktipsGame;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.types.ReducerIF;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Formula: number of equal rows = P * revenue * koefficient;
 *
 * Example:
 *  revenue = 28200278 SEK.
 *  P = 0.0000156708.
 *  number of rows = 266.
 *  koefficient = 0.601918951
 */
public class PercentageReducer implements StryktipsErrorMessages,
        ErrorMessages,
        StryktipsConstants,
        ReducerIF {
    private StryktipsSystem stryktipsSystem;

    /**
     * Create an instance of this reducer.
     */
    public PercentageReducer(StryktipsSystem stryktipsSystem) {
        this.stryktipsSystem = stryktipsSystem;
    }

    @Override
    public void reduce() throws GeneralApplicationException, GameNotSetException, NoReducedRowsException, ReducingParametersNotSetException {
        // Get parameters
        ArrayList currentRows = stryktipsSystem.getReducedSystem();
        int minimumNumberOfPeopleWithFullPot = stryktipsSystem.getPlayed().minimumNumberOfPeopleWithFullPot;
        int maxiumumNumberOfPeopleWithFullPot = stryktipsSystem.getPlayed().maxiumumNumberOfPeopleWithFullPot;
        int revenue = stryktipsSystem.getPlayed().revenue;

        ArrayList newReducedRows = new ArrayList();
        Iterator iterator = currentRows.iterator();

        try (
                PrintWriter writer = new PrintWriter (new FileWriter("c:\\temp\\temp\\percentage_stat.csv"))) {


        while (iterator.hasNext()) {
            int row = 0;
            StryktipsGame game = (StryktipsGame) iterator.next();
            char[] singleRow = game.getSingleRow();

            // Calculate the P for the row.
            double P = 1;

            for (int i = 0; i < NUMBER_OF_GAMES; i++) {
                int pos = 0;
                pos = (singleRow[i] == GAMEVALUE_TIE) ? 1 : pos;
                pos = (singleRow[i] == GAMEVALUE_TWO) ? 2 : pos;

                double rowPercentage = stryktipsSystem.getPlayed().getPercentage(i * NUMBER_OF_GAMEOPTIONS + pos);
                P *= rowPercentage;
            }

            // Execute the formula with min and max for the given koefficient.

            // Debug analys
            String pattern = "#.##############";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            String pString = decimalFormat.format(P);
            //int nCalculated= (int) ((0.798580855d*P + 0.000000165169d) * revenue); vecka 14
            //int nCalculated= (int) ((0.73981155d*P + 0.000000197632d) * revenue);
            //int nCalculated= (int) ((0.536244842d*P + 0.000000285896d) * revenue);
            int nCalculated= (int) ((0.719002656d*P + 0.000000141589d) * revenue);  // vecka 17

            String statistics = pString + ";" + minimumNumberOfPeopleWithFullPot + ";" + maxiumumNumberOfPeopleWithFullPot +";" + nCalculated +";END";


            // Decide if the row should be saved in the new reduced system.
            // 1. Check the low value of number of people that will get full pot.
            // 2. Check the high value of number of people that will get full pot.
            if (nCalculated >= minimumNumberOfPeopleWithFullPot && nCalculated <= maxiumumNumberOfPeopleWithFullPot) {
                // This row is OK, and should be saved/stored/used.
                newReducedRows.add(game);
                statistics += ",OK";
            } else {
                statistics += ",FAIL";
            }

            writer.println(statistics);
        }

        } catch (IOException e) {
            e.printStackTrace();
        }

        stryktipsSystem.setReducedSystem(newReducedRows);
    }
}
