package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * ScrambleSystem: Slumpa 10% nya rader för att skaka om lite. Om klienten har
 * skakat om systemet 1 000 gånger så skakas det om med 20% nya rader en gång.
 * 
 * @author Robert
 * 
 */
public class ScrambleSystem extends IterativeState {
	public static double NUMBER_OF_PERCENT_TO_SCRAMBLE = 0.1;

	public ScrambleSystem(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		// calculate how many rows to add.
		int numberOfRowsToScramble = 0;
		if (((IterativeContext) context).numberOfScrambles++ == 1000) {
			numberOfRowsToScramble = new Double(
					context.systemUnderVerification.size() * 0.2).intValue();
			((IterativeContext) context).numberOfScrambles = 0;
			context.callbackHandler
					.callback("Scramble system with 20 % new rows.");
		} else {
			numberOfRowsToScramble = new Double(
					context.systemUnderVerification.size()
							* NUMBER_OF_PERCENT_TO_SCRAMBLE).intValue();
		}
		// Add rows
		Integer pointer = new Integer(0);

		for (int i = 0; i < numberOfRowsToScramble; i++) {
			addOneNewRow(pointer);
		}

		// Update information to actor
		context.callbackHandler.callback("Scramble system. Added "
				+ numberOfRowsToScramble + " rows.");

		// update counters to their initial values.
		resetSearchVariables();
		// System.out.println("ScrambleSystem: new number of rows = "
		// + context.systemUnderVerification.size());
		return new Generate(context);
	}

}
