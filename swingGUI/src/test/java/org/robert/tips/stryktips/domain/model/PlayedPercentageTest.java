package org.robert.tips.stryktips.domain.model;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.NoReducedRowsException;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.stryktips.exceptions.GameNotSetException;
import org.robert.tips.stryktips.types.StryktipsGame;

import static org.robert.tips.stryktips.types.StryktipsConstants.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PlayedPercentageTest {

    @Test
    public void shouldBeOneRowLeftAfterReduced() throws GameNotSetException, NoReducedRowsException, GeneralApplicationException, ReducingParametersNotSetException {
        // Given
        StryktipsSystem stryktipsSystem = new StryktipsSystem();
        PlayedPercentage played = initTestdata(stryktipsSystem);
        played.revenue = 2800000;
        played.minimumNumberOfPeopleWithFullPot = 60000;
        played.maxiumumNumberOfPeopleWithFullPot = 70000;

        // When
        played.reduce();
        int rows = stryktipsSystem.getReducedSystem().size();

        // Then
        Assert.assertEquals("Should be one row after reduced.", 1, rows);
    }

    @Test
    public void shouldBeThreeRowsLeftAfterReduced() throws GameNotSetException, NoReducedRowsException, GeneralApplicationException, ReducingParametersNotSetException {
        // Given
        StryktipsSystem stryktipsSystem = new StryktipsSystem();
        PlayedPercentage played = initTestdata(stryktipsSystem);
        played.revenue = 2800000;
        played.minimumNumberOfPeopleWithFullPot = 85000;
        played.maxiumumNumberOfPeopleWithFullPot = 100000;

        // When
        played.reduce();
        int rows = stryktipsSystem.getReducedSystem().size();

        // Then
        Assert.assertEquals("Should be two row after reduced.", 3, rows);
    }

    @Test
    public void shouldUseMaxValueWhenReducing() throws GameNotSetException, NoReducedRowsException, GeneralApplicationException, ReducingParametersNotSetException {
        // Given
        StryktipsSystem stryktipsSystem = new StryktipsSystem();
        PlayedPercentage played = initTestdata(stryktipsSystem);
        played.revenue = 2800000;
        played.minimumNumberOfPeopleWithFullPot = 350000;
        played.maxiumumNumberOfPeopleWithFullPot = 360000;

        // When
        played.reduce();
        int rows = stryktipsSystem.getReducedSystem().size();

        // Then
        Assert.assertEquals("Should be one row after reduced.", 1, rows);
    }

    @Test
    public void shouldUseToLowRangeWhenReducing() throws GameNotSetException, NoReducedRowsException, GeneralApplicationException, ReducingParametersNotSetException {
        // Given
        StryktipsSystem stryktipsSystem = new StryktipsSystem();
        PlayedPercentage played = initTestdata(stryktipsSystem);
        played.revenue = 2800000;
        played.minimumNumberOfPeopleWithFullPot = 20000;
        played.maxiumumNumberOfPeopleWithFullPot = 40000;

        // When
        played.reduce();
        int rows = stryktipsSystem.getReducedSystem().size();

        // Then
        Assert.assertEquals("Should be NO row after reduced.", 0, rows);
    }

    @Test
    public void shouldUseToHighRangeWhenReducing() throws GameNotSetException, NoReducedRowsException, GeneralApplicationException, ReducingParametersNotSetException {
        // Given
        StryktipsSystem stryktipsSystem = new StryktipsSystem();
        PlayedPercentage played = initTestdata(stryktipsSystem);
        played.revenue = 2800000;
        played.minimumNumberOfPeopleWithFullPot = 400000;
        played.maxiumumNumberOfPeopleWithFullPot = 500000;

        // When
        played.reduce();
        int rows = stryktipsSystem.getReducedSystem().size();

        // Then
        Assert.assertEquals("Should be NO row after reduced.", 0, rows);
    }

    @Test
    public void shouldMissNumberOfPeopleRange() throws GameNotSetException, NoReducedRowsException, GeneralApplicationException, ReducingParametersNotSetException {
        // Given
        StryktipsSystem stryktipsSystem = new StryktipsSystem();
        PlayedPercentage played = initTestdata(stryktipsSystem);
        played.revenue = 2800000;
        played.minimumNumberOfPeopleWithFullPot = -12;
        played.maxiumumNumberOfPeopleWithFullPot = 0;

        // When
        try {
            played.reduce();
            // Then
            Assert.fail("Should miss a parameter.");
        }
        catch(ReducingParametersNotSetException e){

        }
    }

    private PlayedPercentage initTestdata(StryktipsSystem stryktipsSystem) throws GameNotSetException {
        setMathematicalsystemData(2,0, stryktipsSystem);
        stryktipsSystem.getMathimatical().reduce();
        //printReducedRows(stryktipsSystem.getReducedSystem());

        PlayedPercentage played = stryktipsSystem.getPlayed();
        played.setPercentage(0, 0.2f);
        played.setPercentage(1, 0.3f);
        played.setPercentage(2, 0.5f);
        played.setPercentage(3, 0.2f);
        played.setPercentage(4, 0.3f);
        played.setPercentage(5, 0.5f);
        for (int i= 6; i < 39; i++) {
            played.setPercentage(i, 1); // Fill rest with ones for easier assertions.
        }

        // In resulting system that consists of 4 rows, these are the probability (P).
        // row #1 P = 0.2*0.2 = 0.04
        // row #2 P = 0.2*0.3 = 0.06
        // row #3 P = 0.3*0.2 = 0.06
        // row #4 P = 0.3*0.3 = 0.09

        played.koefficientMin = 0.5f;
        played.koefficientMax = 1.5f;
        return played;
    }

    private void printReducedRows(ArrayList rows) {
        Iterator iterator = rows.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            StryktipsGame game = (StryktipsGame) iterator.next();
            char[] singleRow = game.getSingleRow();

            System.out.println("Row #" + i++ + ": " + game.toString()) ;
        }
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
