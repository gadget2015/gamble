package org.robert.tipsbolag.webgui.dialog;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.tipsbolag.webgui.session.UserSessionFactoryMock;

public class TipsbolagenViewModelTest {

	@Test
	public void bordeHamtaListaMedSpelbolag() {
		// Given
		SpelbolagenViewModel viewModel = new SpelbolagenViewModel(
				new UserSessionFactoryMock());

		// When
		viewModel.initDialog();

		// Then
		Assert.assertEquals("Borde hämta upp alla spelbolagen.", 3, viewModel
				.getSpelbolagModel().getRowCount());
	}

	@Test
	public void bordeVisaInformationOmEttSpelbolag() {
		// Given
		SpelbolagenViewModel viewModel = new SpelbolagenViewModel(
				new UserSessionFactoryMock());
		viewModel.initDialog();

		// When
		viewModel.valjEttSpelbolag();

		// Then
		Assert.assertTrue("Borde visa informations dialogen.",
				viewModel.getRenderSpelbolagInformation());
		Assert.assertEquals("Borde visa 9 transaktioner.", 9, viewModel
				.getAllaTransaktioner().size());
	}
}
