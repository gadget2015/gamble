package org.robert.taanalys.domain.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Definierar en bearbetningsenhet för TA algoritmerna. Detta används för att
 * minska volym data som bearbetas åt gången.
 * 
 */
public class BearbetningsEnhet {
	public Date franDatum;
	public Date tillDatum;

	public static List<BearbetningsEnhet> delaUppArbetet(Date fromDate,
			Date toDate) {
		Calendar start = Calendar.getInstance();
		start.setTime(fromDate);

		Calendar slut = Calendar.getInstance();
		slut.setTime(toDate);

		List<BearbetningsEnhet> enheter = new ArrayList<BearbetningsEnhet>();

		while (start.before(slut)) {
			BearbetningsEnhet enhet = new BearbetningsEnhet();
			enhet.franDatum = start.getTime();

			start.add(Calendar.DAY_OF_YEAR, 30);
			if (start.after(slut)) {
				enhet.tillDatum = slut.getTime();
			} else {
				enhet.tillDatum = start.getTime();
			}

			enheter.add(enhet);
		}

		return enheter;
	}

	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(franDatum) + " till " + sdf.format(tillDatum);
	}
}
