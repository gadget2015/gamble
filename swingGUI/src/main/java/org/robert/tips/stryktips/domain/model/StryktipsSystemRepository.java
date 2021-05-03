package org.robert.tips.stryktips.domain.model;

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
import org.robert.tips.stryktips.exceptions.GameNotSetException;
import org.robert.tips.stryktips.types.RSystemType;
import org.robert.tips.stryktips.types.StryktipsConstants;
import org.robert.tips.stryktips.types.StryktipsGame;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.types.StryktipsXMLConstants;
import org.robert.tips.util.DOMUtility;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.robert.tips.exceptions.InvalidXMLFormatException;

/**
 * Repository for the StryktipsSystem aggregate.
 * @author Robert
 */
public class StryktipsSystemRepository implements StryktipsXMLConstants, StryktipsConstants, StryktipsSystemRepositoryIF {

    /**
     * Save the stryktips system domain object.
     * @param stryktipsSystem
     */
    public void save(StryktipsSystem stryktipsSystem) throws GeneralApplicationException {

        try {
            // save as XML document
            String xmlDocument = createDOMDocument(stryktipsSystem);
            FileOutputStream ostream = new FileOutputStream(stryktipsSystem.getFileName());
            PrintWriter printWriter = new PrintWriter(ostream);
            printWriter.print(xmlDocument);
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new GeneralApplicationException(e.getMessage());
        }
    }

    /**
     * Find the given file and return a newly created StryktipsSystem object.
     * @param file
     * @return New created object.
     * @throws tips.exceptions.GeneralApplicationException
     */
    public StryktipsSystem find(File file) throws GeneralApplicationException, InvalidXMLFormatException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            FileInputStream fis = new FileInputStream(file);
            Document document = builder.parse(fis);

            StryktipsSystem newStryktipsSystem = new StryktipsSystem();

            // umarshall
            unmarshallStryktipsSystem(document, newStryktipsSystem);
            unmarshallMathimatical(document, newStryktipsSystem);
            unmarshallCombination(document, newStryktipsSystem);
            unmarshallBanker(document, newStryktipsSystem);
            unmarshallRSystem(document, newStryktipsSystem);
            unmarshallOdds(document, newStryktipsSystem);
            unmarshallExtended(document, newStryktipsSystem);
            unmarshallPercentage(document, newStryktipsSystem);

            return newStryktipsSystem;
        } catch (ParserConfigurationException ex) {
            throw new GeneralApplicationException("XML parser configuration error: " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            throw new GeneralApplicationException("File not found: " + ex.getMessage());
        } catch (SAXException ex) {
            throw new GeneralApplicationException(("Error while parsing XML structure." + ex.getMessage()));
        } catch (IOException ex) {
            throw new GeneralApplicationException(("IO Exception while reading XML structure." + ex.getMessage()));
        } catch (GameAlreadySetException e) {
            throw new GeneralApplicationException("Try to parse data but it's inconsistent. Details = " + e.getMessage());
        } catch (GameNotSetException e) {
            throw new GeneralApplicationException(("Try to parse data but it's inconsistent. Details = " + e.getMessage()));
        }

    }

    private String createDOMDocument(StryktipsSystem stryktipsSystem) throws GeneralApplicationException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            // create root node.
            Element root = (Element) document.createElement(ROOT);
            document.appendChild(root);

            // Add this document
            Element xmlReducedSystem = marshallStryktipsSystem(document, stryktipsSystem);
            root.appendChild(xmlReducedSystem);

            // add combination document
            Element xmlCombinationSystem = marshallCombination(document, stryktipsSystem);
            root.appendChild(xmlCombinationSystem);

            // add bankers document
            Element xmlBankersSystem = marshallBanker(document, stryktipsSystem);
            root.appendChild(xmlBankersSystem);

            // add odds document
            Element xmlOddsSystem = marshallOdds(document, stryktipsSystem);
            root.appendChild(xmlOddsSystem);

            // add mathimatical document
            Element xmlMathimaticalSystem = marshallMathimatical(document, stryktipsSystem);
            root.appendChild(xmlMathimaticalSystem);

            // add r-system document
            Element xmlRSystem = marshallRSystem(document, stryktipsSystem);
            root.appendChild(xmlRSystem);

            // add extended system
            Element xmlExtendedSystem = marshallExtended(document, stryktipsSystem);
            root.appendChild(xmlExtendedSystem);

            // Add percentage system
            Element xmlPercentageSystem = marshallPercentage(document, stryktipsSystem);
            root.appendChild(xmlPercentageSystem);

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

    private Element marshallBanker(Document document, StryktipsSystem stryktipsSystem) {


        Element xmlBankersSystem = (Element) document.createElement(ROOT_BANKERSSYSTEM);

        // add bankers system

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Element xmlBankersSystemRow = (Element) document.createElement(ROOT_BANKERSSYSTEM_ROW + i);

            // one
            Element xmlBankersSystemRowOne = (Element) document.createElement(ROOT_BANKERSSYSTEM_ROW_ONE);

            if (stryktipsSystem.getBanker().getBankersRow(i) == GAMEVALUE_ONE) {
                xmlBankersSystemRowOne.appendChild(document.createTextNode(new Character(GAMEVALUE_ONE).toString()));
            }

            // tie
            Element xmlBankersSystemRowTie = (Element) document.createElement(ROOT_BANKERSSYSTEM_ROW_TIE);

            if (stryktipsSystem.getBanker().getBankersRow(i) == GAMEVALUE_TIE) {
                xmlBankersSystemRowTie.appendChild(document.createTextNode(new Character(GAMEVALUE_TIE).toString()));
            }

            // two
            Element xmlBankersSystemRowTwo = (Element) document.createElement(ROOT_BANKERSSYSTEM_ROW_TWO);

            if (stryktipsSystem.getBanker().getBankersRow(i) == GAMEVALUE_TWO) {
                xmlBankersSystemRowTwo.appendChild(document.createTextNode(new Character(GAMEVALUE_TWO).toString()));
            }

            xmlBankersSystemRow.appendChild(xmlBankersSystemRowOne);
            xmlBankersSystemRow.appendChild(xmlBankersSystemRowTie);
            xmlBankersSystemRow.appendChild(xmlBankersSystemRowTwo);

            xmlBankersSystem.appendChild(xmlBankersSystemRow);

        }

        return xmlBankersSystem;
    }

    private Element marshallCombination(Document document, StryktipsSystem stryktipsSystem) {
        Element xmlCombination = (Element) document.createElement(ROOT_COMBINATION);

        // add single system
        Element xmlSingleSystem = (Element) document.createElement(ROOT_COMBINATION_SINGLESYSTEM);
        StryktipsGame game = new StryktipsGame(stryktipsSystem.getCombination().getSingleRow());
        xmlSingleSystem.appendChild(document.createTextNode(game.toString()));
        xmlCombination.appendChild(xmlSingleSystem);

        // add min in single system
        Element xmlMinSingleSystem = (Element) document.createElement(ROOT_COMBINATION_MIN_IN_SINGLESYSTEM);
        xmlMinSingleSystem.appendChild(document.createTextNode(Integer.toString(stryktipsSystem.getCombination().getMinInSingleRow())));
        xmlCombination.appendChild(xmlMinSingleSystem);

        // add min in single system
        Element xmlMaxSingleSystem = (Element) document.createElement(ROOT_COMBINATION_MAX_IN_SINGLESYSTEM);
        xmlMaxSingleSystem.appendChild(document.createTextNode(Integer.toString(stryktipsSystem.getCombination().getMaxInSingleRow())));
        xmlCombination.appendChild(xmlMaxSingleSystem);

        return xmlCombination;
    }

    private Element marshallExtended(Document document, StryktipsSystem stryktipsSystem) {
        Element xmlExtended = (Element) document.createElement(ROOT_EXTENDEDSYSTEM);

        // add extended system
        Element xmlExtendedSystem = (Element) document.createElement(ROOT_EXTENDEDSYSTEM_SYSTEM);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Element xmlExtendedSystemRow = (Element) document.createElement(ROOT_EXTENDEDSYSTEM_SYSTEM_ROW + i);

            Element xmlExtendedSystemRowOne = (Element) document.createElement(ROOT_EXTENDEDSYSTEM_SYSTEM_ROW_ONE);
            xmlExtendedSystemRowOne.appendChild(document.createTextNode(String.valueOf(stryktipsSystem.getExtended().getExtendedRow(i * NUMBER_OF_GAMEOPTIONS))));

            Element xmlExtendedSystemRowTie = (Element) document.createElement(ROOT_EXTENDEDSYSTEM_SYSTEM_ROW_TIE);
            xmlExtendedSystemRowTie.appendChild(document.createTextNode(String.valueOf(stryktipsSystem.getExtended().getExtendedRow(i * NUMBER_OF_GAMEOPTIONS + 1))));

            Element xmlExtendedSystemRowTwo = (Element) document.createElement(ROOT_EXTENDEDSYSTEM_SYSTEM_ROW_TWO);
            xmlExtendedSystemRowTwo.appendChild(document.createTextNode(String.valueOf(stryktipsSystem.getExtended().getExtendedRow(i * NUMBER_OF_GAMEOPTIONS + 2))));

            xmlExtendedSystemRow.appendChild(xmlExtendedSystemRowOne);
            xmlExtendedSystemRow.appendChild(xmlExtendedSystemRowTie);
            xmlExtendedSystemRow.appendChild(xmlExtendedSystemRowTwo);

            xmlExtendedSystem.appendChild(xmlExtendedSystemRow);

        }

        xmlExtended.appendChild(xmlExtendedSystem);

        return xmlExtended;
    }

    private Element marshallMathimatical(Document document, StryktipsSystem stryktipsSystem) {
        Element xmlMathimatical = (Element) document.createElement(ROOT_MATHIMATICAL);

        // add mathimatical system
        Element xmlMathimaticalSystem = (Element) document.createElement(ROOT_MATHIMATICAL_MATHIMATICALSYSTEM);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Element xmlMathimaticalSystemRow = (Element) document.createElement(ROOT_MATHIMATICAL_MATHIMATICALSYSTEM_ROW + i);

            Element xmlMathimaticalSystemRowOne = (Element) document.createElement(ROOT_MATHIMATICAL_MATHIMATICALSYSTEM_ROW_ONE);
            xmlMathimaticalSystemRowOne.appendChild(document.createTextNode(String.valueOf(stryktipsSystem.getMathimatical().getMathimaticalRow(i * NUMBER_OF_GAMEOPTIONS))));

            Element xmlMathimaticalSystemRowTie = (Element) document.createElement(ROOT_MATHIMATICAL_MATHIMATICALSYSTEM_ROW_TIE);
            xmlMathimaticalSystemRowTie.appendChild(document.createTextNode(String.valueOf(stryktipsSystem.getMathimatical().getMathimaticalRow(i * NUMBER_OF_GAMEOPTIONS + 1))));

            Element xmlMathimaticalSystemRowTwo = (Element) document.createElement(ROOT_MATHIMATICAL_MATHIMATICALSYSTEM_ROW_TWO);
            xmlMathimaticalSystemRowTwo.appendChild(document.createTextNode(String.valueOf(stryktipsSystem.getMathimatical().getMathimaticalRow(i * NUMBER_OF_GAMEOPTIONS + 2))));

            xmlMathimaticalSystemRow.appendChild(xmlMathimaticalSystemRowOne);
            xmlMathimaticalSystemRow.appendChild(xmlMathimaticalSystemRowTie);
            xmlMathimaticalSystemRow.appendChild(xmlMathimaticalSystemRowTwo);

            xmlMathimaticalSystem.appendChild(xmlMathimaticalSystemRow);

        }

        xmlMathimatical.appendChild(xmlMathimaticalSystem);

        return xmlMathimatical;
    }

    private Element marshallOdds(Document document, StryktipsSystem stryktipsSystem) {
        Element xmlOdds = (Element) document.createElement(ROOT_ODDS);

        // add min in odds system
        Element xmlMinOddsSystem = (Element) document.createElement(ROOT_ODDS_MIN_IN_ODDSSYSTEM);
        xmlMinOddsSystem.appendChild(document.createTextNode(Float.toString(stryktipsSystem.getOdds().getMinimumOdds())));
        xmlOdds.appendChild(xmlMinOddsSystem);

        // add max in odds system
        Element xmlMaxOddsSystem = (Element) document.createElement(ROOT_ODDS_MAX_IN_ODDSSYSTEM);
        xmlMaxOddsSystem.appendChild(document.createTextNode(Float.toString(stryktipsSystem.getOdds().getMaximumOdds())));
        xmlOdds.appendChild(xmlMaxOddsSystem);

        // add odds system
        Element xmlOddsSystem = (Element) document.createElement(ROOT_ODDS_ROWS);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Element xmlOddsSystemRow = (Element) document.createElement(ROOT_ODDS_ROW + i);

            Element xmlOddsSystemRowOne = (Element) document.createElement(ROOT_ODDS_ROW_ONE);
            xmlOddsSystemRowOne.appendChild(document.createTextNode(Float.toString(stryktipsSystem.getOdds().getOddsSystem(i * NUMBER_OF_GAMEOPTIONS)).toString()));

            Element xmlOddsSystemRowTie = (Element) document.createElement(ROOT_ODDS_ROW_TIE);
            xmlOddsSystemRowTie.appendChild(document.createTextNode(Float.toString(stryktipsSystem.getOdds().getOddsSystem(i * NUMBER_OF_GAMEOPTIONS + 1)).toString()));

            Element xmlOddsSystemRowTwo = (Element) document.createElement(ROOT_ODDS_ROW_TWO);
            xmlOddsSystemRowTwo.appendChild(document.createTextNode(Float.toString(stryktipsSystem.getOdds().getOddsSystem(i * NUMBER_OF_GAMEOPTIONS + 2)).toString()));

            xmlOddsSystemRow.appendChild(xmlOddsSystemRowOne);
            xmlOddsSystemRow.appendChild(xmlOddsSystemRowTie);
            xmlOddsSystemRow.appendChild(xmlOddsSystemRowTwo);

            xmlOddsSystem.appendChild(xmlOddsSystemRow);

        }

        xmlOdds.appendChild(xmlOddsSystem);

        return xmlOdds;
    }
    private Element marshallPercentage(Document document, StryktipsSystem stryktipsSystem) {
        Element xml = (Element) document.createElement(ROOT_PERCENTAGE);

        // add revenue parameter
        Element xmlRevenueParameter = (Element) document.createElement(ROOT_PERCENTAGE_REVENUE_PARAMETER);
        xmlRevenueParameter.appendChild(document.createTextNode(Integer.toString(stryktipsSystem.getPlayed().revenue)));
        xml.appendChild(xmlRevenueParameter);

        // Add number of rights LOW range parameter
        Element xmlLowRangeNumberOfRightsParameter = (Element) document.createElement(ROOT_PERCENTAGE_NUMBER_OF_RIGHTS_LOW_PARAMETER);
        xmlLowRangeNumberOfRightsParameter.appendChild(document.createTextNode(Integer.toString(stryktipsSystem.getPlayed().minimumNumberOfPeopleWithFullPot)));
        xml.appendChild(xmlLowRangeNumberOfRightsParameter);

        // Add number of rights HIGH range parameter
        Element xmlHighRangeNumberOfRightsParameter = (Element) document.createElement(ROOT_PERCENTAGE_NUMBER_OF_RIGHTS_HIGH_PARAMETER);
        xmlHighRangeNumberOfRightsParameter.appendChild(document.createTextNode(Integer.toString(stryktipsSystem.getPlayed().maxiumumNumberOfPeopleWithFullPot)));
        xml.appendChild(xmlHighRangeNumberOfRightsParameter);

        // add percentage system
        Element xmlPercentageSystem = (Element) document.createElement(ROOT_PERCENTAGE_ROWS);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Element xmlSystemRow = (Element) document.createElement(ROOT_PERCENTAGE_ROW + i);

            Element xmlSystemRowOne = (Element) document.createElement(ROOT_PERCENTAGE_ROW_ONE);
            xmlSystemRowOne.appendChild(document.createTextNode(Float.toString(stryktipsSystem.getPlayed().getPercentage(i * NUMBER_OF_GAMEOPTIONS)).toString()));

            Element xmlSystemRowTie = (Element) document.createElement(ROOT_PERCENTAGE_ROW_TIE);
            xmlSystemRowTie.appendChild(document.createTextNode(Float.toString(stryktipsSystem.getPlayed().getPercentage(i * NUMBER_OF_GAMEOPTIONS + 1)).toString()));

            Element xmlSystemRowTwo = (Element) document.createElement(ROOT_PERCENTAGE_ROW_TWO);
            xmlSystemRowTwo.appendChild(document.createTextNode(Float.toString(stryktipsSystem.getPlayed().getPercentage(i * NUMBER_OF_GAMEOPTIONS + 2)).toString()));

            xmlSystemRow.appendChild(xmlSystemRowOne);
            xmlSystemRow.appendChild(xmlSystemRowTie);
            xmlSystemRow.appendChild(xmlSystemRowTwo);

            xmlPercentageSystem.appendChild(xmlSystemRow);
        }

        xml.appendChild(xmlPercentageSystem);

        return xml;
    }

    private Element marshallRSystem(Document document, StryktipsSystem stryktipsSystem) {
        Element xmlRSystem = (Element) document.createElement(ROOT_RSYSTEM);

        // add system type
        Element xmlRSystemType = (Element) document.createElement(ROOT_RSYSTEM_SYSTEMTYPE);
        xmlRSystemType.appendChild(document.createTextNode(stryktipsSystem.getRsystem().getSystemType().getSystemType()));
        xmlRSystem.appendChild(xmlRSystemType);

        // add R-system
        Element xmlRSystemRows = (Element) document.createElement(ROOT_RSYSTEM_RSYSTEM);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Element xmlRSystemRow = (Element) document.createElement(ROOT_RSYSTEM_RSYSTEM_ROW + i);

            Element xmlRSystemRowOne = (Element) document.createElement(ROOT_RSYSTEM_RSYSTEM_ROW_ONE);
            xmlRSystemRowOne.appendChild(document.createTextNode(String.valueOf(stryktipsSystem.getRsystem().getRSystemRow(i * NUMBER_OF_GAMEOPTIONS))));

            Element xmlRSystemRowTie = (Element) document.createElement(ROOT_RSYSTEM_RSYSTEM_ROW_TIE);
            xmlRSystemRowTie.appendChild(document.createTextNode(String.valueOf(stryktipsSystem.getRsystem().getRSystemRow(i * NUMBER_OF_GAMEOPTIONS + 1))));

            Element xmlRSystemRowTwo = (Element) document.createElement(ROOT_RSYSTEM_RSYSTEM_ROW_TWO);
            xmlRSystemRowTwo.appendChild(document.createTextNode(String.valueOf(stryktipsSystem.getRsystem().getRSystemRow(i * NUMBER_OF_GAMEOPTIONS + 2))));

            xmlRSystemRow.appendChild(xmlRSystemRowOne);
            xmlRSystemRow.appendChild(xmlRSystemRowTie);
            xmlRSystemRow.appendChild(xmlRSystemRowTwo);

            xmlRSystemRows.appendChild(xmlRSystemRow);

        }

        xmlRSystem.appendChild(xmlRSystemRows);

        return xmlRSystem;
    }

    private Element marshallStryktipsSystem(Document document, StryktipsSystem stryktipsSystem) throws GeneralApplicationException {
        // add reduced system
        Element xmlReducedSystem = (Element) document.createElement(ROOT_REDUCEDSYSTEM);
        Iterator iterator = stryktipsSystem.getReducedSystem().iterator();

        while (iterator.hasNext()) {
            StryktipsGame game = (StryktipsGame) iterator.next();

            Element reducedRow = (Element) document.createElement(ROOT_REDUCEDSYSTEM_ROW);

            reducedRow.appendChild(document.createTextNode(game.toString()));
            xmlReducedSystem.appendChild(reducedRow);
        }

        return xmlReducedSystem;
    }

    private void unmarshallBanker(Document document, StryktipsSystem newStryktipsSystem) throws InvalidXMLFormatException, GameNotSetException {
        Element root = document.getDocumentElement();
        NodeList bankersSystemList = root.getElementsByTagName(ROOT_BANKERSSYSTEM);

        if (bankersSystemList.getLength() > 1) {
            throw new InvalidXMLFormatException(null, "Invalid XML file, too many:" + ROOT_BANKERSSYSTEM + " XML tags");
        }

        // set bankers system
        Node bankersSystem = bankersSystemList.item(0);   // shall only be one tag with this name.      

        DOMUtility util = new DOMUtility();

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Node currentRow = util.findSubNode((ROOT_BANKERSSYSTEM_ROW + i), bankersSystem);

            // one
            Node oneNode = util.findSubNode(ROOT_BANKERSSYSTEM_ROW_ONE, currentRow);
            String one = util.getText(oneNode);

            if (one.length() == 1) {
                newStryktipsSystem.getBanker().setBankersRow((i), GAMEVALUE_ONE);
            }

            // tie
            Node tieNode = util.findSubNode(ROOT_BANKERSSYSTEM_ROW_TIE, currentRow);
            String tie = util.getText(tieNode);

            if (tie.length() == 1) {
                newStryktipsSystem.getBanker().setBankersRow((i), GAMEVALUE_TIE);
            }

            // two
            Node twoNode = util.findSubNode(ROOT_BANKERSSYSTEM_ROW_TWO, currentRow);
            String two = util.getText(twoNode);

            if (two.length() == 1) {
                newStryktipsSystem.getBanker().setBankersRow((i), GAMEVALUE_TWO);
            }
        }
    }

    private void unmarshallCombination(Document document, StryktipsSystem newStryktipsSystem) throws InvalidXMLFormatException, GameAlreadySetException {
        Element root = document.getDocumentElement();
        NodeList combinationSystemList = root.getElementsByTagName(ROOT_COMBINATION);
        Node combinationSystem = combinationSystemList.item(0);   // shall only be one tag with this name.      

        DOMUtility util = new DOMUtility();

        // set single row system
        Node singleRowSystem = util.findSubNode(ROOT_COMBINATION_SINGLESYSTEM, combinationSystem);
        String tmpSingleRow = util.getText(singleRowSystem);

        if (tmpSingleRow.length() == NUMBER_OF_GAMES) {
            char[] singleRow = tmpSingleRow.toCharArray();
            for (int i = 0; i < NUMBER_OF_GAMES; i++) {
                newStryktipsSystem.getCombination().setSingleRow(i, singleRow[i]);
            }
        } else {
            // No single row set.
        }

        // set min value
        Node minValue = util.findSubNode(ROOT_COMBINATION_MIN_IN_SINGLESYSTEM, combinationSystem);
        String tmpMinValue = util.getText(minValue);
        int minInSingleRow = Integer.parseInt(tmpMinValue);
        newStryktipsSystem.getCombination().setMinInSingleRow(minInSingleRow);


        // set max value
        Node maxValue = util.findSubNode(ROOT_COMBINATION_MAX_IN_SINGLESYSTEM, combinationSystem);
        String tmpMaxValue = util.getText(maxValue);
        int maxInSingleRow = Integer.parseInt(tmpMaxValue);
        newStryktipsSystem.getCombination().setMaxInSingleRow(maxInSingleRow);
    }

    private void unmarshallExtended(Document document, StryktipsSystem newStryktipsSystem) throws GameAlreadySetException, InvalidXMLFormatException {
        Element root = document.getDocumentElement();
        NodeList extendedSystemList = root.getElementsByTagName(ROOT_EXTENDEDSYSTEM);
        Node extendedSystemNode = extendedSystemList.item(0);   // shall only be one tag with this name.      

        DOMUtility util = new DOMUtility();

        // set extended system
        Node extendedSystemRowsNode = util.findSubNode(ROOT_EXTENDEDSYSTEM_SYSTEM, extendedSystemNode);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Node currentRow = util.findSubNode((ROOT_EXTENDEDSYSTEM_SYSTEM_ROW + i), extendedSystemRowsNode);

            // one
            Node oneNode = util.findSubNode(ROOT_EXTENDEDSYSTEM_SYSTEM_ROW_ONE, currentRow);
            String one = util.getText(oneNode);

            if (one.length() == 1) {
                newStryktipsSystem.getExtended().setExtendedRow((i*NUMBER_OF_GAMEOPTIONS), GAMEVALUE_ONE);
            }

            // tie
            Node tieNode = util.findSubNode(ROOT_EXTENDEDSYSTEM_SYSTEM_ROW_TIE, currentRow);
            String tie = util.getText(tieNode);

            if (tie.length() == 1) {
                newStryktipsSystem.getExtended().setExtendedRow((i*NUMBER_OF_GAMEOPTIONS + 1), GAMEVALUE_TIE);
            }

            // two
            Node twoNode = util.findSubNode(ROOT_EXTENDEDSYSTEM_SYSTEM_ROW_TWO, currentRow);
            String two = util.getText(twoNode);

            if (two.length() == 1) {
                newStryktipsSystem.getExtended().setExtendedRow((i*NUMBER_OF_GAMEOPTIONS + 2), GAMEVALUE_TWO);
            }
        }
    }

    private void unmarshallMathimatical(Document document, StryktipsSystem newStryktipsSystem) throws InvalidXMLFormatException, GameAlreadySetException {
        Element root = document.getDocumentElement();
        NodeList mathimaticalSystemList = root.getElementsByTagName(ROOT_MATHIMATICAL);
        Node mathimaticalSystemNode = mathimaticalSystemList.item(0);   // shall only be one tag with this name.      

        DOMUtility util = new DOMUtility();

        // set odds system
        Node mathimaticalSystemRowsNode = util.findSubNode(ROOT_MATHIMATICAL_MATHIMATICALSYSTEM, mathimaticalSystemNode);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Node currentRow = util.findSubNode((ROOT_MATHIMATICAL_MATHIMATICALSYSTEM_ROW + i), mathimaticalSystemRowsNode);

            // one
            Node oneNode = util.findSubNode(ROOT_MATHIMATICAL_MATHIMATICALSYSTEM_ROW_ONE, currentRow);
            String one = util.getText(oneNode);

            if (one.length() == 1) {
                newStryktipsSystem.getMathimatical().setMathimaticalRow((i * NUMBER_OF_GAMEOPTIONS), GAMEVALUE_ONE);
            }

            // tie
            Node tieNode = util.findSubNode(ROOT_MATHIMATICAL_MATHIMATICALSYSTEM_ROW_TIE, currentRow);
            String tie = util.getText(tieNode);

            if (tie.length() == 1) {
                newStryktipsSystem.getMathimatical().setMathimaticalRow((i * NUMBER_OF_GAMEOPTIONS + 1), GAMEVALUE_TIE);
            }

            // two
            Node twoNode = util.findSubNode(ROOT_MATHIMATICAL_MATHIMATICALSYSTEM_ROW_TWO, currentRow);
            String two = util.getText(twoNode);

            if (two.length() == 1) {
                newStryktipsSystem.getMathimatical().setMathimaticalRow((i * NUMBER_OF_GAMEOPTIONS + 2), GAMEVALUE_TWO);
            }
        }
    }

    private void unmarshallOdds(Document document, StryktipsSystem newStryktipsSystem) throws InvalidXMLFormatException {
        Element root = document.getDocumentElement();
        NodeList oddsSystemList = root.getElementsByTagName(ROOT_ODDS);
        Node oddsSystemNode = oddsSystemList.item(0);   // shall only be one tag with this name.      

        DOMUtility util = new DOMUtility();

        // set min value
        Node minValue = util.findSubNode(ROOT_ODDS_MIN_IN_ODDSSYSTEM, oddsSystemNode);
        String tmpMinValue = util.getText(minValue);
        float minimumOdds = Float.parseFloat(tmpMinValue);
        newStryktipsSystem.getOdds().setMinimumOdds(minimumOdds);

        // set max value
        Node maxValue = util.findSubNode(ROOT_ODDS_MAX_IN_ODDSSYSTEM, oddsSystemNode);
        String tmpMaxValue = util.getText(maxValue);
        float maximumOdds = Float.parseFloat(tmpMaxValue);
        newStryktipsSystem.getOdds().setMaximumOdds(maximumOdds);

        // set odds system
        Node oddsSystemRowsNode = util.findSubNode(ROOT_ODDS_ROWS, oddsSystemNode);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Node currentRow = util.findSubNode((ROOT_ODDS_ROW + i), oddsSystemRowsNode);

            // one
            Node oneNode = util.findSubNode(ROOT_ODDS_ROW_ONE, currentRow);
            String one = util.getText(oneNode);
            float oddsValue = Float.parseFloat(one);
            newStryktipsSystem.getOdds().setOddsSystem((i * NUMBER_OF_GAMEOPTIONS), oddsValue);

            // tie
            Node tieNode = util.findSubNode(ROOT_ODDS_ROW_TIE, currentRow);
            String tie = util.getText(tieNode);
            oddsValue = Float.parseFloat(tie);
            newStryktipsSystem.getOdds().setOddsSystem((i * NUMBER_OF_GAMEOPTIONS + 1), oddsValue);

            // two
            Node twoNode = util.findSubNode(ROOT_ODDS_ROW_TWO, currentRow);
            String two = util.getText(twoNode);
            oddsValue = Float.parseFloat(two);
            newStryktipsSystem.getOdds().setOddsSystem((i * NUMBER_OF_GAMEOPTIONS + 2), oddsValue);
        }
    }

    private void unmarshallPercentage(Document document, StryktipsSystem newStryktipsSystem) throws InvalidXMLFormatException {
        Element root = document.getDocumentElement();
        NodeList percentageSystemList = root.getElementsByTagName(ROOT_PERCENTAGE);
        Node percentageSystemNode = percentageSystemList.item(0);   // shall only be one tag with this name.

        DOMUtility util = new DOMUtility();

        // set revenue
        Node revenueValue = util.findSubNode(ROOT_PERCENTAGE_REVENUE_PARAMETER, percentageSystemNode);
        String tmpRevenueValue = util.getText(revenueValue);
        int revenue = Integer.parseInt(tmpRevenueValue);
        newStryktipsSystem.getPlayed().revenue = revenue;

        // Set number of rights low.
        Node numberOfRightsLow = util.findSubNode(ROOT_PERCENTAGE_NUMBER_OF_RIGHTS_LOW_PARAMETER, percentageSystemNode);
        String tmpNumberOfRightsLow = util.getText(numberOfRightsLow);
        int minimumNumberOfPeopleWithFullPot = Integer.parseInt(tmpNumberOfRightsLow);
        newStryktipsSystem.getPlayed().minimumNumberOfPeopleWithFullPot = minimumNumberOfPeopleWithFullPot;

        // Set number of rights high.
        Node numberOfRightsHigh = util.findSubNode(ROOT_PERCENTAGE_NUMBER_OF_RIGHTS_HIGH_PARAMETER, percentageSystemNode);
        String tmpNumberOfRightsHigh = util.getText(numberOfRightsHigh);
        int maxiumumNumberOfPeopleWithFullPot = Integer.parseInt(tmpNumberOfRightsHigh);
        newStryktipsSystem.getPlayed().maxiumumNumberOfPeopleWithFullPot = maxiumumNumberOfPeopleWithFullPot;

        // set percentage system
        Node percentageSystemRowsNode = util.findSubNode(ROOT_PERCENTAGE_ROWS, percentageSystemNode);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Node currentRow = util.findSubNode((ROOT_PERCENTAGE_ROW + i), percentageSystemRowsNode);

            // one
            Node oneNode = util.findSubNode(ROOT_PERCENTAGE_ROW_ONE, currentRow);
            String one = util.getText(oneNode);
            float value = Float.parseFloat(one);
            newStryktipsSystem.getPlayed().setPercentage((i * NUMBER_OF_GAMEOPTIONS), value);

            // tie
            Node tieNode = util.findSubNode(ROOT_PERCENTAGE_ROW_TIE, currentRow);
            String tie = util.getText(tieNode);
            value = Float.parseFloat(tie);
            newStryktipsSystem.getPlayed().setPercentage((i * NUMBER_OF_GAMEOPTIONS + 1), value);

            // two
            Node twoNode = util.findSubNode(ROOT_PERCENTAGE_ROW_TWO, currentRow);
            String two = util.getText(twoNode);
            value = Float.parseFloat(two);
            newStryktipsSystem.getPlayed().setPercentage((i * NUMBER_OF_GAMEOPTIONS + 2), value);
        }
    }

    private void unmarshallRSystem(Document document, StryktipsSystem newStryktipsSystem) throws GameAlreadySetException, InvalidXMLFormatException {
        Element root = document.getDocumentElement();
        NodeList rSystemList = root.getElementsByTagName(ROOT_RSYSTEM);
        Node rSystemNode = rSystemList.item(0);   // shall only be one tag with this name.      

        DOMUtility util = new DOMUtility();

        // set system type
        Node systemTypeNode = util.findSubNode(ROOT_RSYSTEM_SYSTEMTYPE, rSystemNode);
        String tmpSystemType = util.getText(systemTypeNode);
        RSystemType systemType = new RSystemType(tmpSystemType);
        newStryktipsSystem.getRsystem().setSystemType(systemType);

        // set rsystem.
        Node rSystemRowsNode = util.findSubNode(ROOT_RSYSTEM_RSYSTEM, rSystemNode);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Node currentRow = util.findSubNode((ROOT_RSYSTEM_RSYSTEM_ROW + i), rSystemRowsNode);

            // one
            Node oneNode = util.findSubNode(ROOT_RSYSTEM_RSYSTEM_ROW_ONE, currentRow);
            String one = util.getText(oneNode);

            if (one.length() == 1) {
                newStryktipsSystem.getRsystem().setRSystemRow((i * NUMBER_OF_GAMEOPTIONS), GAMEVALUE_ONE);
            }

            // tie
            Node tieNode = util.findSubNode(ROOT_RSYSTEM_RSYSTEM_ROW_TIE, currentRow);
            String tie = util.getText(tieNode);

            if (tie.length() == 1) {
                newStryktipsSystem.getRsystem().setRSystemRow((i * NUMBER_OF_GAMEOPTIONS + 1), GAMEVALUE_TIE);
            }

            // two
            Node twoNode = util.findSubNode(ROOT_RSYSTEM_RSYSTEM_ROW_TWO, currentRow);
            String two = util.getText(twoNode);

            if (two.length() == 1) {
                newStryktipsSystem.getRsystem().setRSystemRow((i * NUMBER_OF_GAMEOPTIONS + 2), GAMEVALUE_TWO);
            }
        }
    }

    private void unmarshallStryktipsSystem(Document document, StryktipsSystem newStryktipsSystem) throws InvalidXMLFormatException {
        Element root = document.getDocumentElement();
        NodeList reducedSystemList = root.getElementsByTagName(ROOT_REDUCEDSYSTEM);

        if (reducedSystemList.getLength() > 1) {
            throw new InvalidXMLFormatException(null, "Invalid XML file, too many:" + ROOT_REDUCEDSYSTEM + " XML tags");
        }

        // Add reduced rows.
        Node reducedSystem = reducedSystemList.item(0);   // shall only be one tag with this name.      

        DOMUtility util = new DOMUtility();
        ArrayList reducedSystemRows = util.findSubNodeList(ROOT_REDUCEDSYSTEM_ROW, reducedSystem);

        if (reducedSystemRows != null) {
            // There are saved reduced rows.
            Iterator iterator = reducedSystemRows.iterator();
            ArrayList reducedRows = new ArrayList();

            while (iterator.hasNext()) {
                Node row = (Node) iterator.next();
                String text = util.getText(row);
                char[] theRow = text.toCharArray();
                StryktipsGame game = new StryktipsGame(theRow);
                reducedRows.add(game);
            }

            newStryktipsSystem.setReducedSystem(reducedRows);
        }

    }
}
