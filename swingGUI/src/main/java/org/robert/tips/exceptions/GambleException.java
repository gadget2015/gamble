package org.robert.tips.exceptions;

/**
 * Superclass for all exceptions thrown by this application.
 * @author Robert Siwerz.
 */
public abstract class GambleException extends Exception
{
    public GambleException( String message )
    {
        super( message );
        printStackTrace();  // ToDo
    }
}