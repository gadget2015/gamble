package org.robert.sharenote.webgui.businessdelegate;

import org.robert.sharenote.webgui.businessdelegate.impl.NoteBD;
import org.robert.sharenote.webgui.businessdelegate.mock.NoteMockBD;

public class NoteBusinessDelegateFactory {
	public NoteBusinessDelegate createNoteBD(NoteBusinessType type) {
		if (NoteBusinessType.MOCK == type) {
			return new NoteMockBD();
		} else if (NoteBusinessType.RELEASE == type) {
			return new NoteBD();
		} else {
			throw new RuntimeException(
					"Invalid key given to Business Delegate factory, key = "
							+ type);
		}
	}

	/**
	 * Get delegate setup/ used by current runtime.
	 */
	public NoteBusinessDelegate getRuntimeBD() {
		return createNoteBD(NoteBusinessType.RELEASE);
	}
}
