package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough;

import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * Super state for the Random Algorithm. Pattern: State GoF[95].
 */
public abstract class StepThroughState extends AlgorithmState {

	public StepThroughState(AlgorithmContext context) {
		super(context);
	}

	protected void resetValues() {
		((StepThroughContext) context).nextCombinationToTest = 0;
	}
}
