package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough;

import java.util.List;

import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchCallbackHandler;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;

import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;

/**
 * State context. pattern: State pattern, GoF[95]
 * 
 * @author Robert.
 * 
 */
public class StepThroughContext extends AlgorithmContext {

	/**
	 * The start position in list of all possible combinations.
	 */
	public long startPosition;

	/**
	 * Contains all the combinations that shall be tested. Each value point the
	 * the number in the mathematical system. index=0 point to the first row in
	 * mathematical system.
	 */
	public List<int[]> combinations;

	/**
	 * Defines how many rows the client wants to reserve from server. 
	 */
	public int NUMBER_OF_COMBINATIONS_TO_RESERVE = 100000000;

	/**
	 * Hold the next combination to test. Refers to the combinations array.
	 */
	public int nextCombinationToTest;

	/**
	 * Defines number of combinations that the split should split reservation
	 * into. Default is 1 million.
	 */
	public int NUMBER_OF_COMBINATIONS_IN_CHUNK = 1000000;

	/**
	 * Defines next start position to use when creating combinations to test.
	 */
	public long nextStartPosition;

	public StepThroughContext(StryktipsFinderBD bd, long systemId,
			SearchCallbackHandler callbackHandler) {
		this.state = new Start(this);
		this.businessDelegate = bd;
		this.systemAtServerId = systemId;
		this.callbackHandler = callbackHandler;
		this.callbackHandler.context = this;
	}

}
