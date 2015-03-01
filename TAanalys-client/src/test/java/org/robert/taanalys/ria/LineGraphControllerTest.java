package org.robert.taanalys.ria;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class LineGraphControllerTest {

	@Test
	public void bordeHamtaNastaRodaFarg() {
		// Given
		LineGraphController controller = new LineGraphController();

		// When
		Color rgb = new Color(127, 0, 0);
		rgb = controller.nastaFarg(rgb);

		// Then
		Assert.assertEquals("Borde vara lite rödare.", 159, rgb.getRed());
	}

	@Test
	public void bordeHamtadAllaRodaFarger() {
		// Given
		LineGraphController controller = new LineGraphController();

		// When
		Color rgb = new Color(127, 0, 0);
		for (int i = 0; i < 4; i++) {
			rgb = controller.nastaFarg(rgb);
		}

		// Then
		Assert.assertEquals("Borde vara maxvärde som röd.", 255, rgb.getRed());
	}

	@Test
	public void bordeHamtadAllaRodaFargerOchForstaGronaFargen() {
		// Given
		LineGraphController controller = new LineGraphController();

		// When
		Color rgb = new Color(127, 0, 0);
		for (int i = 0; i < 5; i++) {
			rgb = controller.nastaFarg(rgb);
		}

		// Then
		Assert.assertEquals("Borde vara startvärde som röd.", 0, rgb.getRed());
		Assert.assertEquals("Borde hämta första gröna färgen.", 127,
				rgb.getGreen());
	}

	@Test
	public void bordeHamtadAllaRodaFargerOchAllaGronaFarger() {
		// Given
		LineGraphController controller = new LineGraphController();

		// When
		Color rgb = new Color(127, 0, 0);
		for (int i = 0; i < 9; i++) {
			rgb = controller.nastaFarg(rgb);
		}

		// Then
		Assert.assertEquals("Borde vara startvärde som röd.", 0, rgb.getRed());
		Assert.assertEquals("Borde hämta sista gröna färgen.", 255,
				rgb.getGreen());
	}

	@Test
	public void bordeHamtadAllaRodaFargerOchAllaGronaFargenOchForstaBla() {
		// Given
		LineGraphController controller = new LineGraphController();

		// When
		Color rgb = new Color(127, 0, 0);
		for (int i = 0; i < 10; i++) {
			rgb = controller.nastaFarg(rgb);
		}

		// Then
		Assert.assertEquals("Borde vara startvärde som röd.", 0, rgb.getRed());
		Assert.assertEquals("Borde vara startvärde som gröna.", 0,
				rgb.getGreen());
		Assert.assertEquals("Borde vara första blåa färgen.", 127,
				rgb.getBlue());
	}

	@Test
	public void bordeHamtadAllaRodaOchGronaOchBlaFarger() {
		// Given
		LineGraphController controller = new LineGraphController();

		// When
		Color rgb = new Color(127, 0, 0);
		for (int i = 0; i < 14; i++) {
			rgb = controller.nastaFarg(rgb);
		}

		// Then
		Assert.assertEquals("Borde vara startvärde för röd.", 0, rgb.getRed());
		Assert.assertEquals("Borde vara startvärde för grön.", 0,
				rgb.getGreen());
		Assert.assertEquals("Borde vara sista blåa färgen.", 255, rgb.getBlue());
	}

	/**
	 * När man loopar igenom alla färkombinationer så ska man börja om ifrån
	 * början om man försöker hämta en till färg.
	 */
	@Test
	public void bordeBorjaOmIfranBorjan() {
		// Given
		LineGraphController controller = new LineGraphController();

		// When
		Color rgb = new Color(127, 0, 0);
		for (int i = 0; i < 15; i++) {
			rgb = controller.nastaFarg(rgb);
		}

		// Then
		Assert.assertEquals("Borde vara startvärde för röd.", 127, rgb.getRed());
		Assert.assertEquals("Borde vara startvärde för grön.", 0,
				rgb.getGreen());
		Assert.assertEquals("Borde vara sista blåa färgen.", 0, rgb.getBlue());
	}

	@Test
	public void bordeKonverteraTillDatum() {
		// Given
		LineGraphController controller = new LineGraphController();

		// When
		Calendar datum = controller.konvertera("2013-02-19", "yyyy-MM-dd");

		// Then
		Assert.assertEquals("Borde vara rätt datum.",
				"Tue Feb 19 00:00:00 CET 2013", datum.getTime().toString());
	}

	@Test
	public void bordeMisslyckasAttKonverteraTillDatum() {
		// Given
		LineGraphController controller = new LineGraphController();

		// When
		try {
			controller.konvertera("2013-ss02-19", "yyyy-MM-dd");
			Assert.fail("Borde inte gå att konvertera.");
		} catch (RuntimeException e) {
			// Then it's ok.
		}
	}
	
	@Test
	public void bordeKonverteraTillIngetDatumNarIndataSaknas() {
		// Given
		LineGraphController controller = new LineGraphController();

		// When
		try {
			Calendar datum = controller.konvertera("", "YYYY-MM-dd");
			Assert.assertNull(datum);
		} catch (RuntimeException e) {
			Assert.fail("Borde gå att konvertera.");
		}
	}
}
