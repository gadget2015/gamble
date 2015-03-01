package org.robert.tips.types;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.NoReducedRowsException;
import org.robert.tips.exceptions.ReducingParametersNotSetException;
import org.robert.tips.stryktips.exceptions.GameNotSetException;

/**
 * Defines the interfac for all Reducer classes.
 * @author Robert Siwerz.
 */
public interface ReducerIF
{
    /**
     * Reduce the system.
     */
    public void reduce() throws GeneralApplicationException, GameNotSetException, NoReducedRowsException, ReducingParametersNotSetException;    
}