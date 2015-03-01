package org.robert.sharenote.domain.infrastructure;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.robert.sharenote.domain.model.Note;

/**
 * JPA implementation of repository.
 * 
 * @author Robert
 * 
 */
@Stateless
public class NoteRepositoryImpl implements NoteRepository {
	@PersistenceContext(unitName = "note-unit")
	private EntityManager em;

	/**
	 * EJB 3.0 specification states the need of default constructor.
	 * 
	 */
	public NoteRepositoryImpl() {
	}

	/**
	 * Used by unit tests.
	 * 
	 * @param em
	 *            EntityManager.
	 */
	public NoteRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public Note update(Note noteToUpdate) {
		// 1. Don overwrite admin user id.
		try {
			Note storedNote = findNote(noteToUpdate.getId());
			if (storedNote.getAdminUserId() != null && noteToUpdate != null) {
				noteToUpdate.setAdminUserId(storedNote.getAdminUserId());
			}
		} catch (NoteNotFoundException e) {
			// this is ok, because new notes doesn't have an Id.
		}

		// 2. Update database.
		noteToUpdate = em.merge(noteToUpdate);

		return noteToUpdate;
	}

	@Override
	public Note findNote(long id) throws NoteNotFoundException {
		Note note = em.find(Note.class, id);

		if (note == null) {
			throw new NoteNotFoundException(
					"Can't find Note with given database id, Id = " + id);
		}

		return note;
	}

	@Override
	public List<Note> searchForNote(String searchCriteria, String userId) {
		// make search criteria lowercase so all matches can be found.
		searchCriteria = searchCriteria.toLowerCase();

		// prefix and sufix with wildcard
		String WILDCARD = "%";
		if (searchCriteria.startsWith(WILDCARD) == false) {
			searchCriteria = WILDCARD + searchCriteria;
		}

		if (searchCriteria.endsWith(WILDCARD) == false) {
			searchCriteria += WILDCARD;
		}

		// Perform search query.
		Query query = em
				.createNamedQuery(InfrastructureNamedQuery.SEARCH_FOR_NOTE);
		query.setParameter("searchCriteria", searchCriteria);
		query.setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<Note> searchResult = (List<Note>) query.getResultList();

		return searchResult;
	}
}
