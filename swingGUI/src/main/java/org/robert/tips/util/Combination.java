package org.robert.tips.util;

import java.util.ArrayList;

/**
 * Calculates the number of combinations that can occur from a given part. The
 * algorithm is binominal 'n over r'. The calculation uses neasted forloops to
 * achieve the result. The algorithm is = n! / ( n - r )! * r!. The combinations
 * that are created is an arrays like int[] = { 0,1,2,3,4,... n }, where n is a
 * rownumber in the single row.
 * 
 * <pre>
 * Usage:
 *      Combination combination = new Combination( 13, 3 );
 *         combination.createCombinations();
 *         System.out.println( "Antal rader:" + combination.getCombinations().size() );
 * </pre>
 * 
 * @author Robert Siwerz.
 */
public class Combination {
	private int[] forLoop = new int[100]; // rObjects could max be 100.
	private int[][] startStop = new int[100][2]; // Anger Start och Stop värdena
													// för for-loopen.
	private int rObjects; // the 'r' parameter in 'n over r' algorithm
	private int nObjects; // the 'n' parameter in 'n over r' algorithm
	private ArrayList<int[]> combinations = new ArrayList<int[]>(); // int[], from 0 - n.

	/**
	 * Creates combination object.
	 * 
	 * @param nObjects
	 *            The 'n' parameter in the 'n over r' algorithm.
	 * @param rObjects
	 *            The 'r' parameter in the 'n over r' algorithm.
	 */
	public Combination(int nObjects, int rObjects) {
		this.nObjects = nObjects;
		this.rObjects = rObjects;
	}

	/**
	 * Räknar upp föregående loop med +1, och sätter den nuvarande till dess
	 * startvärde.
	 */
	private void steg(int rad) {
		forLoop[(rad - 1)]++;
		forLoop[rad] = forLoop[(rad - 1)];
	}

	/**
	 * Summerar från position 'element' till rObjects ur matrisen forLoop.
	 */
	private int summaRad(int element) {
		int prel = 0;

		for (int i = element; i < rObjects; i++) {
			prel = prel + forLoop[i];
		}

		return prel;
	}

	/**
	 * Summerar från position 'element' till rObjects ur matrisen startStop.
	 */
	private int summaStartStop(int element) {
		int prel = 0;

		for (int i = element; i < rObjects; i++) {
			prel = prel + startStop[i][1];
		}

		return prel;
	}

	/**
	 * Visar raden som man befinner sig på,dvs elementen i matrisen forLoop.
	 */
	private void visaRad() {
		for (int i = 0; i < rObjects; i++) {
			System.out.print(forLoop[i] + ",");
		}

		System.out.println();
	}

	/**
	 * Add current combination to the collection of combinations.
	 */
	private void addCombination() {
		int[] row = new int[rObjects];

		for (int i = 0; i < rObjects; i++) {
			row[i] = forLoop[i];
		}

		combinations.add(row);
	}

	/**
	 * Initierar forLoop med StartStop-värdena
	 */
	private void initieraStartStop() {
		int i;

		for (i = 0; i < rObjects; i++)
			startStop[i][0] = i;
		for (i = 0; i < rObjects; i++)
			startStop[i][1] = nObjects - rObjects + i;
		for (i = 0; i < rObjects; i++)
			forLoop[i] = startStop[i][0];
	}

	/**
	 * Creates the combinations, e.g use the algorithm and creates all possible
	 * combinations for 'n over r'.
	 */
	public void createCombinations() {
		int position, ok = 0, i;
		int antalRader = 0;
		position = (rObjects == 0) ? 0 : rObjects - 1; // 'r'=0 �r specialfall.
		initieraStartStop();
		combinations.clear();

		while (ok == 0) {
			// visaRad();
			addCombination(); // add the current combination to the combination
								// container.
			if (summaRad(0) == summaStartStop(0))
				ok = 1;
			for (i = 1; i < rObjects; i++)
				if (summaRad(i) == summaStartStop(i))
					steg(i);
			forLoop[position]++;
			antalRader++;
		}
	}

	/**
	 * Get the container that holds the created combinations.
	 * 
	 * @return ArrayList Container with the combinations in as array of int.
	 */
	public ArrayList<int[]> getCombinations() {
		return combinations;
	}

	/**
	 * Calculate number of combinations, don't create and store them in this
	 * object.
	 * 
	 * @return number of combinations this object consists of.
	 */
	public int calculateNumberOfCombinations() {
		int position, ok = 0, i;
		int antalRader = 0;
		position = (rObjects == 0) ? 0 : rObjects - 1; // 'r'=0 �r specialfall.
		initieraStartStop();
		combinations.clear();

		while (ok == 0) {
			if (summaRad(0) == summaStartStop(0))
				ok = 1;
			for (i = 1; i < rObjects; i++)
				if (summaRad(i) == summaStartStop(i))
					steg(i);
			forLoop[position]++;
			antalRader++;
		}
		return antalRader;
	}

	/**
	 * Create the given number of combinations from the given start position.
	 * 
	 */
	public void createCombinations(long startPosition, int numberOfCombinationsToCreate) {
		int position, ok = 0, i;
		int antalRader = 0;
		position = (rObjects == 0) ? 0 : rObjects - 1; // 'r'=0 är specialfall.
		initieraStartStop();
		combinations.clear();

		while (ok == 0) {
			if (antalRader >= startPosition
					&& antalRader < (startPosition + numberOfCombinationsToCreate)) {
				// add the current combination to the combination container.
				addCombination();
			}

			if (summaRad(0) == summaStartStop(0)) {
				ok = 1;
			}

			for (i = 1; i < rObjects; i++) {
				if (summaRad(i) == summaStartStop(i)) {
					steg(i);
				}
			}

			forLoop[position]++;
			antalRader++;

			// End search?
			if (antalRader > (startPosition + numberOfCombinationsToCreate)) {
				ok = 1;
			}
		}
	}
}
