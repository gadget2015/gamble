package org.robert.tips.maltips.domain.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.exceptions.InvalidXMLFormatException;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.maltips.types.MaltipsGame;
import org.robert.tips.maltips.types.MaltipsXMLConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Repository for the MaltipsSystem aggregate root.
 * @author Robert
 */
public class MaltipsSystemRepository implements MaltipsXMLConstants, MaltipsConstants, MaltipsSystemRepositoryIF {

    public void save(MaltipsSystem maltipsSystem) throws GeneralApplicationException {

        try {
            // save as XML document
            String xmlDocument = createDOMDocument(maltipsSystem);
            FileOutputStream ostream = new FileOutputStream(maltipsSystem.getFileName());
            PrintWriter printWriter = new PrintWriter(ostream);
            printWriter.print(xmlDocument);
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new GeneralApplicationException(e.getMessage());
        } catch(Exception e) {
            throw new GeneralApplicationException(e.getMessage());
        }
    }

    public MaltipsSystem find(File file) throws GeneralApplicationException, InvalidXMLFormatException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            FileInputStream fis = new FileInputStream(file);
            Document document = builder.parse(fis);

            MaltipsSystem newMaltipsSystem = new MaltipsSystem();

            // umarshall
            MaltipsSystemMarshaller marshaller = new MaltipsSystemMarshaller();
            marshaller.unmarshallMaltipsSystem(document, newMaltipsSystem);
            marshaller.unmarshallMathimatical(document, newMaltipsSystem);
            marshaller.unmarshallSingleSystem(document, newMaltipsSystem);
            marshaller.unmarshallBankerSystem(document, newMaltipsSystem);
            marshaller.unmarshallRSystem(document, newMaltipsSystem);

            return newMaltipsSystem;
        } catch (GameAlreadySetException ex) {
            throw new GeneralApplicationException("Game already set, incosistent system. " + ex.getMessage());
        } catch (ParserConfigurationException ex) {
            throw new GeneralApplicationException("XML parser configuration error: " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            throw new GeneralApplicationException("File not found: " + ex.getMessage());
        } catch (SAXException ex) {
            throw new GeneralApplicationException(("Error while parsing XML structure." + ex.getMessage()));
        } catch (IOException ex) {
            throw new GeneralApplicationException(("IO Exception while reading XML structure." + ex.getMessage()));
        }
    }

    private String createDOMDocument(MaltipsSystem maltipsSystem) throws GeneralApplicationException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            // create root node.
            Element root = (Element) document.createElement(ROOT);
            document.appendChild(root);

            // Add root entity
            MaltipsSystemMarshaller marshaller = new MaltipsSystemMarshaller();
            Element xmlReducedSystem = marshaller.marshallMaltipsSystem(document, maltipsSystem);
            root.appendChild(xmlReducedSystem);

            // add mathimatical document
            Element xmlMathimaticalSystem = marshaller.marshallMathimatical(document, maltipsSystem);
            root.appendChild(xmlMathimaticalSystem);

            // add single row system
            Element xmlSingleSystem = marshaller.marshallSingleSystem(document, maltipsSystem);
            root.appendChild(xmlSingleSystem);

            // Add banker system
            Element xmlBankerSystem = marshaller.marshallBankerSystem(document, maltipsSystem);
            root.appendChild(xmlBankerSystem);

            // Add R-system
            Element xmlRSystem = marshaller.marshallRSystem(document, maltipsSystem);
            root.appendChild(xmlRSystem);

            // create a string representation of this document, e.g a XML document as a string
            OutputFormat outputFormat = new OutputFormat(METHOD, ENCODING, true);
            StringWriter stringWriter = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(stringWriter, outputFormat);

            serializer.serialize(document);
            StringBuffer tmp = stringWriter.getBuffer();
            String xmlDocument = tmp.toString();

            return xmlDocument;
        } catch (ParserConfigurationException e) {
            throw new GeneralApplicationException(e.getMessage());
        } catch (IOException e) {
            throw new GeneralApplicationException(e.getMessage());
        }
    }

    public void exportReducedSystem(MaltipsSystem maltipsSystem) throws GeneralApplicationException {
        // export reduced system to a .export file.
        String tmpExportFilename = maltipsSystem.getFileName().getAbsolutePath() + ".export";
        File exportFileName = new File(tmpExportFilename);

        try {
            FileOutputStream ostream = new FileOutputStream(exportFileName);
            PrintWriter writer = new PrintWriter(ostream);
            writer.println("Maltipset");

            ArrayList reducedSystem = maltipsSystem.getReducedSystem();
            Iterator iterator = reducedSystem.iterator();

            while (iterator.hasNext()) {
                MaltipsGame game = (MaltipsGame) iterator.next();
                writer.print("E");    // indicates a single row

                writer.write("," + game.toString());
                writer.println();
            }

            writer.close();
        } catch (FileNotFoundException e) {
            throw new GeneralApplicationException("Error creating file.");
        }
    }
}
