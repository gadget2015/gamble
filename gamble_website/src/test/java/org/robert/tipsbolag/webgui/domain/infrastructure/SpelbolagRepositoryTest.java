package org.robert.tipsbolag.webgui.domain.infrastructure;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.tipsbolag.webgui.domain.model.Konto;
import org.robert.tipsbolag.webgui.domain.model.Spelare;
import org.robert.tipsbolag.webgui.domain.model.Spelbolag;
import org.robert.tipsbolag.webgui.domain.model.Transaktion;
import org.robert.tipsbolag.webgui.domain.model.UserId;

public class SpelbolagRepositoryTest {
	private EntityManager em;
	private SpelbolagRepository repository;
	private SpelareRepository spelareRepository;

	@Before
	public void setUp() throws Exception {
		JPAUtility.startInMemoryDerbyDatabase();
		em = JPAUtility.createEntityManager();
		repository = new SpelbolagRepositoryImpl(em);
		spelareRepository = new SpelareRepositoryImpl(em);
	}

	@Test
	public void shouldPersistANewSpelbolag() {

		// Given
		Spelbolag spelbolag = new Spelbolag();
		spelbolag.setInsatsPerOmgang(50);
		Spelare spelareNr1 = new Spelare();
		spelareNr1.setUserId(new UserId("robert.georen@gmail.com"));
		spelbolag.laggTillSpelare(spelareNr1);
		Spelare spelareNr2 = new Spelare();
		spelareNr2.setUserId(new UserId("hjalmar.branting@facebook.com"));
		spelbolag.laggTillSpelare(spelareNr2);

		// When
		em.getTransaction().begin();
		spelbolag = repository.skapaNyttSpelbolag(spelbolag);
		em.getTransaction().commit();

		// Then
		spelbolag = em.find(Spelbolag.class, spelbolag.getId());
		Assert.assertTrue("Should get an Id.", (spelbolag.getId() > 0));
		Assert.assertEquals("Should be same insats.", 50,
				spelbolag.getInsatsPerOmgang());
		Assert.assertEquals("Should be same spelare.",
				"robert.georen@gmail.com", spelbolag.getSpelare().iterator()
						.next().getUserId().getUserId());
		// Clean upp
		JPAUtility.remove(spelbolag, em);
	}

	@Test
	public void bordeHamtaAllaSpelbolag() {
		// Given
		Spelbolag spelbolag1 = new Spelbolag();
		spelbolag1.setInsatsPerOmgang(50);
		Spelbolag spelbolag2 = new Spelbolag();
		spelbolag2.setInsatsPerOmgang(100);
		em.getTransaction().begin();
		spelbolag1 = repository.skapaNyttSpelbolag(spelbolag1);
		spelbolag2 = repository.skapaNyttSpelbolag(spelbolag2);
		em.getTransaction().commit();

		// When
		List<Spelbolag> bolag = repository.hamtaAllaSpelbolag();

		// Then
		Assert.assertEquals("Borde hitta två spelbolag.", 2, bolag.size());

		// Clean upp
		JPAUtility.remove(spelbolag1, em);
		JPAUtility.remove(spelbolag2, em);
	}

	@Test
	public void bordeHamtaSpelareMedAnvandarId() {

		// Given
		Spelbolag spelbolag = new Spelbolag();
		spelbolag.setInsatsPerOmgang(20);
		Spelare spelareNr1 = new Spelare();
		spelareNr1.setUserId(new UserId("robert.georen@gmail.com"));
		spelbolag.laggTillSpelare(spelareNr1);
		Spelare spelareNr2 = new Spelare();
		spelareNr2.setUserId(new UserId("hjalmar.branting@facebook.com"));
		spelbolag.laggTillSpelare(spelareNr2);

		// When
		em.getTransaction().begin();
		spelbolag = repository.skapaNyttSpelbolag(spelbolag);
		em.getTransaction().commit();

		Spelare spelare = spelareRepository
				.hamtaSpelareMedAnvandarId(new UserId("robert.georen@gmail.com"));

		// Then
		Assert.assertTrue("Borde hitta en spelare med det id't.",
				(spelare.getId() > 0));

		// Clean up
		JPAUtility.remove(spelbolag, em);
	}

	@Test
	public void bordeInteHittaSpelareMedAnvandarId() {
		// Given
		Spelbolag spelbolag = new Spelbolag();
		spelbolag.setInsatsPerOmgang(20);
		Spelare spelareNr1 = new Spelare();
		spelareNr1.setUserId(new UserId("robert.georen@gmail.com"));
		spelbolag.laggTillSpelare(spelareNr1);
		Spelare spelareNr2 = new Spelare();
		spelareNr2.setUserId(new UserId("hjalmar.branting@facebook.com"));
		spelbolag.laggTillSpelare(spelareNr2);

		// When
		em.getTransaction().begin();
		spelbolag = repository.skapaNyttSpelbolag(spelbolag);
		em.getTransaction().commit();

		Spelare spelare = spelareRepository
				.hamtaSpelareMedAnvandarId(new UserId("jenny@hotmail.com"));

		// Then
		Assert.assertEquals("Borde INTE hitta en spelare med det id't.", 0,
				spelare.getId());

		// Clean up
		JPAUtility.remove(spelbolag, em);
	}

	@Test
	public void bordeLaggaTillEnTransaktionTillEttSpelbolag() {
		// Given
		Spelbolag spelbolag = new Spelbolag();
		spelbolag.setInsatsPerOmgang(50);
		spelbolag.setKonto(new Konto(555));
		em.getTransaction().begin();
		spelbolag = repository.skapaNyttSpelbolag(spelbolag);
		em.getTransaction().commit();

		// When
		Calendar cal = Calendar.getInstance();
		Transaktion trans = new Transaktion(10, 0, "test", cal.getTime());
		em.getTransaction().begin();
		spelbolag = repository.laggTillTransaktionTillSpelbolag(
				spelbolag.getId(), trans);
		em.getTransaction().commit();

		// Then
		Spelbolag storedBolag = em.find(Spelbolag.class, spelbolag.getId());
		Assert.assertEquals("Borde finnas en transaktion på bolaget.", 1,
				storedBolag.getKonto().getTransaktioner().size());

		// Clean up
		JPAUtility.remove(storedBolag, em);
	}

	@Test
	public void bordeTaBetaltAvAllaSpelareIEttSpelbolag() {
		// Given ett spelbolag med två spelare.
		Spelbolag spelbolag = new Spelbolag();
		spelbolag.setInsatsPerOmgang(20);
		Spelare spelareNr1 = new Spelare();
		spelareNr1.setUserId(new UserId("robert.georen@gmail.com"));
		spelbolag.laggTillSpelare(spelareNr1);
		Spelare spelareNr2 = new Spelare();
		spelareNr2.setUserId(new UserId("hjalmar.branting@facebook.com"));
		spelbolag.laggTillSpelare(spelareNr2);
		em.getTransaction().begin();
		spelbolag = repository.skapaNyttSpelbolag(spelbolag);
		em.getTransaction().commit();

		// When
		em.getTransaction().begin();
		Calendar cal = Calendar.getInstance();
		repository.taBetaltAvAllaSpelare(spelbolag.getId(), cal.getTime());
		em.getTransaction().commit();		

		// Then
		em.clear();
		em.detach(spelbolag);
		Spelbolag storedBolag = em.find(Spelbolag.class, spelbolag.getId());
		Assert.assertEquals("Borde vara 40 kr på spelbolagets konto.", 40,
				storedBolag.getKonto().getSaldo());

		// Clean up
		//JPAUtility.remove(spelbolag, em);
	}
}
