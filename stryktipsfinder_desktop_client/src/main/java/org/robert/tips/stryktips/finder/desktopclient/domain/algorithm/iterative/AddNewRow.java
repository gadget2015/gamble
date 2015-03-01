package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmState;
import org.robert.tips.stryktips.types.StryktipsGame;

/**
 * State: AddNewRow - Lägg till en helt ny rad.
 * 
 * <pre>
 * Rule - Hämtar raden ifrån matematiskt system.
 * </pre>
 */
public class AddNewRow extends IterativeState {

	public AddNewRow(AlgorithmContext context) {
		super(context);
	}

	@Override
	public AlgorithmState next() throws TechnicalException, DomainException {
		IterativeContext iterativeContext = ((IterativeContext) context);
		// System.out.println("AddNewRow:# before:"
		// + context.systemUnderVerification.size()
		// + ", number of added rows:"
		// + iterativeContext.numberOfAddedRows + ", next row to add:"
		// + iterativeContext.nextMathematicalRowToAdd);

		// 1. Lägg tillbaka den nyss tillagda raden eftersom den inte gav något
		// bättre resultat.
		if (iterativeContext.nextMathematicalRowToAdd != 0) {
			// Det är inte första varvet.
			int rowToRemove = iterativeContext.nextMathematicalRowToAdd - 1;
			StryktipsGame previousAddedStryktipsGame = context.mathematicalSystemRows
					.get(rowToRemove);

			context.systemUnderVerification.remove(previousAddedStryktipsGame);
		}

		// 2.Hämtar nästa rad ifrån det matematiska systemet.
		try {

			StryktipsGame newStryktipsGame = context.mathematicalSystemRows
					.get(iterativeContext.nextMathematicalRowToAdd++);

			while (context.systemUnderVerification.contains(newStryktipsGame)) {
				// Raden fanns redan, försöker nästa rad i det matematiska
				// systemt.
				// System.out.println("AddNewRow: " +
				// newStryktipsGame.toString()
				// + " Already exist, index in mathimatical system:"
				// + (iterativeContext.nextMathematicalRowToAdd - 1));
				newStryktipsGame = context.mathematicalSystemRows
						.get(iterativeContext.nextMathematicalRowToAdd++);
			}

			// System.out.println("AddNewRow: Add '" +
			// newStryktipsGame.toString() +
			// "'" + ", next to try:" +
			// iterativeContext.nextMathematicalRowToAdd);

			// 2. Lägger till nya raden till testsystemet.
			context.systemUnderVerification.add(newStryktipsGame);

			// 3. Increment counter for number of rows added.
			((IterativeContext) context).numberOfAddedRows++;

			// 4. Initialize original test system, used as as starting point.
			((IterativeContext) context).systemUnderVerificationOriginal = context.systemUnderVerification
					.toArray(new StryktipsGame[context.systemUnderVerification
							.size()]);

			// 5. Reset removed row.
			((IterativeContext) context).currentlyRemovedRow = 0;

			// 6. Copy system under verification
			copySystemUnderVerification();

			// System.out.println("AddNewRow: system size = "
			// + iterativeContext.systemUnderVerification.size()
			// + ",next mathematical row to add ="
			// + (iterativeContext.nextMathematicalRowToAdd)
			// + ", Added ID=" + newStryktipsGame.toString());
			return new CheckAllRowsAddedOnce(context);
		} catch (IndexOutOfBoundsException e) {
			// All rows added, just continue. This is safety code
			// and this happens sometimes due because of the DontSendSystem
			// state
			// can' reset all variables.
			int numberOfRowsToAdd = context.mathematicalSystemRows.size()
					- context.systemUnderVerification.size() + 1;
			iterativeContext.numberOfAddedRows = numberOfRowsToAdd;

			return new CheckAllRowsAddedOnce(context);
		}
	}
}
