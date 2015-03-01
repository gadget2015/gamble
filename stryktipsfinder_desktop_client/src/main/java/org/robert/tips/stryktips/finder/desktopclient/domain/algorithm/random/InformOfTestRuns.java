package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.random;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;

/**
 * Inform server that the client have done 100 000 tests. This is so the server
 * can show the speed for finding systems.
 * 
 * @author Robert.
 * 
 */
public class InformOfTestRuns extends RandomState {

	public InformOfTestRuns(AlgorithmContext ctx) {
		super(ctx);
	}

	@Override
	public RandomState next() throws TechnicalException, DomainException {
		context.businessDelegate.informServerNumberOfTestRuns(
				context.systemAtServer.getId(), context.numberOfTestRuns);

		context.callbackHandler
				.callback("Inform server that no systems where found in "
						+ context.numberOfTestRuns + " tested rows.");

		context.numberOfTestRuns = 0;

		return new Start(context);
	}

}
