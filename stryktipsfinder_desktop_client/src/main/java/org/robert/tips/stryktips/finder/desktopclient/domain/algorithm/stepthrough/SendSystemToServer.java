package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * Send the system to server.
 * 
 */
public class SendSystemToServer extends StepThroughState {

	public SendSystemToServer(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		sendSystemToServer();
		
		context.callbackHandler
				.callback("The search will end now because a system was found.");
		context.callbackHandler.endSearch();
		
		return new EndState(context);
	}

}
