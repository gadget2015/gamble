package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough;

import java.text.NumberFormat;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * Reservera rader som datorn kan arbeta med.
 * 
 * <pre>
 * 1. Call server and get start position, e.g first row to start searching from. 
 * 2. Create combinations from start position.
 * </pre>
 * 
 * @author Robert
 * 
 */
public class Reserve extends StepThroughState {

	public Reserve(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		// Get start position from server.
		NumberFormat nf = NumberFormat.getInstance();
		context.callbackHandler
				.callback("Call server to reserve combinations to test, try to reserve "
						+ nf.format(((StepThroughContext) context).NUMBER_OF_COMBINATIONS_TO_RESERVE)
						+ " combinations.");
		long startCombination = context.businessDelegate
				.reserveNextUnitOfWork(
						context.systemAtServerId,
						((StepThroughContext) context).NUMBER_OF_COMBINATIONS_TO_RESERVE);

		if (startCombination == -1) {
			// -1 defines that the search has ended.
			context.callbackHandler
					.callback("Search has ended, all combinations had been tested.");
			context.callbackHandler.endSearch();

			return new EndState(context);
		}

		((StepThroughContext) context).startPosition = startCombination;
		((StepThroughContext) context).nextStartPosition = startCombination;

		return new SplitUnitOfWork(context);
	}
}
