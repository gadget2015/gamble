package org.robert.tips.stryktips.domain.model.reduce;

import org.robert.tips.exceptions.GeneralApplicationException;

/**
 * Callback interface used by domain objects when
 * performing reducings.
 * @author Robert
 */
public interface SingleRowCombinationsCallback {

    /**
     * Send information about the progress of the reducing.
     * @param value to set to {0-100}.
     */
    public void updateProgressBar(int value);

    /**
     * Init the progressbar.
     */
    public void initProgressBar() throws GeneralApplicationException;

    /**
     * Reset the progressbar to its normal state.
     */
    public void resetProgressBar() throws GeneralApplicationException;
}
