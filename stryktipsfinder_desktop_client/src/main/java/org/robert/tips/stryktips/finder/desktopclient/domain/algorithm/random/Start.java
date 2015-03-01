package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.random;

import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.RSystemSearchInfo;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;

/**
 * Get the latest system from server.
 * 
 * @author Robert Georen.
 * 
 */
public class Start extends RandomState {

	Start(AlgorithmContext ctx) {
		super(ctx);
	}

	@Override
	public RandomState next() throws TechnicalException {
		// Get latest system from server.
		RSystemSearchInfo systemInfo = context.businessDelegate
				.getSystemInfoWithOnlyLatestSystem(context.systemAtServerId);

		context.setSystemAtServer(systemInfo);

		// Create the mathematical system for the fetched system from server.
		createMathemticalSystem();

		return new Generate(context);
	}

}
