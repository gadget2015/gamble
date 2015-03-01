package org.robert.taanalys.ria.businessdelegate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class InstrumentServiceMock implements InstrumentServiceBD {
	Integer minuteFromOpen = 0;

	@Override
	public Collection<ProcentIntradag> fetch20LatestDays() {
		Collection<ProcentIntradag> days = new ArrayList<ProcentIntradag>();

		// Dagar
		for (int i = 100; i < 150; i++) {
			days.add(createOneDaysPoints(i++));
		}

		return days;
	}

	private PercentEvent createMinuteData(double percent) {
		PercentEvent data = new PercentEvent();
		data.setPercent(percent);
		data.setMinuteOfIntraday(minuteFromOpen++);

		return data;
	}

	private ProcentIntradag createOneDaysPoints(int top) {
		Collection<PercentEvent> data = new ArrayList<PercentEvent>();
		minuteFromOpen = 0;
		for (int i = 0; i < top; i++) {
			double d = (double) (i) / 100;
			data.add(createMinuteData(0.2 + d));
		}
		for (int i = top; i > -100; i--) {
			double d = (double) (i) / 100;
			data.add(createMinuteData(0.1 + d));
		}

		for (int i = -100; i < 100; i++) {
			double d = (double) (i) / 100;
			data.add(createMinuteData(0.1 + d));
		}
		ProcentIntradag day = new ProcentIntradag();
		day.percentEvents = new ArrayList<PercentEvent>();
		day.percentEvents.addAll(data);

		return day;
	}

	@Override
	public UsaOppningseffekt hamtaUSAOppningseffectStatForDeSenasteDagarna(int antalDagar) {
		UsaOppningseffekt statistik = new UsaOppningseffekt();
		// statistik.minstaRorelse = "0.20%";
		// statistik.instrument = "OMXS30";
		statistik.uppgang = 5;
		statistik.nedgang = 3;
		statistik.ingenRorlese = 2;

		return statistik;
	}

	@Override
	public Collection<ProcentIntradag> hamtaUppgangarPgaUSAOppning(
			Date franTid, Date tillTid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ProcentIntradag> hamtaNedgangarPgaUSAOppning(
			Date franTid, Date tillTid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ProcentIntradag> hamtaIngenRorelsePgaUSAOppning(
			Date franTid, Date tillTid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcentIntradag hamtaProcentIntradag(Date time) {
		ProcentIntradag day = createOneDaysPoints(200);

		return day;
	}

	@Override
	public Collection<ProcentIntradag> hamtaLiknandeDagar(
			Date jamforDagen, Date franTid) {
		Collection<ProcentIntradag> liknandeDagar = new ArrayList<ProcentIntradag>();
		liknandeDagar.add(createOneDaysPoints(150));
		liknandeDagar.add(createOneDaysPoints(160));

		return liknandeDagar;
	}
}
