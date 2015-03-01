package org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.RSystemSearchInfo;

/**
 * Business delegate for calling the server.
 * 
 * @author Robert.
 * 
 */
public interface StryktipsFinderBD {
	/**
	 * Get a system information object from server but only with the latest
	 * system. This because it will be too much data to transfer otherwise.
	 * 
	 * @param id
	 *            in database.
	 */
	RSystemSearchInfo getSystemInfoWithOnlyLatestSystem(long id)
			throws TechnicalException;

	/**
	 * Send a found system to server. Only send the latest system, e.g not the
	 * previous ones.
	 */
	void sendSystemToServer(RSystemSearchInfo foundSystem)
			throws TechnicalException, DomainException;

	/**
	 * Inform server that the client has performed x number of tests for the
	 * given system id.
	 * 
	 * @param id
	 * @param numberOfTests
	 */
	void informServerNumberOfTestRuns(long id, int numberOfTests)
			throws TechnicalException, DomainException;

	/**
	 * Used by StepThrough algorithm.
	 * 
	 * @param systemAtServerId
	 *            as database Id.
	 * @return -1 if no more search need to be performed, e.g all combinations has been tested.
	 */
	long reserveNextUnitOfWork(long systemAtServerId, int numberOfRowsToReserve)
			throws TechnicalException;

	/**
	 * Inform that the given unit of work have been tested.
	 */
	public void completedUnitOfWork(long systemId, long startCombination,
			long numberOfCombinationsToReserve) throws TechnicalException;
}
