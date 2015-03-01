package org.robert.taanalys.service.grafanalys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.robert.taanalys.service.GrafDelta;
import org.robert.taanalys.service.ProcentIntradag;

public class GrafIgenkanning {

	public Collection<GrafDelta> hamtaDeMesLikaGraferna(int antalMesLikaDagar,
			ProcentIntradag idag, Collection<ProcentIntradag> dagar) {
		DeltaVardeGrafIgenkanningAlgoritm algoritm = new DeltaVardeGrafIgenkanningAlgoritm();
		List<GrafDelta> mestLikaGrafer = new ArrayList<GrafDelta>();

		for (ProcentIntradag tidigareDag : dagar) {
			double delta = algoritm.calculateDelta(idag, tidigareDag);
			GrafDelta grafDelta = new GrafDelta(delta, tidigareDag);
			mestLikaGrafer.add(grafDelta);

			// Sortera och plocka bort grafen som matchar sämst, e.g ligger
			// först i sorterad lista.
			Collections.sort(mestLikaGrafer, new GrafDeltaComparator());

			if (mestLikaGrafer.size() > antalMesLikaDagar) {
				mestLikaGrafer.remove(0);
			}
		}

		return mestLikaGrafer;
	}

}
