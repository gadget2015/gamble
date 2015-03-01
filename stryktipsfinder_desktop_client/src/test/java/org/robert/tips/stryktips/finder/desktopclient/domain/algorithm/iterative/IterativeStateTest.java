package org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.RSystemSearchInfo;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.StryktipsSystem;
import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;
import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchCallbackHandler;
import org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem.SearchLogView;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBDMock;
import org.robert.tips.stryktips.types.StryktipsGame;

public class IterativeStateTest {

	SearchCallbackHandler callbackHandler;
	StryktipsFinderBD bd;

	@Before
	public void before() {
		this.callbackHandler = new SearchCallbackHandler(new SearchLogView(),
				new SystemInfoModel());
		this.bd = new StryktipsFinderBDMock();
	}

	@Test
	public void shouldConvertToDomainObjects() throws TechnicalException {
		// Given
		IterativeContext context = new IterativeContext(bd, 55, callbackHandler);
		RSystemSearchInfo rsystem = bd.getSystemInfoWithOnlyLatestSystem(88);
		List<StryktipsSystem> system1 = rsystem.getSystems();

		// When
		List<StryktipsGame> rows = ((IterativeState) (context.state))
				.convertToStryktipsGameDomainObjects(system1.get(0));

		// Then
		Assert.assertEquals("Should be same number of rows.",
				rsystem.getNumberOfRows(), rows.size());
	}

	@Test
	public void shouldConvertAndRemoveOneRow() throws TechnicalException {
		// Given
		IterativeContext context = new IterativeContext(bd, 55, callbackHandler);
		RSystemSearchInfo rsystem = bd.getSystemInfoWithOnlyLatestSystem(88);
		List<StryktipsSystem> system1 = rsystem.getSystems();
		List<StryktipsGame> rows1 = ((IterativeState) (context.state))
				.convertToStryktipsGameDomainObjects(system1.get(0));
		List<StryktipsGame> rows2 = ((IterativeState) (context.state))
				.convertToStryktipsGameDomainObjects(system1.get(0));

		// When
		rows2.remove(0);

		// Then
		Assert.assertEquals("Should be one less row", (rows1.size() - 1),
				rows2.size());
	}

	@Test
	public void shouldBeSameRows() throws TechnicalException {
		// Given
		IterativeContext context = new IterativeContext(bd, 55, callbackHandler);
		RSystemSearchInfo rsystem = bd.getSystemInfoWithOnlyLatestSystem(88);
		List<StryktipsSystem> system1 = rsystem.getSystems();
		IterativeState is = ((IterativeState) (context.state));
		List<StryktipsGame> rows1 = is
				.convertToStryktipsGameDomainObjects(system1.get(0));
		List<StryktipsGame> rows2 = is
				.convertToStryktipsGameDomainObjects(system1.get(0));

		// When
		int numberOfEqualRows = is.compare(rows1, rows2);

		// Then
		Assert.assertEquals("Should be same number of rows.",
				rsystem.getNumberOfRows(), numberOfEqualRows);
	}

	@Test
	public void shouldCompareToSystemsAndThereIsThreeLessRows() throws TechnicalException {
		// Given
		IterativeContext context = new IterativeContext(bd, 55, callbackHandler);
		RSystemSearchInfo rsystem = bd.getSystemInfoWithOnlyLatestSystem(88);
		List<StryktipsSystem> system1 = rsystem.getSystems();
		IterativeState is = ((IterativeState) (context.state));
		List<StryktipsGame> rows1 = is
				.convertToStryktipsGameDomainObjects(system1.get(0));
		List<StryktipsGame> rows2 = is
				.convertToStryktipsGameDomainObjects(system1.get(0));
		rows2.remove(0);
		rows2.remove(1);
		rows2.remove(2);

		// When
		int numberOfEqualRows = is.compare(rows1, rows2);

		// Then
		Assert.assertEquals("Should be 3 less rows.",
				(rsystem.getNumberOfRows() - 3), numberOfEqualRows);
	}
	
	@Test
	public void shouldCompareToSystemsAndThereIsOneLessRows() throws TechnicalException {
		// Given
		IterativeContext context = new IterativeContext(bd, 55, callbackHandler);
		RSystemSearchInfo rsystem = bd.getSystemInfoWithOnlyLatestSystem(88);
		List<StryktipsSystem> system1 = rsystem.getSystems();
		IterativeState is = ((IterativeState) (context.state));
		List<StryktipsGame> rows1 = is
				.convertToStryktipsGameDomainObjects(system1.get(0));
		rows1.remove(22);
		List<StryktipsGame> rows2 = is
				.convertToStryktipsGameDomainObjects(system1.get(0));

		// When
		int numberOfEqualRows = is.compare(rows1, rows2);

		// Then
		Assert.assertEquals("Should be 1 less rows.",
				(rsystem.getNumberOfRows() - 1), numberOfEqualRows);
	}
}
