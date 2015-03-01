package org.robert.sharenote.domain.model;

import java.util.Comparator;

/**
 * Comparator for Note domain object that uses the ID as comparing attribute.
 * When sorting this will sort the highest number in the first position/list.
 */
public class NoteComparator implements Comparator<Note> {

	@Override
	public int compare(Note o1, Note o2) {
		// o1 is greater than o2 return -1.
		if (o1.getId() > o2.getId()) {
			return -1;
		} else if (o1.getId() == o2.getId()) {
			return 0;
		} else {
			return 1;
		}
	}
}
