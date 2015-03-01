package org.robert.taanalys.ria.businessdelegate;

import java.util.Collection;
import java.util.Date;

public interface InstrumentServiceBD {

	Collection<ProcentIntradag> fetch20LatestDays();

	UsaOppningseffekt hamtaUSAOppningseffectStatForDeSenasteDagarna(int antalDagar);

	Collection<ProcentIntradag> hamtaNedgangarPgaUSAOppning(Date franTid,
			Date tillTid);

	Collection<ProcentIntradag> hamtaIngenRorelsePgaUSAOppning(Date franTid,
			Date tillTid);

	Collection<ProcentIntradag> hamtaUppgangarPgaUSAOppning(Date franTid,
			Date tillTid);

	/**
	 * Hämtar data för en dag, datat är procentuellt.
	 * 
	 * @param dag
	 *            att hämta data för.
	 */
	ProcentIntradag hamtaProcentIntradag(Date dag);

	Collection<ProcentIntradag> hamtaLiknandeDagar(Date jamforDagen,
			Date franTid);

}
