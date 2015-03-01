package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import java.util.List;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.RSystemSearchInfo;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;
import org.robert.tips.stryktips.types.StryktipsGame;

/**
 * State: Start Hämta ner det senaste systemet.
 * 
 * <pre>
 * Rules: 
 *    - Börja ifrån början av det matematiska systemet när rader ska plockas bort.
 * </pre>
 * 
 * @author Robert
 * 
 */
public class Start extends IterativeState {
	public static String NEWLINE = System.getProperty("line.separator");

	public Start(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		// 1. Get latest system from server.
		RSystemSearchInfo systemInfo = context.businessDelegate
				.getSystemInfoWithOnlyLatestSystem(context.systemAtServerId);

		context.setSystemAtServer(systemInfo);

		// Create the mathematical system for the fetched system from server.
		createMathemticalSystem();

		// 2. Initialize test system
		List<StryktipsGame> games = convertToStryktipsGameDomainObjects(systemInfo
				.getSystems().get(0));
		context.systemUnderVerification = games;
		// context.systemUnderVerification = new Vector<StryktipsGame>();
		// StryktipsSystem latestSystem = systemInfo.getSystems().get(0);
		// StringTokenizer st = new
		// StringTokenizer(latestSystem.getSystemRows(),
		// NEWLINE);
		// int numberOfRows = latestSystem.getNumberOfRows();
		//
		// for (int i = 0; i < numberOfRows; i++) {
		// StryktipsGame row = new StryktipsGame(st.nextToken());
		// context.systemUnderVerification.add(row);
		// }

		// 3. Initialize original test system, used as as starting point.
		copySystemUnderVerification();

		// 4. Set starting position to first row in mathematical system.
		((IterativeContext) context).currentlyRemovedRow = 0;

		return new Generate(context);
	}
}
