package org.robert.tips.stryktips.exceptions;

import org.robert.tips.exceptions.GambleException;

/**
 * The game is already set.
 * @author Robert Siwerz.
 */
public class GameNotSetException extends GambleException
{
    
    public GameNotSetException( String message )
    {
        super( message );
    }
}
