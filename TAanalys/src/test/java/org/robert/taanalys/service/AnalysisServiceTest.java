package org.robert.taanalys.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.taanalys.infrastructure.InstrumentRepository;
import org.robert.taanalys.infrastructure.InstrumentRepositoryImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * När är 15:30 i minuteOfIntraday. 09:00 - 15:30 = 6*60+30=390. Dagens
 * slut=8*60+30=510.
 * 
 * @author Robert
 * 
 */
public class AnalysisServiceTest {

	/**
	 * Testar så att det blir rätt start och slut kurs för de 20 senaste
	 * dagarna.
	 */
	@Test
	public void bordeHamta20SenasteDagarnaSomProcentAnalysMedRattSlutKurser() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryMock();
		Calendar fromDate = skapaStartdatum();
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		Collection<ProcentIntradag> data = service.hamtaTAProcentIntradag(
				fromDate.getTime(), null);

		// Then
		Assert.assertEquals("Borde vara 20 dagars resultat.", 21, data.size());
		Iterator<ProcentIntradag> it = data.iterator();

		// Första dagen
		ProcentIntradag forstaDagen = it.next();
		Assert.assertEquals(
				"Borde vara rätt kurs första minuten på andra dagen.",
				1135.01f, forstaDagen.startQuote);
		Assert.assertEquals(
				"Borde vara rätt kurs sista minuten på första dagen.",
				1134.3994f, forstaDagen.endQuote);
		// Andra dagen
		ProcentIntradag andraDagen = it.next();
		Assert.assertEquals(
				"Borde vara rätt kurs första minuten på andra dagen.",
				1134.3994f, andraDagen.startQuote);
		Assert.assertEquals(
				"Borde vara rätt kurs sista minuteb på andra dagen.",
				1133.7988f, andraDagen.endQuote);
		// sista dagen
		for (int i = 0; i < 17; i++) {
			it.next();
		}
		ProcentIntradag sistaDagen = it.next();
		Assert.assertEquals("Borde vara rätt första minut på sista dagen.",
				1123.6035f, sistaDagen.startQuote);
		Assert.assertEquals("Borde vara rätt sista minut på sista dagen.",
				1123.0029f, sistaDagen.endQuote);
	}

	private Calendar skapaStartdatum() {
		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.DAY_OF_MONTH, -20);
		fromDate.set(Calendar.HOUR_OF_DAY, 0);
		return fromDate;
	}

	/**
	 * Testar så att det blir rätt antal dagar som hämtas.
	 */
	@Test
	public void bordeHamtaSenasteDagenSomProcentAnalys() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryMock();
		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.DAY_OF_MONTH, -0);
		fromDate.set(Calendar.HOUR_OF_DAY, 0);
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		Collection<ProcentIntradag> data = service.hamtaTAProcentIntradag(
				fromDate.getTime(), null);

		// Then
		Assert.assertEquals("Borde vara 1 dagars resultat.", 1, data.size());
	}

	@Test
	public void bordeHamtaEnSpecifikDagSomProcentAnalys() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryMock();
		Calendar fromDate = Calendar.getInstance();
		fromDate.set(2013, 2, 9, 0, 26);
		fromDate.set(Calendar.SECOND, 0);
		Calendar slutDatum = Calendar.getInstance();
		slutDatum.set(2013, 2, 9, 23, 59);
		slutDatum.set(Calendar.SECOND, 0);
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		Collection<ProcentIntradag> data = service.hamtaTAProcentIntradag(
				fromDate.getTime(), slutDatum.getTime());

		// Then
		Assert.assertEquals("Borde vara 1 dagars resultat.", 1, data.size());
	}

	@Test
	public void bordeRaknaUtRattProcentOkningForstaDagen() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryMock();
		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.DAY_OF_MONTH, -20);
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		Collection<ProcentIntradag> data = service.hamtaTAProcentIntradag(
				fromDate.getTime(), null);

		// Then
		ProcentIntradag forstaDagen = data.iterator().next();
		PercentEvent[] events = forstaDagen.percentEvents
				.toArray(new PercentEvent[1]);
		Double percent = new Double(events[10].getPercent());
		Assert.assertEquals("Borde vara rätt procent ökning efter 10 minuter.",
				"0.008819098584353924", percent.toString().substring(0, 20));
		percent = new Double(events[90].getPercent());
		Assert.assertEquals(
				"Borde vara rätt procent ökning efter 90 minuter. start=1135.01, nu=1135.9109",
				"0.07937", percent.toString().substring(0, 7));
	}

	@Test
	public void bordeRaknaUtRattProcentOkningAndraDagen() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryMock();
		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.DAY_OF_MONTH, -20);
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		Collection<ProcentIntradag> data = service.hamtaTAProcentIntradag(
				fromDate.getTime(), null);

		// Then
		Iterator<ProcentIntradag> it = data.iterator();
		it.next();
		ProcentIntradag andraDagen = it.next();
		PercentEvent[] events = andraDagen.percentEvents
				.toArray(new PercentEvent[1]);
		Double percent = new Double(events[0].getPercent());
		Assert.assertEquals(
				"Borde vara rätt procent första minuten. start=1134.3994, nu=1135.02",
				"0.054707", percent.toString().substring(0, 8));
		percent = new Double(events[10].getPercent());
		Assert.assertEquals(
				"Borde vara rätt procent ökning efter 10 minuter, start=1134.3994, nu=1135.2202",
				"0.072355", percent.toString().substring(0, 8));
		percent = new Double(events[90].getPercent());
		Assert.assertEquals(
				"Borde vara rätt procent ökning efter 90 minuter. start=1134.3994, nu=1136.8218",
				"0.2135", percent.toString().substring(0, 6));
	}

	@Test
	public void bordeRaknaUtRattProcentOkningEfter20dagar() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryMock();
		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.DAY_OF_MONTH, -20);
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		Collection<ProcentIntradag> data = service.hamtaTAProcentIntradag(
				fromDate.getTime(), null);

		// Then
		ProcentIntradag sistaDagen = null;
		Iterator<ProcentIntradag> it = data.iterator();
		for (int i = 0; i < 20; i++) {
			sistaDagen = it.next();
		}
		PercentEvent[] events = sistaDagen.percentEvents
				.toArray(new PercentEvent[1]);
		Double percent = new Double(events[539].getPercent());
		Assert.assertEquals(
				"Borde vara rätt procent minskning den 20:e dagen. start=1123.0029, nu=1122.4023",
				"-0.0534", percent.toString().substring(0, 7));
	}

	@Test
	public void bordeBetraktasSomEnUppgangPgaUSAeffekten() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryUSAMock();
		Calendar fromDate = Calendar.getInstance();
		fromDate.set(2013, 2, 9, 17, 02);
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		USAOppningseffekt statistik = service
				.hamtaUSAOppningseffektFranDatum(fromDate.getTime());

		Assert.assertEquals("Borde vara en uppgång.", 1, statistik.uppgang);
	}

	@Test
	public void bordeInteBetraktasSomEnUppgangPgaUSAoppningEftersomUppgangenSkeddeInnanUSAOppning() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryUSAMock();
		Calendar fromDate = Calendar.getInstance();
		fromDate.set(2013, 2, 9, 17, 05);
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		USAOppningseffekt statistik = service
				.hamtaUSAOppningseffektFranDatum(fromDate.getTime());

		Assert.assertEquals("Borde INTE vara en uppgång.", 0, statistik.uppgang);
		Assert.assertEquals("Borde INTE vara en nedgång.", 0, statistik.nedgang);
		Assert.assertEquals("Borde vara ingen rörelse.", 1,
				statistik.ingenRorlese);
	}

	@Test
	public void bordeInteBetraktasSomEnNedgangPgaUSAoppningEftersomNedgangenSkeddeInnanUSAOppning() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryUSAMock();
		Calendar fromDate = Calendar.getInstance();
		fromDate.set(2013, 2, 9, 17, 06);
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		USAOppningseffekt statistik = service
				.hamtaUSAOppningseffektFranDatum(fromDate.getTime());

		Assert.assertEquals("Borde INTE vara en uppgång.", 0, statistik.uppgang);
		Assert.assertEquals("Borde INTE vara en nedgång.", 0, statistik.nedgang);
		Assert.assertEquals("Borde vara ingen rörelse.", 1,
				statistik.ingenRorlese);
	}

	@Test
	public void bordeBetraktasSomEnNedgangPgaUSAeffekten() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryUSAMock();
		Calendar fromDate = Calendar.getInstance();
		fromDate.set(2013, 2, 9, 17, 26);
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		USAOppningseffekt statistik = service
				.hamtaUSAOppningseffektFranDatum(fromDate.getTime());

		Assert.assertEquals("Borde vara en nedgång.", 1, statistik.nedgang);
	}

	@Test
	public void bordeBetraktasSomAttUSAInteHarNagonPaverkan() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryUSAMock();
		Calendar fromDate = Calendar.getInstance();
		fromDate.set(2013, 2, 9, 17, 27);
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		USAOppningseffekt statistik = service
				.hamtaUSAOppningseffektFranDatum(fromDate.getTime());

		Assert.assertEquals("Borde vara en stilla dag efter USA öppningen.", 1,
				statistik.ingenRorlese);
	}

	@Test
	public void bordeBetraktasSomEnNedgangOchUppgangPgaUSAOppning() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryUSAMock();
		Calendar fromDate = Calendar.getInstance();
		fromDate.set(2013, 2, 9, 17, 8);
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		USAOppningseffekt statistik = service
				.hamtaUSAOppningseffektFranDatum(fromDate.getTime());

		Assert.assertEquals("Borde vara en nedgång.", 1, statistik.nedgang);
		Assert.assertEquals("Borde vara en uppgång.", 1, statistik.uppgang);
		Assert.assertEquals("Borde INTE vara ingen rörelse.", 0,
				statistik.ingenRorlese);
	}

	@Test
	public void bordeHamtaInstrumentDataForEnUppgangsdagSomBerorPaUSAOppningenPaSpecifiktDatum() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryUSAMock();
		Calendar fromDate = Calendar.getInstance();
		fromDate.set(2013, 2, 9, 17, 02);
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		Collection<ProcentIntradag> data = service
				.hamtaUppgangsdagarPgaUSAOppningMedDatumintervall(
						fromDate.getTime(), fromDate.getTime());

		Assert.assertEquals("Borde vara en uppgång.", 1, data.size());
	}

	@Test
	public void bordeHamtaInstrumentDataForEnNedgangsdagSomBerorPaUSAOppningenFranEttSpecifiktDatum_2() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryUSAMock();
		Calendar fromDate = Calendar.getInstance();
		fromDate.set(2013, 2, 9, 17, 26);
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		Collection<ProcentIntradag> data = service
				.hamtaNedgangsdagarPgaUSAOppningMedDatumerintervall(
						fromDate.getTime(), fromDate.getTime());

		Assert.assertEquals("Borde vara en nedgång.", 1, data.size());
	}

	@Test
	public void bordeHamtaInstrumentDataForEnIngenrorelsedagSomBerorPaUSAOppningenFranEttSpecifiktDatum() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryUSAMock();
		Calendar fromDate = Calendar.getInstance();
		fromDate.set(2013, 2, 9, 17, 5);
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		Collection<ProcentIntradag> data = service
				.hamtaForLitenRorelsedagarPgaUSAOppningMedDatumintervall(
						fromDate.getTime(), fromDate.getTime());

		Assert.assertEquals("Borde vara ingen rörelse.", 1, data.size());
	}

	@Test
	public void borderHamta1MestLikGrafer() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryUSAMock();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013, 2, 9, 17, 10);
		Date fromDate = calendar.getTime();
		calendar.set(2013, 3, 15, 14, 5);
		Date nu = calendar.getTime();
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		Collection<GrafDelta> data = service
				.hamtaMestLikaGraferMedDeltaAlgoritm(1, nu, fromDate);

		Assert.assertEquals("Borde vara 1 mest lik graf som hämtas.", 1,
				data.size());
	}

	/**
	 * Scenario: Söker fram liknande grafer som angiven dag. Söker över 4
	 * månader vilket innebär att sökningen görs i fyra delsökningar. Detta för
	 * att minska volym data som algoritmen behöver bearbeta.
	 */
	@Test
	public void bordeHamta3MestLikaGraferFor4ManaderMedDelsokningar() {
		// Given
		InstrumentRepository repo = mock(InstrumentRepository.class);
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013, 2, 9, 17, 10);
		Date fromDate = calendar.getTime();

		calendar.set(2013, 6, 15, 14, 5);
		Date dagAttJamfora = calendar.getTime();
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		Collection<GrafDelta> data = service
				.hamtaMestLikaGraferMedDeltaAlgoritm(3, dagAttJamfora, fromDate);

		// Then
		calendar.set(2013, 2, 9, 17, 10);
		calendar.add(Calendar.DAY_OF_YEAR, 30);
		Date slut1 = calendar.getTime();
		verify(repo).hamtaInstrumentEvents(fromDate, slut1);
	}

	/**
	 * Scenario: Söker fram liknande grafer som angiven dag. Söker över 4
	 * månader vilket innebär att sökningen görs i fyra delsökningar. Detta för
	 * att minska volym data som algoritmen behöver bearbeta.
	 */
	@Test
	public void bordeHamta3MestLikaGraferFor4Manader() {
		// Given
		InstrumentRepository repo = new InstrumentRepositoryUSAMock();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013, 2, 9, 17, 2);
		Date fromDate = calendar.getTime();

		calendar.set(2013, 6, 15, 14, 5);
		Date dagAttJamfora = calendar.getTime();
		AnalysService service = new AnalysServiceImpl(repo);

		// When
		Collection<GrafDelta> data = service
				.hamtaMestLikaGraferMedDeltaAlgoritm(3, dagAttJamfora, fromDate);

		// Then
		Assert.assertEquals("Borde hämta data för 3 dagar.", 3, data.size());
	}
}
