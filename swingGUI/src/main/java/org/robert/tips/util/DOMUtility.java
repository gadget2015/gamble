package org.robert.tips.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.robert.tips.exceptions.InvalidXMLFormatException;
import java.util.ArrayList;

/**
 * Utility class used when operating on XML DOM documents.
 * @author Robert Siwerz.
 */
public class DOMUtility
{
    /**
     * Find the named subnode in a node's sublist.
     * <li>Ignores comments and processing instructions.
     * <li>Ignores TEXT nodes (likely to exist and contain ignorable whitespace,
     *     if not validating.
     * <li>Ignores CDATA nodes and EntityRef nodes.
     * <li>Examines element nodes to find one with the specified name.
     * </ul>
     * @param name  the tag name for the element to find
     * @param node  the element node to start searching from
     * @return the Node found
     */
    public Node findSubNode(String name, Node node) throws InvalidXMLFormatException
    {
        if (node.getNodeType() != Node.ELEMENT_NODE) 
        {
            throw new InvalidXMLFormatException( null, "Error: Search node not of element type:" + node.getNodeType() );
        }

        if (! node.hasChildNodes() ) return null;

        NodeList list = node.getChildNodes();
        
        for (int i=0; i < list.getLength(); i++) 
        {
            Node subnode = list.item(i);
            
            if (subnode.getNodeType() == Node.ELEMENT_NODE) 
            {                
                String currentNodeName = subnode.getNodeName();
                
                if ( currentNodeName.equals( name ) )
                {
                    return subnode;
                }
            }
        }
        
        return null;
    }

    /**
    * Return the text that a node contains. This routine:<ul>
    * <li>Ignores comments and processing instructions.
    * <li>Concatenates TEXT nodes, CDATA nodes, and the results of
    *     recursively processing EntityRef nodes.
    * <li>Ignores any element nodes in the sublist.
    *     (Other possible options are to recurse into element sublists or throw an exception.)
    * </ul>
    * @param    node  a  DOM node
    * @return   a String representing its contents
    */
    public String getText(Node node) 
    {
        StringBuffer result = new StringBuffer();
        if (! node.hasChildNodes()) return "";

        NodeList list = node.getChildNodes();
        
        for (int i=0; i < list.getLength(); i++) 
        {
            Node subnode = list.item(i);
            if (subnode.getNodeType() == Node.TEXT_NODE) 
            {
                result.append(subnode.getNodeValue());
            }
            else if (subnode.getNodeType() == Node.CDATA_SECTION_NODE) 
            {
                result.append(subnode.getNodeValue());
            }
            else if (subnode.getNodeType() == Node.ENTITY_REFERENCE_NODE) 
            {
                // Recurse into the subtree for text
                // (and ignore comments)
                result.append(getText(subnode));
            }
        }
        
        return result.toString();
    }
    
    /**
     * Find the named subnode list in a node's sublist.
     * <li>Ignores comments and processing instructions.
     * <li>Ignores TEXT nodes (likely to exist and contain ignorable whitespace,
     *     if not validating.
     * <li>Ignores CDATA nodes and EntityRef nodes.
     * <li>Examines element nodes to find one with the specified name.
     * </ul>
     * @param name  the tag name for the element to find
     * @param node  the element node to start searching from
     * @return ArrayList with found nodes in.
     */
    public ArrayList findSubNodeList(String name, Node node) throws InvalidXMLFormatException
    {
        ArrayList returnList = new ArrayList();
        
        if (node.getNodeType() != Node.ELEMENT_NODE) 
        {
            throw new InvalidXMLFormatException( null, "Error: Search node not of element type:" + node.getNodeType() );
        }

        if (! node.hasChildNodes() ) return null;

        NodeList list = node.getChildNodes();
        
        for (int i=0; i < list.getLength(); i++) 
        {
            Node subnode = list.item(i);
            
            if (subnode.getNodeType() == Node.ELEMENT_NODE) 
            {                
                String currentNodeName = subnode.getNodeName();
                
                if ( currentNodeName.equals( name ) )
                {
                    returnList.add( subnode );
                }
            }
        }
        
        return returnList;
    }    
}