package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.RSystemSearchInfo;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;

/**
 * Start state: init the search algorithm:
 * 
 * <pre>
 * 1. Call server and get system info.
 * 2. Create mathematical system.
 * </pre>
 * 
 * @author Robert
 * 
 */
public class Start extends StepThroughState {

	public Start(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		// Get latest system from server.
		RSystemSearchInfo systemInfo = context.businessDelegate
				.getSystemInfoWithOnlyLatestSystem(context.systemAtServerId);

		context.setSystemAtServer(systemInfo);

		createMathemticalSystem();
		resetValues();
		
		return new Reserve(context);
	}

}
