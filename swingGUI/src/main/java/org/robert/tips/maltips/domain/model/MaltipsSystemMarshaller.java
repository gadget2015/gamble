package org.robert.tips.maltips.domain.model;

import java.util.ArrayList;
import java.util.Iterator;
import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.exceptions.InvalidXMLFormatException;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.maltips.types.MaltipsGame;
import org.robert.tips.maltips.types.MaltipsXMLConstants;
import org.robert.tips.maltips.types.RSystemType;
import org.robert.tips.util.DOMUtility;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Marshaller class for MaltipsSystem domain object.
 * @author Robert Georen.
 */
public class MaltipsSystemMarshaller implements MaltipsXMLConstants, MaltipsConstants {

    Element marshallMaltipsSystem(Document document, MaltipsSystem maltipsSystem) {
        // add reduced system
        Element xmlReducedSystem = (Element) document.createElement(ROOT_REDUCEDSYSTEM);
        Iterator iterator = maltipsSystem.getReducedSystem().iterator();

        while (iterator.hasNext()) {
            MaltipsGame game = (MaltipsGame) iterator.next();

            Element reducedRow = (Element) document.createElement(ROOT_REDUCEDSYSTEM_ROW);

            reducedRow.appendChild(document.createTextNode(game.toString()));
            xmlReducedSystem.appendChild(reducedRow);
        }

        return xmlReducedSystem;
    }

    Element marshallMathimatical(Document document, MaltipsSystem maltipsSystem) {
        Element xmlMathimatical = (Element) document.createElement(ROOT_MATHIMATICAL);

        // add mathimatical system
        Element xmlMathimaticalSystem = (Element) document.createElement(ROOT_MATHIMATICAL_MATHIMATICALSYSTEM);

        // loop over all games.
        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Element xmlMathimaticalSystemRow = (Element) document.createElement(ROOT_MATHIMATICAL_MATHIMATICALSYSTEM_ROW + i);
            String value = String.valueOf(maltipsSystem.getMathimatical().getMathimaticalRow(i));
            xmlMathimaticalSystemRow.appendChild(document.createTextNode(value));

            xmlMathimaticalSystem.appendChild(xmlMathimaticalSystemRow);
        }

        xmlMathimatical.appendChild(xmlMathimaticalSystem);

        return xmlMathimatical;
    }

    void unmarshallMaltipsSystem(Document document, MaltipsSystem newMaltipsSystem) throws InvalidXMLFormatException {
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
                MaltipsGame game = new MaltipsGame(text);
                reducedRows.add(game);
            }

            newMaltipsSystem.setReducedSystem(reducedRows);
        }
    }

    void unmarshallMathimatical(Document document, MaltipsSystem newMaltipsSystem) throws InvalidXMLFormatException, GameAlreadySetException {
        Element root = document.getDocumentElement();
        NodeList mathimaticalSystemList = root.getElementsByTagName(ROOT_MATHIMATICAL);
        Node mathimaticalSystemNode = mathimaticalSystemList.item(0);   // shall only be one tag with this name.
        DOMUtility util = new DOMUtility();

        // get node for the mathimatical system
        Node mathimaticalSystemRowsNode = util.findSubNode(ROOT_MATHIMATICAL_MATHIMATICALSYSTEM, mathimaticalSystemNode);

        // loop over all games
        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Node currentRow = util.findSubNode((ROOT_MATHIMATICAL_MATHIMATICALSYSTEM_ROW + i), mathimaticalSystemRowsNode);

            // get row value
            String value = util.getText(currentRow);

            if (value.length() == 1 || value.length() == 2) {
                newMaltipsSystem.getMathimatical().setMathimaticalRow(i, Integer.parseInt(value));
            }
        }
    }

    Element marshallSingleSystem(Document document, MaltipsSystem maltipsSystem) {
        Element xmlCombination = (Element) document.createElement(ROOT_COMBINATION);

        // add min in single system
        Element xmlMinSingleSystem = (Element) document.createElement(ROOT_COMBINATION_MIN_IN_SINGLESYSTEM);
        xmlMinSingleSystem.appendChild(document.createTextNode(Integer.toString(maltipsSystem.getSingleSystem().getMinInSingleRow())));
        xmlCombination.appendChild(xmlMinSingleSystem);

        // add min in single system
        Element xmlMaxSingleSystem = (Element) document.createElement(ROOT_COMBINATION_MAX_IN_SINGLESYSTEM);
        xmlMaxSingleSystem.appendChild(document.createTextNode(Integer.toString(maltipsSystem.getSingleSystem().getMaxInSingleRow())));
        xmlCombination.appendChild(xmlMaxSingleSystem);

        // add single system
        Element xmlSingleSystemRows = (Element) document.createElement(ROOT_COMBINATION_SINGLESYSTEM);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Element xmlSystemRow = (Element) document.createElement(ROOT_COMBINATION_SINGLESYSTEM_ROW + i);
            String value = String.valueOf(maltipsSystem.getSingleSystem().getSingleSystemRow(i));
            xmlSystemRow.appendChild(document.createTextNode(value));

            xmlSingleSystemRows.appendChild(xmlSystemRow);

        }

        xmlCombination.appendChild(xmlSingleSystemRows);

        return xmlCombination;
    }

    void unmarshallSingleSystem(Document document, MaltipsSystem newMaltipsSystem) throws InvalidXMLFormatException, GameAlreadySetException {
        Element root = document.getDocumentElement();
        NodeList combinationSystemList = root.getElementsByTagName(ROOT_COMBINATION);
        Node combinationSystem = combinationSystemList.item(0);   // shall only be one tag with this name.
        DOMUtility util = new DOMUtility();

        // set min value
        Node minValue = util.findSubNode(ROOT_COMBINATION_MIN_IN_SINGLESYSTEM, combinationSystem);
        String tmpMinValue = util.getText(minValue);
        int minInSingleRow = Integer.parseInt(tmpMinValue);
        newMaltipsSystem.getSingleSystem().setMinInSingleRow(minInSingleRow);

        // set max value
        Node maxValue = util.findSubNode(ROOT_COMBINATION_MAX_IN_SINGLESYSTEM, combinationSystem);
        String tmpMaxValue = util.getText(maxValue);
        int maxInSingleRow = Integer.parseInt(tmpMaxValue);
        newMaltipsSystem.getSingleSystem().setMaxInSingleRow(maxInSingleRow);

        // set single row system
        Node singleRowSystem = util.findSubNode(ROOT_COMBINATION_SINGLESYSTEM, combinationSystem);
        String tmpSingleRow = util.getText(singleRowSystem);

        // set single system rows
        Node singleSystemRowsNode = util.findSubNode(ROOT_COMBINATION_SINGLESYSTEM, combinationSystem);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Node currentRow = util.findSubNode((ROOT_COMBINATION_SINGLESYSTEM_ROW + i), singleSystemRowsNode);

            // get value
            String value = util.getText(currentRow);

            if (value.length() == 1 || value.length() == 2) {
                newMaltipsSystem.getSingleSystem().setSingleSystemRow(i, Integer.parseInt(value));
            }
        }
    }

    Element marshallBankerSystem(Document document, MaltipsSystem maltipsSystem) {
        Element xmlBankers = (Element) document.createElement(ROOT_BANKERSSYSTEM);

        // add bankers system
        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Element xmlBankersSystemRow = (Element) document.createElement(ROOT_BANKERSSYSTEM_ROW + i);
            String value = String.valueOf(maltipsSystem.getBankers().getBankersRow(i));
            xmlBankersSystemRow.appendChild(document.createTextNode(value));

            xmlBankers.appendChild(xmlBankersSystemRow);

        }

        return xmlBankers;
    }

    void unmarshallBankerSystem(Document document, MaltipsSystem newMaltipsSystem) throws InvalidXMLFormatException, GameAlreadySetException {
        Element root = document.getDocumentElement();
        NodeList bankersSystemList = root.getElementsByTagName(ROOT_BANKERSSYSTEM);
        Node bankersSystem = bankersSystemList.item(0);   // shall only be one tag with this name.
        DOMUtility util = new DOMUtility();

        // set bankers system rows

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Node currentRow = util.findSubNode((ROOT_BANKERSSYSTEM_ROW + i), bankersSystem);

            // get value
            String value = util.getText(currentRow);

            if (value.length() == 1 || value.length() == 2) {
                newMaltipsSystem.getBankers().setBankersRow(i, Integer.parseInt(value));
            }
        }
    }

    Element marshallRSystem(Document document, MaltipsSystem maltipsSystem) {
        Element xmlRSystem = (Element) document.createElement(ROOT_RSYSTEM);

        // add system type
        RSystemType rsystemType = maltipsSystem.getRsystem().getRSystemType();
        String systemType = (rsystemType == null) ? "" : rsystemType.getSystemType();
        Element xmlRSystemType = (Element) document.createElement(ROOT_RSYSTEM_SYSTEMTYPE);
        xmlRSystemType.appendChild(document.createTextNode(systemType));
        xmlRSystem.appendChild(xmlRSystemType);

        // add R-system
        Element xmlRSystemRows = (Element) document.createElement(ROOT_RSYSTEM_RSYSTEM);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Element xmlRSystemRow = (Element) document.createElement(ROOT_RSYSTEM_RSYSTEM_ROW + i);
            String value = String.valueOf(maltipsSystem.getRsystem().getRSystemRow(i));
            xmlRSystemRow.appendChild(document.createTextNode(value));
            xmlRSystemRows.appendChild(xmlRSystemRow);
        }

        xmlRSystem.appendChild(xmlRSystemRows);

        return xmlRSystem;
    }

    void unmarshallRSystem(Document document, MaltipsSystem newMaltipsSystem) throws InvalidXMLFormatException, GameAlreadySetException {
        Element root = document.getDocumentElement();
        NodeList rSystemList = root.getElementsByTagName(ROOT_RSYSTEM);
        Node rSystemNode = rSystemList.item(0);   // shall only be one tag with this name.
        DOMUtility util = new DOMUtility();

        // set system type
        Node systemTypeNode = util.findSubNode(ROOT_RSYSTEM_SYSTEMTYPE, rSystemNode);
        String tmpSystemType = util.getText(systemTypeNode);
        newMaltipsSystem.getRsystem().setRSystemType(new RSystemType(tmpSystemType));

        // set rsystem.
        Node rSystemRowsNode = util.findSubNode(ROOT_RSYSTEM_RSYSTEM, rSystemNode);

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            Node currentRow = util.findSubNode((ROOT_RSYSTEM_RSYSTEM_ROW + i), rSystemRowsNode);

            // get value
            String value = util.getText(currentRow);

            if (value.length() == 1 || value.length() == 2) {
                newMaltipsSystem.getRsystem().setRSystemRow(i, Integer.parseInt(value));
            }
        }
    }
}
