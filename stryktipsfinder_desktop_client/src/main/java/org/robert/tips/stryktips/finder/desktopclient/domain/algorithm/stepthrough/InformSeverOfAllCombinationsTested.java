package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * Maybe send server statistics of how many test runs that have been performed.
 * 
 */
public class InformSeverOfAllCombinationsTested extends StepThroughState {
	public static long startTime;

	public InformSeverOfAllCombinationsTested(AlgorithmContext context) {
		super(context);

		if (startTime == 0) {
			startTime = System.currentTimeMillis();
		}
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		// 1. inform server that the unit of work is completed.
		context.businessDelegate
				.completedUnitOfWork(
						context.systemAtServer.getId(),
						((StepThroughContext) context).startPosition,
						((StepThroughContext) context).NUMBER_OF_COMBINATIONS_TO_RESERVE);

		// 2. Decide if the server shall be informed so it can
		// gather statistics.
		long delta = System.currentTimeMillis() - startTime;

		if (delta < context.SHOULD_SEND_INFORMATION_TO_SERVER_IN_MILLISECONDS) {
			return new Start(context);
		} else {
			startTime = 0; // reset value.

			context.businessDelegate.informServerNumberOfTestRuns(
					context.systemAtServer.getId(), context.numberOfTestRuns);
			context.numberOfTestRuns = 0;

			return new Start(context);
		}

	}
}
