package org.robert.tips.util;

import org.robert.tips.exceptions.GambleException;

/**
 * Error while retreiving a text message.
 * @author Robert Siwerz.
 */
public class TextMessageNotFoundException extends GambleException
{
    public TextMessageNotFoundException( String message )
    {
        super( message );    
    }
}