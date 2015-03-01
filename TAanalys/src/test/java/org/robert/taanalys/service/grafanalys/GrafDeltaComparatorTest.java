package org.robert.taanalys.service.grafanalys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.taanalys.service.GrafDelta;
import org.robert.taanalys.service.grafanalys.GrafDeltaComparator;

public class GrafDeltaComparatorTest {

	@Test
	public void bodeSorteraFyraGrafDelta() {
		// Given
		List<GrafDelta> grafData = new ArrayList<GrafDelta>();
		grafData.add(new GrafDelta(0, null));
		grafData.add(new GrafDelta(2, null));
		grafData.add(new GrafDelta(1, null));
		grafData.add(new GrafDelta(2123, null));

		// When
		Collections.sort(grafData, new GrafDeltaComparator());

		// Then
		Assert.assertEquals(2123.0, grafData.get(0).delta);
		Assert.assertEquals(2.0, grafData.get(1).delta);
		Assert.assertEquals(1.0, grafData.get(2).delta);
		Assert.assertEquals(0.0, grafData.get(3).delta);
	}

	@Test
	public void bordeVaraTvaLikaGrafDelta() {
		// Given
		List<GrafDelta> grafData = new ArrayList<GrafDelta>();
		grafData.add(new GrafDelta(2, null));
		grafData.add(new GrafDelta(1, null));
		grafData.add(new GrafDelta(2, null));

		// When
		Collections.sort(grafData, new GrafDeltaComparator());

		// Then
		Assert.assertEquals(2.0, grafData.get(0).delta);
		Assert.assertEquals(2.0, grafData.get(1).delta);
		Assert.assertEquals(1.0, grafData.get(2).delta);
	}
}
