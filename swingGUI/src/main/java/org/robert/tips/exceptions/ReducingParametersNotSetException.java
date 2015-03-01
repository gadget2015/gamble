package org.robert.tips.exceptions;

/**
 * A parameter that is used by the system is missing.
 * @author Robert
 */
public class ReducingParametersNotSetException extends GambleException
{
    
    public ReducingParametersNotSetException( String message )
    {
        super( message );
    }

}
