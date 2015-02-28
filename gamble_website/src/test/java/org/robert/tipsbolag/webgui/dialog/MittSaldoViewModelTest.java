package org.robert.tipsbolag.webgui.dialog;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.tipsbolag.webgui.session.UserSessionFactoryMock;

public class MittSaldoViewModelTest {

	@Test
	public void bordeVisaMeddelandeOmAttManBehoveVaraInloggadForAttSeSalod() {
		// Given
		MittSaldoViewModel viewModel = new MittSaldoViewModel(
				new UserSessionFactoryMock());

		// When
		viewModel.initDialog();

		// Then
		Assert.assertEquals("Borde visa meddelande.", true,
				viewModel.getRenderAuthenticationRequired());
	}

	/**
	 * Borde visa mitt saldo med en lista av transaktioner.
	 */
	@Test
	public void bordeVisaMittSaldo() {
		// Given
		MittSaldoViewModel viewModel = new MittSaldoViewModelMock(
				new UserSessionFactoryMock());
		viewModel.usrFactory.getUserSession().setEmail(
				"robert.georen@gmail.com");

		// When
		viewModel.initDialog();

		// Then
		Assert.assertEquals("Borde INTE visa login länk.", false,
				viewModel.getRenderAuthenticationRequired());
		Assert.assertEquals(
				"Borde finnas en lista med transaktioner i viewmodel.", 7,
				viewModel.getModel().getSpelare().getKonto().getTransaktioner()
						.size());
	}
}
