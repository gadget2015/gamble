package org.robert.tips.stryktipsfinder.domain.infrastructure;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.robert.tips.stryktips.domain.model.Mathimatical;
import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;
import org.robert.tips.stryktipsfinder.domain.model.StryktipsSystem;

/**
 * JPA implementation of repository.
 * 
 * @author Robert
 * 
 */
@Stateless
public class RSystemRepositoryImpl implements RSystemRepository {
	@PersistenceContext(unitName = "rsystem-unit")
	private EntityManager em;

	/**
	 * EJB 3.0 specification states the need of default constructor.
	 * 
	 */
	public RSystemRepositoryImpl() {
	}

	/**
	 * Used by unit tests.
	 * 
	 * @param em
	 *            EntityManager.
	 */
	public RSystemRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public RSystemSearchInfo update(RSystemSearchInfo rsystem) {
		// Calculate number of test runs, e.g speed.
		long totalNumberOfTestRuns = calculateTotalNumberOfTestRuns(rsystem
				.getId());
		rsystem.setSpeed(totalNumberOfTestRuns);

		// Update number of rows.
		if (!rsystem.getSystems().isEmpty()) {
			StryktipsSystem newSystem = rsystem.createLinkedListOfSystems()
					.getLast();

			if (rsystem.getNumberOfRows() > newSystem.getNumberOfRows()) {
				// Only update the system number of rows if it's better
				// than the old value.
				rsystem.setNumberOfRows(newSystem.getNumberOfRows());

				// Calculate reducing grade.
				int reducingGrade = rsystem.calculateReducingGrade();
				rsystem.setReducingGrade(reducingGrade);
			}
		}

		rsystem = em.merge(rsystem);

		return rsystem;
	}

	private long calculateTotalNumberOfTestRuns(long id) {
		Query query = em
				.createNamedQuery(InfrastructureNamedQuery.SUM_OF_TEST_RUNS);
		query.setParameter("systemDbId", id);
		Number sumOfTestRuns = (Number) query.getSingleResult();
		if (sumOfTestRuns != null) {
			return sumOfTestRuns.longValue();
		} else {
			return 0;
		}
	}

	private int calculateNumberOfRowsInMathematicalSystem(
			RSystemSearchInfo rsystem) {
		if (rsystem.getNumberOfRowsInMathematicalSystem() == 0) {
			Mathimatical mathematical = new Mathimatical(
					rsystem.getNumberOfHalfHedging(),
					rsystem.getNumberOfHoleHedging());
			@SuppressWarnings("unchecked")
			List<char[]> mathematicalSystemRows = (List<char[]>) mathematical
					.createsSingleRowsFromMathimaticalSystem();
			int numberOfMathematicalRows = mathematicalSystemRows.size();

			return numberOfMathematicalRows;
		} else {
			return 0;
		}
	}

	@Override
	public List<RSystemSearchInfo> findAllActiveRSystems() {
		Query query = em
				.createNamedQuery(InfrastructureNamedQuery.FIND_ALL_ACTIVE_RSYSTEMS);

		@SuppressWarnings("unchecked")
		List<RSystemSearchInfo> systems = (List<RSystemSearchInfo>) query
				.getResultList();

		if (systems.size() == 0) {
			systems = new ArrayList<RSystemSearchInfo>();
		}

		return systems;
	}

	@Override
	public RSystemSearchInfo findSystem(long id) {
		RSystemSearchInfo system = em.find(RSystemSearchInfo.class, id);

		if (system != null) {
			// always get a fresh copy from db, concurrent problem.
			em.refresh(system);
		}
		
		return system;
	}

	@Override
	public void insert(RSystemSearchInfo rsystem) {
		// Calculate mathematical system if necessary.
		int numberOfRowsInMathematical = calculateNumberOfRowsInMathematicalSystem(rsystem);
		rsystem.setNumberOfRowsInMathematicalSystem(numberOfRowsInMathematical);

		em.persist(rsystem);
	}

}
