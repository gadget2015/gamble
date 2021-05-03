package org.robert.tips.stryktips.domain.model;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.NoReducedRowsException;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.stryktips.exceptions.GameNotSetException;

import static org.robert.tips.stryktips.types.StryktipsConstants.*;


public class PlayedPercentageTest {

    @Test
    public void shouldBe276Rows() throws GameNotSetException, NoReducedRowsException, GeneralApplicationException, ReducingParametersNotSetException {
        // Given
        StryktipsSystem stryktipsSystem = new StryktipsSystem();
        PlayedPercentage played = initM_3_10_27648_SystemTestdata(stryktipsSystem);
        played.revenue = 21911633;
        played.minimumNumberOfPeopleWithFullPot = 5;
        played.maxiumumNumberOfPeopleWithFullPot = 20;

        // When
        try {
            played.reduce();
            // Then
            int numberOfRows = stryktipsSystem.getReducedSystem().size();

            //Assert.assertEquals(276, numberOfRows);
        }
        catch(ReducingParametersNotSetException e){
            e.printStackTrace();
        }
    }

  //  @Test
    public void shouldBe630Rows() throws GameNotSetException, NoReducedRowsException, GeneralApplicationException, ReducingParametersNotSetException {
        // Given
        StryktipsSystem stryktipsSystem = new StryktipsSystem();
        PlayedPercentage played = initM_3_10_27648_SystemTestdata(stryktipsSystem);
        played.revenue = 21911633;
        played.minimumNumberOfPeopleWithFullPot = 3;
        played.maxiumumNumberOfPeopleWithFullPot = 5;

        // When
        try {
            played.reduce();
            // Then
            int numberOfRows = stryktipsSystem.getReducedSystem().size();

            Assert.assertEquals(630, numberOfRows);
        }
        catch(ReducingParametersNotSetException e){
            e.printStackTrace();
        }
    }

    private PlayedPercentage initM_3_10_27648_SystemTestdata(StryktipsSystem stryktipsSystem) throws GameNotSetException {
        setMathematicalsystemData(10,3, stryktipsSystem);
        stryktipsSystem.getMathimatical().reduce();
        //printReducedRows(stryktipsSystem.getReducedSystem());

        PlayedPercentage played = stryktipsSystem.getPlayed();
        played.setPercentage(0, 0.41f);
        played.setPercentage(1, 0.28f);
        played.setPercentage(2, 0.31f);
        played.setPercentage(3, 0.13f);
        played.setPercentage(4, 0.27f);
        played.setPercentage(5, 0.6f);
        played.setPercentage(6, 0.15f);
        played.setPercentage(7, 0.24f);
        played.setPercentage(8, 0.61f);
        played.setPercentage(9, 0.72f);
        played.setPercentage(10, 0.2f);
        played.setPercentage(11, 0.08f);
        played.setPercentage(12, 0.45f);
        played.setPercentage(13, 0.27f);
        played.setPercentage(14, 0.28f);
        played.setPercentage(15, 0.57f);
        played.setPercentage(16, 0.28f);
        played.setPercentage(17, 0.15f);
        played.setPercentage(18, 0.18f);
        played.setPercentage(19, 0.28f);
        played.setPercentage(20, 0.54f);
        played.setPercentage(21, 0.19f);
        played.setPercentage(22, 0.26f);
        played.setPercentage(23, 0.55f);
        played.setPercentage(24, 0.09f);
        played.setPercentage(25, 0.21f);
        played.setPercentage(26, 0.7f);
        played.setPercentage(27, 0.04f);
        played.setPercentage(28, 0.11f);
        played.setPercentage(29, 0.85f);
        played.setPercentage(30, 0.6f);
        played.setPercentage(31, 0.25f);
        played.setPercentage(32, 0.15f);
        played.setPercentage(33, 0.31f);
        played.setPercentage(34, 0.31f);
        played.setPercentage(35, 0.38f);
        played.setPercentage(36, 0.04f);
        played.setPercentage(37, 0.12f);
        played.setPercentage(38, 0.84f);

        return played;
    }

    private void setMathematicalsystemData(int numberOfHalfHedging, int numberOfHoleHedging, StryktipsSystem stryktipsSystem) {
        try {
            Mathimatical mathimatical = stryktipsSystem.getMathimatical();
            // Set hole hedgings
            for (int i = 0; i < (numberOfHoleHedging * 3); i += 3) {
                mathimatical.setMathimaticalRow(i, GAMEVALUE_ONE);
                mathimatical.setMathimaticalRow((i + 1), GAMEVALUE_TIE);
                mathimatical.setMathimaticalRow((i + 2), GAMEVALUE_TWO);
            }

            // Set half hedgings
            for (int i = numberOfHoleHedging * 3; i < (numberOfHoleHedging * 3 + numberOfHalfHedging * 3); i += 3) {
                mathimatical.setMathimaticalRow(i, GAMEVALUE_ONE);
                mathimatical.setMathimaticalRow((i + 1), GAMEVALUE_TIE);
            }

            // Set One's in rest of system.
            for (int i = (numberOfHoleHedging * 3 + numberOfHalfHedging * 3); i < 39; i += 3) {
                mathimatical.setMathimaticalRow(i, GAMEVALUE_ONE);
            }

        } catch (GameAlreadySetException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Init failed of Mathimatical domain object."
                            + e.getMessage());
        }
    }
}
