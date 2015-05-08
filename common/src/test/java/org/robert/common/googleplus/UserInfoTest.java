package org.robert.common.googleplus;

import junit.framework.Assert;

import org.junit.Test;

public class UserInfoTest {

	@Test
	public void shouldBeAValidParsedEmail() {
		// Given when
		UserInfo ui = new UserInfo("{ \"family_name\": \"Georen\",  \"name\": \"Robert Georen\",  \"picture\": \"https://lh4.googleusercontent.com/-_v0NE4IV32c/AAAAAAAAAAI/AAAAAAAAEw0/54YzkmtbJ_4/photo.jpg\",   \"gender\": \"male\",   \"email\": \"robert.georen@gmail.com\",   \"link\": \"https://plus.google.com/106880955009352260440\",   \"given_name\": \"Robert\",   \"id\": \"106880955009352260440\",   \"verified_email\": true}");

		// Then
		Assert.assertEquals("Borde vara rätt email", "robert.georen@gmail.com",
				ui.getEmail());
	}


	@Test
	public void shouldBeAValidParsedName() {
		// Given when
		UserInfo ui = new UserInfo("{ \"family_name\": \"Georen\",  \"name\": \"Robert Georen\",  \"picture\": \"https://lh4.googleusercontent.com/-_v0NE4IV32c/AAAAAAAAAAI/AAAAAAAAEw0/54YzkmtbJ_4/photo.jpg\",   \"gender\": \"male\",   \"email\": \"robert.georen@gmail.com\",   \"link\": \"https://plus.google.com/106880955009352260440\",   \"given_name\": \"Robert\",   \"id\": \"106880955009352260440\",   \"verified_email\": true}");

		// Then
		Assert.assertEquals("Borde vara rätt namn", "Robert Georen",
				ui.getName());
	}

}
