package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * Don't send the found system to server, just continue searching locally on the
 * client.
 */
public class DontSendSystem extends IterativeState {

	public DontSendSystem(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		IterativeContext iterativeCtx = ((IterativeContext) context);

		// reset start of search.
		context.systemUnderVerification = scrambleSystemUnderVerification();

		iterativeCtx.currentlyRemovedRow = 0;

		if (iterativeCtx.previousNumberOfRowsInFoundSystem > context.systemUnderVerification
				.size()) {
			// Reset which row to add next.
			iterativeCtx.nextMathematicalRowToAdd = 0;
			iterativeCtx.numberOfAddedRows = 0;
//			System.out
//					.println("DontSendSystem: Reset add mathimatical variables.");
		}

		copySystemUnderVerification();

		return new Generate(context);
	}
}
