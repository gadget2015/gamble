package org.robert.tips.util.stryktips;

import java.math.BigInteger;

/**
 * Represent a combination, e.g n!/k!*(n-k)!.
 * 
 * @author Robert
 */
public class Faculty {
	private int n;
	private int k;

	public Faculty(int n, int k) {
		this.n = n;
		this.k = k;
	}

	/**
	 * Calculate: n!/(k!*(n-k)!). The calculation is performed so it uses least
	 * resources as possible. The solution is to rewrite the formula:
	 * 
	 * <pre>
	 *   n!              n*(n-1)*(n-2)* ... * (n-k)
	 * --------   ===>   -----------------------   
	 * k!(n-k)!                     k!
	 * </pre>
	 * 
	 * @param n
	 * @param r
	 * @return
	 */
	public BigInteger getNumberOfCombinations() {
		BigInteger result = BigInteger.valueOf(0);

		result = product(n, (n - k + 1));
		result = result.divide(faculty(k));

		return result;
	}

	/**
	 * Calculate faculty.
	 * 
	 * @param n
	 * @return
	 */
	private BigInteger faculty(int n) {
		// System.out.println(n);

		if (n == 0) {
			// ja, 0! är alltid 1
			return BigInteger.valueOf(1);
		}

		// rekursera vidare
		BigInteger value = BigInteger.valueOf(n);

		return value.multiply(faculty(n - 1));
	}

	private BigInteger product(int high, int low) {
		if (high == low) {
			// ja, 0! är alltid 1
			return BigInteger.valueOf(low);
		}

		// rekursera vidare
		BigInteger value = BigInteger.valueOf(high);

		return value.multiply(product(high - 1, low));
	}
}
