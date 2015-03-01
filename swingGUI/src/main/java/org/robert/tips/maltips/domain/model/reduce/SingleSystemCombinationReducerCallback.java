package org.robert.tips.maltips.domain.model.reduce;

import org.robert.tips.exceptions.GeneralApplicationException;

/**
 * Callback interface used when reducing a system with a single system.
 * @author Robert
 */
public interface SingleSystemCombinationReducerCallback {

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
     * Do this after reducing is done.
     */
    public void resetProgressBar() throws GeneralApplicationException;
}
