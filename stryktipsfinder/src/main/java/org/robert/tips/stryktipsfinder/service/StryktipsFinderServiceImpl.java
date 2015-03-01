package org.robert.tips.stryktipsfinder.service;

import java.util.LinkedList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.robert.tips.stryktipsfinder.domain.infrastructure.RSystemRepository;
import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;
import org.robert.tips.stryktipsfinder.domain.model.Status;
import org.robert.tips.stryktipsfinder.domain.model.StepThroughUnitOfWork;
import org.robert.tips.stryktipsfinder.domain.model.StryktipsSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebService end point implementation.
 * 
 * @author Robert.
 * 
 */
@WebService
@Stateless
public class StryktipsFinderServiceImpl implements StryktipsFinderService {
	@EJB
	RSystemRepository rsystemRepository;

	Logger logger = LoggerFactory.getLogger(StryktipsFinderServiceImpl.class
			.getName());

	/**
	 * EJB 3.1 default constructor.
	 */
	public StryktipsFinderServiceImpl() {
	}

	@Override
	public RSystemSearchInfo getSystemInfoWithOnlyLatestSystem(long id) {
		RSystemSearchInfo info = rsystemRepository.findSystem(id);

		if (info == null) {
			// Return an empty object, see Null Object Pattern.
			return new RSystemSearchInfo();
		}

		// Create a linked list of original system from server so that
		// I can get last system and reference to it.
		LinkedList<StryktipsSystem> linkedList = info
				.createLinkedListOfSystems();
		StryktipsSystem lastSystem = linkedList.getLast();

		// Create a DTO
		RSystemSearchInfo dto = new RSystemSearchInfo();
		dto.setId(info.getId());
		dto.setActive(info.isActive());
		dto.setName(info.getName());
		dto.setNumberOfHalfHedging(info.getNumberOfHalfHedging());
		dto.setNumberOfHoleHedging(info.getNumberOfHoleHedging());
		dto.setNumberOfRows(info.getNumberOfRows());
		dto.setNumberOfRowsInMathematicalSystem(info
				.getNumberOfRowsInMathematicalSystem());
		dto.setReducingGrade(info.getReducingGrade());
		dto.setSpeed(info.getSpeed());
		dto.setTypeOfAlgorithm(info.getTypeOfAlgorithm());
		dto.setNumberOfRowsToSearchFor(info.getNumberOfRowsToSearchFor());
		dto.setCurrentStepThrougUnitOfWork(info
				.getCurrentStepThrougUnitOfWork());
		dto.addStryktipsSystem(lastSystem);

		return dto;
	}

	@Override
	public void sendSystemToServer(RSystemSearchInfo foundSystem)
			throws NoSystemFoundException {
		// 1. get data from persistent storage.
		RSystemSearchInfo persistedSearchInfo = rsystemRepository
				.findSystem(foundSystem.getId());

		if (persistedSearchInfo == null) {
			// No system found
			throw new NoSystemFoundException(
					"Invalid rsystem search info database id, id = "
							+ foundSystem.getId());
		}

		// 2. Add found stryktips system
		StryktipsSystem foundStryktipsSystem = foundSystem.getSystems()
				.iterator().next();
		StryktipsSystem newSystem = new StryktipsSystem();
		newSystem.setNumberOfRows(foundStryktipsSystem.getNumberOfRows());
		newSystem.setSystemRows(foundStryktipsSystem.getSystemRows());
		newSystem.setPreviousStryktipsSystem(foundStryktipsSystem
				.getPreviousStryktipsSystem());

		persistedSearchInfo.addStryktipsSystem(newSystem);

		// 3. Merge data in database.
		rsystemRepository.update(persistedSearchInfo);
	}

	@Override
	public void informServerNumberOfTestRuns(long id, int numberOfTests)
			throws NoSystemFoundException {
		// 1. get data from persistent storage.
		RSystemSearchInfo persistedSearchInfo = rsystemRepository
				.findSystem(id);

		if (persistedSearchInfo == null) {
			throw new NoSystemFoundException(
					"Unknown database id for search info given, id = " + id);
		}

		// Upadet last system with number of test runs.
		StryktipsSystem lastSystem = persistedSearchInfo
				.createLinkedListOfSystems().getLast();
		lastSystem.addNumberOfTestRuns(numberOfTests);

		rsystemRepository.update(persistedSearchInfo);
	}

	@Override
	public StepThroughUnitOfWork reserveNextUnitOfWork(long systemId,
			long numberOfCombinationsToReserve) {
		RSystemSearchInfo rSystem = rsystemRepository.findSystem(systemId);
		StepThroughUnitOfWork unitOfWork = rSystem
				.reserveNextUnitOfWork(numberOfCombinationsToReserve);
		logger.info("Reserved unit of work (" + unitOfWork + ") for system("
				+ systemId + "):" + rSystem.getCurrentStepThrougUnitOfWork());
		
		rsystemRepository.update(rSystem); // update reservation.

		return unitOfWork;
	}

	@Override
	public void completedUnitOfWork(long systemId, long startCombination,
			long numberOfCombinationsToReserve) {
		// set the unit of work completed.
		RSystemSearchInfo rSystem = rsystemRepository.findSystem(systemId);
		rSystem.completedUnitOfWork(startCombination,
				numberOfCombinationsToReserve);

		// Update system with eventually new unit of work.
		rsystemRepository.update(rSystem);
	}

	@Override
	public void endSearchForTheSystem(long systemId) {
		// Update system with new status.
		RSystemSearchInfo rSystem = rsystemRepository.findSystem(systemId);
		rSystem.setStatus(Status.DONE);
		rsystemRepository.update(rSystem);
	}
}
