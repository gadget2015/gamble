package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.random;

import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * Super state for the Random Algorithm. Pattern: State GoF[95].
 */
public abstract class RandomState extends AlgorithmState{
	public RandomState(AlgorithmContext ctx) {
		super(ctx);
	}
}
