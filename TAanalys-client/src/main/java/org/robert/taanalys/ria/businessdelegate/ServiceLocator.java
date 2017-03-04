package org.robert.taanalys.ria.businessdelegate;

import java.net.URL;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import javax.xml.ws.BindingProvider;

/**
 * Service Locator. Pattern: J2EE - ServiceLocator.
 * 
 * @author Robert Georen.
 * 
 */

public class ServiceLocator {
	private Logger logger = LoggerFactory.getLogger(ServiceLocator.class
			.getName());
	//private String SEI = "http://localhost:80/taserver/webservices/AnalysServiceImpl?wsdl";
	private String SEI = "http://www.noterepo.com/taserver/webservices/AnalysServiceImpl?wsdl";
	/**
	 * Creates a new service locator.
	 */
	public ServiceLocator() {
	}

	/**
	 * Get the webservice endpoint. Connect to URL in SEI constant.
	 * 
	 * @return SEI for Activity service.
	 * @throws ServiceLocatorException
	 */
	public AnalysService getActivityService()
			throws ServiceLocatorException {
		try {
			// Get service reference.
			URL wsdl = new URL(SEI);
			logger.debug("WSDL location for Service: " + wsdl.toString());

			QName qname = new QName("http://service.taanalys.robert.org/",
					"AnalysService");

			AnalysService_Service service = new AnalysService_Service(wsdl, qname);
			
			// Get port
			AnalysService endpointIF = service.getAnalysServicePort();
			
			// Det tar en stund att räkna fram gaferna för flera år
			Map<String, Object> requestContext = ((BindingProvider)endpointIF).getRequestContext();
			requestContext.put("com.sun.xml.internal.ws.request.timeout", 3600000); // Timeout in millis, 60 minuter
			requestContext.put("com.sun.xml.internal.ws.connect.timeout", 3000000); // Timeout in millis, 60 minuter

			
			return endpointIF;
		} catch (Throwable e) {
			logger.error("ServiceLocator error" + e.getMessage(), e);
			throw new ServiceLocatorException(
					"Error while creating webservice endpoint proxy for the AnalysService endpoint.",
					e);
		}
	}

}
