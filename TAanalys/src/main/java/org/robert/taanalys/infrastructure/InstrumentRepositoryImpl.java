package org.robert.taanalys.infrastructure;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.robert.taanalys.domain.model.InstrumentEvent;

@Stateless
public class InstrumentRepositoryImpl implements InstrumentRepository {
	@PersistenceContext(unitName = "ta-unit")
	private EntityManager em;

	public InstrumentRepositoryImpl() {
	}

	public InstrumentRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public InstrumentEvent sparaInstrument(InstrumentEvent instrument) {
		instrument = em.merge(instrument);

		return instrument;
	}

	@Override
	public Collection<InstrumentEvent> hamtaInstrumentEventsFran(
			Date dateToFechEvents) {
		// Sätter sluttiden till kl. 23:59.
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);

		// Perform search query.
		List<InstrumentEvent> searchResult = hamtaInstrumentEvents(dateToFechEvents, today.getTime());

		return searchResult;
	}

	@Override
	public List<InstrumentEvent> hamtaInstrumentEvents(Date startDatum,
			Date slutDatum) {
		// Perform search query.
		Query query = em
				.createNamedQuery(InfrastructureNamedQuery.SEARCH_FOR_INSTRUMENT_EVENTS_BY_DATES);
		query.setParameter("franDatum", startDatum);
		query.setParameter("tillDatum", slutDatum);

		@SuppressWarnings("unchecked")
		List<InstrumentEvent> searchResult = (List<InstrumentEvent>) query
				.getResultList();

		return searchResult;
	}
}
