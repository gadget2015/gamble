package org.robert.tips.stryktipsfinder.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains list of active unit of works. Pattern: Singleton GoF[98].
 * 
 */
public class UnitOfWorkCache {
	private static UnitOfWorkCache cache;
	public List<StepThroughUnitOfWork> activeStepThroughUnitOfWorks;

	private UnitOfWorkCache() {
		this.activeStepThroughUnitOfWorks = new ArrayList<StepThroughUnitOfWork>();
	}

	public static UnitOfWorkCache getInstance() {
		if (cache == null) {
			cache = new UnitOfWorkCache();
		}

		return cache;
	}
}
