package org.robert.tipsbolag.webgui.domain.infrastructure;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.robert.tipsbolag.webgui.domain.model.Spelbolag;
import org.robert.tipsbolag.webgui.domain.model.Transaktion;

@Stateless
public class SpelbolagRepositoryImpl implements SpelbolagRepository {
	@PersistenceContext(unitName = "spelbolag-unit")
	private EntityManager em;

	/**
	 * EJB 3.0 default constructor.
	 */
	public SpelbolagRepositoryImpl() {
	}

	public SpelbolagRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public Spelbolag skapaNyttSpelbolag(Spelbolag spelbolag) {
		em.persist(spelbolag);
		return spelbolag;
	}

	@Override
	public List<Spelbolag> hamtaAllaSpelbolag() {
		// Perform search query.
		Query query = em
				.createNamedQuery(InfrastructureNamedQuery.HAMTA_ALLA_SPELBOLAG);

		@SuppressWarnings("unchecked")
		List<Spelbolag> searchResult = (List<Spelbolag>) query.getResultList();

		return searchResult;
	}

	@Override
	public Spelbolag laggTillTransaktionTillSpelbolag(long id, Transaktion trans) {
		Spelbolag bolag = em.find(Spelbolag.class, id);
		bolag.getKonto().laggTillTransaktion(trans);
		bolag = em.merge(bolag);

		return bolag;
	}

	@Override
	public Spelbolag taBetaltAvAllaSpelare(long id, Date transaktionsTid) {
		Spelbolag spelbolag = em.find(Spelbolag.class, id);
		spelbolag.taBetaltAvAllaSeplareForEnOmgang(transaktionsTid);
		spelbolag = em.merge(spelbolag);

		return spelbolag;
	}
}
