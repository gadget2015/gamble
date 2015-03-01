package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.random;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.RSystemSearchInfo;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.StryktipsSystem;
import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;
import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchCallbackHandler;
import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchLogView;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBDMock;

/**
 * Test the Random algorithm.
 * 
 * @author Robert.
 * 
 */
public class RandomAlgorithmTest {
	SearchCallbackHandler callbackHandler;
	StryktipsFinderBD bd;

	@Before
	public void before() {
		this.callbackHandler = new SearchCallbackHandler(new SearchLogView(),
				new SystemInfoModel());
		this.bd = new StryktipsFinderBDMock();
	}

	@Test
	public void shouldInitAlgorithm() {
		// Given
		RandomContext context = new RandomContext(null, 0, callbackHandler);

		// When

		// Then
		Assert.assertEquals("Should be in start state.",
				new Start(context).getClass(), context.state.getClass());
	}

	@Test
	public void shouldGetLatestSystemFromServer() throws Exception,
			DomainException {
		// Given
		RandomContext context = new RandomContext(bd, 1967, callbackHandler);

		// When
		context.next();

		// Then
		Assert.assertEquals("Should be in generate state.", new Generate(
				context).getClass(), context.state.getClass());
		Assert.assertEquals("Should call WS endpoint and get latest system.",
				"R-4-2", context.systemAtServer.getName());
		Assert.assertEquals("Should be a mathematical system created.", 324,
				context.mathematicalSystemRows.size());
	}

	@Test
	public void shouldGenerateANewSystemWithOneLessRow() throws Throwable,
			DomainException {
		// Given
		RandomContext context = new RandomContext(bd, 1967, callbackHandler);

		// When
		context.next(); // Get latest system
		context.next(); // Generate new system.

		// Then
		Assert.assertEquals("Should be in verify state.", new VerifySystem(
				context).getClass(), context.state.getClass());
		Assert.assertEquals(
				"Should be a new system to test that consists of one less row.",
				299, context.systemUnderVerification.size());
		Assert.assertEquals("Should be one system generated.", 1,
				context.numberOfTestRuns);
	}

	@Test
	public void shouldBeAValidR_4_2SystemAndThereforeSendItToServer()
			throws Throwable, DomainException {
		// Given
		RandomContext context = new RandomContext(bd, 1967, callbackHandler);

		// When
		context.next(); // Get latest system
		context.next(); // Generate new system.
		context.next(); // Verify system.
		context.next(); // Send it to server

		// Then
		Assert.assertEquals("Should be in start state.",
				new Start(context).getClass(), context.state.getClass());
		RSystemSearchInfo sentSystem = context.businessDelegate
				.getSystemInfoWithOnlyLatestSystem(1967);
		Assert.assertEquals(
				"Should only send one system to server.",
				1,
				((StryktipsFinderBDMock) context.businessDelegate).numberOfSentSystems);
		StryktipsSystem lastAddedSystem = sentSystem.getSystems().get(
				sentSystem.getSystems().size() - 1);
		Assert.assertEquals("Should be an added system with one less row.",
				299, lastAddedSystem.getNumberOfRows());
		Assert.assertEquals("Should be previous system set.", 24,
				lastAddedSystem.getPreviousStryktipsSystem());
		Assert.assertTrue("Should get a callback used by GUI.",
				callbackHandler.message
						.startsWith("Sent found system to server,"));
	}

	@Test
	public void shouldBeAnInvalidR_4_2SystemAndThereforeCheckTestRuns()
			throws Throwable, DomainException {
		// Given
		RandomContext context = new RandomContext(bd, 23, callbackHandler);

		// When
		context.next(); // Get latest system
		context.next(); // Generate new system.
		context.next(); // Verify system.

		// Then
		Assert.assertEquals(
				"Should be in check how many times a system have been generated.",
				new CheckTestRuns(context).getClass(), context.state.getClass());
	}

	@Test
	public void shouldBeAnInvalidR_4_2SystemAndDontHaveDone1000Tests()
			throws Throwable, DomainException {
		// Given
		RandomContext context = new RandomContext(bd, 23, callbackHandler);

		// When
		context.next(); // Get latest system
		context.next(); // Generate new system.
		context.next(); // Verify system.
		context.next(); // Check how many random generated system that have been
						// tested.

		// Then
		Assert.assertEquals("Should be in generate a new system.",
				new Generate(context).getClass(), context.state.getClass());
	}

	@Test
	public void shouldFindTwoSystemFromR_4_2() throws Throwable,
			DomainException {
		// Given
		RandomContext context = new RandomContext(bd, 1967, callbackHandler);

		// When
		// Round #1
		context.next(); // Get latest system
		context.next(); // Generate new system.
		context.next(); // Verify system.
		context.next(); // Send system to server.

		// Round #2
		context.next(); // Get latest system
		context.next(); // Generate new system.
		context.next(); // Verify system.
		context.next(); // Send system to server.

		// Then
		Assert.assertEquals("Should reset number of test runs.", 0,
				context.numberOfTestRuns);
		Assert.assertEquals("Should be in start state.",
				new Start(context).getClass(), context.state.getClass());
	}

	/**
	 * Should inform server that the client has performed some failed test runs.
	 * 
	 * @throws Throwable
	 * @throws TechnicalException
	 */
	@Test
	public void shouldInformOfTestRuns() throws TechnicalException, Throwable {
		// Given
		StryktipsFinderBDMock bd = new StryktipsFinderBDMock();
		RandomContext context = new RandomContext(bd, 1967, callbackHandler);
		context.SHOULD_SEND_INFORMATION_TO_SERVER_IN_MILLISECONDS = 10;
		
		// When
		Boolean informServerOfRuns = false;

		for (int i = 0; i < 350; i++) {
			context.next(); // Get latest system
			context.next(); // Generate new system.
			context.next(); // Verify system.
			context.next(); // Send system to server.

			String startOfMessage = callbackHandler.message.substring(0, 10);

			if ("Inform ser".equals(startOfMessage)) {
				informServerOfRuns = true;
			}
		}

		// Then
		Assert.assertEquals("Should send statistics to server.", true,
				bd.serverInformedOfTestRuns);
		Assert.assertTrue("Should get a callback used by GUI.",
				informServerOfRuns);
	}

	/**
	 * Should inform server that the client has performed some failed test runs.
	 * 
	 * @throws Throwable
	 * @throws TechnicalException
	 */
	@Test
	public void shouldSearchForASystem() throws TechnicalException, Throwable {
		// Given
		StryktipsFinderBDMock bd = new StryktipsFinderBDMock();
		RandomContext context = new RandomContext(bd, 1967, callbackHandler);

		// When
		for (int i = 0; i < 3500; i++) {
			context.next(); // Get latest system
			context.next(); // Generate new system.
			context.next(); // Verify system.
			context.next(); // Send system to server.
		}

		// Then
		Assert.assertTrue("Should found a quite good system",
				context.systemUnderVerification.size() < 100);
//		System.out.println("Numger of rows:" + context.systemUnderVerification.size());
//		for (StryktipsGame game : context.systemUnderVerification) {
//			System.out.println(game);
//		}
	}

}
