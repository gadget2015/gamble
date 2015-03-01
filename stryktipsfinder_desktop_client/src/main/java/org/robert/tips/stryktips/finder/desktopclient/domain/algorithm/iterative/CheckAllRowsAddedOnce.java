package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * Check if all mathematical rows have been added once.
 * 
 */
public class CheckAllRowsAddedOnce extends AlgorithmState {

	public CheckAllRowsAddedOnce(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		IterativeContext iterativeContext = (IterativeContext) context;
		int numberOfRowsToAdd = context.mathematicalSystemRows.size()
				- context.systemUnderVerification.size() + 1;
//		System.out.println("CheckAllRowsAddedOnce: #rows added = "
//				+ iterativeContext.numberOfAddedRows + ", #rows to add = "
//				+ numberOfRowsToAdd);
		if (numberOfRowsToAdd == iterativeContext.numberOfAddedRows) {
//			System.out.println("CheckAllRowsAddedOnce:DONE.");
			return new CheckTestRuns(context);
		} else {
			return new Generate(context);
		}
	}
}
