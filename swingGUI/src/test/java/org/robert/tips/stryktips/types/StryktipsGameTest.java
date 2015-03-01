package org.robert.tips.stryktips.types;

import junit.framework.Assert;

import org.junit.Test;

public class StryktipsGameTest {

	@Test
	public void shouldBe12Rights() {
		// Given
		StryktipsGame sysA = new StryktipsGame(new char[] { '1', '1', '1', '1',
				'1', '1', '1', '1', '1', '1', '1', '1', '1', });
		StryktipsGame sysB = new StryktipsGame(new char[] { 'x', '1', '1', '1',
				'1', '1', '1', '1', '1', '1', '1', '1', '1', });

		// When
		int numberOfRights = sysA.numberOfRights(sysB);

		// Then
		Assert.assertEquals("Should be 12 rights.", 12, numberOfRights);
	}

	@Test
	public void shouldBe13Rights() {
		// Given
		StryktipsGame sysA = new StryktipsGame(new char[] { '1', '1', '1', '1',
				'1', '1', '1', '1', '1', '1', '1', '1', '1', });
		StryktipsGame sysB = new StryktipsGame(new char[] { '1', '1', '1', '1',
				'1', '1', '1', '1', '1', '1', '1', '1', '1', });

		// When
		int numberOfRights = sysA.numberOfRights(sysB);

		// Then
		Assert.assertEquals("Should be 13 rights.", 13, numberOfRights);
	}

	@Test
	public void shouldBe11Rights() {
		// Given
		StryktipsGame sysA = new StryktipsGame(new char[] { '1', '1', '1', '1',
				'1', '1', '1', '1', '1', '1', '1', '1', '1', });
		StryktipsGame sysB = new StryktipsGame(new char[] { '1', '1', '1', '1',
				'1', '1', '1', '1', '1', '1', 'x', '1', '2', });

		// When
		int numberOfRights = sysA.numberOfRights(sysB);

		// Then
		Assert.assertEquals("Should be 11 rights.", 11, numberOfRights);
	}

	@Test
	public void shouldBeZeroRights() {
		// Given
		StryktipsGame sysA = new StryktipsGame(new char[] { '1', '1', '1', '1',
				'1', '1', '1', '1', '1', '1', '1', '1', '1', });
		StryktipsGame sysB = new StryktipsGame(new char[] { 'x', 'x', 'x', 'x',
				'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', });

		// When
		int numberOfRights = sysA.numberOfRights(sysB);

		// Then
		Assert.assertEquals("Should be 0 rights.", 0, numberOfRights);
	}

	@Test
	public void shoudlBeEqual() {
		// Given
		StryktipsGame game1 = new StryktipsGame("111X1X1111111");
		StryktipsGame game2 = new StryktipsGame("111X1X1111111");

		// When
		boolean equal = game1.equals(game2);

		// Then
		Assert.assertEquals("Should be equal", true, equal);
	}
}
