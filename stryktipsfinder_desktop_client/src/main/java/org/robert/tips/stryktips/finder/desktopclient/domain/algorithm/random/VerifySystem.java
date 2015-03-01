package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.random;

import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;

/**
 * Verify the system that it has 12 rights guarantee.
 * 
 * @author Robert.
 * 
 */
public class VerifySystem extends RandomState {

	public VerifySystem(AlgorithmContext ctx) {
		super(ctx);
	}

	@Override
	public RandomState next() {
		boolean okSystem = isSystemUnderVerificationOK();

		if (okSystem) {
			return new SendSystem(context);
		} else {
			return new CheckTestRuns(context);
		}
	}

}
