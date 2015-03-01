package org.robert.tips.util;

import org.robert.tips.maltips.domain.model.reduce.MathimaticalSystemReducer;
import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.maltips.MaltipsDocument;
import org.robert.tips.maltips.domain.model.reduce.MathimaticalSystemReducer;

/**
 * Utility class used by unittests.
 * @author Robert
 */
public class MaltipsUtility {

    public MaltipsUtility() {
    }

    /**
     * Create a mathimatical system with 12 selected matches. 12 over 8 and
     * reduce it to 495 rows.
     * @return
     */
    public MaltipsDocument create_M_12_8_495() {
        MaltipsDocument maltipsSystem = new MaltipsDocument();
        MathimaticalSystemReducer reducer = new MathimaticalSystemReducer(maltipsSystem.getMaltipsSystem());

        try {
            // Create testdata.
            for (int i = 0; i < 12; i++) {
                maltipsSystem.getMaltipsSystem().getMathimatical().setMathimaticalRow(i, i + 1);
            }

            reducer.reduce();

            return maltipsSystem;
        } catch (Throwable e) {
            throw new RuntimeException("Major error in unittest utility");
        }
    }

    public MaltipsDocument create_R_14() throws GameAlreadySetException {
        MaltipsDocument maltipsSystem = new MaltipsDocument();

        for (int i = 0; i < 14; i++) {
            maltipsSystem.getMaltipsSystem().getRsystem().setRSystemRow(i, i + 1);
        }

        return maltipsSystem;
    }

    public MaltipsDocument create_M_13_8_1287() {
        MaltipsDocument maltipsSystem = new MaltipsDocument();
        MathimaticalSystemReducer reducer = new MathimaticalSystemReducer(maltipsSystem.getMaltipsSystem());

        try {
            // Create testdata.
            for (int i = 0; i < 13; i++) {
                maltipsSystem.getMaltipsSystem().getMathimatical().setMathimaticalRow(i, i + 1);
            }

            reducer.reduce();

            return maltipsSystem;
        } catch (Throwable e) {
            throw new RuntimeException("Major error in unittest utility");
        }
    }
}
