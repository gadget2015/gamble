package org.robert.tips.stryktips.domain.model.reduce;

import java.io.File;
import java.io.FileInputStream;
import java.io.LineNumberReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsGame;
import org.robert.tips.stryktips.types.StryktipsConstants;

/**
 * Class that defines a R-system. R-system are a defined reduced system with some
 * reducing rows and guaranted number of rights.
 * @author Robert Siwerz.
 */
public class RSystem implements StryktipsConstants {

    /**
     * Number of bankers in this r-system.
     */
    private int numberOfBankers;
    /**
     * Number of 2/3 set games, e.g 'halvgarderingar'.
     */
    private int numberOfHalfGameOptions;
    /**
     * Number of hole set games, e.g 'helgarderingar'.
     */
    private int numberOfFullGameOptions;
    /**
     * The system definition that this r-system consists of, the container contains StryktipsGame objects.
     */
    private ArrayList rSystemDefinition;
    /**
     * The system frame, e.g this is the system that the actor sees, unreduced system.
     */
    private char[][] systemFrame = new char[NUMBER_OF_GAMES][NUMBER_OF_GAMEOPTIONS];

    /**
     * Create a new instance of this class.
     */
    public RSystem() {
        rSystemDefinition = new ArrayList();
    }

    /**
     * Get a R-system from disk. This method reads the given file from disk and then verify it.
     * @param fileName The filename as text.
     */
    public void getRSystemDefinitionFromResource(String fileName) throws GeneralApplicationException {
        URL resourceURL = null;

        try {
            ClassLoader cl = this.getClass().getClassLoader();
            InputStream is = null;
            
            try {
                resourceURL = cl.getResource(fileName);
                is = resourceURL.openStream();
            } catch (NullPointerException e) {
                // This happens when the application runs from comman-line.
                // There seams not to be any working classloader.
                File systemFileName = new File(fileName);
                FileInputStream fis = new FileInputStream(systemFileName);
                is = fis;
            }

            InputStreamReader isr = new InputStreamReader(is);
            LineNumberReader lnr = new LineNumberReader(isr);

            // read all line in file
            String currentLine = null;

            while ((currentLine = lnr.readLine()) != null) {
                StryktipsGame game = new StryktipsGame(currentLine.toCharArray());
                getRSystemDefinition().add(game);
            }

            lnr.close();

            verifySystemDefinition();
        } catch (IOException e) {
            throw new GeneralApplicationException("Resource not found: " + resourceURL + "," + e.getMessage());
        }
    }

    /**
     * Verify the rsystem definitions, e.g check what kind of rsystem this is and set this objects
     * attributes.
     */
    private void verifySystemDefinition() {
        // First recreate the original unreduced system, where the rsystem belongs to.
        Iterator iterator = getRSystemDefinition().iterator();

        while (iterator.hasNext()) {
            StryktipsGame game = (StryktipsGame) iterator.next();
            char[] singleRow = game.getSingleRow();

            for (int i = 0; i < NUMBER_OF_GAMES; i++) {
                char gameOption = singleRow[i];

                if (gameOption == GAMEVALUE_ONE) {
                    systemFrame[i][ 0] = GAMEVALUE_ONE;
                } else if (gameOption == GAMEVALUE_TIE) {
                    systemFrame[i][ 1] = GAMEVALUE_TIE;
                } else if (gameOption == GAMEVALUE_TWO) {
                    systemFrame[i][ 2] = GAMEVALUE_TWO;
                }
            }
        }

        // find out how many banker, half/full game set there are in the system frame.

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            // check how many game options that are set for a row.
            int numberOfOptions = getNumberOfGameOptions(systemFrame, i);

            // update objects attribute.
            if (numberOfOptions == 1) {
                numberOfBankers++;
            } else if (numberOfOptions == 2) {
                numberOfHalfGameOptions++;
            } else if (numberOfOptions == 3) {
                numberOfFullGameOptions++;
            }
        }
    }

    /**
     * Reduce the given original system with this R-system.
     * @param originalSystem The original system is a char[][] containing '1', 'X' and '2'.
     * @return StryktipsGame The Rsystem.
     */
    public ArrayList createRSystem(char[][] originalSystem) {
        ArrayList rSystem = new ArrayList();

        // iterate over all R-system rows and map them against the original system.
        Iterator iterator = getRSystemDefinition().iterator();

        while (iterator.hasNext()) {
            StryktipsGame game = (StryktipsGame) iterator.next();
            char[] definitionRow = game.getSingleRow();
            char[] reducedRow = new char[NUMBER_OF_GAMES];

            // replace each game option in the definition row.
            for (int i = 0; i < NUMBER_OF_GAMES; i++) {
                int numberOfGameOptions = getNumberOfGameOptions(systemFrame, i);
                int orderInSystem = getOrderInSystem(systemFrame, i);

                // get row number in original system
                int originalSystemRowNumber = getRowNumberInOriginalSystem(numberOfGameOptions, orderInSystem, originalSystem);
                char definitionOption = definitionRow[i];

                // Do the mapping
                if (definitionOption == GAMEVALUE_ONE) {
                    reducedRow[originalSystemRowNumber] = getRowValueInSystem(originalSystem, originalSystemRowNumber, 1);
                } else if (definitionOption == GAMEVALUE_TIE) {
                    reducedRow[originalSystemRowNumber] = getRowValueInSystem(originalSystem, originalSystemRowNumber, 2);
                } else if (definitionOption == GAMEVALUE_TWO) {
                    reducedRow[originalSystemRowNumber] = getRowValueInSystem(originalSystem, originalSystemRowNumber, 3);
                }
            }

            // add reduced system to result
            StryktipsGame reducedGame = new StryktipsGame(reducedRow);
            rSystem.add(reducedGame);
        }

        return rSystem;
    }

    /**
     * Get the numbered order from within all rows that have the same number of game options 
     * that the given row number has. This is that if there are 6 games that have set two
     * game options 'halvgarderingar' and the given rownumber is 10. Then the method looks
     * in the given system and get the number in which the 10:nth row has within the 6 games.
     * This meand that the order can be 1-6 in this example.
     * @param system The system to look in.     
     * @param rowNumber Rownumber in the system frame.
     * @return int Order number.     
     */
    private int getOrderInSystem(char[][] system, int rowNumber) {
        int numberOfOptions = getNumberOfGameOptions(system, rowNumber);
        int orderNumber = 0;

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            int currentRow = getNumberOfGameOptions(system, i);

            if (currentRow == numberOfOptions) {
                orderNumber++;

                if (i == rowNumber) {
                    // The order number is calculated
                    break;
                }
            }
        }

        return orderNumber;
    }

    /**
     * Get number of game options for the given system and row number.
     * @param system The system to look in.
     * @param rowNumber The rownumber to calculate number of game options for.
     * @return int Number of game options, { 1,2 .. NUMBER_OF_GAMEOPTIONS }.
     */
    private int getNumberOfGameOptions(char[][] system, int rowNumber) {
        // check how many game options that are set for a row.
        int numberOfOptions = 0;

        for (int i = 0; i < NUMBER_OF_GAMEOPTIONS; i++) {
            if (system[rowNumber][i] != 0) {
                // a game value is set.
                numberOfOptions++;
            }
        }

        return numberOfOptions;
    }

    /**
     * Get row number in original system that meets the given criterias. There shall only
     * be one found row number for the given search criterias.
     * @param numberOfGameOptions Number of game options.
     * @param orderInSystem The order from the rows that have the name
     * @param originalSystem The orginal system.
     * @return int The rownumber in the original system.
     */
    private int getRowNumberInOriginalSystem(int numberOfGameOptions, int orderInSystem, char[][] originalSystem) {
        int rowNumber = 0;

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            int currentRowOptions = getNumberOfGameOptions(originalSystem, i);

            if (currentRowOptions == numberOfGameOptions) {
                // Number of game options is correct.
                int currentRowOrder = getOrderInSystem(originalSystem, i);

                if (orderInSystem == currentRowOrder) {
                    // order is right!
                    rowNumber = i;   // this is the rownumber in the original system.

                    break;
                }
            }
        }

        return rowNumber;
    }

    /**
     * Get the value for the given row number.
     * @param system The system to look in.
     * @param rowNumber Row number to get value for.
     * @param number This is a value defining the column to get the value for. 
     *               1 = first column where an option is set, 2= secound column ...
     * @return char The value.
     */
    private char getRowValueInSystem(char[][] system, int rowNumber, int number) {
        char value = 0;
        int numberOfValues = 0;

        for (int i = 0; i < NUMBER_OF_GAMEOPTIONS; i++) {
            char tmpValue = system[rowNumber][i];

            if (tmpValue != 0) {
                numberOfValues++;
            }

            if (number == numberOfValues) {
                value = tmpValue;
                break;
            }
        }

        return value;
    }

    public ArrayList getRSystemDefinition() {
        return rSystemDefinition;
    }
}
 