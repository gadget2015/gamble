package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.random;

import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;

/**
 * Check how many rows that have beens tested. There will be a limit of 2 000
 * 000 tests before the server will be informed of about that the client has
 * performed so many tests.
 * 
 * @author Robert Georen.
 * 
 */
public class CheckTestRuns extends RandomState {
	public static long startTime;

	public CheckTestRuns(AlgorithmContext ctx) {
		super(ctx);

		if (startTime == 0) {
			startTime = System.currentTimeMillis();
		}
	}

	@Override
	public RandomState next() {
		long delta = System.currentTimeMillis() - startTime;

		if (delta <= context.SHOULD_SEND_INFORMATION_TO_SERVER_IN_MILLISECONDS) {
			return new Generate(context);
		} else {
			startTime = 0;
			return new InformOfTestRuns(context);
		}
	}

}
