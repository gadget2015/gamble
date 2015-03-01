package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.random;

import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchCallbackHandler;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;

/**
 * State context. pattern: State pattern, GoF[95]
 * 
 * @author Robert.
 * 
 */
public class RandomContext extends AlgorithmContext {
	/**
	 * Creates a new Random context.
	 */
	public RandomContext(StryktipsFinderBD bd, long systemId,
			SearchCallbackHandler callbackHandler) {
		this.state = new Start(this);
		this.businessDelegate = bd;
		this.systemAtServerId = systemId;
		this.callbackHandler = callbackHandler;
		this.callbackHandler.context = this;
		CheckTestRuns.startTime = 0;
	}
}
