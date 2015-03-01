package org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd;

import java.net.URL;

import javax.xml.namespace.QName;

import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.StryktipsFinderServiceImpl;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.StryktipsFinderServiceImplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service Locator. Pattern: J2EE - ServiceLocator.
 * 
 * @author Robert Georen.
 * 
 */

public class ServiceLocator {
	private Logger logger = LoggerFactory.getLogger(ServiceLocator.class
			.getName());
	private String SEI = "http://46.137.165.190/StryktipsFinderServiceImplService/StryktipsFinderServiceImpl?wsdl";

	/**
	 * Creates a new service locator.
	 */
	public ServiceLocator() {
	}

	/**
	 * Get the Activity webservice endpoint. Connect to URL in SEI constant.
	 * 
	 * @return SEI for Activity service.
	 * @throws ServiceLocatorException
	 */
	public StryktipsFinderServiceImpl getActivityService()
			throws ServiceLocatorException {
		try {
			// Get service reference.

			URL wsdl = new URL(SEI);
			logger.debug("WSDL location for Service: " + wsdl.toString());

			QName qname = new QName(
					"http://service.stryktipsfinder.tips.robert.org/",
					"StryktipsFinderServiceImplService");

			StryktipsFinderServiceImplService service = new StryktipsFinderServiceImplService(
					wsdl, qname);

			// Get port
			StryktipsFinderServiceImpl endpointIF = service
					.getStryktipsFinderServiceImplPort();

			return endpointIF;
		} catch (Throwable e) {
			logger.error("ServiceLocator error" + e.getMessage(), e);
			throw new ServiceLocatorException(
					"Error while creating webservice endpoint proxy for the StryktipsFinderServiceImpl endpoint.",
					e);
		}
	}

}
