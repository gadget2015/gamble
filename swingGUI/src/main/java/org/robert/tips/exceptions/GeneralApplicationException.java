package org.robert.tips.exceptions;

/**
 * General failure.
 * @author Robert Siwerz.
 */
public class GeneralApplicationException extends GambleException {

    public GeneralApplicationException(String message) {
        super(message);
    }

    public GeneralApplicationException(String message, Throwable e) {
        super(message);
        System.err.println("Major error: " + e.getMessage());
        e.printStackTrace();
    }
}
