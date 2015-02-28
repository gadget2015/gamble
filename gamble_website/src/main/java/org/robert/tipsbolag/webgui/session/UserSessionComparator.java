package org.robert.tipsbolag.webgui.session;

import java.util.Comparator;

/**
 * Comparator for UserSession objects that uses the create date as comparing
 * attribute. When sorting this will sort the latest date in the first position
 * in the list.
 */
public class UserSessionComparator implements Comparator<UserSession> {

	@Override
	public int compare(UserSession o1, UserSession o2) {
		// o1 is greater than o2 return -1.
		if (o1.getCreatedDate().getTime() > o2.getCreatedDate().getTime()) {
			return -1;
		} else if (o1.getCreatedDate().getTime() == o2.getCreatedDate()
				.getTime()) {
			return 0;
		} else {
			return 1;
		}
	}

}
