package org.robert.tips.exceptions;

import org.robert.tips.exceptions.GambleException;

/**
 * The game is already set.
 * @author Robert Siwerz.
 */
public class GameAlreadySetException extends GambleException
{
    
    public GameAlreadySetException( String message )
    {
        super( message );
    }
}
