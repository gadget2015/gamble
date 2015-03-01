package org.robert.taanalys.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebService;

import org.robert.taanalys.domain.model.BearbetningsEnhet;
import org.robert.taanalys.domain.model.InstrumentEvent;
import org.robert.taanalys.infrastructure.InstrumentRepository;
import org.robert.taanalys.service.grafanalys.GrafIgenkanning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@WebService(portName = "AnalysServicePort", serviceName = "AnalysService", targetNamespace = "http://service.taanalys.robert.org/", endpointInterface = "org.robert.taanalys.service.AnalysService")
public class AnalysServiceImpl implements AnalysService {
	private Logger logger = LoggerFactory.getLogger(AnalysServiceImpl.class);

	@EJB
	InstrumentRepository instrumentRepo;

	public AnalysServiceImpl() {
	}

	public AnalysServiceImpl(InstrumentRepository repo) {
		this.instrumentRepo = repo;
	}

	@Override
	public Collection<ProcentIntradag> hamtaTAProcentIntradag(Date fromDate,
			Date tillDatum) {
		// Hämtar all data ifrån ett visst datum.
		if (tillDatum == null) {
			// Saknas slutdatum så sätts det till slutet på aktuell dag.
			Calendar today = Calendar.getInstance();
			today.set(Calendar.HOUR_OF_DAY, 23);
			tillDatum = today.getTime();
		}

		Collection<InstrumentEvent> events = instrumentRepo
				.hamtaInstrumentEvents(fromDate, tillDatum);

		// Omforma till procentsatser.
		Collection<ProcentIntradag> allaDagar = new ArrayList<ProcentIntradag>();

		if (events.isEmpty()) {
			logger.info("Sökningen på instrumenthändelse resulterade i inget resultat.");

			return allaDagar;
		} else {
			InstrumentEvent firstEvent = events.iterator().next();
			float startQuote = firstEvent.quote;
			ProcentIntradag tidigareDagen = new ProcentIntradag();
			tidigareDagen.startQuote = startQuote;
			tidigareDagen.time = firstEvent.time;
			int minuteInomTradingDagen = 0;

			ProcentIntradag aktuellDag = tidigareDagen.clone();

			for (InstrumentEvent event : events) {
				if (aktuellDag.sammaDag(event.time)) {
					aktuellDag.percentEvents.add(skapaEttEvent(
							aktuellDag.startQuote, event.quote,
							minuteInomTradingDagen++, event));
					aktuellDag.endQuote = event.quote;
				} else {
					allaDagar.add(aktuellDag);

					tidigareDagen = (ProcentIntradag) aktuellDag.clone();
					aktuellDag = new ProcentIntradag();
					aktuellDag.time = event.time;
					aktuellDag.startQuote = tidigareDagen.endQuote;
					aktuellDag.endQuote = event.quote;
					minuteInomTradingDagen = 0;
					aktuellDag.percentEvents.add(skapaEttEvent(
							aktuellDag.startQuote, event.quote,
							minuteInomTradingDagen, event));
				}
			}

			allaDagar.add(aktuellDag); // Lägger in den sista dagen.

			return allaDagar;
		}
	}

	private PercentEvent skapaEttEvent(float startQuote, float quote,
			int minuteInomTradingDagen, InstrumentEvent event) {
		PercentEvent percentEvent = new PercentEvent();
		percentEvent.setPercent((quote - startQuote) / startQuote * 100);
		percentEvent.setMinuteOfIntraday(minuteInomTradingDagen);
		if (minuteInomTradingDagen == 0) {
			int i = 0;
			// System.out.println(event);
			i = 7;
		}
		return percentEvent;
	}

	@Override
	public USAOppningseffekt hamtaUSAOppningseffektFranDatum(Date franDatum) {
		Collection<ProcentIntradag> allaDagar = hamtaTAProcentIntradag(
				franDatum, null);
		USAOppningseffekt statistik = new USAOppningseffekt();

		for (ProcentIntradag dag : allaDagar) {
			USAOppningseffekt before = statistik.clone();
			statistik.antalDagar++;
			statistik = dag.beraknaUSAOppningsEffekt(statistik);

			// Kollar om det inte hänt någon rörelse alls.
			if (before.isNotChanged(statistik)) {
				// Dagen slutar med ingen rörelse alls.
				statistik.ingenRorlese++;
			}
		}

		return statistik;
	}

	@Override
	public Collection<ProcentIntradag> hamtaUppgangsdagarPgaUSAOppningMedDatumintervall(
			Date startDatum, Date slutDatum) {
		Collection<ProcentIntradag> allaDagar = hamtaTAProcentIntradag(
				startDatum, slutDatum);
		Collection<ProcentIntradag> uppgangsdagar = new ArrayList<ProcentIntradag>();

		USAOppningseffekt statistik = new USAOppningseffekt();

		for (ProcentIntradag dag : allaDagar) {
			USAOppningseffekt before = statistik.clone();
			statistik.antalDagar++;
			statistik = dag.beraknaUSAOppningsEffekt(statistik);

			if (statistik.arEnUppgang(before)) {
				uppgangsdagar.add(dag);
			}
		}

		return uppgangsdagar;
	}

	@Override
	public Collection<ProcentIntradag> hamtaNedgangsdagarPgaUSAOppningMedDatumerintervall(
			Date startDatum, Date slutDatum) {
		Collection<ProcentIntradag> allaDagar = hamtaTAProcentIntradag(
				startDatum, slutDatum);
		Collection<ProcentIntradag> nedgangsdagar = new ArrayList<ProcentIntradag>();

		USAOppningseffekt statistik = new USAOppningseffekt();

		for (ProcentIntradag dag : allaDagar) {
			USAOppningseffekt before = statistik.clone();
			statistik.antalDagar++;
			statistik = dag.beraknaUSAOppningsEffekt(statistik);

			if (statistik.arEnNedgang(before)) {
				nedgangsdagar.add(dag);
			}
		}

		return nedgangsdagar;
	}

	@Override
	public Collection<ProcentIntradag> hamtaForLitenRorelsedagarPgaUSAOppningMedDatumintervall(
			Date startDatum, Date slutDatum) {
		Collection<ProcentIntradag> allaDagar = hamtaTAProcentIntradag(
				startDatum, slutDatum);
		Collection<ProcentIntradag> forLitenRorelse = new ArrayList<ProcentIntradag>();

		USAOppningseffekt statistik = new USAOppningseffekt();

		for (ProcentIntradag dag : allaDagar) {
			USAOppningseffekt before = statistik.clone();
			statistik.antalDagar++;
			statistik = dag.beraknaUSAOppningsEffekt(statistik);

			// Kollar om det inte hänt någon rörelse alls.
			if (before.isNotChanged(statistik)) {
				// Dagen slutar med ingen rörelse alls.
				forLitenRorelse.add(dag);
			}
		}

		return forLitenRorelse;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public Collection<GrafDelta> hamtaMestLikaGraferMedDeltaAlgoritm(
			int antalLikaDagar, Date dagAttJamfora, Date fromDate) {
		// Hämtar procent data/grafen för aktuell dag som en graf ska hittas
		// för.
		ProcentIntradag dagAttJamforaMed = hamtaProcentIntradag(dagAttJamfora);

		// Dela upp bearbetningen i en per månad med givet fråndatum till dagens
		// datum.
		Calendar slutDatum = Calendar.getInstance();
		List<BearbetningsEnhet> bearbetningar = BearbetningsEnhet
				.delaUppArbetet(fromDate, slutDatum.getTime());
		int antalBearbetningar = bearbetningar.size();
		Collection<GrafDelta> totaltMestLikaDagar = new ArrayList<GrafDelta>();

		for (int i = 0; i < antalBearbetningar; i++) {
			// Hämat data för som ingår i en bearbetning.
			Date startBearbetning = bearbetningar.get(i).franDatum;
			Date slutBearbetning = bearbetningar.get(i).tillDatum;
			Collection<ProcentIntradag> dagarAttSokaBland = hamtaTAProcentIntradag(
					startBearbetning, slutBearbetning);

			// Letar fram liknande dagar för aktuell bearbetning.
			GrafIgenkanning service = new GrafIgenkanning();
			Collection<GrafDelta> mestLikaDagar = service
					.hamtaDeMesLikaGraferna(antalLikaDagar, dagAttJamforaMed,
							dagarAttSokaBland);

			// Väger bearbetningen lika dagar med den totalt sätt mest lika
			// dagarna.
			totaltMestLikaDagar.addAll(mestLikaDagar);
			Collection<ProcentIntradag> totaltDagarAttSokaBland = new ArrayList<ProcentIntradag>();
			for (GrafDelta delta : totaltMestLikaDagar) {
				totaltDagarAttSokaBland.add(delta.intraDag);
			}

			// Gör en ny analys för att sålla bort några dagar (antalLikaDagar)
			// ifrån de nya
			// totala mängden lika dagar.
			totaltMestLikaDagar = service.hamtaDeMesLikaGraferna(
					antalLikaDagar, dagAttJamforaMed, totaltDagarAttSokaBland);
			
			// Clean up memory consumption, e.g do a GC.
			System.gc();
		}

		return totaltMestLikaDagar;
	}

	@Override
	public ProcentIntradag hamtaProcentIntradag(Date datum) {
		Calendar dagen = Calendar.getInstance();
		dagen.setTime(datum);
		dagen.set(Calendar.HOUR_OF_DAY, 0);
		dagen.set(Calendar.MINUTE, 0);
		Date franDatum = dagen.getTime();

		dagen.set(Calendar.HOUR_OF_DAY, 23);
		dagen.set(Calendar.MINUTE, 59);
		Date tillDatum = dagen.getTime();

		Collection<ProcentIntradag> dagar = hamtaTAProcentIntradag(franDatum,
				tillDatum);
		if (dagar.size() == 0) {
			logger.info("Hittade inget data för angivet datum: start="
					+ franDatum + ", till=" + tillDatum);
			return new ProcentIntradag(); // NullReference-Pattern.
		} else {
			return dagar.iterator().next();
		}
	}
}
