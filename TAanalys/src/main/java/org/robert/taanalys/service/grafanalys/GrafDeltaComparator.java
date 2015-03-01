package org.robert.taanalys.service.grafanalys;

import java.util.Comparator;

import org.robert.taanalys.service.GrafDelta;

/**
 * Comparator for GrafDelta domain object that uses the delta as comparing
 * attribute. When sorting this will sort the highest number in the first
 * position/list.
 */
public class GrafDeltaComparator implements Comparator<GrafDelta> {

	@Override
	public int compare(GrafDelta o1, GrafDelta o2) {
		// o1 is greater than o2 return -1.
		if (o1.delta > o2.delta) {
			return -1;
		} else if (o1.delta == o2.delta) {
			return 0;
		} else {
			return 1;
		}
	}

}
