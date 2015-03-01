package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import java.util.ArrayList;
import java.util.List;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;
import org.robert.tips.stryktips.types.StryktipsGame;

/**
 * Har sökningen hamnat i en återvändsgränd?
 * 
 * <pre>
 * Regel: 
 * 1. Ett system har hittats med samma antal rader x gånger. 
 * 2. Samma antal rader har upprepats i sekvens 1 000 gånger, t.ex 55,56,55,56,55,56...
 * </pre>
 */
public class InStaleState extends IterativeState {
	public static int STALE_LIMIT = 0;
	public static int numberOfTimesWithSameRowCount = 0;

	public InStaleState(AlgorithmContext context) {
		super(context);

		if (STALE_LIMIT == 0) {
			STALE_LIMIT = context.systemAtServer
					.getNumberOfRowsInMathematicalSystem() * 2;
		}
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		// 1. Ett system har hittats med samma antal rader x gånger.
		IterativeContext iterativeCtx = (IterativeContext) context;
		int numberOfRowsInFoundSystem = context.systemUnderVerification.size();
		if (iterativeCtx.currentNumberOfRowsInFoundSystem == numberOfRowsInFoundSystem) {
			iterativeCtx.numberOfTimesFoundSameSystem++;
		}

		// System.out.println(iterativeCtx.numberOfTimesFoundSameSystem +
		// ",prev="
		// + iterativeCtx.previousNumberOfRowsInFoundSystem + ", curr="
		// + iterativeCtx.currentNumberOfRowsInFoundSystem + ", now="
		// + numberOfRowsInFoundSystem);
		if (iterativeCtx.numberOfTimesFoundSameSystem > STALE_LIMIT) {
			System.out.println("StaleState: same system found too many times.");
			return new ScrambleSystem(context);
		}

		// 2. Samma antal rader har upprepats i sekvens 1 000 gånger, t.ex
		// 55,56,55,56,55,56...
		if (iterativeCtx.previousNumberOfRowsInFoundSystem == numberOfRowsInFoundSystem) {
			// System.out.println("loop="
			// + iterativeCtx.numberOfTimesFoundSystemInLoop);

			iterativeCtx.numberOfTimesFoundSystemInLoop++;
			if (iterativeCtx.numberOfTimesFoundSystemInLoop > STALE_LIMIT) {
				System.out
						.println("InStaleState: Scramble system due to sequence, 55,56,55...");
				System.out.println(context.systemUnderVerification.toString());

				// Print debug information
				if (iterativeCtx.previousFoundSystem != null) {
					List<StryktipsGame> l = new ArrayList<StryktipsGame>(
							iterativeCtx.systemUnderVerification);
					int numberOfEquals = compare(
							iterativeCtx.previousFoundSystem, l);
					System.out
							.println("Number of equal rows="
									+ numberOfEquals
									+ ", tot#"
									+ iterativeCtx.systemUnderVerification
											.size()
									+ ", original size = "
									+ iterativeCtx.systemUnderVerificationOriginal.length);
				}
				iterativeCtx.previousFoundSystem = new ArrayList<StryktipsGame>(
						iterativeCtx.systemUnderVerification);
				// return new ScrambleSystem(context);
			}
		}

		iterativeCtx.previousNumberOfRowsInFoundSystem = iterativeCtx.currentNumberOfRowsInFoundSystem;
		iterativeCtx.currentNumberOfRowsInFoundSystem = numberOfRowsInFoundSystem;

		return new DontSendSystem(context);
	}
}
