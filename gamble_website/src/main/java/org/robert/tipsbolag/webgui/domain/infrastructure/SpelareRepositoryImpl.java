package org.robert.tipsbolag.webgui.domain.infrastructure;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.robert.tipsbolag.webgui.domain.model.Spelare;
import org.robert.tipsbolag.webgui.domain.model.Transaktion;
import org.robert.tipsbolag.webgui.domain.model.UserId;

@Stateless
public class SpelareRepositoryImpl implements SpelareRepository {

	@PersistenceContext(unitName = "spelbolag-unit")
	private EntityManager em;

	/**
	 * EJB 3.0 default constructor.
	 */
	public SpelareRepositoryImpl() {
	}

	public SpelareRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public Spelare laggTillEnTransaktion(UserId userId, Transaktion trans) {
		Spelare lagradSpelare = hamtaSpelareMedAnvandarId(userId);
		lagradSpelare.getKonto().laggTillTransaktion(trans);
		lagradSpelare = em.merge(lagradSpelare);

		return lagradSpelare;
	}

	@Override
	public Spelare hamtaSpelareMedAnvandarId(UserId anvandarId) {
		// Perform search query.
		Query query = em
				.createNamedQuery(InfrastructureNamedQuery.HAMTA_EN_SPELARE_MED_ANVANDARID);
		query.setParameter("anvandarId", anvandarId.getUserId());

		try {
			Spelare searchResult = (Spelare) query.getSingleResult();
			return searchResult;
		} catch (NoResultException e) {
			return new Spelare(); // NullObject pattern.
		}
	}

}
