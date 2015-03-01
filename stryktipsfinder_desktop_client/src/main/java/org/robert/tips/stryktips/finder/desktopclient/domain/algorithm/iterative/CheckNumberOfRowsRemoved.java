package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * State: CheckNumberOfRowsRemoved Har alla N rader provats att plockas bort?
 */
public class CheckNumberOfRowsRemoved extends AlgorithmState {

	public CheckNumberOfRowsRemoved(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		int currentlyRemovedRow = ((IterativeContext) context).currentlyRemovedRow;
		//System.out.println("CheckNumberOfRowsRemoved:" + context.systemUnderVerification.size() + "," + currentlyRemovedRow);
		if (currentlyRemovedRow == context.systemUnderVerification.size() - 1) {
			// No more idea to try to remove a row.
			((IterativeContext) context).currentlyRemovedRow = 0;
			return new AddNewRow(context);
		} else {
			// Set pointer to next row to remove.
			((IterativeContext) context).currentlyRemovedRow++;
			return new Generate(context);
		}
	}

}
