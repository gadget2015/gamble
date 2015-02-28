package org.robert.tipsbolag.webgui.domain.infrastructure;

import java.util.Date;
import java.util.List;

import org.robert.tipsbolag.webgui.domain.model.Spelbolag;
import org.robert.tipsbolag.webgui.domain.model.Transaktion;

public interface SpelbolagRepository {

	Spelbolag skapaNyttSpelbolag(Spelbolag spelbolag);

	List<Spelbolag> hamtaAllaSpelbolag();

	Spelbolag laggTillTransaktionTillSpelbolag(long id, Transaktion trans);

	Spelbolag taBetaltAvAllaSpelare(long id, Date time);

}
