package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;


public class VerifySystem extends AlgorithmState {

	public VerifySystem(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		boolean okSystem = isSystemUnderVerificationOK();

		if (okSystem) {
			return new ShouldSendSystemToServer(context);
		} else {
			return new PutBackRow(context);
		}

	}

}
