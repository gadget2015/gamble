package org.robert.tips.stryktips.domain.model;

import java.io.File;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.InvalidXMLFormatException;

/**
 * Repository for storing/finding stryktips systems.
 * @author Robert
 */
public interface StryktipsSystemRepositoryIF {

    /**
     * Find the given file and return a newly created StryktipsSystem object.
     * @param file
     * @return New created object.
     * @throws tips.exceptions.GeneralApplicationException
     */
    public StryktipsSystem find(File file) throws GeneralApplicationException, InvalidXMLFormatException;

    /**
     * Save the stryktips system domain object.
     * @param stryktipsSystem
     */
    public void save(StryktipsSystem stryktipsSystem) throws GeneralApplicationException;
}
