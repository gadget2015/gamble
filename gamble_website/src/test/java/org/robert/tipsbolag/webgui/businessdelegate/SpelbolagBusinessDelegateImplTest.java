package org.robert.tipsbolag.webgui.businessdelegate;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.tipsbolag.webgui.businessdelegate.impl.SpelbolagBusinessDelegateImpl;

public class SpelbolagBusinessDelegateImplTest {

	@Test
	public void bordeSkapaEnAOuth2InloggningsURL() {
		// Given
		SpelbolagBusinessDelegateImpl bd = new SpelbolagBusinessDelegateImpl();
		
		// When
		AuthenticationResponse response = bd.createAOuth2AuthenticateRequest("http://www.stryktipsbolag.se/oauth2callback", "myID");
		
		// Then
		Assert.assertEquals("https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com/auth/plus.me%20https://www.googleapis.com/auth/userinfo.email&state=myID&redirect_uri=http://www.stryktipsbolag.se/oauth2callback&response_type=code&client_id=607736284212-t8iejp7vq3pf853r88ncspgreb7fvtgo.apps.googleusercontent.com&access_type=offline", response.authenticationRequest);
	}
	
}
