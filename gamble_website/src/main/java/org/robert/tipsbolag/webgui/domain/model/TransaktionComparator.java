package org.robert.tipsbolag.webgui.domain.model;

import java.util.Comparator;

/**
 * Comparator for Transaktion objects that uses the create date as comparing
 * attribute. When sorting this will sort the latest date in the first position
 * in the list.
 */
public class TransaktionComparator implements Comparator<Transaktion> {

	@Override
	public int compare(Transaktion o1, Transaktion o2) {
		// o1 is greater than o2 return -1.
		if (o1.getTransaktionsTid().getTime() > o2.getTransaktionsTid()
				.getTime()) {
			return -1;
		} else if (o1.getTransaktionsTid().getTime() == o2.getTransaktionsTid()
				.getTime()) {
			return 0;
		} else {
			return 1;
		}
	}

}
