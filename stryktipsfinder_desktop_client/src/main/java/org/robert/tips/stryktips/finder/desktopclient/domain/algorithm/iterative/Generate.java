package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;
import org.robert.tips.stryktips.types.StryktipsGame;

/**
 * State: Generate Plocka bort en rad.
 * 
 * 
 * @author Robert
 * 
 */
public class Generate extends AlgorithmState {

	public Generate(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		int rowToRemove = ((IterativeContext) context).currentlyRemovedRow;
		StryktipsGame stryktipsRowToRemove = ((IterativeContext) context).systemUnderVerificationOriginal[rowToRemove];

		// Remove the row.
		boolean isRemoved = context.systemUnderVerification
				.remove(stryktipsRowToRemove);
		
		if (isRemoved == false) {
			// Try to remove next row
			rowToRemove++;
			stryktipsRowToRemove = ((IterativeContext) context).systemUnderVerificationOriginal[rowToRemove];			
			isRemoved = context.systemUnderVerification.remove(stryktipsRowToRemove);
		}
		
		context.numberOfTestRuns++;
//		System.out.println("Generate: Remove row(" + rowToRemove + "):"
//				+ stryktipsRowToRemove + ", isRemoved= " + isRemoved
//				+ ", size=" + context.systemUnderVerification.size());
		return new VerifySystem(context);
	}
}
