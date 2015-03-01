package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;
import org.robert.tips.stryktips.types.StryktipsGame;

/**
 * State: PutBackRow Lägg tillbaka den tidigare borttagna raden.
 * 
 */
public class PutBackRow extends AlgorithmState {

	public PutBackRow(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		int removedRow = ((IterativeContext) context).currentlyRemovedRow;
		StryktipsGame stryktipsRowThatIsRemoved = ((IterativeContext) context).systemUnderVerificationOriginal[removedRow];
		context.systemUnderVerification.add(stryktipsRowThatIsRemoved);
		//System.out.println("PutBackRow:" + stryktipsRowThatIsRemoved);
		return new CheckNumberOfRowsRemoved(context);
	}
}
