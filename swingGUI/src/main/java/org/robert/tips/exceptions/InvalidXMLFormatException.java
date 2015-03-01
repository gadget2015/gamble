package org.robert.tips.exceptions;

/**
 * General failure.
 * @author Robert Siwerz.
 */
public class InvalidXMLFormatException extends GambleException
{
    
    public InvalidXMLFormatException( Exception e, String message )
    {
        super( message );
    }
}
