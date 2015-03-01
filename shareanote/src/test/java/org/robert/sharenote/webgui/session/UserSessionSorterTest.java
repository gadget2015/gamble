package org.robert.sharenote.webgui.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class UserSessionSorterTest {

	@Test
	public void shouldCompareThreeUserSessions() {
		// Given
		long startTime = System.currentTimeMillis();
		List<UserSession> userSessions = new ArrayList<UserSession>();
		UserSession u1 = new UserSession();
		u1.setCreatedDate(new Date(startTime));
		userSessions.add(u1);

		UserSession u2 = new UserSession();
		u2.setCreatedDate(new Date(startTime + 100));
		userSessions.add(u2);

		UserSession u3 = new UserSession();
		u3.setCreatedDate(new Date(startTime + 50));
		userSessions.add(u3);

		// When
		Collections.sort(userSessions, new UserSessionComparator());

		// Then
		Assert.assertEquals(startTime + 100, userSessions.get(0).getCreatedDate()
				.getTime());
		Assert.assertEquals(startTime + 50, userSessions.get(1).getCreatedDate()
				.getTime());
		Assert.assertEquals(startTime, userSessions.get(2).getCreatedDate().getTime());
	}

	@Test
	public void shouldBeTwoEqualUserSessions() {
		// Given
		long startTime = System.currentTimeMillis();
		List<UserSession> userSessions = new ArrayList<UserSession>();
		UserSession u1 = new UserSession();
		u1.setCreatedDate(new Date(startTime));
		userSessions.add(u1);

		UserSession u2 = new UserSession();
		u2.setCreatedDate(new Date(startTime));
		userSessions.add(u2);


		// When
		Collections.sort(userSessions, new UserSessionComparator());

		// Then
		Assert.assertEquals(startTime, userSessions.get(0).getCreatedDate()
				.getTime());
		Assert.assertEquals(startTime, userSessions.get(1).getCreatedDate()
				.getTime());
	}
	
	@Test
	public void shouldJustBeOneObjectToSort() {
		// Given
		long startTime = System.currentTimeMillis();
		List<UserSession> userSessions = new ArrayList<UserSession>();
		UserSession u1 = new UserSession();
		u1.setCreatedDate(new Date(startTime));
		userSessions.add(u1);

		// When
		Collections.sort(userSessions, new UserSessionComparator());

		// Then
		Assert.assertEquals(startTime, userSessions.get(0).getCreatedDate()
				.getTime());
	}
}
