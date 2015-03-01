package org.robert.taanalys.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
* Definierar en dags kursrörelse för en index/aktie med procent som mätenhet.
*/
public class ProcentIntradag {
        // Lista med alla mätpunkter under en dags i procentsats.
	public Collection<PercentEvent> percentEvents = new ArrayList<PercentEvent>();
	public float startQuote;
	public float endQuote;
	public Date time;

	public boolean sammaDag(Date toCompareTo) {
		Calendar thisOne = Calendar.getInstance();
		thisOne.setTime(time);

		Calendar toCompare = Calendar.getInstance();
		toCompare.setTime(toCompareTo);

		if ((thisOne.get(Calendar.YEAR) == toCompare.get(Calendar.YEAR))
				&& (thisOne.get(Calendar.MONTH) == toCompare
						.get(Calendar.MONTH))
				&& (thisOne.get(Calendar.DAY_OF_MONTH) == toCompare
						.get(Calendar.DAY_OF_MONTH))) {
			return true;
		} else {
			return false;
		}
	}

	public ProcentIntradag clone() {
		ProcentIntradag cloned = new ProcentIntradag();
		cloned.startQuote = startQuote;
		cloned.endQuote = endQuote;
		cloned.time = (Date) time.clone();

		return cloned;
	}

	public USAOppningseffekt beraknaUSAOppningsEffekt(
			USAOppningseffekt statistik) {
		float MINIMAL_RORELSE_I_PROCENT = 0.25f;
		int MINUTES_OF_INTRADAY_WHEN_USA_OPENS = 390;
		double procentVidUSAOppning = 0;
		boolean uppgang = false;
		boolean nedgang = false;

		for (PercentEvent event : percentEvents) {
			if (event.getMinuteOfIntraday() == MINUTES_OF_INTRADAY_WHEN_USA_OPENS) {
				// kl. är 15:30.
				procentVidUSAOppning = event.getPercent();
			}

			if (event.getMinuteOfIntraday() > MINUTES_OF_INTRADAY_WHEN_USA_OPENS) {
				double andringEfterOppning = event.getPercent()
						- procentVidUSAOppning;

				if (andringEfterOppning > MINIMAL_RORELSE_I_PROCENT) {
					// Dagen slutar med en uppgång;
					uppgang = true;
				} else if (andringEfterOppning < -MINIMAL_RORELSE_I_PROCENT) {
					// Dagen slutar med en nedgång.
					nedgang = true;
				}
			}
		}

		// Sammanställ resultat.
		if (uppgang)
			statistik.uppgang++;
		if (nedgang)
			statistik.nedgang++;

		return statistik;
	}

	public void laggTillEvent(PercentEvent aMinute) {
		this.percentEvents.add(aMinute);
	}

	public PercentEvent hamtaPercentEvent(int minuteOfIntraday) {
		for (PercentEvent event : percentEvents) {
			if (event.getMinuteOfIntraday() == minuteOfIntraday) {
				return event;
			}
		}
		
		return new PercentEvent();
	}
}
