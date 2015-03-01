package org.robert.taanalys.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.robert.taanalys.domain.model.InstrumentEvent;
import org.robert.taanalys.infrastructure.InstrumentRepository;

public class InstrumentRepositoryMock implements InstrumentRepository {

	@Override
	public InstrumentEvent sparaInstrument(InstrumentEvent event) {
		return null;
	}

	@Override
	public Collection<InstrumentEvent> hamtaInstrumentEventsFran(
			Date dateToFechEvents) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToFechEvents);
		return skapa20DagarsTestdata(calendar);
	}

	private Collection<InstrumentEvent> skapa20DagarsTestdata(
			Calendar startDatum) {
		Collection<InstrumentEvent> days = new ArrayList<InstrumentEvent>();
		float toAdd = 0.01f;
		Date now = Calendar.getInstance().getTime();
		int i = 0;
		while (startDatum.getTime().before(now)) {
			// System.out.println(startDatum.getTime() + ":" + ++i);
			days.addAll(skapaTestdata(startDatum, toAdd));
			toAdd += 0.01;

			startDatum.add(Calendar.DAY_OF_MONTH, 1);
			startDatum.set(Calendar.HOUR_OF_DAY, 0);
		}

		return days;
	}

	private Collection<InstrumentEvent> skapaTestdata(Calendar calendar,
			float changes) {
		Collection<InstrumentEvent> data = new ArrayList<InstrumentEvent>();
		float x = 0;
		InstrumentEvent event = new InstrumentEvent();
		event.quote = 1135.0f;

		for (int i = 0; i < 9; i++) { // börsen är öppen 8.5 timmar.
			calendar.set(Calendar.HOUR_OF_DAY, 9 + i);
			if (i < 4) {
				x = changes;
			} else {
				x = -changes;
			}

			for (int j = 0; j < 60; j++) {// ett event per minut.
				calendar.set(Calendar.MINUTE, j);
				float quoteToAdd = event.quote + x;
				event = new InstrumentEvent();
				event.name = "OMXS30";
				event.quote = quoteToAdd;
				event.time = calendar.getTime();
				data.add(event);
				// System.out.println(i * 60 + j + ":" + event);
			}
		}

		return data;
	}

	@Override
	public Collection<InstrumentEvent> hamtaInstrumentEvents(Date startDatum,
			Date slutDatum) {

		if ("Sat Mar 09 00:26:00 CET 2013".equals(startDatum.toString())
				&& "Sat Mar 09 23:59:00 CET 2013".equals(slutDatum.toString())) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDatum);

			return skapaTestdata(calendar, 0.05f);
		} else {
			return hamtaInstrumentEventsFran(startDatum);
		}

	}

}
