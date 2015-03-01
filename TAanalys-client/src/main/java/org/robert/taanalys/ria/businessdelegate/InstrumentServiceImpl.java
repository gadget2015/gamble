package org.robert.taanalys.ria.businessdelegate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class InstrumentServiceImpl implements InstrumentServiceBD {
	
	@Override
	public Collection<ProcentIntradag> fetch20LatestDays() {
		ServiceLocator serviceLocator = new ServiceLocator();
		try {
			// Get service endpoint
			AnalysService endpoint = serviceLocator.getActivityService();

			// Create request
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DAY_OF_MONTH, -1);
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(now.getTime());
			XMLGregorianCalendar fronDatum = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);

			now = Calendar.getInstance();
			now.set(Calendar.HOUR_OF_DAY, 23);
			now.set(Calendar.MINUTE, 59);
			c.setTime(now.getTime());
			XMLGregorianCalendar tillDatum = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);

			// Call service
			Collection<ProcentIntradag> allData = endpoint
					.hamtaTAProcentIntradag(fronDatum, tillDatum);
			// printDebug(allData);
			return allData;
		} catch (ServiceLocatorException e) {
			throw new RuntimeException(
					"Error while looking for host to connect to: "
							+ e.getMessage());
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(
					"Datum konverteringsfel vid anrop till Webservice: "
							+ e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	private void printDebug(Collection<ProcentIntradag> allData) {
		for (ProcentIntradag dag : allData) {
			System.out.println("Dag:" + dag.time + ", Start quote:"
					+ dag.startQuote + ", end quote:" + dag.endQuote);
			for (PercentEvent event : dag.getPercentEvents()) {
				System.out.println(event.getPercent());
			}
		}
	}

	@Override
	public UsaOppningseffekt hamtaUSAOppningseffectStatForDeSenasteDagarna(
			int antalDagar) {
		ServiceLocator serviceLocator = new ServiceLocator();
		try {
			// Get service endpoint
			AnalysService endpoint = serviceLocator.getActivityService();

			// Create request
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DAY_OF_MONTH, -antalDagar);
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(now.getTime());
			XMLGregorianCalendar fronDatum = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);

			// Call service
			UsaOppningseffekt statistik = endpoint
					.hamtaUSAOppningseffektFranDatum(fronDatum);
			// printDebug(allData);
			return statistik;
		} catch (ServiceLocatorException e) {
			throw new RuntimeException(
					"Error while looking for host to connect to: "
							+ e.getMessage());
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(
					"Datum konverteringsfel vid anrop till Webservice: "
							+ e.getMessage());
		}
	}

	@Override
	public Collection<ProcentIntradag> hamtaNedgangarPgaUSAOppning(
			Date franTid, Date tillTid) {
		ServiceLocator serviceLocator = new ServiceLocator();

		try {
			// Get service endpoint
			AnalysService endpoint = serviceLocator.getActivityService();

			// Create request
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(franTid);
			XMLGregorianCalendar fronDatum = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);

			c.setTime(tillTid);
			XMLGregorianCalendar tillDatum = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);

			// Call service
			Collection<ProcentIntradag> allData = endpoint
					.hamtaNedgangsdagarPgaUSAOppningMedDatumerintervall(
							fronDatum, tillDatum);

			return allData;
		} catch (ServiceLocatorException e) {
			throw new RuntimeException(
					"Error while looking for host to connect to: "
							+ e.getMessage());
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(
					"Datum konverteringsfel vid anrop till Webservice: "
							+ e.getMessage());
		}
	}

	@Override
	public Collection<ProcentIntradag> hamtaIngenRorelsePgaUSAOppning(
			Date franTid, Date tillTid) {
		ServiceLocator serviceLocator = new ServiceLocator();

		try {
			// Get service endpoint
			AnalysService endpoint = serviceLocator.getActivityService();

			// Create request
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(franTid);
			XMLGregorianCalendar fronDatum = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);

			c.setTime(tillTid);
			XMLGregorianCalendar tillDatum = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);

			// Call service
			Collection<ProcentIntradag> allData = endpoint
					.hamtaForLitenRorelsedagarPgaUSAOppningMedDatumintervall(
							fronDatum, tillDatum);

			return allData;
		} catch (ServiceLocatorException e) {
			throw new RuntimeException(
					"Error while looking for host to connect to: "
							+ e.getMessage());
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(
					"Datum konverteringsfel vid anrop till Webservice: "
							+ e.getMessage());
		}
	}

	@Override
	public Collection<ProcentIntradag> hamtaUppgangarPgaUSAOppning(
			Date franTid, Date tillTid) {
		ServiceLocator serviceLocator = new ServiceLocator();
		try {
			// Get service endpoint
			AnalysService endpoint = serviceLocator.getActivityService();

			// Create request
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(franTid);
			XMLGregorianCalendar fronDatum = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);

			c.setTime(tillTid);
			XMLGregorianCalendar tillDatum = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);

			// Call service
			Collection<ProcentIntradag> allData = endpoint
					.hamtaUppgangsdagarPgaUSAOppningMedDatumintervall(
							fronDatum, tillDatum);

			return allData;
		} catch (ServiceLocatorException e) {
			throw new RuntimeException(
					"Error while looking for host to connect to: "
							+ e.getMessage());
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(
					"Datum konverteringsfel vid anrop till Webservice: "
							+ e.getMessage());
		}
	}

	@Override
	public ProcentIntradag hamtaProcentIntradag(Date dagAttHamtaGrafFor) {
		ServiceLocator serviceLocator = new ServiceLocator();
		try {
			// Get service endpoint
			AnalysService endpoint = serviceLocator.getActivityService();

			// Create request
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(dagAttHamtaGrafFor);
			XMLGregorianCalendar aktuellDag = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);

			// Call service
			ProcentIntradag dagen = endpoint.hamtaProcentIntradag(aktuellDag);

			return dagen;
		} catch (ServiceLocatorException e) {
			throw new RuntimeException(
					"Error while looking for host to connect to: "
							+ e.getMessage());
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(
					"Datum konverteringsfel vid anrop till Webservice: "
							+ e.getMessage());
		}
	}

	@Override
	public Collection<ProcentIntradag> hamtaLiknandeDagar(Date jamforDagen,
			Date franTid) {
		ServiceLocator serviceLocator = new ServiceLocator();
		try {
			// Get service endpoint
			AnalysService endpoint = serviceLocator.getActivityService();

			// Create request
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(franTid);
			XMLGregorianCalendar fronDatum = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);

			c.setTime(jamforDagen);
			XMLGregorianCalendar jamforDag = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);

			// Call service
			Collection<GrafDelta> liknandeDagar = endpoint
					.hamtaMestLikaGraferMedDeltaAlgoritm(5, jamforDag,
							fronDatum);

			// Snyggar till returdata.
			Collection<ProcentIntradag> dagar = new ArrayList<ProcentIntradag>();

			for (GrafDelta dag : liknandeDagar) {
				dagar.add(dag.intraDag);
			}

			return dagar;
		} catch (ServiceLocatorException e) {
			throw new RuntimeException(
					"Error while looking for host to connect to: "
							+ e.getMessage());
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(
					"Datum konverteringsfel vid anrop till Webservice: "
							+ e.getMessage());
		}
	}
}
