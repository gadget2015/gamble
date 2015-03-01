package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * Informera servern hur många tester som körts.
 * 
 */
public class InformServerOfTestRuns extends IterativeState {

	public InformServerOfTestRuns(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		context.businessDelegate.informServerNumberOfTestRuns(
				context.systemAtServerId, context.numberOfTestRuns);
		// 1. Call business tier.
		context.businessDelegate.informServerNumberOfTestRuns(
				context.systemAtServerId, context.numberOfTestRuns);

		// 2. Reset number of test runs so when server is informed next time it
		// will be the delta value sent.
		context.callbackHandler.callback("Inform server of # test runs.");
		resetSearchVariables();

		return new Start(context);
	}

}
