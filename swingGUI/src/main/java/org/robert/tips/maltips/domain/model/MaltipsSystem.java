package org.robert.tips.maltips.domain.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.robert.tips.maltips.types.MaltipsConstants;
import org.robert.tips.maltips.types.MaltipsGame;

/**
 * This is the root entity in the Maltips aggregate.
 * @author Robert
 */
public class MaltipsSystem {

    private Mathimatical mathimatical;
    private Bankers bankers;
    private File fileName;			    // Filename
    private RSystem rsystem;
    private SingleSystem singleSystem;

    public MaltipsSystem() {
        this.mathimatical = new Mathimatical(this);
        this.bankers = new Bankers(this);
        this.rsystem = new RSystem(this);
        this.singleSystem = new SingleSystem(this);
    }
    /**
     * Contains the reduced rows. The object type in the container is StryktipsGame.
     */
    private ArrayList reducedSystem = new ArrayList();

    /**
     * @return the mathimatical
     */
    public Mathimatical getMathimatical() {
        return mathimatical;
    }

    /**
     * @return the fileName
     */
    public File getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(File fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the reducedSystem
     */
    public ArrayList getReducedSystem() {
        return reducedSystem;
    }

    /**
     * @return the bankers
     */
    public Bankers getBankers() {
        return bankers;
    }

    /**
     * @return the rsystem
     */
    public RSystem getRsystem() {
        return rsystem;
    }

    /**
     * @return the singleSystem
     */
    public SingleSystem getSingleSystem() {
        return singleSystem;
    }

    /**
     * @param reducedSystem the reducedSystem to set
     */
    public void setReducedSystem(ArrayList reducedSystem) {
        this.reducedSystem = reducedSystem;
    }

    /**
     * Calculate number of rights in the reduced system with the single row set
     * in this document model.
     * @param rightRow The right row. { 1, 2, 3,... }
     * @return Contains data for how many rights there are for a number of rights,
     * e.g { 0 - 8 } rights. This is for example
     * that numberOfRights[6]=10 is that there are 10 rows that gives 6 rights.
     */
    public int[] checkNumberOfRights(int[] rightRow) {
        int[] numberOfRights = new int[MaltipsConstants.NUMBER_GAMES_IN_MALTIPS + 1];

        Iterator iterator = getReducedSystem().iterator();

        while (iterator.hasNext()) {
            MaltipsGame game = (MaltipsGame) iterator.next();
            int[] maltipsRow = game.getMaltipsRow();

            int numberOfRightsInCurrentRow = 0;

            // check the curret reduced row againt the right row
            for (int i = 0; i < MaltipsConstants.NUMBER_GAMES_IN_MALTIPS; i++) {
                for (int j = 0; j < MaltipsConstants.NUMBER_GAMES_IN_MALTIPS; j++) {
                    if (maltipsRow[i] == rightRow[j]) {
                        numberOfRightsInCurrentRow++;
                        break;
                    }
                }
            }

            numberOfRights[numberOfRightsInCurrentRow]++;
        }

        return numberOfRights;
    }
}
