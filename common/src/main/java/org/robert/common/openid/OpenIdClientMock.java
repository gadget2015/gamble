package org.robert.common.openid;

import java.util.List;
import java.util.Map;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;

public class OpenIdClientMock implements OpenIdClient {
	public boolean verificationCalled;
	@Override
	public boolean verifyGoogleResponse(String receivingURL,
			Map<String, String> parameters, DiscoveryInformation discoverInfo) {
		if (receivingURL.contains("fail.auth")) {
			this.verificationCalled = false;

			return false;
		} else if (receivingURL != null && parameters.isEmpty() == false
				&& discoverInfo != null) {
			this.verificationCalled = true;

			return true;
		} else {
			this.verificationCalled = false;

			return false;
		}
	}
	@Override
	public List<DiscoveryInformation> discover(String url)
			throws DiscoveryException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DiscoveryInformation associate(List<DiscoveryInformation> discoveries) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AuthRequest authenticate(DiscoveryInformation discovered,
			String returnToUrl) throws MessageException, ConsumerException {
		// TODO Auto-generated method stub
		return null;
	}

}
