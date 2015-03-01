package org.robert.taanalys.service;

import java.util.Collection;
import java.util.Date;

import javax.jws.WebService;

@WebService(targetNamespace = "http://service.taanalys.robert.org/")
public interface AnalysService {
	/**
	 * Omformar en dags kurs till procentsats på dess rörelse jämfört med
	 * öppningskursen. Detta istället för hur många punkter indexet/kursen gått
	 * upp eller ner.
	 * 
	 * @param franDatum
	 * @param tillDatum
	 * @return
	 */
	Collection<ProcentIntradag> hamtaTAProcentIntradag(Date franDatum,
			Date tillDatum);

	USAOppningseffekt hamtaUSAOppningseffektFranDatum(Date franDatum);

	Collection<ProcentIntradag> hamtaUppgangsdagarPgaUSAOppningMedDatumintervall(
			Date startDatum, Date slutDatum);

	Collection<ProcentIntradag> hamtaNedgangsdagarPgaUSAOppningMedDatumerintervall(
			Date startDatum, Date slutDatum);

	Collection<ProcentIntradag> hamtaForLitenRorelsedagarPgaUSAOppningMedDatumintervall(
			Date startDatum, Date slutDatum);

	/**
	 * Hämtar de X st grafer som är mest lika en angiven dag. Grafer som ska
	 * jämföras med är dem som anges som från datum.
	 */
	Collection<GrafDelta> hamtaMestLikaGraferMedDeltaAlgoritm(
			int antalLikaDagar, Date dagAttJamfora, Date fromDate);

	/**
	 * Hämtar procent graf data för given dag. Hämtar data från tidig morgon
	 * (00:00) till sen kväll (24:00) given dag.
	 */
	ProcentIntradag hamtaProcentIntradag(Date datum);
}
