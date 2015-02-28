package org.robert.tipsbolag.webgui.domain.infrastructure;

import org.robert.tipsbolag.webgui.domain.model.Spelare;
import org.robert.tipsbolag.webgui.domain.model.Transaktion;
import org.robert.tipsbolag.webgui.domain.model.UserId;

public interface SpelareRepository {
	Spelare hamtaSpelareMedAnvandarId(UserId anvandarId);
	Spelare laggTillEnTransaktion(UserId userId, Transaktion trans);
}
