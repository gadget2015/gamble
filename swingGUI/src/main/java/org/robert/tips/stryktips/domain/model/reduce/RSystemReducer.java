package org.robert.tips.stryktips.domain.model.reduce;

import org.robert.tips.stryktips.domain.model.reduce.RSystem;
import java.io.File;
import java.io.FileInputStream;
import org.robert.tips.stryktips.StryktipsDocument;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.types.RSystemType;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsErrorMessages;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.types.GambleApplicationConstants;
import org.robert.tips.types.ReducerIF;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.PropertyResourceBundle;
import org.robert.tips.stryktips.domain.model.StryktipsSystem;
import org.robert.tips.stryktips.exceptions.GameNotSetException;

/**
 * This class is used to create a R-system with 
 * the game set.This is a utility class.
 * @author Robert Siwerz.
 */
public class RSystemReducer implements StryktipsErrorMessages,
        ErrorMessages,
        StryktipsConstants,
        ReducerIF,
        GambleApplicationConstants {

    private StryktipsSystem stryktipsSystem;

    /**
     * Create an instance of this class.
     * @param stryktipsDocument The stryktips model.
     */
    public RSystemReducer(StryktipsSystem stryktipsSystem) {
        this.stryktipsSystem = stryktipsSystem;
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
        // check
        checkRSystemSet();

        // reduce wiht R-system
        RSystem rSystem = new RSystem();
        String systemType = stryktipsSystem.getRsystem().getSystemType().getSystemType();
        PropertyResourceBundle rsystems = loadRSystems();
        String systemFileName = rsystems.getString(systemType);
        rSystem.getRSystemDefinitionFromResource(RESOURCES_PATH + systemFileName);
        char[][] actorsSystem = stryktipsSystem.getRsystem().getRSystem();

        ArrayList reduced = rSystem.createRSystem(actorsSystem);

        stryktipsSystem.setReducedSystem(reduced);
    }

    /**
     * Check so that the rows in the R-system match the selected system type.
     * @throws GameNotSetException Missing to set a game in R system.     
     */
    private boolean checkRSystemSet() throws GameNotSetException {
        // find out how many banker, half/full game set there are in the actors R-System.
        char[][] rSystem = stryktipsSystem.getRsystem().getRSystem();
        int numberOfBankers = 0;
        int numberOfHalfGameOptions = 0;
        int numberOfFullGameOptions = 0;

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            // check how many game options that are set for a row.
            int numberOfOptions = getNumberOfGameOptions(rSystem, i);

            // update objects attribute.
            if (numberOfOptions == 1) {
                numberOfBankers++;
            } else if (numberOfOptions == 2) {
                numberOfHalfGameOptions++;
            } else if (numberOfOptions == 3) {
                numberOfFullGameOptions++;
            }
        }

        // verify system type against actors selected R-System.
        RSystemType systemType = stryktipsSystem.getRsystem().getSystemType();

        if (numberOfBankers == systemType.getNumberOfBankers()
                && numberOfHalfGameOptions == systemType.getNumberOfHalfSetGames()
                && numberOfFullGameOptions == systemType.getNumberofFullSetGames()) {
            return true;
        } else {
            throw new GameNotSetException("Missing a game for the system to be complete.");
        }
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
     * Load all R system from resource.
     */
    private PropertyResourceBundle loadRSystems() throws GeneralApplicationException {
        URL resourceURL = null;

        try {
            String resource = RESOURCES_PATH + RSYSTEM_RESOURCENAME;

            ClassLoader cl = this.getClass().getClassLoader();
            resourceURL = cl.getResource(resource);
            InputStream is = null;

            try {
                is = resourceURL.openStream();
            } catch (NullPointerException e) {
                // This happens when the application runs from comman-line.
                // There seams not to be any working classloader.
                File systemFileName = new File(resource);
                FileInputStream fis = new FileInputStream(systemFileName);
                is = fis;
            }

            PropertyResourceBundle resourceBoundle = new PropertyResourceBundle(is);

            return resourceBoundle;
        } catch (IOException e) {
            throw new GeneralApplicationException("Resource not found: " + resourceURL + "," + e.getMessage());
        }
    }
}

