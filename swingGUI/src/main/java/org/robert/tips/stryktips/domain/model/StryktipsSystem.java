package org.robert.tips.stryktips.domain.model;

import java.io.File;
import java.util.ArrayList;

/**
 * This is the root entity in the stryktips aggregate.
 * @author Robert
 */
public class StryktipsSystem {

    private Odds odds;
    private Mathimatical mathimatical;
    private RSystem Rsystem;
    private Banker banker;
    private Combination combination;
    private File fileName;			    // Filename
    private Extended  extended;

    /**
     * Contains the reduced rows. The object type in the container is StryktipsGame.
     */
    private ArrayList reducedSystem = new ArrayList();

    public StryktipsSystem() {
        this.odds = new Odds(this);
        this.mathimatical = new Mathimatical(this);
        this.Rsystem = new RSystem(this);
        this.banker = new Banker(this);
        this.combination = new Combination(this);
        this.extended = new Extended(this);
    }

    public Odds getOdds() {
        return odds;
    }

    public void setOdds(Odds odds) {
        this.odds = odds;
    }

    public File getFileName() {
        return fileName;
    }

    public void setFileName(File fileName) {
        this.fileName = fileName;
    }

    public ArrayList getReducedSystem() {
        return reducedSystem;
    }

    public void setReducedSystem(ArrayList reducedSystem) {
        this.reducedSystem = reducedSystem;
    }

    public Mathimatical getMathimatical() {
        return mathimatical;
    }

    public void setMathimatical(Mathimatical mathimatical) {
        this.mathimatical = mathimatical;
    }

    public RSystem getRsystem() {
        return Rsystem;
    }

    public Banker getBanker() {
        return banker;
    }

    public Combination getCombination() {
        return combination;
    }

    public Extended getExtended() {
        return extended;
    }

    public void setExtended(Extended extended) {
        this.extended = extended;
    }
}
