package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough;

import java.text.NumberFormat;
import java.util.List;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;
import org.robert.tips.util.Combination;

/**
 * Split of UnitOfWork into 2 GB pieces,e.g so it can fit into RAM.
 * 
 * @author Robert
 * 
 */
public class SplitUnitOfWork extends StepThroughState {

	public SplitUnitOfWork(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		StepThroughContext stepThrougCtx = (StepThroughContext) context;

		// Create combinations
		int n = context.systemAtServer.getNumberOfRowsInMathematicalSystem();
		int r = context.systemAtServer.getNumberOfRowsToSearchFor();
		Combination combination = new Combination(n, r);

		context.callbackHandler
				.callback("Create list of combinations to test.");
		combination.createCombinations(stepThrougCtx.nextStartPosition,
				stepThrougCtx.NUMBER_OF_COMBINATIONS_IN_CHUNK);
		List<int[]> combinations = combination.getCombinations();
		// nice message to actor
		NumberFormat nf = NumberFormat.getInstance();
		context.callbackHandler.callback("Start testing "
				+ nf.format(combinations.size())
				+ " combinations of kombin(n/r)=(" + n + "/" + r
				+ "). Combination position = "
				+ nf.format(stepThrougCtx.nextStartPosition));

		// Set combinations in context.
		stepThrougCtx.combinations = combinations;

		// Update next positions for creating combinations.
		stepThrougCtx.nextStartPosition += stepThrougCtx.NUMBER_OF_COMBINATIONS_IN_CHUNK;

		return new Generate(context);
	}
}
