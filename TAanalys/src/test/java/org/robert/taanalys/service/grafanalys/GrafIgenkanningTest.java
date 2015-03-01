package org.robert.taanalys.service.grafanalys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.taanalys.service.GrafDelta;
import org.robert.taanalys.service.MotherMock;
import org.robert.taanalys.service.ProcentIntradag;
import org.robert.taanalys.service.grafanalys.GrafIgenkanning;

public class GrafIgenkanningTest {

	@Test
	public void bordeHittaDeTvaMestLikaTidigareGraferUtavTreMojliga() {
		// Given
		Collection<ProcentIntradag> dagar = new ArrayList<ProcentIntradag>();
		ProcentIntradag dag1 = MotherMock.skapaTestdata(0.20f, 550);
		dagar.add(dag1);
		ProcentIntradag dag2 = MotherMock.skapaTestdata(0.25f, 550);
		dagar.add(dag2);
		ProcentIntradag dag3 = MotherMock.skapaTestdata(0.10f, 550);
		dagar.add(dag3);
		ProcentIntradag idag = MotherMock.skapaTestdata(0.22f, 400);

		// When
		GrafIgenkanning service = new GrafIgenkanning();
		Collection<GrafDelta> mestLikaDagar = service
				.hamtaDeMesLikaGraferna(2, idag, dagar);

		// Then
		Assert.assertEquals("Borde hämta två mest lika dagar.", 2,
				mestLikaDagar.size());
		Iterator<GrafDelta> it = mestLikaDagar.iterator();
		Assert.assertEquals("Dag 1 ska hämtas.",12.000000476837158d, it.next().delta);
		Assert.assertEquals("Dag 2 ska hämtas.",7.999998331069946d, it.next().delta);
	}
}
