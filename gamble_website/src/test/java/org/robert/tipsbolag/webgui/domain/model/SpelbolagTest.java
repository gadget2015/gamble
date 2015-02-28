package org.robert.tipsbolag.webgui.domain.model;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

public class SpelbolagTest {

	@Test
	public void bordeSpelaEnOmgangMedEnSpelare() {
		Spelbolag bolag = createSpelbolag(50);
		Spelare spelare1 = createSpelare(0);
		bolag.laggTillSpelare(spelare1);
		Calendar cal = Calendar.getInstance();

		// When
		bolag.taBetaltAvAllaSeplareForEnOmgang(cal.getTime());

		// Then
		Assert.assertEquals("Borde finns pengar på bolags kontot.", 50, bolag
				.getKonto().getSaldo());
		Assert.assertEquals("Borde inte finns pengar på spelarens konto.", -50,
				spelare1.getKonto().getSaldo());
	}

	private Spelare createSpelare(int initialDebit) {
		Spelare spelare1 = new Spelare();
		spelare1.setUserId(new UserId("test.spelare@gamble.com"));
		Konto spelarKonto = new Konto();
		spelarKonto.setKontonr(22);
		Calendar cal = Calendar.getInstance();
		Transaktion debit = new Transaktion(initialDebit, 0, "Start belopp",
				cal.getTime());
		spelarKonto.laggTillTransaktion(debit);
		spelare1.setKonto(spelarKonto);

		return spelare1;
	}

	@Test
	public void bordeSpelaEnOmgangMedTvaSpelare() {
		// Given
		Spelbolag bolag = createSpelbolag(50);

		Spelare spelare1 = createSpelare(0);
		bolag.laggTillSpelare(spelare1);

		Spelare spelare2 = createSpelare(500);
		bolag.laggTillSpelare(spelare2);

		// When
		Calendar cal = Calendar.getInstance();
		bolag.taBetaltAvAllaSeplareForEnOmgang(cal.getTime());

		// Then
		Assert.assertEquals("Borde finnas pengar på bolagskontot.", 100, bolag
				.getKonto().getSaldo());
		Assert.assertEquals("Borde inte finns pengar på spelarens konto.", -50,
				spelare1.getKonto().getSaldo());
		Assert.assertEquals(
				"Borde finns lite mindre pengar på spelarens konto.", 450,
				spelare2.getKonto().getSaldo());
	}

	private Spelbolag createSpelbolag(int insats) {
		Spelbolag bolag = new Spelbolag();
		bolag.setInsatsPerOmgang(insats);
		Konto bolagKonto = new Konto();
		bolagKonto.setKontonr(89);
		bolag.setKonto(bolagKonto);

		return bolag;
	}
}
