package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import java.util.List;

import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchCallbackHandler;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;
import org.robert.tips.stryktips.types.StryktipsGame;

/**
 * State context. pattern: State pattern, GoF[95]
 * 
 * @author Robert.
 * 
 */
public class IterativeContext extends AlgorithmContext {

	/**
	 * The currently removed row number from system under verification original.
	 */
	public int currentlyRemovedRow;

	/**
	 * Contains the original test system used by current test loop.
	 */
	public StryktipsGame[] systemUnderVerificationOriginal;

	public int numberOfAddedRows;

	/**
	 * Hold pointer to which row to add next from the mathematical system to the
	 * test system.
	 */
	public int nextMathematicalRowToAdd;

	public int currentNumberOfRowsInFoundSystem;

	public int numberOfTimesFoundSameSystem;

	public int previousNumberOfRowsInFoundSystem;

	public int numberOfTimesFoundSystemInLoop;

	public List<StryktipsGame> previousFoundSystem;

	/**
	 * Counter for how many times the client have scrambled the system.
	 */
	public int numberOfScrambles;

	/**
	 * Creates a new Iterative context.
	 */
	public IterativeContext(StryktipsFinderBD bd, long systemId,
			SearchCallbackHandler callbackHandler) {
		this.state = new Start(this);
		this.businessDelegate = bd;
		this.systemAtServerId = systemId;
		this.callbackHandler = callbackHandler;
		this.callbackHandler.context = this;
		CheckTestRuns.startTime = 0;
	}
}
