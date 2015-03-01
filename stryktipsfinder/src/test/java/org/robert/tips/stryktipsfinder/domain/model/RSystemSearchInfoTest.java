package org.robert.tips.stryktipsfinder.domain.model;

import org.junit.Assert;
import org.junit.Test;

public class RSystemSearchInfoTest {

	@Test
	public void shouldGetFirstUnitOfWorkToComplete() {
		// Given
		RSystemSearchInfo searchInfo = createR_0_5();

		// When
		StepThroughUnitOfWork nextPosition = searchInfo
				.reserveNextUnitOfWork(1000);
		searchInfo.completedUnitOfWork(0, 1000l);

		// Then
		Assert.assertEquals("Should have got the first position.", 0l,
				nextPosition.startCombination);
		Assert.assertEquals(
				"Should have tested 1 000 rows",
				1000l,
				searchInfo.getCurrentStepThrougUnitOfWork().testedUpToCombination);
	}

	/**
	 * Should simulate one user (e.g reserve and release in sequence) performing
	 * two requests for unit of works to complete.
	 */
	@Test
	public void shouldGetSecondPositionInSequence() {
		// Given
		RSystemSearchInfo searchInfo = createR_0_5();

		// When
		StepThroughUnitOfWork nextPosition = searchInfo
				.reserveNextUnitOfWork(1000);
		searchInfo.completedUnitOfWork(0, 1000l);
		nextPosition = searchInfo.reserveNextUnitOfWork(1000);
		searchInfo.completedUnitOfWork(1000, 1000l);

		// Then
		Assert.assertEquals("Should get the second position.", 1000l,
				nextPosition.startCombination);
		Assert.assertEquals(
				"Should have tested 2 000 rows",
				2000l,
				searchInfo.getCurrentStepThrougUnitOfWork().testedUpToCombination);
	}

	/**
	 * Should simulate one user (e.g reserve and release in sequence) performing
	 * three requests for unit of works to process/complete.
	 */
	@Test
	public void shouldGetThirdPosition() {
		// Given
		RSystemSearchInfo searchInfo = createR_0_5();

		// When getting a position twice
		StepThroughUnitOfWork nextPosition = searchInfo
				.reserveNextUnitOfWork(1000);
		searchInfo.completedUnitOfWork(0, 1000l);

		nextPosition = searchInfo.reserveNextUnitOfWork(1000);
		searchInfo.completedUnitOfWork(1000, 1000l);

		nextPosition = searchInfo.reserveNextUnitOfWork(1000);
		searchInfo.completedUnitOfWork(2000, 1000l);

		// Then
		Assert.assertEquals("Should be the third position.", 2000l,
				nextPosition.startCombination);
		Assert.assertEquals(
				"Should have tested 3 000 rows",
				3000l,
				searchInfo.getCurrentStepThrougUnitOfWork().testedUpToCombination);
	}

	/**
	 * Should simulate two actors reserve and complete a unit of work. Both
	 * actors reserve the unit if work at same time and release them at same
	 * time too.
	 */
	@Test
	public void shouldSimulateTwoActorsReserveOnce() {
		// Given
		RSystemSearchInfo searchInfo = createR_0_5();

		// When two actors reserve a position and release them
		StepThroughUnitOfWork actor1 = searchInfo.reserveNextUnitOfWork(1000);
		StepThroughUnitOfWork actor2 = searchInfo.reserveNextUnitOfWork(1000);
		searchInfo.completedUnitOfWork(actor1.startCombination, 1000l);
		searchInfo.completedUnitOfWork(actor2.startCombination, 1000l);

		// Then
		Assert.assertEquals(
				"Should have tested 2 000 rows.",
				2000l,
				searchInfo.getCurrentStepThrougUnitOfWork().testedUpToCombination);
		Assert.assertEquals("Should be in correct position.", 1000l,
				searchInfo.getCurrentStepThrougUnitOfWork().startCombination);
	}

	/**
	 * Should simulate two actors reserve and complete a unit of work TWICE.
	 * Both actors reserve the unit of work at same time and release them at
	 * same time to.
	 */
	@Test
	public void shouldSimulateTwoActorsReserveTwice() {
		// Given
		RSystemSearchInfo searchInfo = createR_0_5();

		// When two actors reserve a position and release them
		StepThroughUnitOfWork actor1 = searchInfo.reserveNextUnitOfWork(1000);
		StepThroughUnitOfWork actor2 = searchInfo.reserveNextUnitOfWork(1000);
		searchInfo.completedUnitOfWork(actor1.startCombination, 1000l);
		searchInfo.completedUnitOfWork(actor2.startCombination, 1000l);

		actor1 = searchInfo.reserveNextUnitOfWork(1000);
		actor2 = searchInfo.reserveNextUnitOfWork(1000);
		searchInfo.completedUnitOfWork(actor1.startCombination, 1000l);
		searchInfo.completedUnitOfWork(actor2.startCombination, 1000l);

		// Then
		Assert.assertEquals(
				"Should have tested correct number of rows.",
				4000l,
				searchInfo.getCurrentStepThrougUnitOfWork().testedUpToCombination);
		Assert.assertEquals("Should be in correct position.", 3000l,
				searchInfo.getCurrentStepThrougUnitOfWork().startCombination);
	}

	/**
	 * Should simulate two actors reserve and complete a unit of work TWICE. The
	 * actors reserve and complete them NOT in sequence. In this scenario Actor
	 * 1 has a faster computer than actor 2, e.g actor 1 completes his two unit
	 * of works faster than Actor 2.
	 * 
	 * <pre>
	 * This is the scenario:
	 * 1. Actor 1 reserve 1000 rows. #tested rows=0. start = 0
	 * 2. Actor 2 reserve 1000 rows. #tested rows=0. start = 1000.
	 * 3. Actor 1 complete 1000 rows.#tested rows=1000. start = 0.
	 * 4. Actor 1 reserve 1000 rows. #tested rows=1000. start = 2000.
	 * 5. Actor 1 complete 1000 rows.#tested rows=1000. start = 2000.
	 * 6. Actor 2 complete 1000 rows.#tested rows=3000. start = 1000.
	 * 7. Actor 2 reserve 1000 rows. #tested rows=3000. start = 3000.
	 * 8. Actor 2 complete 1000 rows.#tested rows=4000. start = 3000.
	 * </pre>
	 */
	@Test
	public void shouldSimulateTwoActorsWhereActorOneHasFasterComputer() {
		// Given
		RSystemSearchInfo searchInfo = createR_0_5();

		// When two actors reserve a position and release them but not in
		// sequence.
		StepThroughUnitOfWork actor1 = searchInfo.reserveNextUnitOfWork(1000);
		StepThroughUnitOfWork actor2 = searchInfo.reserveNextUnitOfWork(1000);
		// Actor 1 complete search.
		// 3.
		searchInfo.completedUnitOfWork(actor1.startCombination, 1000l);
		// 4.
		actor1 = searchInfo.reserveNextUnitOfWork(1000);
		Assert.assertEquals("Should got right unit of work.", 2000l,
				actor1.startCombination);
		// 5.
		searchInfo.completedUnitOfWork(actor1.startCombination, 1000l);
		Assert.assertEquals(
				"Should still only have completed fist unit of work.",
				1000l,
				searchInfo.getCurrentStepThrougUnitOfWork().testedUpToCombination);

		// Now actor two lives up and complete it's unit of works.
		// 6.
		searchInfo.completedUnitOfWork(actor2.startCombination, 1000l);
		Assert.assertEquals(
				"Should have completed two fist unit of work.",
				3000l,
				searchInfo.getCurrentStepThrougUnitOfWork().testedUpToCombination);
		// 7.
		actor2 = searchInfo.reserveNextUnitOfWork(1000);
		Assert.assertEquals("Should got right unit of work.", 3000l,
				actor2.startCombination);
		// 8.
		searchInfo.completedUnitOfWork(actor2.startCombination, 1000l);

		// Then
		Assert.assertEquals(
				"Should have tested correct number of rows.",
				4000l,
				searchInfo.getCurrentStepThrougUnitOfWork().testedUpToCombination);
	}

	/**
	 * Should simulate two actors reserve and complete a unit of work TWICE. The
	 * actors reserve and complete them NOT in sequence. The first actor has a
	 * slower computer than actor two, so actor 2 completes his two unit of
	 * works faster.
	 * 
	 * <pre>
	 * This is the scenario:
	 * 1. Actor 1 reserve 1000 rows. #tested rows=0. start = 0.
	 * 2. Actor 2 reserve 1000 rows. #tested rows=0. start = 1000.
	 * 3. Actor 1 complete 1000 rows.#tested rows=1000. start = 0.
	 * 4. Actor 1 reserve 1000 rows. #tested rows=1000. start= 2000.
	 * 5. Actor 2 complete 1000 rows.#tested rows=2000. start = 1000.
	 * 6. Actor 2 reserve 1000 rows. #tested rows=2000. start = 3000.
	 * 7. Actor 2 complete 1000 rows.#tested rows=2000. start = 3000
	 * 8. Actor 1 complete 1000 rows.#tested rows=4000. start=2000.
	 * </pre>
	 */
	@Test
	public void shouldSimulateTwoActorsWhereActorTwoHasFasterComputer() {
		// Given
		RSystemSearchInfo searchInfo = createR_0_5();

		// When two actors reserve a position and release them but not in
		// sequence.
		StepThroughUnitOfWork actor1 = searchInfo.reserveNextUnitOfWork(1000);
		StepThroughUnitOfWork actor2 = searchInfo.reserveNextUnitOfWork(1000);
		// 3.
		searchInfo.completedUnitOfWork(actor1.startCombination, 1000l);
		Assert.assertEquals(
				"Should have tested 1000 combinations.",
				1000l,
				searchInfo.getCurrentStepThrougUnitOfWork().testedUpToCombination);
		// 4.
		actor1 = searchInfo.reserveNextUnitOfWork(1000);
		// 5.
		searchInfo.completedUnitOfWork(actor2.startCombination, 1000l);
		Assert.assertEquals(
				"Should have tested 2000 combinations.",
				2000l,
				searchInfo.getCurrentStepThrougUnitOfWork().testedUpToCombination);
		// 6.
		actor2 = searchInfo.reserveNextUnitOfWork(1000l);
		Assert.assertEquals("Should get correct unit of work", 3000l,
				actor2.startCombination);

		// 7.
		searchInfo.completedUnitOfWork(actor2.startCombination, 1000l);
		Assert.assertEquals(
				"Should be 2000 combinations tested.",
				2000l,
				searchInfo.getCurrentStepThrougUnitOfWork().testedUpToCombination);

		// 8.
		searchInfo.completedUnitOfWork(actor1.startCombination, 1000l);
		Assert.assertEquals(
				"Should be 4 000 combinations tested.",
				4000l,
				searchInfo.getCurrentStepThrougUnitOfWork().testedUpToCombination);
	}

	/**
	 * Should get last combinations to test. Number of combinations: 32/4=35960
	 */
	@Test
	public void shouldGetLastUnitOfWork() {
		// Given
		RSystemSearchInfo searchInfo = createR_0_5();
		StepThroughUnitOfWork unitOfWork = new StepThroughUnitOfWork();
		unitOfWork.startCombination = 34000;
		unitOfWork.status = StepThroughUnitOfWorkStatus.COMPLETED;
		unitOfWork.testedUpToCombination = 35000;
		searchInfo.setCurrentStepThrougUnitOfWork(unitOfWork);

		// When getting a position twice
		StepThroughUnitOfWork nextPosition = searchInfo
				.reserveNextUnitOfWork(1000);
		searchInfo.completedUnitOfWork(35000,
				(nextPosition.testedUpToCombination - 35000));

		// Then
		Assert.assertEquals("Should get last combination.", 35000l,
				nextPosition.startCombination);
		Assert.assertEquals(
				"Should have tested all combinations.",
				35960l,
				searchInfo.getCurrentStepThrougUnitOfWork().testedUpToCombination);
	}

	private RSystemSearchInfo createR_0_5() {
		RSystemSearchInfo searchInfo = new RSystemSearchInfo();
		searchInfo.setNumberOfHalfHedging(5);
		searchInfo.setNumberOfHoleHedging(0);
		searchInfo.setName("R-0-5");
		searchInfo.setNumberOfRowsInMathematicalSystem(32);
		searchInfo.setTypeOfAlgorithm(Algorithm.STEP_THROUGH);
		searchInfo.setNumberOfRowsToSearchFor(4);
		searchInfo.setNumberOfRowsInMathematicalSystem(32);
		return searchInfo;
	}

	/**
	 * The system should not throw exception/fail/halt when the last unit of
	 * work has been reserved. In this case the system should just return the
	 * same unit of work forever.
	 */
	@Test
	public void shouldNotFailWehnLastUnitOfWorkIsAlreadyTaken() {
		// Given
		RSystemSearchInfo searchInfo = createR_0_5();
		StepThroughUnitOfWork unitOfWork = new StepThroughUnitOfWork();
		unitOfWork.startCombination = 35000;
		unitOfWork.status = StepThroughUnitOfWorkStatus.COMPLETED;
		unitOfWork.testedUpToCombination = 35960l;
		searchInfo.setCurrentStepThrougUnitOfWork(unitOfWork);

		// When getting a position twice
		StepThroughUnitOfWork nextPosition = searchInfo
				.reserveNextUnitOfWork(1000);
		searchInfo.completedUnitOfWork(35000,
				(nextPosition.testedUpToCombination - 35000));

		// Then
		Assert.assertEquals("Should get last combination.", 35000l,
				nextPosition.startCombination);
		Assert.assertEquals(
				"Should have tested all combinations.",
				35960l,
				searchInfo.getCurrentStepThrougUnitOfWork().testedUpToCombination);
	}

	/**
	 * Should simulate that if an actor has reserved a unit of work and then
	 * shutdown his/her computer. Then after 5 reservation request the unit of
	 * work for the shutdown actor should be returned. This is so that the
	 * search will not be able to continue to search forward.
	 */
	@Test
	public void shouldReuseUnitOfWorkAfter5ConsecutiveRequests() {
		// Given
		RSystemSearchInfo searchInfo = createR_0_5();

		// When reserve unit of work 5 times.
		StepThroughUnitOfWork nextPosition = null;
		for (int i = 0; i < 6; i++) {
			nextPosition = searchInfo.reserveNextUnitOfWork(1000);
		}

		// Then the first unit of work should be returned.
		Assert.assertEquals("Should return first unit of work.", 0l,
				nextPosition.startCombination);
		Assert.assertEquals("Should return first unit of work.", 1000l,
				nextPosition.testedUpToCombination);
	}
}
