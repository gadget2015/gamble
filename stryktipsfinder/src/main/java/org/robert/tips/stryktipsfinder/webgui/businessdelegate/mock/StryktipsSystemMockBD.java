package org.robert.tips.stryktipsfinder.webgui.businessdelegate.mock;

import java.util.ArrayList;
import java.util.List;

import org.robert.tips.stryktipsfinder.domain.model.Algorithm;
import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;
import org.robert.tips.stryktipsfinder.domain.model.Status;
import org.robert.tips.stryktipsfinder.domain.model.StryktipsSystem;
import org.robert.tips.stryktipsfinder.webgui.businessdelegate.StryktipsSystemBusinessDelegate;

public class StryktipsSystemMockBD implements StryktipsSystemBusinessDelegate {
	public List<RSystemSearchInfo> getlAllRSystems;
 

	public List<RSystemSearchInfo> getAllRSystems() {
		List<RSystemSearchInfo> systems = new ArrayList<RSystemSearchInfo>();

		systems.add(createSearchInfo(1, true, "R-4-4", 189, 27394830,
				Algorithm.RANDOM, 2048, Status.ON_GOING));
		systems.add(createSearchInfo(2, false, "R-2-4", 124, 21,
				Algorithm.RANDOM, 1024, Status.DONE));
		systems.add(createSearchInfo(3, true, "R-4-3", 137, 19,
				Algorithm.RANDOM, 4096, Status.ON_GOING));

		this.getlAllRSystems = systems;

		return systems;
	}

	private RSystemSearchInfo createSearchInfo(int id, boolean active,
			String name, int numberOfRows, int speed, Algorithm algorithm,
			int numberOfRowsInMathematicalSystem, Status status) {
		RSystemSearchInfo sys = new RSystemSearchInfo();
		sys.setId(id);
		sys.setActive(active);
		sys.setName(name);
		sys.setNumberOfRows(numberOfRows);
		sys.setNumberOfRowsInMathematicalSystem(numberOfRowsInMathematicalSystem);
		sys.setSpeed(speed);
		sys.setReducingGrade(numberOfRowsInMathematicalSystem/numberOfRows);
		sys.setTypeOfAlgorithm(algorithm);
		sys.setStatus(status);

		// Add a path of stryktips system
		sys.addStryktipsSystem(createStryktipsSystem(150, 0, 0));
		sys.addStryktipsSystem(createStryktipsSystem(149, 0, 1));
		sys.addStryktipsSystem(createStryktipsSystem(148, 1, 2));
		sys.addStryktipsSystem(createStryktipsSystem(146, 2, 3));
		sys.addStryktipsSystem(createStryktipsSystem(145, 3, 4));
		sys.addStryktipsSystem(createStryktipsSystem(144, 4, 5));
		
		return sys;
	}

	private StryktipsSystem createStryktipsSystem(int numberOfRows,
			int previousStryktipsSystem, int id) {
		StryktipsSystem system = new StryktipsSystem();
		system.Id = id;
		system.setSystemRows("1x1x1x1x1x1x1\n1x1x12212x1");
		system.setNumberOfRows(numberOfRows);
		system.setPreviousStryktipsSystem(previousStryktipsSystem);

		return system;
	}

}
