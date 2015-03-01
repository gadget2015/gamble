package org.robert.tips.stryktipsfinder.service;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.tips.stryktipsfinder.domain.infrastructure.JPAUtility;
import org.robert.tips.stryktipsfinder.domain.infrastructure.RSystemRepository;
import org.robert.tips.stryktipsfinder.domain.infrastructure.RSystemRepositoryImpl;
import org.robert.tips.stryktipsfinder.domain.model.Algorithm;
import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;
import org.robert.tips.stryktipsfinder.domain.model.StepThroughUnitOfWork;
import org.robert.tips.stryktipsfinder.domain.model.StepThroughUnitOfWorkStatus;
import org.robert.tips.stryktipsfinder.domain.model.StryktipsSystem;
import org.robert.tips.stryktipsfinder.domain.model.UnitOfWorkCache;

public class StryktipsFinderServiceImplTest {
	private EntityManager em;
	private RSystemRepository repository;

	@Before
	public void setUp() throws Exception {
		JPAUtility.startInMemoryDerbyDatabase();
		em = JPAUtility.createEntityManager();
		repository = new RSystemRepositoryImpl(em);
	}

	/**
	 * Add Rsysteminfo and 3 StryktipsSystem in datastorage.
	 */
	private RSystemSearchInfo createRsystemWith3SystemsIn(String name,
			Algorithm algorithm, boolean active) {
		RSystemSearchInfo rsystem = new RSystemSearchInfo();
		rsystem.setName(name);
		rsystem.setTypeOfAlgorithm(algorithm);
		rsystem.setActive(active);
		rsystem.setNumberOfHalfHedging(7);
		rsystem.setNumberOfHoleHedging(0);
		rsystem.setSystems(new ArrayList<StryktipsSystem>());
		rsystem.setNumberOfRows(32);
		rsystem.setReducingGrade(12);
		rsystem.setNumberOfRowsInMathematicalSystem(128);
		rsystem.setNumberOfRowsToSearchFor(11);
		em.persist(rsystem);
		StryktipsSystem previousSystem = new StryktipsSystem();

		for (int i = 0; i < 3; i++) {
			StryktipsSystem foundSystem = new StryktipsSystem();
			foundSystem.setNumberOfRows(324 - i);
			foundSystem.setSystemRows("1111111111111");
			foundSystem.setPreviousStryktipsSystem(previousSystem.Id);
			foundSystem.setNumberOfTestRuns(1967);
			rsystem.getSystems().add(foundSystem);
			em.merge(rsystem);
			em.flush();
			em.refresh(rsystem);
			previousSystem = rsystem.createLinkedListOfSystems().getLast();
		}

		return rsystem;
	}

	@Test
	public void shouldGetSystemInfo() {
		// Given
		StryktipsFinderServiceImpl service = new StryktipsFinderServiceImpl();
		em.getTransaction().begin();
		service.rsystemRepository = repository;
		RSystemSearchInfo rsystem = createRsystemWith3SystemsIn("R-2-6",
				Algorithm.RANDOM, true);
		rsystem = repository.update(rsystem);
		em.getTransaction().commit();

		// When
		RSystemSearchInfo info = service
				.getSystemInfoWithOnlyLatestSystem(rsystem.getId());

		// Then
		Assert.assertEquals("Should be corret system name.", "R-2-6",
				info.getName());
		Assert.assertEquals(
				"Should only be the latest stryktipssystem returned.", 1, info
						.getSystems().size());
		Assert.assertEquals("Should be latest system.", 322, info.getSystems()
				.iterator().next().getNumberOfRows());
		Assert.assertEquals("Should verify unit of work attribute.", 11,
				info.getNumberOfRowsToSearchFor());

		// Clean up
		em.getTransaction().begin();
		em.remove(rsystem);
		em.getTransaction().commit();
	}

	@Test
	public void shouldNotFindAnyRSystemSearchInfo() {
		// Given
		StryktipsFinderServiceImpl service = new StryktipsFinderServiceImpl();
		service.rsystemRepository = repository;

		// When
		RSystemSearchInfo info = service
				.getSystemInfoWithOnlyLatestSystem(2323);

		// Then
		Assert.assertTrue("Should be an empty info object.", info.isEmpty());
	}

	@Test
	public void shouldSendFoundSystemToServer() throws Exception {
		// Given
		StryktipsFinderServiceImpl service = new StryktipsFinderServiceImpl();
		service.rsystemRepository = repository;
		em.getTransaction().begin();
		RSystemSearchInfo searchInfo = createRsystemWith3SystemsIn("R-2-6",
				Algorithm.RANDOM, true);
		searchInfo = repository.update(searchInfo);
		em.getTransaction().commit();

		// Simulate that actor found one system.
		RSystemSearchInfo foundSearchInfo = new RSystemSearchInfo();
		foundSearchInfo.setNumberOfRows(67);
		foundSearchInfo.setId(searchInfo.getId());
		StryktipsSystem foundSystem = new StryktipsSystem();
		foundSystem.setNumberOfRows(31);
		foundSystem.setPreviousStryktipsSystem(searchInfo
				.createLinkedListOfSystems().getLast().Id);
		foundSystem.setSystemRows("111112111111x");
		foundSearchInfo.addStryktipsSystem(foundSystem);

		// When
		em.getTransaction().begin();
		service.sendSystemToServer(foundSearchInfo);
		em.getTransaction().commit();

		// Then
		searchInfo = service.getSystemInfoWithOnlyLatestSystem(searchInfo
				.getId());
		Assert.assertEquals("Should only be one system returned.", 1,
				searchInfo.getSystems().size());

		Assert.assertEquals("Should only return the simulated found system.",
				31, searchInfo.getSystems().iterator().next().getNumberOfRows());
		Assert.assertEquals(
				"Should update rsysteminfo with 31 rows in found system.", 31,
				searchInfo.getNumberOfRows());
		Assert.assertEquals("Should update reducing grade.", 4,
				searchInfo.getReducingGrade());

		// Clean up
		em.getTransaction().begin();
		searchInfo = em.find(RSystemSearchInfo.class, searchInfo.getId());
		em.remove(searchInfo);
		em.getTransaction().commit();
	}

	@Test
	public void shouldFailSendFoundSystemToServerDueToInvalidSystemId() {
		// Given
		StryktipsFinderServiceImpl service = new StryktipsFinderServiceImpl();
		service.rsystemRepository = repository;
		// Simulate that actor found one system.
		em.getTransaction().begin();
		RSystemSearchInfo foundSearchInfo = createRsystemWith3SystemsIn(
				"R-2-6", Algorithm.RANDOM, true);
		em.getTransaction().commit();
		long id = foundSearchInfo.getId();
		foundSearchInfo.setId(121233);
		StryktipsSystem foundSystem = new StryktipsSystem();
		foundSystem.setNumberOfRows(101);
		foundSystem.setPreviousStryktipsSystem(1);
		foundSystem.setSystemRows("111112111111x");
		foundSearchInfo.getSystems().clear();
		foundSearchInfo.getSystems().add(foundSystem);

		// When
		try {
			em.getTransaction().begin();
			service.sendSystemToServer(foundSearchInfo);
			em.getTransaction().rollback();
			Assert.fail("Should not be able to send system to server due to invalid database id.");
		} catch (NoSystemFoundException e) {
			// Then ... OK
			em.getTransaction().rollback();
		}

		// Clean up
		em.getTransaction().begin();
		foundSearchInfo = em.find(RSystemSearchInfo.class, id);
		em.remove(foundSearchInfo);
		em.getTransaction().commit();
	}

	@Test
	public void shouldInformServerOfNumberOfTestRuns()
			throws NoSystemFoundException {
		// Given
		StryktipsFinderServiceImpl service = new StryktipsFinderServiceImpl();
		service.rsystemRepository = repository;
		em.getTransaction().begin();
		RSystemSearchInfo searchInfo = createRsystemWith3SystemsIn("R-2-7",
				Algorithm.RANDOM, true);
		searchInfo = repository.update(searchInfo);
		em.getTransaction().commit();

		// When
		em.getTransaction().begin();
		service.informServerNumberOfTestRuns(searchInfo.getId(), 1000);
		em.getTransaction().commit();

		// Then
		searchInfo = service.getSystemInfoWithOnlyLatestSystem(searchInfo
				.getId());
		Assert.assertEquals("Should be test runs performed.", 2967, searchInfo
				.getSystems().iterator().next().getNumberOfTestRuns());
		Assert.assertEquals("Should not update reducing grade.", 12,
				searchInfo.getReducingGrade());

		// Clean up
		em.getTransaction().begin();
		searchInfo = em.find(RSystemSearchInfo.class, searchInfo.getId());
		em.remove(searchInfo);
		em.getTransaction().commit();
	}

	@Test
	public void shouldFailInformServerOfNumberOfTestRunsDueToInvalidSystemId() {
		// Given
		StryktipsFinderServiceImpl service = new StryktipsFinderServiceImpl();
		service.rsystemRepository = repository;

		// When
		try {
			service.informServerNumberOfTestRuns(5455, 1000);
			Assert.fail("Should not be able to update # runs on none existing system info.");
		} catch (NoSystemFoundException e) {
			// Then ... ok
		}
	}

	@Test
	public void shouldReserveUnitOfWork() {
		// clear active list of unit of works
		UnitOfWorkCache cache = UnitOfWorkCache.getInstance();
		cache.activeStepThroughUnitOfWorks = new ArrayList<StepThroughUnitOfWork>();
		
		// Given
		StryktipsFinderServiceImpl service = new StryktipsFinderServiceImpl();
		service.rsystemRepository = repository;

		RSystemSearchInfo info = new RSystemSearchInfo();
		info.setNumberOfHalfHedging(7);
		info.setNumberOfHoleHedging(0);
		info.setName("R-0-5");
		info.setTypeOfAlgorithm(Algorithm.STEP_THROUGH);
		info.setNumberOfRowsToSearchFor(11);
		StepThroughUnitOfWork unitOfWork = new StepThroughUnitOfWork();
		unitOfWork.startCombination = 0;
		unitOfWork.status = StepThroughUnitOfWorkStatus.COMPLETED;
		unitOfWork.testedUpToCombination = 500000000;
		info.setCurrentStepThrougUnitOfWork(unitOfWork);
		em.getTransaction().begin();
		repository.insert(info);
		em.getTransaction().commit();
		long NUMBER_OF_COMBINATIONS_TO_RESERVE = 500000000l;
		

		
		// When
		em.getTransaction().begin();
		StepThroughUnitOfWork unitOfWorkToDo = service.reserveNextUnitOfWork(
				info.getId(), NUMBER_OF_COMBINATIONS_TO_RESERVE);
		System.out.println("DEBUG:" + unitOfWorkToDo);
		service.completedUnitOfWork(info.getId(),
				unitOfWorkToDo.startCombination, NUMBER_OF_COMBINATIONS_TO_RESERVE);
		em.getTransaction().commit();

		// Then
		info = em.find(RSystemSearchInfo.class, info.getId());
		System.out.println("DEBUG: " + info.getCurrentStepThrougUnitOfWork());
		Assert.assertEquals("Should have completed a search.", 1000000000l,
				info.getCurrentStepThrougUnitOfWork().testedUpToCombination);

		// Clean upp
		JPAUtility.remove(info, em);
	}
}
