package org.robert.tipsbolag.webgui.dialog;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.tipsbolag.webgui.domain.model.Spelare;
import org.robert.tipsbolag.webgui.domain.model.UserId;
import org.robert.tipsbolag.webgui.session.UserSessionFactoryMock;

public class AdministreraSpelbolagViewModelTest {
	@Test
	public void bordeVisaInformationOmAttManBehoverVaraInloggad() {
		// Given
		AdministreraSpelbolagViewModel viewModel = new AdministreraSpelbolagViewModel(
				new UserSessionFactoryMock());

		// When
		viewModel.initDialog();

		// Then
		Assert.assertEquals("Borde visa meddelande.", true,
				viewModel.getRenderAuthenticationRequired());
	}

	/**
	 * Borde visa Administrations dialogen.
	 */
	@Test
	public void bordeVisaAdministrationsStartDialogen() {
		// Given
		AdministreraSpelbolagViewModel viewModel = new AdministreraSpelbolagViewModel(
				new UserSessionFactoryMock());
		viewModel.usrFactory.getUserSession().setEmail(
				"robert.georen@gmail.com");

		// When
		viewModel.initDialog();

		// Then
		Assert.assertEquals(
				"Borde INTE visa felmeddelande om att man måste vara inloggad.",
				false, viewModel.getRenderAuthenticationRequired());
		Assert.assertEquals(
				"Borde visa att spelaren är administratör för ett bolag.",
				true, viewModel.getAuthenticatedAsAdmin());
		Assert.assertEquals(
				"Borde vara rätt spelbolag som spelaren är administratör för.",
				"The sharks", viewModel.getModel().getSpelare()
						.getAdministratorForSpelbolag().getNamn());
	}

	/**
	 * Borde visa Administrations dialogen.
	 */
	@Test
	public void bordeVaraAutentiseradMenInteVaraAdministrator() {
		// Given
		AdministreraSpelbolagViewModel viewModel = new AdministreraSpelbolagViewModel(
				new UserSessionFactoryMock());
		viewModel.usrFactory.getUserSession().setEmail("kalle.anka@swipnet.se");

		// When
		viewModel.initDialog();

		// Then
		Assert.assertEquals(
				"Borde INTE visa felmeddelande om att man måste vara inloggad.",
				false, viewModel.getRenderAuthenticationRequired());
		Assert.assertEquals(
				"Borde visa att spelaren är administratör för ett bolag.",
				false, viewModel.getAuthenticatedAsAdmin());
	}

	@Test
	public void bordeValjaEnSpelareAttVisaHansAllaTransaktioner() {
		// Given
		AdministreraSpelbolagViewModel viewModel = new AdministreraSpelbolagViewModel(
				new UserSessionFactoryMock());
		viewModel.usrFactory.getUserSession().setEmail(
				"robert.georen@gmail.com");

		// When
		viewModel.initDialog();
		viewModel.valjEnSpelare();

		// Then
		Assert.assertEquals(
				"Borde hämta lista med personens (kalle.anka@swipnet.se) transaktioner.",
				4, viewModel.getValdSpelare().getKonto()
						.getSummeratransaktion().size());
		Assert.assertEquals(
				"Borde visa tabell med spelarens alla transaktioner.", true,
				viewModel.getRenderValdSpelaresAllaTransaktioner());
	}

	@Test
	public void bordeRegistreraEnTransaktionForEnSpelare() {
		// Given
		AdministreraSpelbolagViewModel viewModel = new AdministreraSpelbolagViewModel(
				new UserSessionFactoryMock());
		viewModel.usrFactory.getUserSession().setEmail(
				"robert.georen@gmail.com");

		// When
		viewModel.initDialog();
		viewModel.valjEnSpelare(); // väljer första spelaren.
		viewModel.laggTillEnNyTransaktionForValdSpelare();

		// Then
		Spelare storedSpelare = viewModel.businessDelegate
				.hamtaSpelare(new UserId("kalle.anka@swipnet.se"));
		Assert.assertEquals(
				"Borde vara en till transaktion i spelarens (kalle.anka@swipnet.se) konto",
				5, storedSpelare.getKonto().getTransaktioner().size());
		Assert.assertEquals("Borde stänga registrera rutan.", false,
				viewModel.getRenderValdSpelaresAllaTransaktioner());
	}

	@Test
	public void bordeRegistreraEnTransaktionForSpelbolagetSomManArAdministratorFor() {
		// Given
		AdministreraSpelbolagViewModel viewModel = new AdministreraSpelbolagViewModel(
				new UserSessionFactoryMock());
		viewModel.usrFactory.getUserSession().setEmail(
				"robert.georen@gmail.com");

		// When
		viewModel.initDialog();
		viewModel.valjEnSpelare(); // väljer första spelaren.
		viewModel.laggTilEnNyTransaktionForValtSpelbolag();

		// Then
		Assert.assertEquals(
				"Borde vara en till transaktion på spelbolaget 'The sharks'",
				5, viewModel.usrFactory.getUserSession().getSpelare()
						.getAdministratorForSpelbolag().getKonto()
						.getTransaktioner().size());
	}

	@Test
	public void bordeTaBetaltAvAllaSpelareIBolaget() {
		// Given
		AdministreraSpelbolagViewModel viewModel = new AdministreraSpelbolagViewModel(
				new UserSessionFactoryMock());
		viewModel.usrFactory.getUserSession().setEmail(
				"robert.georen@gmail.com");
		Calendar cal = Calendar.getInstance();
		viewModel.setTransaktionTid(cal.getTime());

		// When
		viewModel.initDialog();
		viewModel.taBetaltAvAllaSpelare();

		// Then
		Assert.assertEquals("Borde ökat saldo på spelkontot.", 25, viewModel
				.getModel().getSpelare().getAdministratorForSpelbolag()
				.getKonto().getSaldo());
	}
}
