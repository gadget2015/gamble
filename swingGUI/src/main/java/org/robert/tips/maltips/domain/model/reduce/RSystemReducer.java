package org.robert.tips.maltips.domain.model.reduce;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.maltips.domain.model.MaltipsSystem;
import org.robert.tips.types.ErrorMessages;
import org.robert.tips.types.GambleApplicationConstants;
import org.robert.tips.types.ReducerIF;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.maltips.types.MaltipsErrorMessages;
import org.robert.tips.maltips.types.RSystemType;
import org.robert.tips.maltips.types.RSystem;
import java.util.ArrayList;
import org.robert.tips.exceptions.ReducingParametersNotSetException;

/**
 * This class is used to create a R-system with 
 * the game set.This is a utility class.
 * @author Robert Siwerz.
 */
public class RSystemReducer implements MaltipsErrorMessages,
        ErrorMessages,
        MaltipsConstants,
        ReducerIF,
        GambleApplicationConstants {

    private MaltipsSystem maltipsSystem;

    /**
     * Create an instance of this class.
     * @param maltipsSystem
     */
    public RSystemReducer(MaltipsSystem maltipsSystem) {
        this.maltipsSystem = maltipsSystem;
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
    public void reduce() throws GeneralApplicationException, ReducingParametersNotSetException {
        // check
        if (!checkRSystemSet()) {
            return;
            // reduce wiht R-system
        }
        RSystemType systemType =maltipsSystem.getRsystem().getRSystemType();
        RSystem rSystem = new RSystem(systemType);

        int[] actorsSystem = maltipsSystem.getRsystem().getSelectedRows();

        ArrayList reduced = rSystem.createRSystem(actorsSystem);

        maltipsSystem.setReducedSystem(reduced);
    }

    /**
     * Check so that the rows in the R-system match the selected system type.
     * @throws GeneralApplicationException Major error.     
     */
    private boolean checkRSystemSet() throws GeneralApplicationException, ReducingParametersNotSetException {
        RSystemType systemType = maltipsSystem.getRsystem().getRSystemType();

        if (systemType == null) {
            throw new ReducingParametersNotSetException(INVALID_RSYSTEM_SET);
        }

        int numberOfGames = systemType.getNumberOfGamesInSystem();
        int[] selectedRSystemGames = maltipsSystem.getRsystem().getSelectedRows();

        if (numberOfGames != selectedRSystemGames.length) {
            throw new ReducingParametersNotSetException(INVALID_RSYSTEM_SET + " " + numberOfGames);
        } else {
            return true;
        }
    }
}

