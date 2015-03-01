package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough;

import java.util.ArrayList;
import java.util.List;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;
import org.robert.tips.stryktips.types.StryktipsGame;

/**
 * Get next combinations to test and use those combinations in conjunction with
 * mathematical system to set the system to verify.
 * 
 */
public class Generate extends StepThroughState {

	public Generate(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		StepThroughContext stepThroughCtx = (StepThroughContext) context;

		// Get combination to use when creating system to test.
		int numberOfRowsToSearchFor = context.systemAtServer
				.getNumberOfRowsToSearchFor();
		int combinationToTest = stepThroughCtx.nextCombinationToTest;
		int[] combination = stepThroughCtx.combinations.get(combinationToTest);

		// Add combination from mathematical system.
		List<StryktipsGame> systemToTest = new ArrayList<StryktipsGame>();

		for (int i = 0; i < numberOfRowsToSearchFor; i++) {
			StryktipsGame game = context.mathematicalSystemRows
					.get(combination[i]);
			systemToTest.add(game);
		}

		// Update context.
		context.systemUnderVerification = systemToTest;
		context.numberOfTestRuns++;

		return new Verify(context);
	}
}
