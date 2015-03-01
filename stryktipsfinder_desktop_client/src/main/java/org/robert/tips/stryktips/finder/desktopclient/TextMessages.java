package org.robert.tips.stryktips.finder.desktopclient;

import java.util.PropertyResourceBundle;
import java.util.Locale;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.io.InputStream;
import org.robert.tips.exceptions.GeneralApplicationException;

/** 
 * Used to retreive text messages for example buttons, menu items etc.
 * @author Robert Siwerz.
 */
public class TextMessages {

    public static String RESOURCE_FILENAME = "properties/TextMessages";
    private static final String SUFIX = ".properties";
    private PropertyResourceBundle resourceBoundle;
    private static TextMessages textMessages;

    public static TextMessages getInstance() throws GeneralApplicationException {
        if (textMessages == null) {
            textMessages = new TextMessages();
        }

        return textMessages;
    }

    public static TextMessages getInstance(String urlResource) throws GeneralApplicationException {
        if (textMessages == null) {
            textMessages = new TextMessages(urlResource);
        }

        return textMessages;
    }

    /**
     * Create the only instance of text message retreival.
     */
    private TextMessages() throws GeneralApplicationException {
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String resource = RESOURCE_FILENAME + "_" + language + "_" + country + SUFIX;

        try {
            ClassLoader cl = this.getClass().getClassLoader();
            InputStream is = null;

            try {
                URL resourceURL = cl.getResource(resource);
                is = resourceURL.openStream();
            } catch (NullPointerException e) {
                System.out.println("Fail getting resource by using classloader.getResource().");
                // This happens when the application runs from command-line.
                // There seams not to be any working classloader.
                try {
                    File systemFileName = new File(resource);
                    FileInputStream fis = new FileInputStream(systemFileName);
                    is = fis;
                } catch (java.security.AccessControlException e2) {
                    e2.printStackTrace();
                    throw new GeneralApplicationException("Error while getting text message resource.");
                }
            }

            resourceBoundle = new PropertyResourceBundle(is);
        } catch (NullPointerException e) {
            throw new GeneralApplicationException("Resource not found: " + resource, e);
        } catch (IOException e) {
            throw new GeneralApplicationException("Resource not found: " + resource, e);
        }
    }

    private TextMessages(String urlResource) throws GeneralApplicationException{
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String resource = urlResource + RESOURCE_FILENAME + "_" + language + "_" + country + SUFIX;

        try {
            URL resourceURL = new URL(resource);
            InputStream is = resourceURL.openStream();

            resourceBoundle = new PropertyResourceBundle(is);
        }  catch (IOException e) {
            throw new GeneralApplicationException("Resource not found: " + resource, e);
        }
    }

    /**
     * Get a text message for the given key.
     * @param key for the text message to retreive.
     * @return String the text message.
     */
    public String getText(String key) {
        String text = resourceBoundle.getString(key);

        return text;
    }
}
