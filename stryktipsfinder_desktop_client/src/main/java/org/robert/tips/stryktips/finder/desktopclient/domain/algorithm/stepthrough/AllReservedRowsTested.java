package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * Check if all combinations reserved has been tested.
 * Increment pointer of which combination to test.
 * 
 */
public class AllReservedRowsTested extends StepThroughState {

	public AllReservedRowsTested(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		StepThroughContext stepThroughCtx = (StepThroughContext) context;

		if (stepThroughCtx.nextCombinationToTest == stepThroughCtx.NUMBER_OF_COMBINATIONS_IN_CHUNK - 1) {
			return new AllSplittedUnitOfWorksTested(context);
		} else {
			stepThroughCtx.nextCombinationToTest++;

			return new Generate(context);
		}
	}
}
