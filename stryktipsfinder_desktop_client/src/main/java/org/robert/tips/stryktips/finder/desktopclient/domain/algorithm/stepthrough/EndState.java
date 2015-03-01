package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * Do nothing, just return same state.
 */
public class EndState extends StepThroughState {

	public EndState(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		return new EndState(context);
	}

}
