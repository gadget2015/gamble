package org.robert.tips.stryktips.domain.model;

import java.util.ArrayList;

import javax.management.RuntimeErrorException;

import org.robert.tips.exceptions.GameAlreadySetException;
import org.robert.tips.stryktips.domain.model.reduce.MathimaticalSystemReducer;
import org.robert.tips.stryktips.exceptions.GameNotSetException;
import static org.robert.tips.stryktips.types.StryktipsConstants.*;

/**
 * Entity that defines data used to create a matchimatical system.
 * 
 * @author Robert Georen.
 */
public class Mathimatical {

	/**
	 * contains the mathimatical system.
	 */
	private char[][] mathimaticalSystem = new char[NUMBER_OF_GAMES][NUMBER_OF_GAMEOPTIONS];
	/**
	 * referens to the stryktips system.
	 */
	private StryktipsSystem stryktipsSystem;

	/**
	 * Creates a new instance of this document.
	 * 
	 * @param stryktipsDocument
	 *            The stryktips document.
	 */
	public Mathimatical(StryktipsSystem stryktipsSystem) {
		this.stryktipsSystem = stryktipsSystem;
	}

	public Mathimatical() {
	}

	/**
	 * Create a new mathematical system with given half and hole hedgings and
	 * the rest games filled with ones '1'.
	 * 
	 * @param numberOfHalfHedging
	 * @param numberOfHoleHedging
	 */
	public Mathimatical(int numberOfHalfHedging, int numberOfHoleHedging) {
		try {
			// Set hole hedgings
			for (int i = 0; i < (numberOfHoleHedging * 3); i += 3) {
				setMathimaticalRow(i, GAMEVALUE_ONE);
				setMathimaticalRow((i + 1), GAMEVALUE_TIE);
				setMathimaticalRow((i + 2), GAMEVALUE_TWO);
			}

			// Set half hedgings
			for (int i = numberOfHoleHedging * 3; i < (numberOfHoleHedging * 3 + numberOfHalfHedging * 3); i += 3) {
				setMathimaticalRow(i, GAMEVALUE_ONE);
				setMathimaticalRow((i + 1), GAMEVALUE_TIE);
			}

			// Set One's in rest of system.
			for (int i = (numberOfHoleHedging * 3 + numberOfHalfHedging * 3); i < 39; i += 3) {
				setMathimaticalRow(i, GAMEVALUE_ONE);
			}

		} catch (GameAlreadySetException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Init failed of Mathimatical domain object."
							+ e.getMessage());
		}
	}

	/**
	 * Set a row in the mathimatical system.
	 * 
	 * @param position
	 *            integer value between { 0-38 }. 0-2=first row, 3-5=second row.
	 * @param character
	 *            The game character/option.
	 * @throws GamelAlreadySetException
	 *             The game is already set the in the mathimatical system.
	 */
	public void setMathimaticalRow(int position, char character)
			throws GameAlreadySetException {
		// calcualte row and colnumber
		int rowNumber = (position - position % NUMBER_OF_GAMEOPTIONS)
				/ NUMBER_OF_GAMEOPTIONS;
		int colNumber = position % NUMBER_OF_GAMEOPTIONS;

		// get the character in the mathimatical system
		char mathimaticalRowCharacter = 0;
		mathimaticalRowCharacter = (colNumber == 0) ? GAMEVALUE_ONE
				: mathimaticalRowCharacter;
		mathimaticalRowCharacter = (colNumber == 1) ? GAMEVALUE_TIE
				: mathimaticalRowCharacter;
		mathimaticalRowCharacter = (colNumber == 2) ? GAMEVALUE_TWO
				: mathimaticalRowCharacter;

		// First check so this game option isn't already set.
		if (mathimaticalSystem[rowNumber][colNumber] == character) {
			throw new GameAlreadySetException("position:" + position
					+ ", character:" + character);
		}

		mathimaticalSystem[rowNumber][colNumber] = character;
	}

	/**
	 * Get the character/option for the given position in the mathimatical
	 * system.
	 * 
	 * @param position
	 *            integer value between { 0-38 }. 0-2=first row, 3-5=second row.
	 * @return char The character/option.
	 */
	public char getMathimaticalRow(int position) {
		int rowNumber = (position - position % NUMBER_OF_GAMEOPTIONS)
				/ NUMBER_OF_GAMEOPTIONS;
		int colNumber = position % NUMBER_OF_GAMEOPTIONS;
		char returnValue = mathimaticalSystem[rowNumber][colNumber];

		return returnValue;
	}

	public void setMathimaticalSystem(char[][] mathimaticalSystem) {
		this.mathimaticalSystem = mathimaticalSystem;
	}

	/**
	 * Creates all single rows that the mathimatical system consists of.
	 * 
	 * @return Singel rows, e.g array of rows {'1','X','X','X','2',...}.
	 */
	public ArrayList createsSingleRowsFromMathimaticalSystem() {
		ArrayList singleRows = new ArrayList(); // contains char[] = { '1', 'X',
												// '1', ... }

		// This is the so called 'V', e.g a lot of for-loops.
		// First create three arrays that hold the start, jump and end postions
		// that
		// are used in the 13 for-loops.
		int[] startPosition = new int[NUMBER_OF_GAMES];
		int[] jumpPosition = new int[NUMBER_OF_GAMES];
		int[] endPosition = new int[NUMBER_OF_GAMES];

		for (int i = 0; i < NUMBER_OF_GAMES; i++) {
			startPosition[i] = (mathimaticalSystem[i][0] == GAMEVALUE_ONE) ? 0
					: (mathimaticalSystem[i][1] == GAMEVALUE_TIE) ? 1 : 2;
			jumpPosition[i] = (mathimaticalSystem[i][0] == GAMEVALUE_ONE
					&& mathimaticalSystem[i][1] != GAMEVALUE_TIE && mathimaticalSystem[i][2] == GAMEVALUE_TWO) ? 2
					: 1;
			endPosition[i] = (mathimaticalSystem[i][2] == GAMEVALUE_TWO) ? 2
					: (mathimaticalSystem[i][1] == GAMEVALUE_TIE) ? 1 : 0;
		}

		// The 13 for-loops
		for (int a = startPosition[0]; a <= endPosition[0]; a += jumpPosition[0]) {
			for (int b = startPosition[1]; b <= endPosition[1]; b += jumpPosition[1]) {
				for (int c = startPosition[2]; c <= endPosition[2]; c += jumpPosition[2]) {
					for (int d = startPosition[3]; d <= endPosition[3]; d += jumpPosition[3]) {
						for (int e = startPosition[4]; e <= endPosition[4]; e += jumpPosition[4]) {
							for (int f = startPosition[5]; f <= endPosition[5]; f += jumpPosition[5]) {
								for (int g = startPosition[6]; g <= endPosition[6]; g += jumpPosition[6]) {
									for (int h = startPosition[7]; h <= endPosition[7]; h += jumpPosition[7]) {
										for (int i = startPosition[8]; i <= endPosition[8]; i += jumpPosition[8]) {
											for (int j = startPosition[9]; j <= endPosition[9]; j += jumpPosition[9]) {
												for (int k = startPosition[10]; k <= endPosition[10]; k += jumpPosition[10]) {
													for (int l = startPosition[11]; l <= endPosition[11]; l += jumpPosition[11]) {
														for (int m = startPosition[12]; m <= endPosition[12]; m += jumpPosition[12]) {
															char[] row = new char[NUMBER_OF_GAMES];
															row[0] = mathimaticalSystem[0][a];
															row[1] = mathimaticalSystem[1][b];
															row[2] = mathimaticalSystem[2][c];
															row[3] = mathimaticalSystem[3][d];
															row[4] = mathimaticalSystem[4][e];
															row[5] = mathimaticalSystem[5][f];
															row[6] = mathimaticalSystem[6][g];
															row[7] = mathimaticalSystem[7][h];
															row[8] = mathimaticalSystem[8][i];
															row[9] = mathimaticalSystem[9][j];
															row[10] = mathimaticalSystem[10][k];
															row[11] = mathimaticalSystem[11][l];
															row[12] = mathimaticalSystem[12][m];

															singleRows.add(row);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return singleRows;
	}

	/**
	 * Create a mathimatical system from the Mathimatical document model.
	 * 
	 * <pre>
	 * Flow:
	 * 1. Check so that all rows are set in the mathimatical system.
	 * 3. Create the mathimatical system.
	 * </pre>
	 * 
	 * @throws GeneralApplicationException
	 *             Major error.
	 */
	public void reduce() throws GameNotSetException {
		MathimaticalSystemReducer reducer = new MathimaticalSystemReducer(
				stryktipsSystem);
		reducer.reduce();
	}
}
