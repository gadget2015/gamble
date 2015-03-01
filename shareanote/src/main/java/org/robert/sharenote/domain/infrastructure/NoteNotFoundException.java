package org.robert.sharenote.domain.infrastructure;

public class NoteNotFoundException extends Exception {

	public NoteNotFoundException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
