package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm;

import java.util.Collection;
import java.util.List;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.RSystemSearchInfo;
import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchCallbackHandler;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;
import org.robert.tips.stryktips.types.StryktipsGame;

/**
 * Base class for all algorithms contexts.
 * 
 * @author Robert
 * 
 */
public abstract class AlgorithmContext {
	public AlgorithmState state;
	public StryktipsFinderBD businessDelegate;
	public long systemAtServerId;
	public RSystemSearchInfo systemAtServer;
	public Collection<StryktipsGame> systemUnderVerification;

	// Mathematical system created from system from server.
	public List<StryktipsGame> mathematicalSystemRows;

	public int numberOfRightsInSystemUnderTest;
	public int numberOfTestRuns;

	public SearchCallbackHandler callbackHandler;
	
	/**
	 * Times in milliseconds before sending statistics to server/or getting new
	 * system from server. Default: 120 minuter= 1000* 60 * 120 = 7 200 000 millisekunder.
	 */
	public int SHOULD_SEND_INFORMATION_TO_SERVER_IN_MILLISECONDS = 7200000;

	public void setSystemAtServer(RSystemSearchInfo systemInfo) {
		this.systemAtServer = systemInfo;
	}

	public void next() throws TechnicalException, DomainException {
		this.state = state.next();
	}
}
