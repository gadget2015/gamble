package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * Verify if the system under test gives 12 rights guarantee.
 * 
 */
public class Verify extends StepThroughState {

	public Verify(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		boolean okSystem = isSystemUnderVerificationOK();

		if (okSystem) {
			return new SendSystemToServer(context);
		} else {
			return new AllReservedRowsTested(context);
		}
		
	}

}
