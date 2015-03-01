package org.robert.tips.util;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class CombinationTest {
	@Test
	public void shouldCreate8over3() {
		// Given
		Combination combination = new Combination(8, 3);

		// When
		combination.createCombinations();

		// Then
		Assert.assertEquals("Should create combinations.", 56, combination
				.getCombinations().size());
	}

	@Test
	public void shouldCreate8over4() {
		// Given
		Combination combination = new Combination(8, 4);

		// When
		combination.createCombinations();

		// Then
		Assert.assertEquals("Should create combinations.", 70, combination
				.getCombinations().size());
	}

	@Test
	public void shouldCreate12over3() {
		// Given
		Combination combination = new Combination(12, 3);

		// When
		combination.createCombinations();

		// Then
		Assert.assertEquals("Should create combinations.", 220, combination
				.getCombinations().size());
	}

	@Test
	public void shouldCreate32over3() {
		// Given
		Combination combination = new Combination(32, 3);

		// When
		combination.createCombinations();

		// Then
		Assert.assertEquals("Should create combinations.", 4960, combination
				.getCombinations().size());
	}

	@Test
	public void shouldCreate64over3() {
		// Given
		Combination combination = new Combination(64, 3);

		// When
		combination.createCombinations();

		// Then
		Assert.assertEquals("Should create combinations.", 41664, combination
				.getCombinations().size());
	}

	@Test
	public void shouldCreate128over3() {
		// Given
		Combination combination = new Combination(128, 3);

		// When
		combination.createCombinations();

		// Then
		Assert.assertEquals("Should create combinations.", 341376, combination
				.getCombinations().size());
	}

	@Test
	public void shouldCalculate128over4() {
		// Given
		Combination combination = new Combination(128, 4);

		// When
		int numberOfCombinations = combination.calculateNumberOfCombinations();

		// Then
		Assert.assertEquals("Should create ~ 10 miljon combinations.",
				10668000, numberOfCombinations);
	}

	/**
	 * Should create first 10 million combinations from a combinations system
	 * that can't be created all at once.
	 */
	@Test
	public void shouldCreate1000CombinationsFrom128over5() {
		// Given
		Combination combination = new Combination(128, 5);

		// When
		combination.createCombinations(0, 1000);
		List<int[]> combinations = combination.getCombinations();

		// Then
		Assert.assertEquals(
				"Should create combinations from beginning of all possible combinations.",
				1000, combinations.size());
	}

	/**
	 * Should create first 10 million combinations from a combinations system
	 * that can't be created all at once.
	 */
	@Test
	public void shouldCreateOneMillionCombinationsFrom128over5() {
		// Given
		Combination combination = new Combination(128, 5);

		// When
		combination.createCombinations(30000000, 1000000);
		List<int[]> combinations = combination.getCombinations();

		// Then
		Assert.assertEquals(
				"Should create combinations from middle of all possible combinations.",
				1000000, combinations.size());
		Assert.assertFalse("Should not contain first combination.",
				combinations.contains(new int[] { 0, 1, 2, 3, 4 }));
		int[] firstElement = combinations.get(0);
		int[] shouldBe = new int[] { 2, 96, 98, 124, 125 };
		if (firstElement[0] == shouldBe[0] && firstElement[1] == shouldBe[1]
				&& firstElement[2] == shouldBe[2]
				&& firstElement[3] == shouldBe[3]
				&& firstElement[4] == shouldBe[4]) {
		} else {
			Assert.fail("Should be first combination set.");
		}
	}

	/**
	 * Should get the last combinations even if the not is as many as requested.
	 * This means that if you request 1 million rows from last combinations you
	 * will get only one combination.
	 */
	@Test
	public void shouldGetUpToLastCombination() {
		// Given
		Combination combination = new Combination(128, 4);
		int numberOfCombinations = combination.calculateNumberOfCombinations();

		// When
		combination.createCombinations(numberOfCombinations - 1, 1000000);
		List<int[]> combinations = combination.getCombinations();

		// Then
		Assert.assertEquals("Should create one combination.", 1,
				combinations.size());
	}
}
