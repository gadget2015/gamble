package org.robert.tipsbolag.webgui;

public class JSONParser {

	/**
	 * Get a data value with given attribute name.
	 */
	public String getJSON_Attribute(String JSON, String attributeName) {
		int start = JSON.indexOf("\"" + attributeName + "\"");
		if (start != -1) {
			int startData = JSON.indexOf("\"", start + attributeName.length()
					+ 2); // +2 because of " character at end of data.
			int stop = JSON.indexOf("\",", start);

			if (stop == -1) {
				// Have reached end of JSON message
				stop = JSON.indexOf("\"", startData + 1);
			}
			return JSON.substring(startData + 1, stop);
		} else {
			return null;
		}
	}
}
