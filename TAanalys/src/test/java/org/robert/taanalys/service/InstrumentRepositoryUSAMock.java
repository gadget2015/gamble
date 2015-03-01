package org.robert.taanalys.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.robert.taanalys.domain.model.InstrumentEvent;
import org.robert.taanalys.infrastructure.InstrumentRepository;

/**
 * Används för att simulara händelser efter att USA börsen öppnat. Alltså, data
 * innan 15:30 är irrelevant.
 * 
 */
public class InstrumentRepositoryUSAMock implements InstrumentRepository {

	@Override
	public InstrumentEvent sparaInstrument(InstrumentEvent event) {

		return null;
	}

	@Override
	public Collection<InstrumentEvent> hamtaInstrumentEventsFran(
			Date datumAttHamtaIfran) {
		Calendar startDatum = Calendar.getInstance();
		startDatum.setTime(datumAttHamtaIfran);

		Collection<InstrumentEvent> days = new ArrayList<InstrumentEvent>();
		int minute = startDatum.get(Calendar.MINUTE);

		// Lägg till en uppgångs dag.
		if (minute == 0) {
			days.addAll(skapaKonstantTestdataEfterUSAOppning(startDatum, 0.04f));
		}
		
		// Lägg till en uppgångs dag.
		if (minute == 2) {
			days.addAll(skapaKonstantTestdataEfterUSAOppning(startDatum, 0.05f));
		}

		// Lägg till en nedgångs dag.
		if (minute == 26) {
			days.addAll(skapaKonstantTestdataEfterUSAOppning(startDatum, -.05f));
		}

		// Lägg till en nedgångs dag.
		if (minute == 27) {
			days.addAll(skapaKonstantTestdataEfterUSAOppning(startDatum,
					-.0001f));
		}

		// Lägg till en uppgångdag före USA öppning.
		if (minute == 05) {
			days.addAll(skapaTestdataForeUSAOppning(startDatum, .05f));
		}

		// Lägg till en nedgångsdag före USA öppning
		if (minute == 06) {
			days.addAll(skapaTestdataForeUSAOppning(startDatum, -.05f));
		}

		// Lägg till en kurva som både går upp och ner innan stängning
		if (minute == 8) {
			days.addAll(skapaTestdataEfterUSAOppningSomEnSinkurva(startDatum,
					.1f));
		}

		// Lägg till tre uppgångdagar efter att USA öppnat.
		if (minute == 9) {
			days.addAll(skapaKonstantTestdataEfterUSAOppning(startDatum, .05f));

			Calendar nastaDatum = Calendar.getInstance();
			nastaDatum.setTime(datumAttHamtaIfran);
			nastaDatum.add(Calendar.DAY_OF_MONTH, 1);
			days.addAll(skapaKonstantTestdataEfterUSAOppning(nastaDatum, .06f));

			nastaDatum.add(Calendar.DAY_OF_MONTH, 1);
			days.addAll(skapaKonstantTestdataEfterUSAOppning(nastaDatum, .04f));
		}

		// Lägg till tre uppgångdagar FÖRE att USA öppnat.
		if (minute == 10) {
			days.addAll(skapaTestdataForeUSAOppning(startDatum, .05f));

			startDatum.add(Calendar.DAY_OF_MONTH, 1);
			days.addAll(skapaKonstantTestdataEfterUSAOppning(startDatum, .06f));

			startDatum.add(Calendar.DAY_OF_MONTH, 1);
			days.addAll(skapaKonstantTestdataEfterUSAOppning(startDatum, .04f));
		}

		return days;
	}

	private Collection<InstrumentEvent> skapaTestdataEfterUSAOppningSomEnSinkurva(
			Calendar startDatum, float changes) {
		Collection<InstrumentEvent> data = new ArrayList<InstrumentEvent>();
		InstrumentEvent event = new InstrumentEvent();
		event.quote = 1135.0f;
		float x = 0;

		for (int i = 0; i < 9; i++) { // börsen är öppen 8.5 timmar.
			startDatum.set(Calendar.HOUR_OF_DAY, 9 + i);

			for (int j = 0; j < 60; j++) {
				// ett event per minut.
				startDatum.set(Calendar.MINUTE, j);
				float quoteToAdd = event.quote + x;
				event = new InstrumentEvent();
				event.name = "OMXS30";
				event.quote = quoteToAdd;
				event.time = startDatum.getTime();
				data.add(event);

				int minutesOfIntraday = i * 60 + j;

				if (minutesOfIntraday >= 390) {
					// kl. 15.30.
					x = changes;
				}
				if (minutesOfIntraday > 450) {
					// kl. 16:40 510 =17:30
					x = -changes * 2;
				}

				// System.out.println(minutesOfIntraday + ":" + event);
			}
		}

		return data;
	}

	/**
	 * Skapar testdata: Mellan stockholms öppning och USA öppning så ökar
	 * kursen/index konstant med given enhet (changes) men efter att USA öppnar
	 * så står kursen helt stilla.
	 * 
	 * @param changes
	 */
	private Collection<? extends InstrumentEvent> skapaTestdataForeUSAOppning(
			Calendar startDatum, float changes) {
		Collection<InstrumentEvent> data = new ArrayList<InstrumentEvent>();
		InstrumentEvent event = new InstrumentEvent();
		event.quote = 1135.0f;
		float x = 0;

		for (int i = 0; i < 9; i++) { // börsen är öppen 8.5 timmar.
			startDatum.set(Calendar.HOUR_OF_DAY, 9 + i);

			for (int j = 0; j < 60; j++) {
				// ett event per minut.
				startDatum.set(Calendar.MINUTE, j);
				float quoteToAdd = event.quote + x;
				event = new InstrumentEvent();
				event.name = "OMXS30";
				event.quote = quoteToAdd;
				event.time = startDatum.getTime();
				data.add(event);

				if (i * 60 + j < 390) {
					// Före kl. 15.30.
					x = changes;
				} else {
					// Efter kl. 15.30 så lägger man inte till något mer =
					// grafen blir konstant till slutet
					x = 0;
				}
			}
		}

		return data;
	}

	/**
	 * Skapar testdata. Data mellan börsen öppning 09:00 till 15:30 är samma
	 * kurs, men efter kl. 15:30 (USA öppnar) så ökar kursen/index konstant med
	 * given enhet
	 * 
	 * @param changes
	 *            som kursen ska öka efter kl. 15:3.
	 */
	private Collection<InstrumentEvent> skapaKonstantTestdataEfterUSAOppning(
			Calendar calendar, float changes) {
		Collection<InstrumentEvent> data = new ArrayList<InstrumentEvent>();
		InstrumentEvent event = new InstrumentEvent();
		event.quote = 1135.0f;
		float x = 0;

		for (int i = 0; i < 9; i++) { // börsen är öppen 8.5 timmar.
			calendar.set(Calendar.HOUR_OF_DAY, 9 + i);

			for (int j = 0; j < 60; j++) {
				// ett event per minut.
				calendar.set(Calendar.MINUTE, j);
				float quoteToAdd = event.quote + x;
				event = new InstrumentEvent();
				event.name = "OMXS30";
				event.quote = quoteToAdd;
				event.time = calendar.getTime();
				data.add(event);
				// System.out.println(i * 60 + j + ":" + event);

				if (i * 60 + j >= 390) {
					// kl. 15.30. Gör att grafen ökar konstant med + x för varje
					// minut.
					x = changes;
				}
			}
		}

		return data;
	}

	@Override
	public Collection<InstrumentEvent> hamtaInstrumentEvents(Date startDatum,
			Date slutDatum) {
		return hamtaInstrumentEventsFran(startDatum);
	}
}
