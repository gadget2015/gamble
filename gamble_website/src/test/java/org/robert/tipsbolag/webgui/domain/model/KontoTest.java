package org.robert.tipsbolag.webgui.domain.model;

import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class KontoTest {
	/**
	 * Fallande ordning = senaste datumet först.
	 */
	@Test
	public void bordeSorteraTvaTransaktionerIFallandeOrdning() {
		// Given
		Konto spelarKonto = new Konto();
		spelarKonto.setKontonr(22);
		spelarKonto.laggTillTransaktion(createTransaktion(500, 0, 2012, 4, 7));
		spelarKonto.laggTillTransaktion(createTransaktion(250, 0, 2012, 4, 9));

		// When
		List<Transaktion> sortedTrans = spelarKonto.getSummeratransaktion();

		// Then
		Assert.assertEquals("Borde 250 som första transaktion.", 250,
				sortedTrans.get(0).getDebit());
	}

	/**
	 * Fallande ordning = senaste datumet först.
	 */
	@Test
	public void bordeSorteraFemTransaktionerIFallandeOrdning() {
		// Given
		Konto spelarKonto = new Konto();
		spelarKonto.setKontonr(22);
		spelarKonto.laggTillTransaktion(createTransaktion(100, 0, 2012, 1, 30));
		spelarKonto.laggTillTransaktion(createTransaktion(200, 0, 2012, 4, 20));
		spelarKonto
				.laggTillTransaktion(createTransaktion(335, 0, 2012, 12, 19));
		spelarKonto.laggTillTransaktion(createTransaktion(500, 0, 2012, 5, 22));
		spelarKonto.laggTillTransaktion(createTransaktion(250, 0, 2012, 4, 18));

		// When
		List<Transaktion> sortedTrans = spelarKonto.getSummeratransaktion();

		// Then
		Assert.assertEquals("Borde 335 som första transaktion.", 335,
				sortedTrans.get(0).getDebit());
	}

	@Test
	public void bordeSummeraNormalflodeAvTransaktioner() {
		// Given
		Konto spelarKonto = new Konto();
		spelarKonto.setKontonr(22);
		spelarKonto.laggTillTransaktion(createTransaktion(100, 0, 2012, 1, 30));
		spelarKonto.laggTillTransaktion(createTransaktion(0, 50, 2012, 4, 20));
		spelarKonto.laggTillTransaktion(createTransaktion(0, 50, 2012, 4, 22));
		spelarKonto.laggTillTransaktion(createTransaktion(0, 50, 2012, 4, 24));
		spelarKonto.laggTillTransaktion(createTransaktion(0, 50, 2012, 4, 25));
		spelarKonto.laggTillTransaktion(createTransaktion(200, 0, 2012, 5, 6));
		
		// When
		List<Transaktion> sortedTrans = spelarKonto.getSummeratransaktion();

		// Then
		Assert.assertEquals("Borde vara rätt saldo på första transaktionen.",
				100, sortedTrans.get(0).getSaldo());
		Assert.assertEquals("Borde vara rätt saldo på andra transaktionen.",
				-100, sortedTrans.get(1).getSaldo());
	}

	@Test
	public void bordeSummeraTransaktionerOverArsskifte() {
		// Given
		Konto spelarKonto = new Konto();
		spelarKonto.setKontonr(22);
		spelarKonto.laggTillTransaktion(createTransaktion(100, 0, 2012, 1, 30));
		spelarKonto.laggTillTransaktion(createTransaktion(0, 50, 2013, 1, 20));
		
		// When
		List<Transaktion> sortedTrans = spelarKonto.getSummeratransaktion();

		// Then
		Assert.assertEquals("Borde vara rätt saldo på första transaktionen.",
				50, sortedTrans.get(0).getSaldo());
		Assert.assertEquals("Borde vara rätt saldo på andra transaktionen.",
				100, sortedTrans.get(1).getSaldo());
	}

	private Transaktion createTransaktion(int debit, int kredit, int year,
			int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		Transaktion trans = new Transaktion(debit, kredit, "Text",
				cal.getTime());

		return trans;
	}
}
