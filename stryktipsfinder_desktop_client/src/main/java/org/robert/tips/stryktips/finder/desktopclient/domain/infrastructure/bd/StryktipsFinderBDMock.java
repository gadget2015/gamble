package org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.Algorithm;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.RSystemSearchInfo;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.StryktipsSystem;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative.Start;

public class StryktipsFinderBDMock implements StryktipsFinderBD {
	public RSystemSearchInfo info;
	HashMap<String, RSystemSearchInfo> systemInfoRepository;
	public boolean serverInformedOfTestRuns;
	public int numberOfSentSystems;
	HashMap<String, Integer> systemPositionRepository;
	public boolean hasCompletedUnitOfWork;

	public StryktipsFinderBDMock() {
		this.systemInfoRepository = new HashMap<String, RSystemSearchInfo>();
		this.systemPositionRepository = new HashMap<String, Integer>();

		// Add: R-4-2-300
		RSystemSearchInfo info = new RSystemSearchInfo();
		info.setActive(true);
		info.setId(1967);
		info.setName("R-4-2");
		info.setNumberOfHoleHedging(4);
		info.setNumberOfHalfHedging(2);
		info.setNumberOfRows(300);
		info.setNumberOfRowsInMathematicalSystem(324);
		info.setTypeOfAlgorithm(Algorithm.RANDOM);
		long id = 1;

		for (int i = 324; i > 300; i--) {
			StryktipsSystem row = new StryktipsSystem();
			row.setId(id++);
			row.setNumberOfRows(i);
			if (id != 2) {
				// Not first system added.
				row.setPreviousStryktipsSystem(id - 2);
			}
			row.setSystemRows("1111111111111\r\n111111111111x");
			info.getSystems().add(row);
		}

		// reverse order of systems because this might be the case
		// when calling a real Webserivice, e.g order not difinite.
		StryktipsSystem[] original = new StryktipsSystem[24];
		info.getSystems().toArray(original);
		info.getSystems().clear();

		for (int i = 23; i >= 0; i--) {
			// revers order.
			info.getSystems().add(original[i]);
		}

		systemInfoRepository.put(Integer.toString(1967), info);

		// Add: R-4-2-23
		info = new RSystemSearchInfo();
		info.setActive(true);
		info.setId(23);
		info.setName("R-4-2");
		info.setNumberOfHoleHedging(4);
		info.setNumberOfHalfHedging(2);
		info.setNumberOfRows(23);
		info.setNumberOfRowsInMathematicalSystem(324);
		info.setTypeOfAlgorithm(Algorithm.RANDOM);

		systemInfoRepository.put(Integer.toString(23), info);

		// Add: R-4-2-93, have 12 right guarantee.
		info = new RSystemSearchInfo();
		info.setActive(true);
		info.setId(55);
		info.setName("R-4-2");
		info.setNumberOfHoleHedging(4);
		info.setNumberOfHalfHedging(2);
		info.setNumberOfRows(93);
		info.setNumberOfRowsInMathematicalSystem(324);
		info.setTypeOfAlgorithm(Algorithm.ITERATIVE);

		StryktipsSystem row = new StryktipsSystem();
		row.setId(id++);
		row.setNumberOfRows(93);
		row.setSystemRows(readRsystem("R-4-2-93.txt"));
		info.getSystems().add(row);
		systemInfoRepository.put(Integer.toString(55), info);

		// Add: R-4-2-54, have 12 right guarantee.
		info = new RSystemSearchInfo();
		info.setActive(true);
		info.setId(88);
		info.setName("R-4-2");
		info.setNumberOfHoleHedging(4);
		info.setNumberOfHalfHedging(2);
		info.setNumberOfRows(54);
		info.setNumberOfRowsInMathematicalSystem(324);
		info.setTypeOfAlgorithm(Algorithm.ITERATIVE);

		row = new StryktipsSystem();
		row.setId(id++);
		row.setNumberOfRows(54);
		row.setSystemRows(readRsystem("R-4-2-54.txt"));
		info.getSystems().add(row);
		systemInfoRepository.put(Integer.toString(88), info);

		// Add: R-4-2-50, this don't give 12 right.
		info = new RSystemSearchInfo();
		info.setActive(true);
		info.setId(343);
		info.setName("R-4-2");
		info.setNumberOfHoleHedging(4);
		info.setNumberOfHalfHedging(2);
		info.setNumberOfRows(50);
		info.setNumberOfRowsInMathematicalSystem(324);
		info.setTypeOfAlgorithm(Algorithm.ITERATIVE);

		row = new StryktipsSystem();
		row.setId(id++);
		row.setNumberOfRows(50);
		row.setSystemRows(readRsystem("R-4-2-50.txt"));
		info.getSystems().add(row);
		systemInfoRepository.put(Integer.toString(343), info);

		// Add: R-0-5, Number of combinations: 32/4=35960
		info = new RSystemSearchInfo();
		info.setActive(true);
		info.setId(293);
		info.setName("R-0-5");
		info.setNumberOfHoleHedging(0);
		info.setNumberOfHalfHedging(5);
		info.setNumberOfRows(5);
		info.setNumberOfRowsInMathematicalSystem(32);
		info.setTypeOfAlgorithm(Algorithm.STEP_THROUGH);
		info.setNumberOfRowsToSearchFor(4);

		systemInfoRepository.put(Integer.toString(293), info);

		// Add: R-0-5 : Give 12 right guarantee
		info = new RSystemSearchInfo();
		info.setActive(true);
		info.setId(57);
		info.setName("R-0-5");
		info.setNumberOfHoleHedging(0);
		info.setNumberOfHalfHedging(5);
		info.setNumberOfRows(23);
		info.setNumberOfRowsInMathematicalSystem(32);
		info.setTypeOfAlgorithm(Algorithm.STEP_THROUGH);
		info.setNumberOfRowsToSearchFor(22);

		row = new StryktipsSystem();
		row.setId(id++);
		row.setNumberOfRows(50);
		row.setSystemRows("1111111111111");
		info.getSystems().add(row);

		systemInfoRepository.put(Integer.toString(57), info);

		// Add: R-0-7, Number of combinations: 128/11=2.3E+15
		// with start combinations to test = 0;
		info = new RSystemSearchInfo();
		info.setActive(true);
		info.setId(923);
		info.setName("R-0-7");
		info.setNumberOfHoleHedging(0);
		info.setNumberOfHalfHedging(7);
		info.setNumberOfRows(11);
		info.setNumberOfRowsInMathematicalSystem(128);
		info.setTypeOfAlgorithm(Algorithm.STEP_THROUGH);
		info.setNumberOfRowsToSearchFor(11);

		systemInfoRepository.put(Integer.toString(923), info);
		systemPositionRepository.put(String.valueOf(923), -1000000);
	}

	private String readRsystem(String fileName) {
		File file = new File("target" + File.separator + "test-classes"
				+ File.separator + fileName);

		StringBuffer sb = new StringBuffer();
		Scanner scanner;

		try {
			scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				sb.append(line + Start.NEWLINE);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found:" + e.getMessage()
					+ ", Path=" + file.getAbsolutePath());
		}
		return sb.toString();
	}

	@Override
	public RSystemSearchInfo getSystemInfoWithOnlyLatestSystem(long id) {
		RSystemSearchInfo info = systemInfoRepository.get(Long.toString(id));

		// Create a linked list of original system from server so that
		// I can get last system and reference to it.
		MockUtil util = new MockUtil();
		LinkedList<StryktipsSystem> linkedList = util
				.createLinkedListOfSystems(info.getSystems());

		// Only return the last system.
		StryktipsSystem lastSystem = linkedList.getLast();

		// Clone object to simulate RPC and return data.
		RSystemSearchInfo clonedInfo = new RSystemSearchInfo();
		clonedInfo.setActive(info.isActive());
		clonedInfo.setId(info.getId());
		clonedInfo.setName(info.getName());
		clonedInfo.setNumberOfHalfHedging(info.getNumberOfHalfHedging());
		clonedInfo.setNumberOfHoleHedging(info.getNumberOfHoleHedging());
		clonedInfo.setNumberOfRows(info.getNumberOfRows());
		clonedInfo.setNumberOfRowsInMathematicalSystem(info
				.getNumberOfRowsInMathematicalSystem());
		clonedInfo.setReducingGrade(info.getReducingGrade());
		clonedInfo.setSpeed(info.getSpeed());
		clonedInfo.setTypeOfAlgorithm(info.getTypeOfAlgorithm());
		clonedInfo
				.setNumberOfRowsToSearchFor(info.getNumberOfRowsToSearchFor());

		// clone last stryktips system
		if (lastSystem != null) {
			StryktipsSystem clonedStryktipsSystem = new StryktipsSystem();
			clonedStryktipsSystem.setId(lastSystem.getId());
			clonedStryktipsSystem.setNumberOfRows(lastSystem.getNumberOfRows());
			clonedStryktipsSystem.setNumberOfTestRuns(lastSystem
					.getNumberOfTestRuns());
			clonedStryktipsSystem.setPreviousStryktipsSystem(lastSystem
					.getPreviousStryktipsSystem());
			clonedStryktipsSystem.setSystemRows(lastSystem.getSystemRows());
			clonedInfo.getSystems().add(clonedStryktipsSystem);
		}

		// System.out
		// .println("StryktipsFinderBDMock: getSystemInfoWithOnlyLatestSystem - number of systems:"
		// + info.getSystems().size()
		// + ", numberOfRows = "
		// + info.getNumberOfRows());

		return clonedInfo;
	}

	@Override
	public void sendSystemToServer(RSystemSearchInfo newRSystem) {
		// Update repository with new system.
		// The found system contains a stryktipsystem that have an 0 as id, e.g
		// this is the new system found. That system should have an id set
		// by the persistence mechanism later...but I do it by hand now.

		// 1. Find highest id
		long highestId = 0;
		RSystemSearchInfo persistedSystem = systemInfoRepository.get(Long
				.toString(newRSystem.getId()));

		for (StryktipsSystem system : persistedSystem.getSystems()) {
			if (system.getId() > highestId) {
				highestId = system.getId();
			}
		}

		// 2. find system that don't have an Id set.
		StryktipsSystem foundSystem = null;

		for (StryktipsSystem system : newRSystem.getSystems()) {
			if (system.getId() == 0) {
				system.setId(highestId + 1);
				system.setPreviousStryktipsSystem(highestId);
				foundSystem = system;
			}
		}

		// 3. update number of rows in rsysteminfo metadata object.
		if (foundSystem.getNumberOfRows() < persistedSystem.getNumberOfRows()) {
			persistedSystem.setNumberOfRows(foundSystem.getNumberOfRows());
		}

		// 4. Persist
		persistedSystem.getSystems().add(foundSystem);
		systemInfoRepository.put(Long.toString(persistedSystem.getId()),
				persistedSystem);

		// System.out
		// .println("StryktipsFinderBDMock: sendSystemToServer - Number of rows :"
		// + foundSystem.getNumberOfRows()
		// + ", total number of systems:"
		// + persistedSystem.getSystems().size() +
		// ", info # rows = " + persistedSystem.getNumberOfRows());

		// Set size in bd mock used by unit test.
		this.numberOfSentSystems = newRSystem.getSystems().size();
	}

	@Override
	public void informServerNumberOfTestRuns(long id, int numberOfTests) {
		this.serverInformedOfTestRuns = true;
	}

	/**
	 * Utility class that has a copy-paste code from domain model.
	 * 
	 * @author Robert
	 * 
	 */
	private class MockUtil {

		// private int next = 0;

		/**
		 * Create a linked list with first systems first and the last at the end
		 * of the list.
		 */
		public LinkedList<StryktipsSystem> createLinkedListOfSystems(
				List<StryktipsSystem> systems) {
			// Create a linked list of original system from server so that
			// I can get last system and reference to it.
			LinkedList<StryktipsSystem> linkedList = new LinkedList<StryktipsSystem>();
			StryktipsSystem firstSystem = null;

			// 1. find first system, e.g no previous system reference.
			for (StryktipsSystem system : systems) {
				if (system.getPreviousStryktipsSystem() == 0) {
					firstSystem = system;

					break;
				}
			}

			// 2. Add first system to start of liked list.
			linkedList.add(firstSystem);
			StryktipsSystem nextSystem = getNextStryktipsSystem(systems,
					firstSystem);

			// 3. fill up rest of linked list.
			while (nextSystem != null) {
				linkedList.add(nextSystem);
				nextSystem = getNextStryktipsSystem(systems, nextSystem);
			}

			return linkedList;
		}

		private StryktipsSystem getNextStryktipsSystem(
				Collection<StryktipsSystem> systems,
				StryktipsSystem previousSystem) {
			for (StryktipsSystem nextSystem : systems) {
				if ((previousSystem.getId() == nextSystem
						.getPreviousStryktipsSystem())
						&& (previousSystem.getId() != nextSystem.getId())) {
					return nextSystem;
				}
			}

			return null;
		}
	}

	/**
	 * Hämtar nästa position i följd.
	 */
	@Override
	public long reserveNextUnitOfWork(long systemAtServerId,
			int numberOfRowsToReserve) {
		Integer persistedPosition = systemPositionRepository.get(Long
				.toString(systemAtServerId));

		if (persistedPosition == null) {
			persistedPosition = 17000;
		} else if (systemAtServerId == 293 && persistedPosition == 35000) {
			persistedPosition = -1; // last combination.
		} else {
			persistedPosition += numberOfRowsToReserve;
		}

		systemPositionRepository.put(Long.toString(systemAtServerId),
				persistedPosition);

		return persistedPosition;
	}

	@Override
	public void completedUnitOfWork(long systemId, long startCombination,
			long numberOfCombinationsToReserve) {
		hasCompletedUnitOfWork = true;
	}

}
