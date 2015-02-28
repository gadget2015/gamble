package org.robert.tipsbolag.webgui.domain.infrastructure;

import java.util.Calendar;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.tipsbolag.webgui.domain.model.Spelare;
import org.robert.tipsbolag.webgui.domain.model.Transaktion;
import org.robert.tipsbolag.webgui.domain.model.UserId;

public class SpelareRepositoryTest {
	private EntityManager em;
	private SpelareRepository repository;

	@Before
	public void setUp() throws Exception {
		JPAUtility.startInMemoryDerbyDatabase();
		em = JPAUtility.createEntityManager();
		repository = new SpelareRepositoryImpl(em);
	}

	@Test
	public void bordeLaggaTillEnTransaktionTillEnSpelare() {
		// Given
		Spelare spelareNr1 = new Spelare();
		UserId userId = new UserId("robert.georen@gmail.com");
		spelareNr1.setUserId(userId);
		em.getTransaction().begin();
		em.persist(spelareNr1);
		em.getTransaction().commit();
		
		// When
		em.getTransaction().begin();
		Calendar cal = Calendar.getInstance();
		Transaktion trans = new Transaktion(10, 0, "test", cal.getTime());
		spelareNr1 = repository
				.laggTillEnTransaktion(userId, trans);
		em.getTransaction().commit();

		// Then
		Spelare storedSpelare = em.find(Spelare.class, spelareNr1.getId());
		Assert.assertEquals("Borde vara en transaktion på spelarens konto.", 1,
				storedSpelare.getKonto().getTransaktioner().size());
		
		// Clean up
		JPAUtility.remove(spelareNr1, em);
	}
}
