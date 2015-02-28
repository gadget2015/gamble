package org.robert.tipsbolag.webgui.businessdelegate;

import org.openid4java.discovery.DiscoveryInformation;

public class AuthenticationResponse {
	public String authenticationRequest;
	public DiscoveryInformation discoveryInformation;

	/**
	 * An assoc_handle look like this:
	 * AMlYA9U-ieXoPz_RV1ysVoEDS5YKmgSyVFe9vCucMfryv6EvT-lF6M0bAc917xUunjj72Doo
	 */
	public String getOpenidAssocHandle() {
		return getOpenidAssocHandle(authenticationRequest);
	}

	public static String getOpenidAssocHandle(String url) {
		int startPos = url.indexOf("assoc_handle") + 13;
		int endPos = url.indexOf("&openid", startPos);
		String assoc_handle = url.substring(startPos, endPos);

		return assoc_handle;

	}

}
