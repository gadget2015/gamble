package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.random;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;

/**
 * Send the stryktips system to the server so other can fetch it.
 * 
 * @author Robert.
 * 
 */
public class SendSystem extends RandomState {

	public SendSystem(AlgorithmContext ctx) {
		super(ctx);
	}

	@Override
	public RandomState next() throws TechnicalException, DomainException {
		sendSystemToServer();
		//printSystemUnderVerification();
		return new Start(context);
	}

}
