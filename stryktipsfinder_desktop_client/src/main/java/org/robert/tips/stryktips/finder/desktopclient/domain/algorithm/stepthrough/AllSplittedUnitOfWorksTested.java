package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * Check if all reserved combinations are tested, e.g this implies that all
 * chunks has been tested.
 * 
 */
public class AllSplittedUnitOfWorksTested extends StepThroughState {

	public AllSplittedUnitOfWorksTested(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		StepThroughContext stepThroughCtx = (StepThroughContext) context;
		long testUpToThisCombination = stepThroughCtx.startPosition
				+ stepThroughCtx.NUMBER_OF_COMBINATIONS_TO_RESERVE
				- stepThroughCtx.NUMBER_OF_COMBINATIONS_IN_CHUNK;

		if (stepThroughCtx.nextStartPosition != testUpToThisCombination) {
			return new SplitUnitOfWork(context);
		} else {
			return new InformSeverOfAllCombinationsTested(context);
		}
	}

}
