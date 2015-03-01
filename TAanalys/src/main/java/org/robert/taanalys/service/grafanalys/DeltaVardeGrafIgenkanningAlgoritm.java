package org.robert.taanalys.service.grafanalys;

import org.robert.taanalys.service.PercentEvent;
import org.robert.taanalys.service.ProcentIntradag;

public class DeltaVardeGrafIgenkanningAlgoritm {

	/**
	 * Jämför aktuell dag upp till antal minuter som registrerats för dagen med
	 * den tidigare dagen.
	 */
	public double calculateDelta(ProcentIntradag aktuell,
			ProcentIntradag tidigare) {
		// Beräknar deltavärde för varje minut.
		double delta = 0d;

		for (PercentEvent minute : aktuell.percentEvents) {
			int minuteOfIntraday = minute.getMinuteOfIntraday();
			PercentEvent dag2Minut = tidigare
					.hamtaPercentEvent(minuteOfIntraday);

			delta += Math.abs(minute.getPercent() - dag2Minut.getPercent());
		}

		return delta;
	}

}
