package org.robert.sharenote.webgui.dialog;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.sharenote.webgui.businessdelegate.NoteBusinessDelegate;
import org.robert.sharenote.webgui.businessdelegate.mock.NoteMockBD;
import org.robert.sharenote.webgui.dialog.controller.Controller;
import org.robert.sharenote.webgui.session.UserSessionFactory;
import org.robert.sharenote.webgui.session.UserSessionFactoryMock;

public class AuthenticateByOpenIdControllerTest {
	UserSessionFactory userSessionFactory;
	NoteBusinessDelegate businessDelegate;

	@Before
	public void init() {
		this.businessDelegate = new NoteMockBD();
		this.userSessionFactory = new UserSessionFactoryMock();
	}

	@Test
	public void shouldRedirectActorToGoogleLoginPage() {
		// Given
		HeaderView view = mock(HeaderView.class);
		Controller controller = new AuthenticateByOpenIdController(view,
				businessDelegate, userSessionFactory);
		 
		// When
		String outcome = controller.perform();

		// Then
		Assert.assertEquals("Should be ok.", "success", outcome);
		verify(view).redirectToGoogleAuthentication(
				"http://localhost:1967/sharenote/googlelogin_mock.xhtml?openid.assoc_handle=DSFKJSDK323sDSD&openid.ui=popup");
		Assert.assertEquals(
				"Should put discoveryinformation in actors session.",
				"http://noterepo.com", userSessionFactory.getUserSession()
						.getOpenIdDiscoveryInformation().getOPEndpoint()
						.toString());
	}
}
