package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * State: SendSystem Skicka upp raden till server.
 * 
 * @author Robert
 * 
 */
public class SendSystem extends IterativeState {

	public SendSystem(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		// 1. scramble the found system, this will make the search not constant
		// found same lowest system.
		context.systemUnderVerification = scrambleSystemUnderVerification();

		// Send system to server.
		sendSystemToServer();
		
		// reset variables.
		resetSearchVariables();
		//printSystemUnderVerification();

		return new Start(context);
	}

}
