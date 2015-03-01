package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.Main;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.Algorithm;
import org.robert.tips.stryktips.finder.desktopclient.dialog.Model;
import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;
import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.FindStryktipsSystemRunnable;
import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchCallbackHandler;
import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchLogView;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBDMock;

public class StepThroughAlgorithmTest {
	SearchCallbackHandler callbackHandler;
	StryktipsFinderBD bd;

	@Before
	public void before() {
		this.callbackHandler = new SearchCallbackHandler(new SearchLogView(),
				new SystemInfoModel());
		this.bd = new StryktipsFinderBDMock();
	}

	@Test
	public void shouldInitAlgorthmAndBeInStartState() {
		// Given
		StepThroughContext context = new StepThroughContext(null, 0,
				callbackHandler);

		// Then
		Assert.assertEquals("Should be in start state.",
				new Start(context).getClass(), context.state.getClass());
	}

	/**
	 * Should init the algorithm and execute the start state. This implies to
	 * create a mathematical system for the fetched system.
	 */
	@Test
	public void shouldInitAlgorithmWithMathematicalSystem()
			throws TechnicalException, DomainException {
		// Given
		StepThroughContext context = new StepThroughContext(bd, 293,
				callbackHandler);

		// When
		context.next();

		// Then
		Assert.assertEquals("Should be in Reserve state.", new Reserve(context)
				.getClass().getName(), context.state.getClass().getName());
		Assert.assertEquals("Should get system from server.", 32,
				context.systemAtServer.getNumberOfRowsInMathematicalSystem());
		Assert.assertEquals("Should be a mathematical system created.", 32,
				context.mathematicalSystemRows.size());
	}

	/**
	 * Should get start position from server, this implies to reserve the start
	 * position. (32/4)=35960 combinations.
	 */
	@Test
	public void shouldReserveCombinations() throws TechnicalException,
			DomainException {
		// Given
		StepThroughContext context = new StepThroughContext(bd, 293,
				callbackHandler);
		context.NUMBER_OF_COMBINATIONS_TO_RESERVE = 1004;

		// When
		context.next(); // Start
		context.next(); // Reserve

		// Then
		Assert.assertEquals("Should set start position. ", 17000,
				context.startPosition);
	}

	@Test
	public void shouldGenerateFirstCombinationToTest()
			throws TechnicalException, DomainException {
		// Given
		StepThroughContext context = new StepThroughContext(bd, 923,
				callbackHandler);
		context.NUMBER_OF_COMBINATIONS_TO_RESERVE = 1000000;
		context.NUMBER_OF_COMBINATIONS_IN_CHUNK = 10000;

		// When
		context.next(); // Start
		context.next(); // Reserve
		context.next(); // Split
		context.next(); // Generate

		// Then
		Assert.assertEquals("Should generate system with 11 rows.", 11,
				context.systemUnderVerification.size());
		Assert.assertEquals(
				"Should NOT increment pointer for the next combination to test.",
				0, context.nextCombinationToTest);
		Assert.assertEquals("Should have splitted reservation into chunks.",
				context.NUMBER_OF_COMBINATIONS_IN_CHUNK,
				context.combinations.size());
		Assert.assertEquals("Should set next position to create chunk from.",
				context.NUMBER_OF_COMBINATIONS_IN_CHUNK,
				context.nextStartPosition);
	}

	@Test
	public void shouldVerifyFirstCombinationToTestAndFailToBe12RightsGuarantee()
			throws TechnicalException, DomainException {
		// Given
		StepThroughContext context = new StepThroughContext(bd, 293,
				callbackHandler);
		context.NUMBER_OF_COMBINATIONS_TO_RESERVE = 1004;

		// When
		context.next(); // Start
		context.next(); // Reserve
		context.next(); // Split
		context.next(); // Generate
		context.next(); // Verify

		// Then
		Assert.assertEquals("Should be in AllReservedRowsTested state.",
				new AllReservedRowsTested(null).getClass().getName(),
				context.state.getClass().getName());
	}

	@Test
	public void shouldVerifyFirstCombinationToTestAndSucceedToBe12RightsGuarantee()
			throws TechnicalException, DomainException {
		// Given
		StepThroughContext context = new StepThroughContext(bd, 57,
				callbackHandler);
		context.NUMBER_OF_COMBINATIONS_TO_RESERVE = 1004;

		// When
		context.next(); // Start
		context.next(); // Reserve
		context.next(); // Split
		context.next(); // Generate
		context.next(); // Verify

		// Then
		Assert.assertEquals("Should be in AllReservedRowsTested state.",
				new SendSystemToServer(null).getClass().getName(),
				context.state.getClass().getName());
	}

	@Test
	public void shouldSendSystemToServerAfterVerifyingFirstCombinationToTest()
			throws TechnicalException, DomainException {
		// Given
		StepThroughContext context = new StepThroughContext(bd, 57,
				callbackHandler);
		context.NUMBER_OF_COMBINATIONS_TO_RESERVE = 1004;
		Main.model = new Model();
		Main.model.threadPriority = Thread.NORM_PRIORITY;
		Runnable runnable = new FindStryktipsSystemRunnable(bd, 1967,
				callbackHandler, Algorithm.RANDOM);
		Main.model.currentSearch = (FindStryktipsSystemRunnable) runnable;

		// When
		context.next(); // Start
		context.next(); // Reserve
		context.next(); // Split
		context.next(); // Generate
		context.next(); // Verify
		context.next(); // Send system to server.

		// Then
		Assert.assertEquals("Should send system once.", 1,
				((StryktipsFinderBDMock) bd).numberOfSentSystems);
	}

	@Test
	public void shouldHaveTested5Combinations() throws TechnicalException,
			DomainException {
		// Given
		StepThroughContext context = new StepThroughContext(bd, 293,
				callbackHandler);
		context.NUMBER_OF_COMBINATIONS_TO_RESERVE = 1004;

		// When
		context.next(); // Start
		context.next(); // Reserve
		context.next(); // Split

		for (int i = 0; i < 5; i++) {
			context.next(); // Generate
			context.next(); // Verify
			context.next(); // AllReservedRowsTested
		}

		// Then 5 combinations should have been tested.
		Assert.assertEquals("Should have tested 5 combinations.", 5,
				context.nextCombinationToTest);
	}

	/**
	 * Should have tested all combinations in current chunk of combinations.
	 * 
	 */
	@Test
	public void shouldHaveTestedAllCombinations() throws TechnicalException,
			DomainException {
		// Given
		StepThroughContext context = new StepThroughContext(bd, 293,
				callbackHandler);
		context.NUMBER_OF_COMBINATIONS_TO_RESERVE = 1004;
		context.NUMBER_OF_COMBINATIONS_IN_CHUNK = 500;

		// When
		context.next(); // Start
		context.next(); // Reserve
		context.next(); // Split

		for (int i = 0; i < context.NUMBER_OF_COMBINATIONS_IN_CHUNK; i++) {
			context.next(); // Generate
			context.next(); // Verify
			context.next(); // AllReservedRowsTested
		}
		context.next(); // All splitted unit of works.

		// Then all combinations should have been tested.
		Assert.assertEquals("Should have tested all combinations in chunk.",
				context.NUMBER_OF_COMBINATIONS_IN_CHUNK - 1,
				context.nextCombinationToTest);
		Assert.assertEquals("Should be in correct state.", new SplitUnitOfWork(
				context).getClass().getName(), context.state.getClass()
				.getName());
		Assert.assertEquals("Should have correct number of test runs.",
				context.NUMBER_OF_COMBINATIONS_IN_CHUNK,
				context.numberOfTestRuns);
	}

	@Test
	public void shouldSendStatisticsToServer() throws TechnicalException,
			DomainException, InterruptedException {
		// Given
		StepThroughContext context = new StepThroughContext(bd, 293,
				callbackHandler);
		context.NUMBER_OF_COMBINATIONS_TO_RESERVE = 1000;
		context.NUMBER_OF_COMBINATIONS_IN_CHUNK = 100;
		context.SHOULD_SEND_INFORMATION_TO_SERVER_IN_MILLISECONDS = 0;

		// When
		context.next(); // Start
		context.next(); // Reserve
		context.nextStartPosition = 17800;
		context.next(); // Split into chunk

		for (int i = 0; i < context.NUMBER_OF_COMBINATIONS_IN_CHUNK; i++) {
			context.next(); // Generate
			context.next(); // Verify
			context.next(); // AllReservedRowsTested
		}

		Thread.sleep(100);
		context.next(); // All splitted unit of works.
		context.next(); // InformServerOfAllCombinationsTested

		// Then statistics should have been sent to the server.
		Assert.assertEquals(
				"Should have tested all combinations and passed through inform server state.",
				new Start(context).getClass().getName(), context.state
						.getClass().getName());
		Assert.assertEquals("Should reset number of test runs.", 0,
				context.numberOfTestRuns);
		Assert.assertEquals(
				"Should have called business tier informing that the unit of work is tested",
				true, ((StryktipsFinderBDMock) bd).hasCompletedUnitOfWork);
	}

	/**
	 * This test validates that their will not be exceptions when the search
	 * runs for a while.
	 */
	@Test
	public void shouldRunAlgorithmForAWhileToSeeThatItNotHals()
			throws TechnicalException, DomainException, InterruptedException {
		// Given
		StepThroughContext context = new StepThroughContext(bd, 293,
				callbackHandler);
		context.NUMBER_OF_COMBINATIONS_TO_RESERVE = 100;
		context.NUMBER_OF_COMBINATIONS_IN_CHUNK = 10;

		// When
		context.next(); // Start
		context.next(); // Reserve
		context.next(); // Split

		// Run 20 rounds.
		for (int i = 0; i < context.NUMBER_OF_COMBINATIONS_IN_CHUNK * 200; i++) {
			context.next(); // Generate
			context.next(); // Verify
			context.next(); // AllReservedRowsTested
		}

		// Then
		Assert.assertEquals("Should be testing combinations up to.", 25000,
				context.startPosition);
	}

	/**
	 * Should get next unit of work for the reserved combinations. This is get
	 * one chunk and test all thats combinations and then get next chunk.
	 */
	@Test
	public void shouldGetSecondTestChunk() throws TechnicalException,
			DomainException, InterruptedException {
		// Given
		StepThroughContext context = new StepThroughContext(bd, 293,
				callbackHandler);
		context.NUMBER_OF_COMBINATIONS_TO_RESERVE = 100;
		context.NUMBER_OF_COMBINATIONS_IN_CHUNK = 10;
		context.SHOULD_SEND_INFORMATION_TO_SERVER_IN_MILLISECONDS = 0;

		// When
		context.next(); // Start
		context.next(); // Reserve
		context.next(); // Split

		for (int i = 0; i < context.NUMBER_OF_COMBINATIONS_IN_CHUNK; i++) {
			context.next(); // Generate
			context.next(); // Verify
			context.next(); // AllReservedRowsTested
		}

		Thread.sleep(100);
		context.next(); // All splitted unit of works.
		Assert.assertEquals("Should be in split state.", new SplitUnitOfWork(
				context).getClass().getName(), context.state.getClass()
				.getName());
		context.next(); // Split unit of work.

		// Then statistics should have been sent to the server.
		Assert.assertEquals(
				"Should have tested all combinations and passed through inform server state.",
				new Generate(context).getClass().getName(), context.state
						.getClass().getName());
	}

	/**
	 * Should search to the end of all combinations and inform actor of it.
	 */
	@Test
	public void shouldSearchToEndOfCombinations() throws TechnicalException,
			DomainException {
		// Given
		Main.model = new Model();
		Runnable runnable = new FindStryktipsSystemRunnable(bd, 1967,
				callbackHandler, Algorithm.RANDOM);
		Main.model.currentSearch = (FindStryktipsSystemRunnable) runnable;
		StepThroughContext context = new StepThroughContext(bd, 293,
				callbackHandler);
		context.NUMBER_OF_COMBINATIONS_TO_RESERVE = 100;
		context.NUMBER_OF_COMBINATIONS_IN_CHUNK = 10;

		// When
		context.next(); // Start
		context.next(); // Reserve
		context.next(); // Split

		// Run to end.
		for (int i = 0; i < context.NUMBER_OF_COMBINATIONS_IN_CHUNK * 500; i++) {
			context.next(); // Generate
			context.next(); // Verify
			context.next(); // AllReservedRowsTested
		}

		// Then
		Assert.assertEquals("Should have tested all combinations.", 35000,
				context.startPosition);
		Assert.assertEquals("Should inform actor that search has ended.",
				"Search has ended, all combinations had been tested.",
				callbackHandler.message);
	}
}
