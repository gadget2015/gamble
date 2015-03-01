package org.robert.tips.maltips.domain.model;

import java.io.File;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.InvalidXMLFormatException;

/**
 * MaltipsSystem repository.
 * @author Robert
 */
public interface MaltipsSystemRepositoryIF {

    /**
     * Find and open the given maltipssystem.
     * @param file to open.
     * @return The maltipssystem.
     * @throws GeneralApplicationException
     * @throws InvalidXMLFormatException
     */
    public MaltipsSystem find(File file) throws GeneralApplicationException, InvalidXMLFormatException;

    /**
     * Save the given maltipssystem.
     * @param maltipsSystem
     * @throws GeneralApplicationException
     */
    public void save(MaltipsSystem maltipsSystem) throws GeneralApplicationException;

    /**
     * Export the maltipssystem to a SvenskaSpel format.
     * @param maltipsSystem to export.
     * @throws GeneralApplicationException
     */
    public void exportReducedSystem(MaltipsSystem maltipsSystem)throws GeneralApplicationException;
}
