package org.robert.sharenote.domain.infrastructure;

import java.util.List;

import javax.ejb.Local;

import org.robert.sharenote.domain.model.Note;

/**
 * Repository for working with the RSystem aggregate root.
 * 
 * @author Robert
 * 
 */
@Local
public interface NoteRepository {
	/**
	 * Update a Note, but not the admin user Id if there exist one.
	 */
	Note update(Note aNote);

	/**
	 * 
	 * @param id
	 *            in database.
	 */
	Note findNote(long id) throws NoteNotFoundException;

	/**
	 * 
	 * @param userId
	 *            that performs the search.
	 */
	List<Note> searchForNote(String searchCriteria, String userId);
}
