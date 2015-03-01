package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * State: CheckTestRuns Har provat att köra systemet X minuter? Om sökning
 * pågått x minuter så informeras servern om det.
 * 
 */
public class CheckTestRuns extends AlgorithmState {
	public static long startTime;

	public CheckTestRuns(AlgorithmContext context) {
		super(context);

		if (startTime == 0) {
			startTime = System.currentTimeMillis();
		}
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		long delta = System.currentTimeMillis() - startTime;
		
		if (delta <= context.SHOULD_SEND_INFORMATION_TO_SERVER_IN_MILLISECONDS) {
			return new ScrambleSystem(context);
		} else {
			startTime = 0; // reset value.
			return new InformServerOfTestRuns(context);
		}

	}

}
