package org.robert.taanalys.service.grafanalys;

import java.text.DecimalFormat;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.taanalys.service.MotherMock;
import org.robert.taanalys.service.ProcentIntradag;
import org.robert.taanalys.service.grafanalys.DeltaVardeGrafIgenkanningAlgoritm;

public class DeltaVardeGrafIgenkanningAlgoritmTest {

	@Test
	public void bordeVaraTvaIdentiskaGrafer() {
		// Given
		ProcentIntradag dag1 = MotherMock.skapaTestdata(0.25f, 600);
		ProcentIntradag dag2 = MotherMock.skapaTestdata(0.25f, 600);

		// When
		DeltaVardeGrafIgenkanningAlgoritm algoritm = new DeltaVardeGrafIgenkanningAlgoritm();
		double delta = algoritm.calculateDelta(dag1, dag2);

		// Then
		Assert.assertEquals("Borde vara lika.", 0d, delta);
	}

	@Test
	public void bordeVaraTvaIdentiskaGraferEfterHalvadagen() {
		// Given
		ProcentIntradag dag1 = MotherMock.skapaTestdata(0.25f, 300);
		ProcentIntradag dag2 = MotherMock.skapaTestdata(0.25f, 600);

		// When
		DeltaVardeGrafIgenkanningAlgoritm algoritm = new DeltaVardeGrafIgenkanningAlgoritm();
		double delta = algoritm.calculateDelta(dag1, dag2);

		// Then
		Assert.assertEquals("Borde vara lika.", 0d, delta);
	}

	@Test
	public void bordeVaraTvaOlikaGrafer() {
		// Given
		ProcentIntradag dag1 = MotherMock.skapaTestdata(0.25f, 600);
		ProcentIntradag dag2 = MotherMock.skapaTestdata(0.20f, 600);

		// When
		DeltaVardeGrafIgenkanningAlgoritm algoritm = new DeltaVardeGrafIgenkanningAlgoritm();
		double delta = algoritm.calculateDelta(dag1, dag2);

		// Then
		DecimalFormat df = new DecimalFormat("#.0##");
		Assert.assertEquals("Borde vara olika.", "30,0", df.format(delta));
	}

	/**
	 * Delta = (600*0.5) = 300.
	 */
	@Test
	public void bordeVaraTvaOlikaGraferEftersomDemArMotsatta() {
		// Given
		ProcentIntradag dag1 = MotherMock.skapaTestdata(-0.25f, 600);
		ProcentIntradag dag2 = MotherMock.skapaTestdata(0.25f, 600);

		// When
		DeltaVardeGrafIgenkanningAlgoritm algoritm = new DeltaVardeGrafIgenkanningAlgoritm();
		double delta = algoritm.calculateDelta(dag1, dag2);

		// Then
		DecimalFormat df = new DecimalFormat("#.0##");
		Assert.assertEquals("Borde vara olika.", "300,0", df.format(delta));
	}

}
