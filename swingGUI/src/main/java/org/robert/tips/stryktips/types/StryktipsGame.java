package org.robert.tips.stryktips.types;

import java.io.Serializable;

/**
 * Defines a stryktips game with 13 rows in.
 * 
 * @author Robert Siwerz.
 */
public class StryktipsGame implements StryktipsConstants, Serializable {
	private static final long serialVersionUID = 1L;
	private char[] singleRow;

	/**
	 * Creates a stryktips game instance.
	 * 
	 * @param singleRow
	 *            The single row game.
	 */
	public StryktipsGame(char[] singleRow) {
		this.singleRow = singleRow;
	}

	public StryktipsGame(String row) {
		this.singleRow = new char[StryktipsConstants.NUMBER_OF_GAMES];

		for (int i = 0; i < StryktipsConstants.NUMBER_OF_GAMES; i++) {
			singleRow[i] = row.charAt(i);
		}
	}

	/**
	 * Get the single row.
	 * 
	 * @return char[] The singlerow
	 */
	public char[] getSingleRow() {
		return singleRow;
	}

	/**
	 * Compare this object to the given object.
	 * 
	 * @param o
	 *            The object to compare with.
	 * @return boolean true = object is equal to the given one.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof StryktipsGame) {
			StryktipsGame stryktipsGame = (StryktipsGame) o;
			char[] toCompareWith = stryktipsGame.getSingleRow();

			for (int i = 0; i < NUMBER_OF_GAMES; i++) {
				if (toCompareWith[i] != singleRow[i]) {
					return false; // object is not equal.
				}
			}

			return true; // object is equal.
		} else {
			return false; // object is not equal.
		}
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/**
	 * Get the debug string.
	 * 
	 * @return String The debug string.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < NUMBER_OF_GAMES; i++) {
			sb.append(singleRow[i]);
		}

		return sb.toString();
	}

	public int numberOfRights(StryktipsGame sysB) {
		char[] toCompareWith = sysB.getSingleRow();
		int numberOfRights = 0;

		for (int i = 0; i < NUMBER_OF_GAMES; i++) {
			if (toCompareWith[i] == singleRow[i]) {
				numberOfRights++;
			}
		}

		return numberOfRights;
	}
}