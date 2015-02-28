package org.robert.tipsbolag.webgui.businessdelegate;

import org.robert.tipsbolag.webgui.businessdelegate.mock.SpelbolagBusinessDelegateMock;

public class SpelbolagBusinessDelegateFactory {
	public SpelbolagBusinessDelegate createNoteBD(SpelbolagBusinessType type) {
		if (SpelbolagBusinessType.MOCK == type) {
			return new SpelbolagBusinessDelegateMock();
		} else if (SpelbolagBusinessType.RELEASE == type) {
			return null;
		} else {
			throw new RuntimeException(
					"Invalid key given to Business Delegate factory, key = "
							+ type);
		}
	}

	/**
	 * Get delegate setup/ used by current runtime.
	 */
	public SpelbolagBusinessDelegate getRuntimeBD() {
		return createNoteBD(SpelbolagBusinessType.MOCK);
	}
}
