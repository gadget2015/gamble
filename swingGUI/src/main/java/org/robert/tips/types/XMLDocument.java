package org.robert.tips.types;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.InvalidXMLFormatException;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

/**
 * Defines interface for document that can be represented as a XML document.
 * @author Robert Siwerz.
 */
public interface XMLDocument
{
    /**
     * Get a DOM Element object representing the document.
     * @param document The DOM document to use when creating Elements.
     * @return Element XML DOM Element object.
     */
    public Element getXMLElement( Document document ) throws GeneralApplicationException;    
    
    /**
     * Set a DOM document. This implies populating the object that implements this
     * interface with data from the given DOM document.
     * @param document The DOM document to populate with.
     */
    public void setDOMDocument( Document document ) throws InvalidXMLFormatException;
}
