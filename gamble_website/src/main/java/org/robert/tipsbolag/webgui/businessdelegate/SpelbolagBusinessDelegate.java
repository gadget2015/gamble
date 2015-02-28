package org.robert.tipsbolag.webgui.businessdelegate;

import java.util.Date;
import java.util.List;

import org.robert.tipsbolag.webgui.domain.model.Spelare;
import org.robert.tipsbolag.webgui.domain.model.Spelbolag;
import org.robert.tipsbolag.webgui.domain.model.Transaktion;
import org.robert.tipsbolag.webgui.domain.model.UserId;


public interface SpelbolagBusinessDelegate {

	AuthenticationResponse createAuthenticateRequest(String retrunUrl);

	Spelare hamtaSpelare(UserId userId);

	List<Spelbolag> hamtaAllaSpelbolag();

	Spelbolag laggTillTransaktionTillSpelbolag(
			long spelbolagId, Transaktion nyTransaktion);

	Spelare laggTillTransaktionForSpelare(UserId userId,
			Transaktion nyTransaktion);

	Spelbolag taBetaltAvAllaSpelare(long spelbolagId, Date transaktionTid);

	AuthenticationResponse createAOuth2AuthenticateRequest(String retrunUrl, String clientSessionID);

} 
