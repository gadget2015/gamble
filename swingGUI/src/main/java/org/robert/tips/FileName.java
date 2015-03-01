/**
 * This class represents a physic filename with attributes.
 * @author Robert Geor�n.
 */
package org.robert.tips;
import java.io.File;

public class FileName extends File implements java.io.Serializable{
	
	/**
	 * Contructor
	 */
	public FileName(File file){
		super(file.getAbsolutePath());	
	}
	/**
	 * Get the File object.
	 */
	public String getFile(){
		return getAbsolutePath();
	}
}
