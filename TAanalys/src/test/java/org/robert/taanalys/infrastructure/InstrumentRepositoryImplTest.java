package org.robert.taanalys.infrastructure;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.taanalys.domain.model.InstrumentEvent;

public class InstrumentRepositoryImplTest {
	private EntityManager em;
	private InstrumentRepository repository;

	@Before
	public void setUp() throws Exception {
		JPAUtility.startInMemoryDerbyDatabase();
		em = JPAUtility.createEntityManager();
		repository = new InstrumentRepositoryImpl(em);
	}

	@Test
	public void bordeSparaOMXS30() {
		// Given
		InstrumentEvent instrument = skapaEttEvent(Calendar.getInstance()
				.getTime());

		// When
		em.getTransaction().begin();
		instrument = repository.sparaInstrument(instrument);
		em.getTransaction().commit();

		// Then
		instrument = em.find(InstrumentEvent.class, instrument.id);
		Assert.assertTrue("Should get an Id.", (instrument.id > 0));
		Assert.assertEquals("Should be same text.", "OMXS30", instrument.name);

		JPAUtility.remove(instrument, em);
	}

	private InstrumentEvent skapaEttEvent(Date handelseDatum) {
		InstrumentEvent instrument = new InstrumentEvent();
		instrument.name = "OMXS30";
		instrument.quote = 12321;
		instrument.time = handelseDatum;

		return instrument;
	}

	@Test
	public void bordeHamtaTreEventsFranOchMedEttVisstDatumNarDetFinns4Totalt() {
		// Given
		Calendar handelseTid = Calendar.getInstance();
		handelseTid.add(Calendar.DAY_OF_MONTH, -10);
		em.getTransaction().begin();
		InstrumentEvent event1 = repository
				.sparaInstrument(skapaEttEvent(handelseTid.getTime()));
		handelseTid.add(Calendar.DAY_OF_MONTH, 1);
		InstrumentEvent event2 = repository
				.sparaInstrument(skapaEttEvent(handelseTid.getTime()));
		handelseTid.add(Calendar.DAY_OF_MONTH, 1);
		InstrumentEvent event3 = repository
				.sparaInstrument(skapaEttEvent(handelseTid.getTime()));
		handelseTid.add(Calendar.DAY_OF_MONTH, -20);
		InstrumentEvent event4 = repository
				.sparaInstrument(skapaEttEvent(handelseTid.getTime()));
		em.getTransaction().commit();

		// When
		handelseTid = Calendar.getInstance();
		handelseTid.add(Calendar.DAY_OF_MONTH, -10);
		handelseTid.set(Calendar.HOUR_OF_DAY, 0);
		Collection<InstrumentEvent> events = repository
				.hamtaInstrumentEventsFran(handelseTid.getTime());

		// Then
		Assert.assertEquals("Borde vara 3 events.", 3, events.size());

		JPAUtility.remove(event1, em);
		JPAUtility.remove(event2, em);
		JPAUtility.remove(event3, em);
		JPAUtility.remove(event4, em);
	}

	@Test
	public void bordeInteHittaNogotEvent() {
		// Given
		Calendar handelseTid = Calendar.getInstance();
		handelseTid.add(Calendar.DAY_OF_MONTH, -10);
		handelseTid.set(Calendar.HOUR_OF_DAY, 0);
		Collection<InstrumentEvent> events = repository
				.hamtaInstrumentEventsFran(handelseTid.getTime());
		int before = events.size();

		// When
		events = repository.hamtaInstrumentEventsFran(handelseTid.getTime());

		// Then
		Assert.assertEquals("Borde vara 0 events.", 0, (events.size() - before));
	}

	@Test
	public void bordeHamtaEttEventFranEttSpecifiktDatum() {
		// Given
		Calendar handelseTid = Calendar.getInstance();
		handelseTid.set(2013, 1, 12, 9, 15);
		handelseTid.set(Calendar.SECOND, 0);
		em.getTransaction().begin();
		InstrumentEvent event1 = repository
				.sparaInstrument(skapaEttEvent(handelseTid.getTime()));
		handelseTid.set(2013, 1, 13, 9, 15);
		InstrumentEvent event2 = repository
				.sparaInstrument(skapaEttEvent(handelseTid.getTime()));
		handelseTid.set(2013, 1, 14, 9, 15);
		InstrumentEvent event3 = repository
				.sparaInstrument(skapaEttEvent(handelseTid.getTime()));
		em.getTransaction().commit();

		// When
		handelseTid.set(2013, 1, 13, 9, 15);
		Collection<InstrumentEvent> events = repository.hamtaInstrumentEvents(
				handelseTid.getTime(), handelseTid.getTime());

		// Then
		Assert.assertEquals("Borde vara 1 event.", 1, events.size());
		Assert.assertEquals("Borde vara rätt handlese.",
				"Wed Feb 13 09:15:00 CET 2013",
				events.iterator().next().time.toString());

		JPAUtility.remove(event1, em);
		JPAUtility.remove(event2, em);
		JPAUtility.remove(event3, em);
	}

	@Test
	public void bordeHamtaTvaEventFranEttDatumIntervall() {
		// Given
		Calendar handelseTid = Calendar.getInstance();
		handelseTid.set(2013, 1, 12, 9, 15);
		handelseTid.set(Calendar.SECOND, 0);
		em.getTransaction().begin();
		InstrumentEvent event1 = repository
				.sparaInstrument(skapaEttEvent(handelseTid.getTime()));
		handelseTid.set(2013, 1, 13, 9, 15);
		InstrumentEvent event2 = repository
				.sparaInstrument(skapaEttEvent(handelseTid.getTime()));
		handelseTid.set(2013, 1, 14, 9, 15);
		InstrumentEvent event3 = repository
				.sparaInstrument(skapaEttEvent(handelseTid.getTime()));
		em.getTransaction().commit();

		// When
		handelseTid.set(2013, 1, 12, 9, 15);
		Calendar tillDatum = Calendar.getInstance();
		tillDatum.set(2013, 1, 13, 9, 15);
		Collection<InstrumentEvent> events = repository.hamtaInstrumentEvents(
				handelseTid.getTime(), tillDatum.getTime());

		// Then
		Assert.assertEquals("Borde vara 2 event.", 2, events.size());
		Iterator<InstrumentEvent> it = events.iterator();
		Assert.assertEquals("Borde vara rätt handlese.",
				"Tue Feb 12 09:15:00 CET 2013", it.next().time.toString());
		Assert.assertEquals("Borde vara rätt handlese.",
				"Wed Feb 13 09:15:00 CET 2013", it.next().time.toString());

		// Clean upp
		JPAUtility.remove(event1, em);
		JPAUtility.remove(event2, em);
		JPAUtility.remove(event3, em);
	}
}
