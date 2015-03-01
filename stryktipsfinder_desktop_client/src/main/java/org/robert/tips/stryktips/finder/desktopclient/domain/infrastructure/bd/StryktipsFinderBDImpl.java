package org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.NoSystemFoundException_Exception;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.RSystemSearchInfo;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.StepThroughUnitOfWork;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.StepThroughUnitOfWorkStatus;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.StryktipsFinderServiceImpl;

public class StryktipsFinderBDImpl implements StryktipsFinderBD {

	@Override
	public RSystemSearchInfo getSystemInfoWithOnlyLatestSystem(long id)
			throws TechnicalException {
		ServiceLocator serviceLocator = new ServiceLocator();
		try {
			StryktipsFinderServiceImpl endpoint = serviceLocator
					.getActivityService();

			return endpoint.getSystemInfoWithOnlyLatestSystem(id);
		} catch (ServiceLocatorException e) {
			throw new TechnicalException(
					"Error while looking for host to connect to: "
							+ e.getMessage());
		}
	}

	@Override
	public void sendSystemToServer(RSystemSearchInfo foundSystem)
			throws TechnicalException, DomainException {
		ServiceLocator serviceLocator = new ServiceLocator();
		try {
			StryktipsFinderServiceImpl endpoint = serviceLocator
					.getActivityService();

			endpoint.sendSystemToServer(foundSystem);
		} catch (ServiceLocatorException e) {
			throw new TechnicalException(
					"Error while looking for host to connect to: "
							+ e.getMessage());
		} catch (NoSystemFoundException_Exception e) {
			throw new DomainException(e.getMessage());
		}

	}

	@Override
	public void informServerNumberOfTestRuns(long id, int numberOfTests)
			throws TechnicalException, DomainException {
		ServiceLocator serviceLocator = new ServiceLocator();
		try {
			StryktipsFinderServiceImpl endpoint = serviceLocator
					.getActivityService();

			endpoint.informServerNumberOfTestRuns(id, numberOfTests);
		} catch (ServiceLocatorException e) {
			throw new TechnicalException(
					"Error while looking for host to connect to: "
							+ e.getMessage());
		} catch (NoSystemFoundException_Exception e) {
			throw new DomainException(e.getMessage());
		}
	}

	@Override
	public long reserveNextUnitOfWork(long systemAtServerId,
			int numberOfRowsToReserve) throws TechnicalException {
		ServiceLocator serviceLocator = new ServiceLocator();

		try {
			StryktipsFinderServiceImpl endpoint = serviceLocator
					.getActivityService();

			StepThroughUnitOfWork unitOfWork = endpoint.reserveNextUnitOfWork(
					systemAtServerId, numberOfRowsToReserve);

			if (unitOfWork.getStatus() == StepThroughUnitOfWorkStatus.COMPLETED) {
				return -1;
			} else {
				return unitOfWork.getStartCombination();
			}
		} catch (ServiceLocatorException e) {
			throw new TechnicalException(
					"Error while looking for host to connect to: "
							+ e.getMessage());
		}
	}

	@Override
	public void completedUnitOfWork(long systemId, long startCombination,
			long numberOfCombinationsToReserve) throws TechnicalException {
		ServiceLocator serviceLocator = new ServiceLocator();

		try {
			StryktipsFinderServiceImpl endpoint = serviceLocator
					.getActivityService();

			endpoint.completedUnitOfWork(systemId, startCombination,
					numberOfCombinationsToReserve);
		} catch (ServiceLocatorException e) {
			throw new TechnicalException(
					"Error while looking for host to connect to: "
							+ e.getMessage());
		}
	}

}
