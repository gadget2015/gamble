package org.robert.tips.stryktipsfinder.domain.infrastructure;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.tips.stryktipsfinder.domain.model.Algorithm;
import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;
import org.robert.tips.stryktipsfinder.domain.model.StepThroughUnitOfWork;
import org.robert.tips.stryktipsfinder.domain.model.StryktipsSystem;

public class RSystemRepositoryTest {
	private EntityManager em;
	private RSystemRepository repository;

	@Before
	public void setUp() throws Exception {
		JPAUtility.startInMemoryDerbyDatabase();
		em = JPAUtility.createEntityManager();
		repository = new RSystemRepositoryImpl(em);
	}

	@Test
	public void shouldUpdateAnRsystemSearchInfo() {
		// Given
		RSystemSearchInfo rsystem = new RSystemSearchInfo();
		rsystem.setName("R-4-4-xxx");
		rsystem.setNumberOfHalfHedging(4);
		rsystem.setNumberOfHoleHedging(4);

		// When
		em.getTransaction().begin();
		repository.insert(rsystem);
		em.getTransaction().commit();

		em.getTransaction().begin();
		rsystem = repository.findSystem(rsystem.getId());
		rsystem.setName("R-4-4-yyy");
		rsystem.setNumberOfRowsToSearchFor(7);
		StepThroughUnitOfWork unitOfWork = new StepThroughUnitOfWork();
		unitOfWork.startCombination = 1000;
		rsystem.setCurrentStepThrougUnitOfWork(unitOfWork);
		rsystem = repository.update(rsystem);
		em.getTransaction().commit();

		// Then
		rsystem = repository.findSystem(rsystem.getId());
		Assert.assertTrue("Should be inserted a new ID.", rsystem.getId() > 0);
		Assert.assertEquals("Should be new system name.", "R-4-4-yyy",
				rsystem.getName());
		Assert.assertEquals("Should be attribut set.", 7,
				rsystem.getNumberOfRowsToSearchFor());
		Assert.assertEquals("Should update step through relation.", 1000,
				rsystem.getCurrentStepThrougUnitOfWork().startCombination);
		Assert.assertEquals("Should be a calculated mathematical system.",
				1296, rsystem.getNumberOfRowsInMathematicalSystem());

		// Clean up
		JPAUtility.remove(rsystem, em);
	}

	@Test
	public void shouldUpdateAnRsystemSearchInfoWithOneNewStryktipssystem() {
		// Given
		em.getTransaction().begin();
		RSystemSearchInfo rsystem = new RSystemSearchInfo();
		rsystem.setName("R-4-4");
		rsystem.setNumberOfHoleHedging(4);
		rsystem.setNumberOfHalfHedging(4);
		rsystem.setNumberOfRows(150);
		StryktipsSystem system = new StryktipsSystem();
		system.setNumberOfRows(150);
		system.setSystemRows("1111111111111");
		system.setNumberOfTestRuns(1005);
		rsystem.addStryktipsSystem(system);
		repository.insert(rsystem);
		long id = rsystem.getSystems().iterator().next().Id;
		em.getTransaction().commit();

		// When
		em.getTransaction().begin();
		rsystem = repository.findSystem(rsystem.getId());
		system = new StryktipsSystem();
		system.setNumberOfRows(149);
		system.setPreviousStryktipsSystem(id);
		system.setSystemRows("1111111111112");
		system.setNumberOfTestRuns(1500);
		rsystem.addStryktipsSystem(system);

		rsystem = repository.update(rsystem);
		em.getTransaction().commit();

		// Then
		Assert.assertTrue("Should be inserted a new ID.", rsystem.getId() > 0);
		Assert.assertEquals("Should be two systems.", 2, rsystem.getSystems()
				.size());
		Assert.assertEquals("Should calculate total sum of test runs.", 2505,
				rsystem.getSpeed());
		Assert.assertEquals("Should calculate reducing grade.", 8,
				rsystem.getReducingGrade());
		Assert.assertEquals(
				"Should be a new number of rows set in rsystem info.", 149,
				rsystem.getNumberOfRows());

		// Clean up
		JPAUtility.remove(rsystem, em);
	}

	@Test
	public void shouldNotUpdateNumberOfRowsInRsystemSearchInfoWhenAddingANewStryktipssystem() {
		// Given
		em.getTransaction().begin();
		RSystemSearchInfo rsystem = new RSystemSearchInfo();
		rsystem.setName("R-4-4");
		rsystem.setNumberOfHoleHedging(4);
		rsystem.setNumberOfHalfHedging(4);
		rsystem.setNumberOfRows(150);
		StryktipsSystem system = new StryktipsSystem();
		system.setNumberOfRows(150);
		system.setSystemRows("1111111111111");
		system.setNumberOfTestRuns(1005);
		rsystem.addStryktipsSystem(system);
		repository.insert(rsystem);
		long id = rsystem.getSystems().iterator().next().Id;
		em.getTransaction().commit();

		// When
		em.getTransaction().begin();
		rsystem = repository.findSystem(rsystem.getId());
		system = new StryktipsSystem();
		system.setNumberOfRows(160);
		system.setPreviousStryktipsSystem(id);
		system.setSystemRows("1111111111112");
		system.setNumberOfTestRuns(1500);
		rsystem.addStryktipsSystem(system);

		rsystem = repository.update(rsystem);
		em.getTransaction().commit();

		// Then
		Assert.assertEquals(
				"Should be a new number of rows set in rsystem info.", 150,
				rsystem.getNumberOfRows());

		// Clean up
		JPAUtility.remove(rsystem, em);
	}

	@Test
	public void shouldGetAllRSystemSearchInfos() {
		// Given
		int before = repository.findAllActiveRSystems().size();
		em.getTransaction().begin();
		RSystemSearchInfo sys1 = createRsystem("R-4-4", Algorithm.ITERATIVE,
				true);
		RSystemSearchInfo sys2 = createRsystem("R-0-7", Algorithm.ITERATIVE,
				true);
		RSystemSearchInfo sys3 = createRsystem("R-3-6", Algorithm.ITERATIVE,
				true);
		RSystemSearchInfo sys4 = createRsystem("R-5-6", Algorithm.ITERATIVE,
				false);
		em.getTransaction().commit();

		// When
		List<RSystemSearchInfo> rsystems = repository.findAllActiveRSystems();

		// Then
		Assert.assertEquals("Should be 3 more rsystems.", 3 + before,
				rsystems.size());

		// Clean up
		em.getTransaction().begin();
		em.remove(sys1);
		em.remove(sys2);
		em.remove(sys3);
		em.remove(sys4);
		em.getTransaction().commit();
	}

	@Test
	public void shouldGetAllRSystemSearchInfosButThereWasNone() {
		// Given

		// When
		List<RSystemSearchInfo> rsystems = repository.findAllActiveRSystems();

		// Then
		Assert.assertEquals("Should be an empty list returned.", 0,
				rsystems.size());
	}

	private RSystemSearchInfo createRsystem(String name, Algorithm algorithm,
			boolean active) {
		RSystemSearchInfo rsystem = new RSystemSearchInfo();
		rsystem.setName(name);
		rsystem.setTypeOfAlgorithm(algorithm);
		rsystem.setActive(active);
		rsystem.setSystems(new ArrayList<StryktipsSystem>());

		return repository.update(rsystem);
	}

	@Test
	public void shouldInsertAnRsystemSearchInfo() {
		// Given
		RSystemSearchInfo rsystem = new RSystemSearchInfo();
		rsystem.setName("R-4-2-xxx");
		rsystem.setNumberOfHalfHedging(4);
		rsystem.setNumberOfHoleHedging(2);

		// When
		em.getTransaction().begin();
		repository.insert(rsystem);
		em.getTransaction().commit();

		// Then
		Assert.assertTrue("Should be inserted a new ID.", rsystem.getId() > 0);
		Assert.assertEquals("Should be new system name.", "R-4-2-xxx",
				rsystem.getName());
		Assert.assertEquals("Should be a calculated mathematical system.", 144,
				rsystem.getNumberOfRowsInMathematicalSystem());

		// Clean up
		JPAUtility.remove(rsystem, em);
	}

	@Test
	public void shouldInsertAVeryBigSystem() {
		// Given
		RSystemSearchInfo rsystem = new RSystemSearchInfo();
		rsystem.setName("R-4-4-xxx");
		rsystem.setNumberOfHalfHedging(4);
		rsystem.setNumberOfHoleHedging(2);
		StryktipsSystem system = new StryktipsSystem();
		system.setNumberOfRows(150);
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 3000; i++) {
			sb.append("1111111111111");
		}

		system.setSystemRows(sb.toString());
		system.setNumberOfTestRuns(1005);
		rsystem.addStryktipsSystem(system);

		// When
		em.getTransaction().begin();
		repository.insert(rsystem);
		em.getTransaction().commit();

		// Then
		Assert.assertTrue("Should be inserted a new ID.", rsystem.getId() > 0);
		system = rsystem.getSystems().iterator().next();
		Assert.assertEquals(
				"Should be able to persist a system with very many rows, e.g more than 8 000 characters.",
				39000, system.getSystemRows().length());
		Assert.assertEquals("Should be same rows.", sb.toString(),
				system.getSystemRows());

		// Clean up
		JPAUtility.remove(rsystem, em);
	}
}
