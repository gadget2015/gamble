package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.random;

import java.util.HashMap;
import java.util.Random;

import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.types.StryktipsGame;

/**
 * Generate a new stryktips system to test.
 * 
 * @author Robert.
 * 
 */
public class Generate extends RandomState {

	public Generate(AlgorithmContext ctx) {
		super(ctx);
	}

	@Override
	public RandomState next() {
		// Create a new random system with one less row.
		int numberOfRowsAtSever = context.systemAtServer.getNumberOfRows();

		HashMap<String, StryktipsGame> systemToTest = new HashMap<String, StryktipsGame>();

		while (systemToTest.size() < (numberOfRowsAtSever - 1)) {
			Random rnd = new Random();
			int rowNumber = rnd.nextInt(context.mathematicalSystemRows.size());
			StryktipsGame row = context.mathematicalSystemRows.get(rowNumber);
			
			systemToTest.put(Integer.toString(rowNumber), row);
		}

		context.systemUnderVerification = systemToTest.values();
		context.numberOfTestRuns++;

		// Change to next state.
		return new VerifySystem(context);
	}
}
