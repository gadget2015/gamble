package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * Är det funna systemet bättre än det som finns på servern?
 * 
 * @author Robert
 * 
 */
public class ShouldSendSystemToServer extends IterativeState {

	public ShouldSendSystemToServer(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		int numberOfRowsInFoundSystem = context.systemUnderVerification.size();
		int numberOfRowsInSystemAtServer = context.systemAtServer
				.getNumberOfRows();
//		System.out.println("ShouldSendSystemToServer: Found a system, # rows= "
//				+ numberOfRowsInFoundSystem + ", # rows server = "
//				+ numberOfRowsInSystemAtServer + ", currentlyRemovedRow="
//				+ ((IterativeContext) context).currentlyRemovedRow);
		if (numberOfRowsInFoundSystem < numberOfRowsInSystemAtServer) {
			return new SendSystem(context);
		} else {
			return new InStaleState(context);
		}
	}
}
