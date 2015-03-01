package org.robert.tips.stryktips.domain.model;

import junit.framework.Assert;

import org.junit.Test;

public class MathematicalTest {

	@Test
	public void shouldInitSystemWith_4HoleAnd4Half() {
		// Given
		Mathimatical mathematical = new Mathimatical(4,4);
		
		// When
		int rows= mathematical.createsSingleRowsFromMathimaticalSystem().size();
		
		// Then
		Assert.assertEquals("Should be many rows in mathematical system", 1296, rows);
	}
}
