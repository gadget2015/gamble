package org.robert.tips.stryktipsfinder.domain.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.robert.tips.stryktipsfinder.domain.infrastructure.InfrastructureNamedQuery;
import org.robert.tips.util.stryktips.Faculty;

/**
 * Defines a reduced Stryktips system.
 * 
 * @author Robert Georen.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = InfrastructureNamedQuery.FIND_ALL_ACTIVE_RSYSTEMS, query = "SELECT systeminfoystem FROM RSystemSearchInfo systeminfoystem WHERE systeminfoystem.active = true"),
		@NamedQuery(name = InfrastructureNamedQuery.SUM_OF_TEST_RUNS, query = "SELECT SUM(sys.numberOfTestRuns) FROM RSystemSearchInfo systeminfoystem JOIN systeminfoystem.systems sys WHERE systeminfoystem.id = :systemDbId") })
public class RSystemSearchInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

	/**
	 * For example: R-4-5, where 4 = hel, 5=halv.
	 */
	private String name;
	private int numberOfHoleHedging;
	private int numberOfHalfHedging;
	private int numberOfRows;
	private int numberOfRowsInMathematicalSystem;
	private int reducingGrade;

	@Enumerated(EnumType.STRING)
	private Algorithm typeOfAlgorithm;
	private long speed;
	private boolean active;

	@Enumerated(EnumType.STRING)
	private Status status;

	@OneToMany(cascade = CascadeType.ALL)
	private Collection<StryktipsSystem> systems;

	/**
	 * Used by StepThrough algorithm.
	 */
	private int numberOfRowsToSearchFor;

	@OneToOne(cascade = CascadeType.ALL)
	private StepThroughUnitOfWork currentStepThrougUnitOfWork;

	@Transient
	private List<StepThroughUnitOfWork> activeStepThroughUnitOfWorks;

	/**
	 * Constructor used by EJB.
	 */
	public RSystemSearchInfo() {
		UnitOfWorkCache cache = UnitOfWorkCache.getInstance();
		this.activeStepThroughUnitOfWorks = cache.activeStepThroughUnitOfWorks;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setTypeOfAlgorithm(Algorithm typeOfAlgorithm) {
		this.typeOfAlgorithm = typeOfAlgorithm;
	}

	public Algorithm getTypeOfAlgorithm() {
		return typeOfAlgorithm;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setSystems(Collection<StryktipsSystem> systems) {
		this.systems = systems;
	}

	public Collection<StryktipsSystem> getSystems() {
		return systems;
	}

	public void addStryktipsSystem(StryktipsSystem system) {
		if (systems == null) {
			systems = new ArrayList<StryktipsSystem>();
		}

		systems.add(system);
	}

	public void setNumberOfRowsInMathematicalSystem(
			int numberOfRowsInMathematicalSystem) {
		this.numberOfRowsInMathematicalSystem = numberOfRowsInMathematicalSystem;
	}

	public int getNumberOfRowsInMathematicalSystem() {
		return numberOfRowsInMathematicalSystem;
	}

	public void setReducingGrade(int reducingGrade) {
		this.reducingGrade = reducingGrade;
	}

	public int getReducingGrade() {
		return reducingGrade;
	}

	public int getNumberOfHoleHedging() {
		return numberOfHoleHedging;
	}

	public void setNumberOfHoleHedging(int numberOfHoleHedging) {
		this.numberOfHoleHedging = numberOfHoleHedging;
	}

	public int getNumberOfHalfHedging() {
		return numberOfHalfHedging;
	}

	public void setNumberOfHalfHedging(int numberOfHalfHedging) {
		this.numberOfHalfHedging = numberOfHalfHedging;
	}

	/**
	 * Check if object is empty.
	 * 
	 * @return true = is empty.
	 */
	public boolean isEmpty() {
		if (getId() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Create a linked list with first systems first and the last at the end of
	 * the list.
	 */
	public LinkedList<StryktipsSystem> createLinkedListOfSystems() {
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
			Collection<StryktipsSystem> systems, StryktipsSystem previousSystem) {
		for (StryktipsSystem nextSystem : systems) {
			if ((previousSystem.Id == nextSystem.getPreviousStryktipsSystem())
					&& (previousSystem.Id != nextSystem.Id)) {
				return nextSystem;
			}
		}

		return null;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	/**
	 * Calculate the reducing grade for this rsystem.
	 * 
	 * @return grade in hole units (rounded, but not precise.).
	 */
	public int calculateReducingGrade() {
		int reducingGrade = numberOfRowsInMathematicalSystem / numberOfRows;

		return reducingGrade;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public long getSpeed() {
		return speed;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public void setNumberOfRowsToSearchFor(int numberOfRowsToSearchFor) {
		this.numberOfRowsToSearchFor = numberOfRowsToSearchFor;
	}

	public int getNumberOfRowsToSearchFor() {
		return this.numberOfRowsToSearchFor;
	}

	/**
	 * Reserve next unit of work to be tested. If the search has come to an end
	 * this method just returns the last unit of work. If there are 5
	 * uncompleted unit of works in list of active unit of work, then the first
	 * one of those is returned.
	 * 
	 * @param numberOfCombinationsToReserve
	 */
	public StepThroughUnitOfWork reserveNextUnitOfWork(
			long numberOfCombinationsToReserve) {
		// 1. Check if all unit of works has been tested.
		if (hasTestedAllCombinations(numberOfCombinationsToReserve)) {
			return getCurrentStepThrougUnitOfWork();
		}

		// 2. Check if there are 5 uncompleted unit of work in list of unit of
		// works.
		if (isThereTooManyUncompletedUnitOfWorks()) {
			return firstUncompletedUnitOfWork();
		}

		// 3. Calculate next position
		long nextPositionToReserve = 0;

		if (this.getCurrentStepThrougUnitOfWork() == null) {
			// First time, null pointer pattern.
			this.setCurrentStepThrougUnitOfWork(new StepThroughUnitOfWork());
			nextPositionToReserve = 0;

		} else {
			nextPositionToReserve = this.getCurrentStepThrougUnitOfWork().startCombination
					+ numberOfCombinationsToReserve;

			while (isPositionReserved(nextPositionToReserve)) {
				nextPositionToReserve += numberOfCombinationsToReserve;
			}
		}

		// 4. Add unit if work to list of all units of works.
		StepThroughUnitOfWork nextUnitOfWork = new StepThroughUnitOfWork();
		nextUnitOfWork.startCombination = nextPositionToReserve;
		nextUnitOfWork.testedUpToCombination = calculateTestedUpToCombination(
				nextPositionToReserve, numberOfCombinationsToReserve);

		activeStepThroughUnitOfWorks.add(nextUnitOfWork);

		return nextUnitOfWork;
	}

	private StepThroughUnitOfWork firstUncompletedUnitOfWork() {
		for (StepThroughUnitOfWork unitOfWork : activeStepThroughUnitOfWorks) {
			if (unitOfWork.status == StepThroughUnitOfWorkStatus.UNCOMPLETED) {
				return unitOfWork;
			}
		}

		return null;
	}

	private boolean isThereTooManyUncompletedUnitOfWorks() {
		int numberOfUncompletedUnitOfWorks = 0;

		for (StepThroughUnitOfWork unitOfWork : activeStepThroughUnitOfWorks) {
			if (unitOfWork.status == StepThroughUnitOfWorkStatus.UNCOMPLETED) {
				numberOfUncompletedUnitOfWorks++;
			}
		}

		final int NUMBER_OF_UNCOMPLETED_UNIT_OF_WORKS_BEFORE_RESTART = 5;

		if (numberOfUncompletedUnitOfWorks >= NUMBER_OF_UNCOMPLETED_UNIT_OF_WORKS_BEFORE_RESTART) {
			return true;
		} else {
			return false;
		}
	}

	private boolean hasTestedAllCombinations(long numberOfCombinationsToReserve) {
		if (getCurrentStepThrougUnitOfWork() == null) {
			return false;
		}

		BigInteger numberOfCombinations = calculateTotalNumberOfCombinations();
		BigInteger numberOfCombinationsTested = BigInteger
				.valueOf(getCurrentStepThrougUnitOfWork().startCombination
						+ numberOfCombinationsToReserve);
		if (numberOfCombinationsTested.compareTo(numberOfCombinations) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Calculate how many combinations that this unit of work tests.
	 */
	private long calculateTestedUpToCombination(long nextPositionToReserve,
			long numberOfCombinationsToReserve) {
		if (nextPositionToReserve == 0) {
			// This happen first time a unit of work is being processed.
			return numberOfCombinationsToReserve;
		} else {
			BigInteger numberOfCombinations = calculateTotalNumberOfCombinations();

			BigInteger testedUpToCombination = BigInteger
					.valueOf(nextPositionToReserve
							+ numberOfCombinationsToReserve);

			if (testedUpToCombination.compareTo(numberOfCombinations) > 0) {
				return numberOfCombinations.longValue();
			} else {
				return testedUpToCombination.longValue();
			}
		}
	}

	private BigInteger calculateTotalNumberOfCombinations() {
		Faculty faculty = new Faculty(numberOfRowsInMathematicalSystem,
				numberOfRowsToSearchFor);
		BigInteger numberOfCombinations = faculty.getNumberOfCombinations();

		return numberOfCombinations;
	}

	private boolean isPositionReserved(long nextPositionToReserve) {
		// Is the calculated next position already reserved?
		for (StepThroughUnitOfWork position : activeStepThroughUnitOfWorks) {
			if (position.startCombination == nextPositionToReserve) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Inform that the given range have been tested.
	 */
	public void completedUnitOfWork(long startCombination,
			long numberOfCombinationsToReserve) {
		// 1. Search for the unit of work that is going to be released.
		for (StepThroughUnitOfWork position : activeStepThroughUnitOfWorks) {
			if (position.startCombination == startCombination
					&& position.testedUpToCombination == (startCombination + numberOfCombinationsToReserve)) {
				// Found unit of work and mark it performed.
				position.status = StepThroughUnitOfWorkStatus.COMPLETED;

				break;
			}
		}

		// 2. Check if any of the completed unit of work is next after the
		// current one. If it's, remove it.
		while (removeNextUnitOfWork() == true) {
		}

	}

	/**
	 * Remove next unit of work that is completed if it's next to the current
	 * one.
	 * 
	 * @return true = unit of work removed.
	 */
	private boolean removeNextUnitOfWork() {
		for (StepThroughUnitOfWork unitOfWork : activeStepThroughUnitOfWorks) {
			if (unitOfWork.status == StepThroughUnitOfWorkStatus.COMPLETED) {
				// It's completed, is it next in sequence after the current one
				if (this.getCurrentStepThrougUnitOfWork()
						.isEqualToNextInSequence(unitOfWork)) {
					this.setCurrentStepThrougUnitOfWork(unitOfWork);

					// Remove the completed unit if work from list with active
					// unit of works.
					activeStepThroughUnitOfWorks.remove(unitOfWork);

					return true;
				}
			}
		}

		// Couldn't remove any unit of works.
		return false;
	}

	public void setCurrentStepThrougUnitOfWork(
			StepThroughUnitOfWork currentStepThrougUnitOfWork) {
		this.currentStepThrougUnitOfWork = currentStepThrougUnitOfWork;
	}

	public StepThroughUnitOfWork getCurrentStepThrougUnitOfWork() {
		return currentStepThrougUnitOfWork;
	}
}
