package org.robert.common.openid;

import java.util.List;
import java.util.Map;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;

public class OpenIdClientImpl implements OpenIdClient {
	static String HTTPS_WWW_GOOGLE_COM_ACCOUNTS_O8_ID = "https://www.google.com/accounts/o8/id";
	static ConsumerManager openIdClient;

	public OpenIdClientImpl() {
		if (openIdClient == null) {
			openIdClient = new ConsumerManager();
		}
	}

	@Override
	public boolean verifyGoogleResponse(String receivingURL,
			Map<String, String> parameters, DiscoveryInformation discoverInfo) {
		ParameterList params = new ParameterList(parameters);

		try {
			VerificationResult result = openIdClient.verify(receivingURL,
					params, discoverInfo);
			
			// Verify result
			Identifier code = result.getVerifiedId();
			if (code == null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// An verification exception has occurred.
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiscoveryInformation> discover(String identifier)
			throws DiscoveryException {
		return (List<DiscoveryInformation>) (openIdClient.discover(identifier));
	}

	@Override
	public DiscoveryInformation associate(List<DiscoveryInformation> discoveries) {
		return openIdClient.associate(discoveries);
	}

	@Override
	public AuthRequest authenticate(DiscoveryInformation discovered,
			String returnToUrl) throws MessageException, ConsumerException {
		return openIdClient.authenticate(discovered, returnToUrl);
	}

}
