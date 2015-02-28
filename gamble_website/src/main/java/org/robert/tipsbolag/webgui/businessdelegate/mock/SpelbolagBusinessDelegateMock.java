package org.robert.tipsbolag.webgui.businessdelegate.mock;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.robert.tipsbolag.webgui.businessdelegate.AuthenticationResponse;
import org.robert.tipsbolag.webgui.businessdelegate.SpelbolagBusinessDelegate;
import org.robert.tipsbolag.webgui.domain.model.Konto;
import org.robert.tipsbolag.webgui.domain.model.Spelare;
import org.robert.tipsbolag.webgui.domain.model.Spelbolag;
import org.robert.tipsbolag.webgui.domain.model.Transaktion;
import org.robert.tipsbolag.webgui.domain.model.UserId;

public class SpelbolagBusinessDelegateMock implements SpelbolagBusinessDelegate {

	private static Map<String, Spelare> spelareRepository;
	private static Map<Long, Spelbolag> spelbolagRepository;
	Calendar cal = Calendar.getInstance();

	public SpelbolagBusinessDelegateMock() {
		if (spelareRepository == null) {
			initRepository();
		}
	}

	public void initRepository() {
		// init repository.
		spelareRepository = new HashMap<String, Spelare>();

		// Spelare #1 : robert.georen@gmail.com
		Spelare s1 = createSpelare();
		s1.setUserId(new UserId("robert.georen@gmail.com"));
		s1.setId(232355);
		cal.set(2012, 10, 02);
		s1.getKonto().laggTillTransaktion(
				new Transaktion(150, 0, "Insättning.", cal.getTime()));
		cal.set(2012, 10, 9);
		s1.getKonto().laggTillTransaktion(
				new Transaktion(0, 50, "Spelat i bolag 'old timers'.", cal
						.getTime()));
		cal.set(2012, 10, 16);
		s1.getKonto().laggTillTransaktion(
				new Transaktion(0, 50, "Spelat i bolag 'old timers'.", cal
						.getTime()));
		cal.set(2012, 10, 23);
		s1.getKonto().laggTillTransaktion(
				new Transaktion(0, 50, "Spelat i bolag 'old timers'.", cal
						.getTime()));
		cal.set(2012, 10, 30);
		s1.getKonto().laggTillTransaktion(
				new Transaktion(0, 50, "Spelat i bolag 'old timers'.", cal
						.getTime()));
		cal.set(2012, 11, 1);
		s1.getKonto().laggTillTransaktion(
				new Transaktion(200, 0, "Insättning'.", cal.getTime()));
		cal.set(2012, 11, 8);
		s1.getKonto().laggTillTransaktion(
				new Transaktion(0, 50, "Spelat i bolag 'old timers'.", cal
						.getTime()));

		spelareRepository.put("robert.georen@gmail.com", s1);

		// Spelare #2 : kalle.anka@swipnet.se
		Spelare s2 = createSpelare();
		s2.setUserId(new UserId("kalle.anka@swipnet.se"));
		s2.setId(892);
		cal.set(2012, 10, 02);
		s2.getKonto().laggTillTransaktion(
				new Transaktion(500, 0, "Insättning.", cal.getTime()));
		cal.set(2012, 10, 9);
		s2.getKonto().laggTillTransaktion(
				new Transaktion(0, 50, "Spelat i bolag 'old timers'.", cal
						.getTime()));
		cal.set(2012, 10, 16);
		s2.getKonto().laggTillTransaktion(
				new Transaktion(0, 50, "Spelat i bolag 'old timers'.", cal
						.getTime()));
		cal.set(2012, 10, 23);
		s2.getKonto().laggTillTransaktion(
				new Transaktion(0, 50, "Spelat i bolag 'old timers'.", cal
						.getTime()));
		spelareRepository.put("kalle.anka@swipnet.se", s2);

		//
		// Initiera spelbolagen.
		//
		spelbolagRepository = new HashMap<Long, Spelbolag>();

		// Bolag 'Old timers'.
		Spelbolag bolag = new Spelbolag();
		bolag.setId(1);
		bolag.setInsatsPerOmgang(50);
		bolag.setNamn("Roberts tipskompisar - Old timers");
		ArrayList<Spelare> spelareIBolaget = new ArrayList<Spelare>();
		spelareIBolaget.add(s1);
		spelareIBolaget.add(s2);
		bolag.setSpelare(spelareIBolaget);
		Konto bolagsKonto = new Konto();
		bolagsKonto.setKontonr(9012901);
		bolag.setKonto(bolagsKonto);
		cal.set(2012, 05, 20);
		bolagsKonto.laggTillTransaktion(new Transaktion(50, 0,
				"Insättning ifrån spelare 'robert.georen@gmail.com'.", cal
						.getTime()));
		bolagsKonto.laggTillTransaktion(new Transaktion(50, 0,
				"Insättning ifrån spelare 'kalle.anka@swipnet.se'.", cal
						.getTime()));
		bolagsKonto.laggTillTransaktion(new Transaktion(0, 100,
				"Betalt Stryktipset till Svenskaspel.", cal.getTime()));
		cal.set(2012, 05, 27);
		bolagsKonto.laggTillTransaktion(new Transaktion(50, 0,
				"Insättning ifrån spelare 'robert.georen@gmail.com'.", cal
						.getTime()));
		bolagsKonto.laggTillTransaktion(new Transaktion(50, 0,
				"Insättning ifrån spelare 'kalle.anka@swipnet.se'.", cal
						.getTime()));
		bolagsKonto.laggTillTransaktion(new Transaktion(0, 105,
				"Betalt Stryktipset till Svenskaspel.", cal.getTime()));
		cal.set(2012, 06, 4);
		bolagsKonto.laggTillTransaktion(new Transaktion(50, 0,
				"Insättning ifrån spelare 'robert.georen@gmail.com'.", cal
						.getTime()));
		bolagsKonto.laggTillTransaktion(new Transaktion(50, 0,
				"Insättning ifrån spelare 'kalle.anka@swipnet.se'.", cal
						.getTime()));
		bolagsKonto.laggTillTransaktion(new Transaktion(0, 96,
				"Betalt Stryktipset till Svenskaspel.", cal.getTime()));

		spelbolagRepository.put(new Long(1), bolag);

		// Bolag: The sharks
		Spelbolag sharks = new Spelbolag();
		sharks.setId(2);
		sharks.setInsatsPerOmgang(30);
		sharks.setNamn("The sharks");
		spelareIBolaget = new ArrayList<Spelare>();
		spelareIBolaget.add(s2);
		sharks.setSpelare(spelareIBolaget);
		bolagsKonto = new Konto();
		bolagsKonto.setKontonr(33312197);
		sharks.setKonto(bolagsKonto);
		cal.set(2012, 05, 20);
		bolagsKonto.laggTillTransaktion(new Transaktion(50, 0,
				"Insättning ifrån spelare 'kalle.anka@swipnet.se'.", cal
						.getTime()));
		bolagsKonto.laggTillTransaktion(new Transaktion(0, 50,
				"Betalt Stryktipset till Svenskaspel.", cal.getTime()));
		cal.set(2012, 05, 27);
		bolagsKonto.laggTillTransaktion(new Transaktion(50, 0,
				"Insättning ifrån spelare 'kalle.anka@swipnet.se'.", cal
						.getTime()));
		bolagsKonto.laggTillTransaktion(new Transaktion(0, 55,
				"Betalt Stryktipset till Svenskaspel.", cal.getTime()));
		spelbolagRepository.put(new Long(2), sharks);

		// Bolag: lantisar
		bolag = new Spelbolag();
		bolag.setId(3);
		bolag.setInsatsPerOmgang(50);
		bolag.setNamn("Lantisar");
		spelareIBolaget = new ArrayList<Spelare>();
		spelareIBolaget.add(s2);
		bolag.setSpelare(spelareIBolaget);
		bolagsKonto = new Konto();
		bolagsKonto.setKontonr(33312197);
		bolag.setKonto(bolagsKonto);
		spelbolagRepository.put(new Long(3), bolag);

		// Koppla ihop så en spelare även är administratör för ett spelbolaget
		// sharks.
		s1.setAdministratorForSpelbolag(sharks);
	}

	private Spelare createSpelare() {
		Spelare spelare1 = new Spelare();
		Konto spelarKonto = new Konto();
		spelarKonto.setKontonr(22);
		spelare1.setKonto(spelarKonto);

		return spelare1;
	}

	@Override
	public AuthenticationResponse createAuthenticateRequest(String retrunUrl) {
		AuthenticationResponse response = new AuthenticationResponse();
		String request = "http://localhost:1967/tips-website/googlelogin_mock.xhtml?openid.assoc_handle=DSFKJSDK323sDSD&openid.ui=popup";
		response.authenticationRequest = request;

		try {
			response.discoveryInformation = new DiscoveryInformation(new URL(
					"http://www.tipsbolag.se"));

		} catch (DiscoveryException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public Spelare hamtaSpelare(UserId userId) {
		Spelare spelare = spelareRepository.get(userId.getUserId());

		return spelare;
	}

	@Override
	public List<Spelbolag> hamtaAllaSpelbolag() {
		List<Spelbolag> all = new ArrayList<Spelbolag>();
		all.addAll(spelbolagRepository.values());

		return all;
	}

	@Override
	public Spelbolag laggTillTransaktionTillSpelbolag(long spelbolagId,
			Transaktion nyTransaktion) {
		Spelbolag bolag = spelbolagRepository.get(spelbolagId);
		bolag.getKonto().laggTillTransaktion(nyTransaktion);

		return bolag;
	}

	@Override
	public Spelare laggTillTransaktionForSpelare(UserId userId,
			Transaktion nyTransaktion) {
		Spelare persisteradSpelare = spelareRepository.get(userId.getUserId());
		persisteradSpelare.getKonto().laggTillTransaktion(nyTransaktion);

		return persisteradSpelare;
	}

	@Override
	public Spelbolag taBetaltAvAllaSpelare(long spelbolagId, Date transaktionTid) {
		Spelbolag bolag = spelbolagRepository.get(spelbolagId);
		bolag.taBetaltAvAllaSeplareForEnOmgang(transaktionTid);

		return bolag;
	}

	@Override
	public AuthenticationResponse createAOuth2AuthenticateRequest(
			String retrunUrl, String clientSessionID) {
		// TODO Auto-generated method stub
		return null;
	}
}