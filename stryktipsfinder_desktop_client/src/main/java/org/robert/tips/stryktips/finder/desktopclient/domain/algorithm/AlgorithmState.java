package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.robert.tips.stryktips.domain.model.Mathimatical;
import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.RSystemSearchInfo;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.StryktipsSystem;
import org.robert.tips.stryktips.finder.desktopclient.dialog.Controller;
import org.robert.tips.stryktips.types.StryktipsGame;

/**
 * Super state all algorithm states. Pattern: State GoF[95].
 */
public abstract class AlgorithmState {
	public static final int SYSTEM_GUARANTEE = 12;
	public AlgorithmContext context;

	public AlgorithmState(AlgorithmContext context) {
		this.context = context;
	}

	/**
	 * Go to next state in the state machine.
	 */
	public abstract AlgorithmState next() throws TechnicalException,
			DomainException;

	/**
	 * Check if the system under verification full fills specified guarantee
	 * (normally 12 rights).
	 */
	public boolean isSystemUnderVerificationOK() {
		// Loop over all mathematical rows and each row should
		// Give at least 12 rights guarantee.
		int mathematicalRowNr = 0;
		for (StryktipsGame mathematicalRow : context.mathematicalSystemRows) {
			int maxNumberOfRights = 0;

			for (StryktipsGame testSystemRow : context.systemUnderVerification) {
				int numberOfRights = mathematicalRow
						.numberOfRights(testSystemRow);

				if (numberOfRights > maxNumberOfRights) {
					maxNumberOfRights = numberOfRights;
				}

				if (maxNumberOfRights >= SYSTEM_GUARANTEE) {
					break;
				}
			}
			// System.out.println("AlgoritmState: Math ID=" + mathematicalRowNr
			// + ", maxNumberOfRights=" + maxNumberOfRights);
			if (maxNumberOfRights < SYSTEM_GUARANTEE) {
				// Not a valid system.
				return false;
			}

			mathematicalRowNr++;
		}

		return true;
	}

	/**
	 * Send the found system to server, e.g make it public available.
	 */
	protected void sendSystemToServer() throws TechnicalException,
			DomainException {
		// 1. The found system must be added and only sent it to server.
		RSystemSearchInfo searchInfo = context.systemAtServer;
		StryktipsSystem dtoSystem = new StryktipsSystem();
		dtoSystem.setNumberOfRows(context.systemUnderVerification.size());
		dtoSystem.setPreviousStryktipsSystem(context.systemAtServer
				.getSystems().get(0).getId());

		// Add actual stryktips rows.
		StringBuffer sb = new StringBuffer();

		for (StryktipsGame row : context.systemUnderVerification) {
			sb.append(row.toString());
			sb.append(Controller.NEW_LINE); // new line
		}

		dtoSystem.setSystemRows(sb.toString());
		searchInfo.getSystems().clear(); // only sends new one
		searchInfo.getSystems().add(dtoSystem);

		// 3. Call server
		context.businessDelegate.sendSystemToServer(searchInfo);

		// 4. Reset test runs
		context.numberOfTestRuns = 0;

		// 5. Inform actor of event.
		context.callbackHandler
				.callback("Sent found system to server, number of rows = "
						+ dtoSystem.getNumberOfRows());
	}

	/**
	 * DEBUG method.
	 */
	protected void printSystemUnderVerification() {
		System.out.println("System # rows ="
				+ context.systemUnderVerification.size());
		for (StryktipsGame game : context.systemUnderVerification) {
			System.out.println(game);
		}
	}

	protected void createMathemticalSystem() {
		// Create the mathematical system for the fetched system from server.
		int numberOfHalfHedging = context.systemAtServer
				.getNumberOfHalfHedging();
		int numberOfHoleHedging = context.systemAtServer
				.getNumberOfHoleHedging();

		Mathimatical mathimaticalSystem = new Mathimatical(numberOfHalfHedging,
				numberOfHoleHedging);

		// Convert to domain type.
		List<StryktipsGame> rows = new ArrayList<StryktipsGame>();
		@SuppressWarnings("unchecked")
		List<char[]> mathematicalSystemRows = (List<char[]>) mathimaticalSystem
				.createsSingleRowsFromMathimaticalSystem();

		for (int i = 0; i < mathematicalSystemRows.size(); i++) {
			StryktipsGame row = new StryktipsGame(mathematicalSystemRows.get(i));
			rows.add(row);
		}

		context.mathematicalSystemRows = rows;
	}
}
