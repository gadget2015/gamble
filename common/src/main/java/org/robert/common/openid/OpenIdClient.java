package org.robert.common.openid;

import java.util.List;
import java.util.Map;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;

/**
 * Kapslar in OpenId4Java klienten.
 */
public interface OpenIdClient {
	boolean verifyGoogleResponse(String receivingURL,
			Map<String, String> parameters, DiscoveryInformation discoverInfo);

	List<DiscoveryInformation> discover(String url) throws DiscoveryException;

	DiscoveryInformation associate(List<DiscoveryInformation> discoveries);

	AuthRequest authenticate(DiscoveryInformation discovered, String returnToUrl)
			throws MessageException, ConsumerException;
}
