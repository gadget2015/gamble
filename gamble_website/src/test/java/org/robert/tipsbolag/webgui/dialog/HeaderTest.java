package org.robert.tipsbolag.webgui.dialog;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.tipsbolag.webgui.session.UserSessionFactoryMock;

/**
 * Inloggning till Google med AOuth2, se spec:
 * https://developers.google.com/accounts/docs/OAuth2WebServer
 * 
 * 
 */
public class HeaderTest {
	@Test
	public void bordeLoggainMedGoogleKonto() {
		// Given
		HeaderViewModelMock viewModel = new HeaderViewModelMock(
				new UserSessionFactoryMock());

		// When
		viewModel.loggaInMedGoogleKonto();

		// Then
		Assert.assertNotNull(
				"Borde sätta openId discoveryinformation i sessionen.",
				viewModel.usrFactory.getUserSession()
						.getOpenIdDiscoveryInformation());
		Assert.assertNotNull("Borde sätta assoc_handle i sessionen.",
				viewModel.usrFactory.getUserSession().getAssocHandle());
		Assert.assertEquals("Borde göra en redirect till google.", true,
				viewModel.redirectToGoogle);
	}

	@Test
	public void bordeLoggainMedAOuth2TillGoogle() {
		// Given
		HeaderViewModelMock viewModel = new HeaderViewModelMock(
				new UserSessionFactoryMock());

		// When
		viewModel.loggaInMedAOuth2TillGoogle();

		// Then
		Assert.assertEquals("Borde göra en redirect till google.", true,
				viewModel.redirectToGoogle);
	}

	@Test
	public void bordeVisaVemSomArInloggad() {
		// Given
		UserSessionFactoryMock usr = new UserSessionFactoryMock();
		usr.getUserSession().setEmail("robert.georen@gmail.com");
		HeaderViewModel viewModel = new HeaderViewModel(usr);

		// When
		viewModel.initDialog();

		// Then
		Assert.assertEquals("Borde visa användar id.", true,
				viewModel.getRenderAnvandarIdLabel());
	}
}
