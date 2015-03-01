package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.StryktipsSystem;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;
import org.robert.tips.stryktips.types.StryktipsGame;

/**
 * Super state for the Iterative Algorithm. Pattern: State GoF[95].
 */
public abstract class IterativeState extends AlgorithmState {

	public IterativeState(AlgorithmContext context) {
		super(context);
	}

	/**
	 * Copy the system under verification and store it in an original attribute.
	 */
	protected void copySystemUnderVerification() {
		((IterativeContext) context).systemUnderVerificationOriginal = context.systemUnderVerification
				.toArray(new StryktipsGame[context.systemUnderVerification
						.size()]);
	}

	protected void addOneNewRow(Integer pointer) {
		StryktipsGame newStryktipsGame = context.mathematicalSystemRows
				.get(pointer++);

		while (context.systemUnderVerification.contains(newStryktipsGame)) {
			// Raden fanns redan, försöker nästa rad i det matematiska
			// systemt.
			// System.out.println("AddNewRow: " + newStryktipsGame.toString() +
			// " Already exist, current try:"
			// + iterativeContext.nextMathematicalRowToAdd);
			newStryktipsGame = context.mathematicalSystemRows.get(pointer++);
		}
		// System.out.println("AddNewRow: Add '" + newStryktipsGame.toString() +
		// "'" + ", next to try:" + iterativeContext.nextMathematicalRowToAdd);

		context.systemUnderVerification.add(newStryktipsGame);
	}

	protected void resetSearchVariables() {
		copySystemUnderVerification();

		resetCommonContextVariablesUsedInMainFlow();

		// Stale variables
		((IterativeContext) context).currentNumberOfRowsInFoundSystem = 0;
		((IterativeContext) context).numberOfTimesFoundSameSystem = 0;
		((IterativeContext) context).previousNumberOfRowsInFoundSystem = 0;
		((IterativeContext) context).numberOfTimesFoundSystemInLoop = 0;
		// System.out.println("resetSearchVariables");
	}

	protected void resetCommonContextVariablesUsedInMainFlow() {
		// Common variables used by main flow.
		((IterativeContext) context).nextMathematicalRowToAdd = 0;
		((IterativeContext) context).currentlyRemovedRow = 0;
		((IterativeContext) context).numberOfAddedRows = 0;
	}

	public List<StryktipsGame> convertToStryktipsGameDomainObjects(
			StryktipsSystem stryktipsSystem) {
		Vector<StryktipsGame> domainObjects = new Vector<StryktipsGame>();
		StringTokenizer st = new StringTokenizer(
				stryktipsSystem.getSystemRows(), Start.NEWLINE);
		int numberOfRows = stryktipsSystem.getNumberOfRows();

		for (int i = 0; i < numberOfRows; i++) {
			StryktipsGame row = new StryktipsGame(st.nextToken());
			domainObjects.add(row);
		}

		return domainObjects;
	}

	public int compare(List<StryktipsGame> rows1, List<StryktipsGame> rows2) {
		int numberOfEqualRows = 0;
		for (StryktipsGame game : rows1) {
			if (rows2.contains(game)) {
				numberOfEqualRows++;
			}
		}

		return numberOfEqualRows;
	}

	/**
	 * Scramble the system under verification, this will make the search not constant found
	 * same lowest system.
	 */
	protected LinkedList<StryktipsGame> scrambleSystemUnderVerification() {
		Random rnd = new Random();
		LinkedList<StryktipsGame> scrambled = new LinkedList<StryktipsGame>();
		
		for (StryktipsGame game : context.systemUnderVerification) {
			int first = rnd.nextInt(2);

			if (first == 1) {
				scrambled.addFirst(game);
			} else {
				scrambled.addLast(game);
			}
		}
		
		return scrambled;
	}
}
