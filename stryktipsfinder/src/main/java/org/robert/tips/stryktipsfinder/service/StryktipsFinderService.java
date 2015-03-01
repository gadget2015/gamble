package org.robert.tips.stryktipsfinder.service;

import javax.jws.WebMethod;

import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;
import org.robert.tips.stryktipsfinder.domain.model.StepThroughUnitOfWork;

/**
 * Defines operations on the stryktips finder serivce.
 * 
 * @author Robert.
 * 
 */
public interface StryktipsFinderService {
	/**
	 * Get a system information object from server but only with the latest
	 * system. This because it will be too much data to transfer otherwise.
	 * 
	 * @param id
	 *            in database.
	 * @return info object if found otherwise an empty info object.
	 */
	@WebMethod
	RSystemSearchInfo getSystemInfoWithOnlyLatestSystem(long id);

	/**
	 * Send a found system to server. Only send the latest system, e.g not the
	 * previous ones.
	 * 
	 * @throws NoSystemFoundException
	 */
	@WebMethod
	void sendSystemToServer(RSystemSearchInfo foundSystem)
			throws NoSystemFoundException;

	/**
	 * Inform server that the client has performed x number of tests for the
	 * given system id.
	 * 
	 * @param id
	 * @param numberOfTests
	 * @throws NoSystemFoundException
	 */
	@WebMethod
	void informServerNumberOfTestRuns(long id, int numberOfTests)
			throws NoSystemFoundException;

	/**
	 * Reserve a unit of work that is used in the step through algorithm.
	 * 
	 * @param systemId
	 *            for the system to reserve a unit of work on.
	 * @param numberOfCombinationsToReserve
	 */
	@WebMethod
	public StepThroughUnitOfWork reserveNextUnitOfWork(long systemId,
			long numberOfCombinationsToReserve);

	/**
	 * Inform that the given unit of work have been tested.
	 */
	@WebMethod
	public void completedUnitOfWork(long systemId, long startCombination,
			long numberOfCombinationsToReserve);

	/**
	 * Change status to search completed for the given system.
	 */
	@WebMethod
	public void endSearchForTheSystem(long systemId);
}
