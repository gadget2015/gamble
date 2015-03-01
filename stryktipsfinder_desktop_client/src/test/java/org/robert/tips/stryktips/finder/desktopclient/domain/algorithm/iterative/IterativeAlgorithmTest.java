package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;
import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchCallbackHandler;
import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchLogView;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBDMock;
import org.robert.tips.stryktips.types.StryktipsGame;

public class IterativeAlgorithmTest {

	SearchCallbackHandler callbackHandler;
	StryktipsFinderBD bd;

	@Before
	public void before() {
		this.callbackHandler = new SearchCallbackHandler(new SearchLogView(),
				new SystemInfoModel());
		this.bd = new StryktipsFinderBDMock();
	}

	@Test
	public void shouldVerifyThatTestSystemGive12RightGuarantee()
			throws TechnicalException, DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 55, callbackHandler);

		// When
		context.next(); // init
		boolean isOk = context.state.isSystemUnderVerificationOK();

		// Then is should have 12 righ guarantee.
		Assert.assertEquals("Should be 12 right guarantee", true, isOk);
	}

	@Test
	public void shouldVerifyThatTestSystemGive12RightGuarantee_2()
			throws TechnicalException, DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 88, callbackHandler);

		// When
		context.next(); // init
		boolean isOk = context.state.isSystemUnderVerificationOK();

		// Then is should have 12 righ guarantee.
		Assert.assertEquals("Should be 12 right guarantee", true, isOk);
	}

	@Test
	public void shouldVerifyThatTestSystemDontGive12RightGuarantee()
			throws TechnicalException, DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 343,
				callbackHandler);

		// When
		context.next(); // init
		boolean isOk = context.state.isSystemUnderVerificationOK();

		// Then is should have 12 righ guarantee.
		Assert.assertEquals("Should be 12 right guarantee", false, isOk);
	}

	@Test
	public void shouldInitAlgorithm() {
		// Given
		IterativeContext context = new IterativeContext(null, 0,
				callbackHandler);

		// When

		// Then
		Assert.assertEquals("Should be in start state.",
				new Start(context).getClass(), context.state.getClass());
	}

	@Test
	public void shouldGetLatestSystemFromServer() throws Exception,
			DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 55, callbackHandler);

		// When
		context.next();

		// Then
		Assert.assertEquals("Should be in generate state.", new Generate(
				context).getClass(), context.state.getClass());
		Assert.assertEquals("Should call WS endpoint and get latest system.",
				"R-4-2", context.systemAtServer.getName());
		Assert.assertEquals("Should be a mathematical system created.", 324,
				context.mathematicalSystemRows.size());
		Assert.assertEquals("Should initialize test system.", 93,
				context.systemUnderVerification.size());
	}

	@Test
	public void shouldGenerateANewSystemWithOneLessRow() throws Throwable,
			DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 55, callbackHandler);

		// When
		context.next(); // Get latest system
		context.next(); // Generate new system.

		// Then
		Assert.assertEquals("Should be in verify state.", new VerifySystem(
				context).getClass(), context.state.getClass());
		Assert.assertEquals(
				"Should be a new system to test that consists of one less row.",
				92, context.systemUnderVerification.size());
		Assert.assertEquals("Should be one test system generated.", 1,
				context.numberOfTestRuns);
		Assert.assertEquals("Should change next row to remove.", 0,
				context.currentlyRemovedRow);
	}

	@Test
	public void shouldBeAnInvalidR_4_2SystemAndThereforeBeInPutBackTheRow()
			throws Throwable, DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 343,
				callbackHandler);

		// When
		context.next(); // Get latest system
		context.next(); // Generate new system.
		context.next(); // Verify system.

		// Then
		Assert.assertEquals("Should be in put back row state.", new PutBackRow(
				context).getClass(), context.state.getClass());
	}

	@Test
	public void shouldBeAnInvalidR_4_2SystemAndPutBackRow() throws Throwable,
			DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 343,
				callbackHandler);

		// When
		context.next(); // Get latest system
		context.next(); // Generate new system.
		context.next(); // Verify system.
		context.next(); // Put back row

		// Then
		Assert.assertEquals("Should be in Check number of rows removed state.",
				new CheckNumberOfRowsRemoved(context).getClass(),
				context.state.getClass());
		Assert.assertEquals("Should put back previously removed row.", 50,
				context.systemUnderVerification.size());
	}

	@Test
	public void shouldNotHaveRemovedAllRowsYet() throws Throwable,
			DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 343,
				callbackHandler);

		// When trying 2 times to remove a row.
		context.next(); // Get latest system
		context.next(); // Generate new system.
		context.next(); // Verify system.
		context.next(); // Put back row
		context.next(); // check number of rows removed.
		context.next(); // Generate new system.
		context.next(); // Verify system.
		context.next(); // Put back row
		context.next(); // check number of rows removed.

		// Then
		Assert.assertEquals("Should be in Generate a new testsystem state.",
				new Generate(context).getClass(), context.state.getClass());
		Assert.assertEquals("Should only have tested 2 rows.", 2,
				context.currentlyRemovedRow);
		Assert.assertEquals("Should be same size of testsystem as before.", 50,
				context.systemUnderVerification.size());
	}

	@Test
	public void shouldHaveRemovedAllRows() throws Throwable, DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 343,
				callbackHandler);

		// When trying system length times to remove a row, all rows should have
		// been removed one times.
		context.next(); // Get latest system

		for (int i = 0; i < 50; i++) {
			context.next(); // Generate new system.
			context.next(); // Verify system.
			context.next(); // Put back row
			context.next(); // Check number of rows removed.
		}

		// Then the algorithm should be in add new row state.
		Assert.assertEquals("Should be in Add new row state.", new AddNewRow(
				context).getClass(), context.state.getClass());
		Assert.assertEquals("Should reset current test row.", 0,
				((IterativeContext) context).currentlyRemovedRow);
		Assert.assertEquals(
				"Should be same number of rows as before removing rows.", 50,
				context.systemUnderVerification.size());
		int numberOfRowsEqual = checkHowManyRowsThatAreEqual(context);
		boolean sameAsOrignal = (numberOfRowsEqual == 50) ? true : false;
		Assert.assertTrue("Should be same row as before removing rows.",
				sameAsOrignal);
	}

	@Test
	public void shouldHaveAddedANewRowDueToAllRowsHaveBeenRemovedOnce()
			throws TechnicalException, DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 343,
				callbackHandler);

		// When trying 50 times to remove a row (test system contain 50 rows).
		context.next(); // Get latest system

		for (int i = 0; i < 50; i++) {
			context.next(); // Generate new system.
			context.next(); // Verify system.
			context.next(); // Put back row
			context.next(); // Check number of rows removed.
		}

		context.next(); // Transfer to State: CheckAllRowsAddedOnce.

		// Then the algorithm should have performed add new row state.
		Assert.assertEquals("Should be in CheckAllRowsAddedOnce state.",
				new CheckAllRowsAddedOnce(context).getClass(),
				context.state.getClass());
		Assert.assertEquals("Should add one new row.", 51,
				context.systemUnderVerification.size());
		Assert.assertEquals("Should be a new row to add.", 1,
				context.numberOfAddedRows);
	}

	/**
	 * Test a system that can't be better and then add a new row. The new row
	 * don't do anything better, e.g try to remove all rows and when removing
	 * the just added row the system will again get 12 right guarantee.
	 */
	@Test
	public void shouldHaveAddedANewRowAndFoundANewSystem()
			throws TechnicalException, DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 88, callbackHandler);

		// When searching for a system for a while...
		context.next(); // Get latest system

		for (int i = 0; i < (54 + 55); i++) {
			context.next(); // Generate new system.
			context.next(); // Verify system.
			context.next(); // Put back row
			context.next(); // Check number of rows removed.
		}
		int numberOfAddedRows = context.numberOfAddedRows;
		context.next(); // Should system be sent to server = Yes.
		context.next(); // Don't sent system to.

		// Then the algorithm should have performed add new row state.
		Assert.assertEquals(
				"Should be same number of rows as when started searching.", 54,
				context.systemUnderVerification.size());
		Assert.assertEquals("Should have added a row 1 time.", 1,
				numberOfAddedRows);
	}

	@Test
	public void shouldHaveAddedANewRowMoreThan10Times()
			throws TechnicalException, DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 88, callbackHandler);

		// When searching for a system for a while...
		context.next(); // Get latest system
		int numberOfRowsAdded = 0;
		int before = 0;

		for (int i = 0; i < 3000; i++) {
			context.next(); // Generate new system.
			context.next(); // Verify system.
			context.next(); // Put back row
			context.next(); // Check number of rows removed.
			if (context.numberOfAddedRows != before) {
				// Row have been added
				numberOfRowsAdded++;
				before = context.numberOfAddedRows;
			}
		}

		// Then the algorithm should have performed add new row state.
		Assert.assertTrue("Should add rows. # = " + context.numberOfAddedRows,
				(numberOfRowsAdded > 10));
	}

	@Test
	public void shouldFoundSystemWithOneLessRow() throws TechnicalException,
			DomainException {
		// Given
		StryktipsFinderBDMock mock = (StryktipsFinderBDMock) bd;
		IterativeContext context = new IterativeContext(bd, 55, callbackHandler);

		// When trying changing states 100 times a new system will be found.
		for (int i = 0; i < 100; i++) {
			context.next(); // Change to next state
			if (mock.getSystemInfoWithOnlyLatestSystem(55).getNumberOfRows() == 54)
				break;
		}

		// Then the algorithm should say that the system is valid and therefore
		// send it to server.
		Assert.assertEquals("Should send system to server.", 1,
				mock.numberOfSentSystems);
	}

	@Test
	public void shouldHaveAddedAllMathmaticalRows() throws TechnicalException,
			DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 343,
				callbackHandler);

		// When searching for a system for a while...
		context.next(); // Get latest system

		for (int i = 0; i < 14110; i++) {
			context.next(); // Generate new system.
			context.next(); // Verify system.
			context.next(); // Put back row
			context.next(); // Check number of rows removed.
		}

		// Then the algorithm should have performed add new row
		// for all mathematical rows in the system.
		Assert.assertEquals("Should have added all mathematical rows.",
				(324 - 50), context.numberOfAddedRows);
		Assert.assertEquals("Should be in Check Test runs state.",
				new CheckTestRuns(context).getClass(), context.state.getClass());
	}

	@Test
	public void shouldScrambleTheSystem() throws TechnicalException,
			DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 343,
				callbackHandler);
		context.SHOULD_SEND_INFORMATION_TO_SERVER_IN_MILLISECONDS = 20000;

		// When searching for a system for a while...
		context.next(); // Get latest system

		for (int i = 0; i < 14110; i++) {
			context.next(); // Generate new system.
			context.next(); // Verify system.
			context.next(); // Put back row
			context.next(); // Check number of rows removed.
		}
		context.next(); // check test runs.
		context.next(); // Scramble.

		// Then the system should be scrambled
		Assert.assertEquals("Should be 10 % scrambled rows.", 56,
				context.systemUnderVerification.size());
		Assert.assertTrue("Should inform that the system is scrambled.",
				callbackHandler.message.startsWith("Scramble system."));
	}

	private int checkHowManyRowsThatAreEqual(IterativeContext context) {
		int numberOfRowsThatAreEqual = 0;

		for (int i = 0; i < context.systemUnderVerificationOriginal.length; i++) {
			StryktipsGame originalGame = context.systemUnderVerificationOriginal[i];
			if (context.systemUnderVerification.contains(originalGame)) {
				numberOfRowsThatAreEqual++;
			} else {
				// Do nothing;
			}
		}

		return numberOfRowsThatAreEqual;
	}

	@Test
	public void shouldInformServerOfTestRuns() throws TechnicalException,
			DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 343,
				callbackHandler);
		context.SHOULD_SEND_INFORMATION_TO_SERVER_IN_MILLISECONDS = 0;
		ScrambleSystem.NUMBER_OF_PERCENT_TO_SCRAMBLE = 0.07;

		// When
		for (int i = 0; i < 29095; i++) {
			context.next(); // Generate new system.
			context.next(); // Verify system.
			context.next(); // Put back row
			context.next(); // Check number of rows removed.
		}
		context.next();

		// Then
		Assert.assertEquals("Should have informed server.",
				"Inform server of # test runs.", callbackHandler.message);
		Assert.assertEquals("Should be in start state.",
				new Start(context).getClass(), context.state.getClass());
	}

	@Test
	public void shouldRunForAWhileWithoutExceptions()
			throws TechnicalException, DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 55, callbackHandler);
		context.SHOULD_SEND_INFORMATION_TO_SERVER_IN_MILLISECONDS = 2;

		// When
		for (int i = 0; i < 35000; i++) {
			context.next(); // Generate new system.
			context.next(); // Verify system.
			context.next(); // Put back row
			context.next(); // Check number of rows removed.
		}

		// Then no exception should have been thrown.
	}

	/**
	 * Should find a system with 12 right guarantee but the found system is not
	 * better than the system at the server. This implies that the system should
	 * not be sent to the server.
	 */
	@Test
	public void shouldFoundSystemWithOneLessRowButNotBetterThanSystemAtServer()
			throws TechnicalException, DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 88, callbackHandler);

		// When trying changing states 100 times a new system will be found.
		int numberOfAddedRows = 0;
		int before = 0;
		for (int i = 0; i < 439; i++) {
			context.next(); // Change to next state

			if (context.numberOfAddedRows > before) {
				// Row added
				numberOfAddedRows++;
				before = context.numberOfAddedRows;
			}
		}

		// Then the algorithm should say that the system is valid but not
		// send it to server.
		Assert.assertEquals("Should have added one row.", 1, numberOfAddedRows);
		Assert.assertEquals("Should not send system.", new DontSendSystem(
				context).getClass().getName(), context.state.getClass()
				.getName());
	}

	/**
	 * Should find a system with 12 right guarantee that is better than the
	 * system got from server. This should be possible with the given test
	 * system.
	 */
	@Test
	public void shouldFindABetterSystemThanSystemAtServer()
			throws TechnicalException, DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 88, callbackHandler);

		// When trying changing states many times a new system will be found.
		for (int i = 0; i < 27539; i++) {
			context.next(); // Change to next state
		}

		// Then the there should be find a better system.
		Assert.assertTrue("Should find a better system."
				+ context.systemUnderVerification.size(),
				(54 > context.systemUnderVerification.size()));
	}

	/**
	 * Should be in stale state.
	 */
	// @Test
	public void shouldBeInStaleState() throws TechnicalException,
			DomainException {
		// Given
		IterativeContext context = new IterativeContext(bd, 88, callbackHandler);
		InStaleState.STALE_LIMIT = 400;
		Boolean scrambled = Boolean.FALSE;

		// When trying changing states many times a new system will be found.
		for (int i = 0; i < 800000; i++) {
			context.next(); // Change to next state
			if (callbackHandler.message.startsWith("Scramble system.")) {
				scrambled = Boolean.TRUE;
			}
		}

		// Then the system should be scramble due to stale.
		Assert.assertEquals("System shall be scrambled when staled.",
				Boolean.TRUE, scrambled);
	}
}
