package org.robert.common.googleplus;

import junit.framework.Assert;

import org.junit.Test;

public class GoogleTokenTest {

	@Test
	public void shouldBeAValidParsedAccessToken_1() {
		// Given and when
		GoogleToken gt = new GoogleToken(
				"Response:{\"access_token\" : \"ya29.JQECaf7W2wS1qC_L-YKss9T2nDvYWeOkG0LeCj1lxOucL1tWV5fdLXVjYdHK4DlRICncyAmDHfXyFg\",  \"token_type\" : \"Bearer\",  \"expires_in\" : 3585,  \"id_token\" : \"eyJhbGciOiJSUzI1NiIsImtpZCI6IjhmOGZlOTM3NmYwNDY5NWZiOTQ2YjBmOWYyNWJhNmNmZWU1MTI4ZTgifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiaWQiOiIxMDY4ODA5NTUwMDkzNTIyNjA0NDAiLCJzdWIiOiIxMDY4ODA5NTUwMDkzNTIyNjA0NDAiLCJhenAiOiI2MDc3MzYyODQyMTItdDhpZWpwN3ZxM3BmODUzcjg4bmNzcGdyZWI3ZnZ0Z28uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJlbWFpbCI6InJvYmVydC5nZW9yZW5AZ21haWwuY29tIiwiYXRfaGFzaCI6Ild2NU15SVNuZGtESXdSVE5pck5uY1EiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXVkIjoiNjA3NzM2Mjg0MjEyLXQ4aWVqcDd2cTNwZjg1M3I4OG5jc3BncmViN2Z2dGdvLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwidG9rZW5faGFzaCI6Ild2NU15SVNuZGtESXdSVE5pck5uY1EiLCJ2ZXJpZmllZF9lbWFpbCI6dHJ1ZSwiY2lkIjoiNjA3NzM2Mjg0MjEyLXQ4aWVqcDd2cTNwZjg1M3I4OG5jc3BncmViN2Z2dGdvLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiaWF0IjoxNDI0OTAwNDExLCJleHAiOjE0MjQ5MDQzMTF9.afxW5IGnO-FMp2Q61eUb5pp4gmmJx95kxEXLkTP8-WA5ltYatzoSwRpoNwWAjg282B6kfxDqCjrV0RSjyY5h72_RQwFtvOrlciqkL_5tWmM3U0GK9zflKntPjU4nWT3cD7A6gZTaMIVDmqKqhvQViDcAOolL7_V6R2_P0TkrL4s\"}");

		// Then
		Assert.assertEquals(
				"Borde vara rätt access_token",
				"ya29.JQECaf7W2wS1qC_L-YKss9T2nDvYWeOkG0LeCj1lxOucL1tWV5fdLXVjYdHK4DlRICncyAmDHfXyFg",
				gt.getAccess_token());
	}

	@Test
	public void shouldBeAValidParsedAccessToken_2() {
		// Given and when
		GoogleToken gt = new GoogleToken(
				"Response:{\"access_token\":\"ya29.JQECaf7W2wS1qC_L-YKss9T2nDvYWeOkG0LeCj1lxOucL1tWV5fdLXVjYdHK4DlRICncyAmDHfXyFg\",  \"token_type\" : \"Bearer\",  \"expires_in\" : 3585,  \"id_token\" : \"eyJhbGciOiJSUzI1NiIsImtpZCI6IjhmOGZlOTM3NmYwNDY5NWZiOTQ2YjBmOWYyNWJhNmNmZWU1MTI4ZTgifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiaWQiOiIxMDY4ODA5NTUwMDkzNTIyNjA0NDAiLCJzdWIiOiIxMDY4ODA5NTUwMDkzNTIyNjA0NDAiLCJhenAiOiI2MDc3MzYyODQyMTItdDhpZWpwN3ZxM3BmODUzcjg4bmNzcGdyZWI3ZnZ0Z28uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJlbWFpbCI6InJvYmVydC5nZW9yZW5AZ21haWwuY29tIiwiYXRfaGFzaCI6Ild2NU15SVNuZGtESXdSVE5pck5uY1EiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXVkIjoiNjA3NzM2Mjg0MjEyLXQ4aWVqcDd2cTNwZjg1M3I4OG5jc3BncmViN2Z2dGdvLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwidG9rZW5faGFzaCI6Ild2NU15SVNuZGtESXdSVE5pck5uY1EiLCJ2ZXJpZmllZF9lbWFpbCI6dHJ1ZSwiY2lkIjoiNjA3NzM2Mjg0MjEyLXQ4aWVqcDd2cTNwZjg1M3I4OG5jc3BncmViN2Z2dGdvLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiaWF0IjoxNDI0OTAwNDExLCJleHAiOjE0MjQ5MDQzMTF9.afxW5IGnO-FMp2Q61eUb5pp4gmmJx95kxEXLkTP8-WA5ltYatzoSwRpoNwWAjg282B6kfxDqCjrV0RSjyY5h72_RQwFtvOrlciqkL_5tWmM3U0GK9zflKntPjU4nWT3cD7A6gZTaMIVDmqKqhvQViDcAOolL7_V6R2_P0TkrL4s\"}");

		// Then
		Assert.assertEquals(
				"Borde vara rätt access_token",
				"ya29.JQECaf7W2wS1qC_L-YKss9T2nDvYWeOkG0LeCj1lxOucL1tWV5fdLXVjYdHK4DlRICncyAmDHfXyFg",
				gt.getAccess_token());
	}
	
	@Test
	public void shouldBeAnErrorJSONMessage() {
		// Given and when
		GoogleToken gt = new GoogleToken(
				"{ \"error\" : \"invalid_grant\",  \"error_description\" : \"Code was already redeemed.\"}");

		// Then
		Assert.assertEquals("Borde vara rätt error text", "invalid_grant",
				gt.getError());
		Assert.assertEquals("Borde vara rätt error_description.",
				"Code was already redeemed.", gt.getError_description());
	}
}
