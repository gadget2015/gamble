package org.robert.tipsbolag.webgui.businessdelegate.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.ax.FetchRequest;
import org.robert.common.openid.OpenIdClient;
import org.robert.common.openid.OpenIdClientImpl;
import org.robert.tipsbolag.webgui.businessdelegate.AuthenticationResponse;
import org.robert.tipsbolag.webgui.businessdelegate.SpelbolagBusinessDelegate;
import org.robert.tipsbolag.webgui.domain.infrastructure.SpelareRepository;
import org.robert.tipsbolag.webgui.domain.infrastructure.SpelbolagRepository;
import org.robert.tipsbolag.webgui.domain.model.Spelare;
import org.robert.tipsbolag.webgui.domain.model.Spelbolag;
import org.robert.tipsbolag.webgui.domain.model.Transaktion;
import org.robert.tipsbolag.webgui.domain.model.UserId;

@Stateless
public class SpelbolagBusinessDelegateImpl implements SpelbolagBusinessDelegate {
	static String HTTPS_WWW_GOOGLE_COM_ACCOUNTS_O8_ID = "https://www.google.com/accounts/o8/id";
	static ConsumerManager openIdClient;

	@EJB
	SpelbolagRepository spelbolagRepo;

	@EJB
	SpelareRepository spelareRepository;

	public AuthenticationResponse createAuthenticateRequest(String returnURL) {
		OpenIdClient openIdClient = new OpenIdClientImpl();
		List<DiscoveryInformation> discoveries;

		try {
			discoveries = (List<DiscoveryInformation>) openIdClient
					.discover(HTTPS_WWW_GOOGLE_COM_ACCOUNTS_O8_ID);

			DiscoveryInformation discovered = openIdClient
					.associate(discoveries);
			AuthenticationResponse response = new AuthenticationResponse();
			response.discoveryInformation = discovered;
			AuthRequest authReq = openIdClient.authenticate(discovered,
					"http://" + returnURL + "/googleauthentication.servlet");

			FetchRequest fetch = FetchRequest.createFetchRequest();
			fetch.addAttribute("email",
					"http://schema.openid.net/contact/email", // type URI
					true); // required

			// attach the extension to the authentication request
			authReq.addExtension(fetch);

			// Create response
			String serviceURL = authReq.getDestinationUrl(true);
			response.authenticationRequest = serviceURL;

			return response;

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Spelare hamtaSpelare(UserId userId) {
		return spelareRepository.hamtaSpelareMedAnvandarId(userId);
	}

	@Override
	public List<Spelbolag> hamtaAllaSpelbolag() {
		return spelbolagRepo.hamtaAllaSpelbolag();
	}

	@Override
	public Spelbolag laggTillTransaktionTillSpelbolag(long spelbolagId,
			Transaktion nyTransaktion) {
		return spelbolagRepo.laggTillTransaktionTillSpelbolag(spelbolagId,
				nyTransaktion);
	}

	@Override
	public Spelare laggTillTransaktionForSpelare(UserId userId,
			Transaktion nyTransaktion) {
		return spelareRepository.laggTillEnTransaktion(userId, nyTransaktion);
	}

	@Override
	public Spelbolag taBetaltAvAllaSpelare(long spelbolagId, Date transaktionTid) {
		return spelbolagRepo.taBetaltAvAllaSpelare(spelbolagId, transaktionTid);
	}

	@Override
	public AuthenticationResponse createAOuth2AuthenticateRequest(
			String retrunUrl, String clientSessionID) {
		// Creates a request URL
		String AOuth2_URL_Template = "https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com/auth/plus.me%20https://www.googleapis.com/auth/userinfo.email&state=&redirect_uri=&response_type=code&client_id=607736284212-t8iejp7vq3pf853r88ncspgreb7fvtgo.apps.googleusercontent.com&access_type=offline";
		String requestURL = AOuth2_URL_Template.replaceAll("redirect_uri=",
				"redirect_uri=" + retrunUrl);
		requestURL = requestURL
				.replaceAll("state=", "state=" + clientSessionID);

		// wrap up response of method.
		AuthenticationResponse response = new AuthenticationResponse();
		response.authenticationRequest = requestURL;

		return response;
	}

}
