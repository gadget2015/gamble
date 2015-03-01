package org.robert.tips.util.stryktips;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

public class Calculator {
	@Test
	public void shouldCalculate_34992_over_450() {
		BigInteger nFakultet = faculty(8500);
		System.out.println(nFakultet);
	}

	// beräknar rekursivt
	public BigInteger faculty(int n) {
		System.out.println(n);

		if (n == 0) {
			// ja, 0! är alltid 1
			return BigInteger.valueOf(1);
		}

		// rekursera vidare
		BigInteger value = BigInteger.valueOf(n);
		
		return value.multiply(faculty(n - 1));
	}

}
