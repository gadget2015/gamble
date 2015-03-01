package org.robert.sharenote.domain.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robert.sharenote.domain.model.Note;

public class NoteRepositoryTest {
	private EntityManager em;
	private NoteRepository repository;

	@Before
	public void setUp() throws Exception {
		JPAUtility.startInMemoryDerbyDatabase();
		em = JPAUtility.createEntityManager();
		repository = new NoteRepositoryImpl(em);
	}

	@Test
	public void shouldPersistANewNote() {
		// Given
		Note aNote = new Note();
		aNote.setText("Hej hopp");

		// When
		em.getTransaction().begin();
		aNote = repository.update(aNote);
		em.getTransaction().commit();

		// Then
		aNote = em.find(Note.class, aNote.getId());
		Assert.assertTrue("Should get an Id.", (aNote.getId() > 0));
		Assert.assertEquals("Should be same text.", "Hej hopp", aNote.getText());
	}

	@Test
	public void shouldGetANote() throws NoteNotFoundException {
		// Given
		Note aNote = new Note();
		aNote.setText("Hej hopp");
		em.getTransaction().begin();
		aNote = repository.update(aNote);
		em.getTransaction().commit();

		// When
		aNote = repository.findNote(aNote.getId());

		// Then
		Assert.assertTrue("Should get an Id.", (aNote.getId() > 0));
	}

	@Test
	public void shouldUpdateNote() throws NoteNotFoundException {
		// Given
		Note aNote = new Note();
		aNote.setText("Hej hopp");
		aNote.setAdminUserId("robert.georen@gmail.com");
		em.getTransaction().begin();
		aNote = repository.update(aNote);
		em.getTransaction().commit();

		// When
		aNote.setText("Ny text");
		em.getTransaction().begin();
		aNote = repository.update(aNote);
		em.getTransaction().commit();
		aNote = repository.findNote(aNote.getId());

		// Then
		Assert.assertEquals("Should be new text.", "Ny text", aNote.getText());
		Assert.assertEquals("Should not change admin user Id",
				"robert.georen@gmail.com", aNote.getAdminUserId());
	}

	@Test
	public void shouldUpdateNoteBuNotChangeUserId()
			throws NoteNotFoundException {
		// Given
		Note aNote = new Note();
		aNote.setText("Hej hopp");
		aNote.setAdminUserId("robert.georen@gmail.com");
		em.getTransaction().begin();
		aNote = repository.update(aNote);
		em.flush();
		em.getTransaction().commit();

		// When
		Note bNote = new Note();
		bNote.setId(aNote.getId());
		bNote.setText("Ny text");
		bNote.setAdminUserId("sunge.mangs@gmail.com");
		em.getTransaction().begin();
		bNote = repository.update(bNote);
		em.getTransaction().commit();
		Note cNote = repository.findNote(aNote.getId());

		// Then
		Assert.assertEquals("Should be new text.", "Ny text", cNote.getText());
		Assert.assertEquals("Should not change admin user Id",
				"robert.georen@gmail.com", cNote.getAdminUserId());
	}

	@Test
	public void shouldNotFindNote() {
		// Given

		// When
		try {
			repository.findNote(2323);
			Assert.fail("Should throw an exception.");
		} catch (NoteNotFoundException e) {
			// Then
		}
	}

	@Test
	public void shouldSearchAndFindThreeNotes() {
		// Given
		String searchCriteria = "ote";
		List<Note> searchResult = repository
				.searchForNote(searchCriteria, null);
		int countBefore = searchResult.size();

		em.getTransaction().begin();
		repository.update(new Note(0, "Note 1"));
		repository.update(new Note(0, "Note 2"));
		repository.update(new Note(0, "Note 3"));
		em.getTransaction().commit();

		// When
		searchResult = repository.searchForNote(searchCriteria, null);

		// Then
		Assert.assertEquals("Should be 3 more in search result.",
				(countBefore + 3), searchResult.size());
	}

	@Test
	public void shouldSearchAndFindThreeNotesDueToActorIsAuthorized() {
		// Given
		String searchCriteria = "ote";
		List<Note> searchResult = repository.searchForNote(searchCriteria,
				"robert.georen@gmail.com");
		int countBefore = searchResult.size();

		em.getTransaction().begin();
		repository.update(new Note(0, "Note 1", null, false));
		repository
				.update(new Note(0, "Note 2", "robert.georen@gmail.com", true));
		repository.update(new Note(0, "Note 3", "robert.georen@gmail.com",
				false));
		em.getTransaction().commit();

		// When
		searchResult = repository.searchForNote(searchCriteria,
				"robert.georen@gmail.com");

		// Then
		Assert.assertEquals("Should be 3 more in search result.",
				(countBefore + 3), searchResult.size());
	}

	/**
	 * Should return an empty list.
	 */
	@Test
	public void shouldSearchAndNotFindAnyNotes() {
		// Given
		String searchCriteria = "not in database%";

		// When
		List<Note> searchResult = repository
				.searchForNote(searchCriteria, null);

		// Then
		Assert.assertEquals("Should be an empty search result.", true,
				searchResult.isEmpty());
	}

	@Test
	public void shouldSearchAndFindOneNote() {
		// Given
		String searchCriteria = "ote 1%";
		List<Note> searchResult = repository
				.searchForNote(searchCriteria, null);
		int countBefore = searchResult.size();

		em.getTransaction().begin();
		repository.update(new Note(0, "Note 1"));
		em.getTransaction().commit();

		// When
		searchResult = repository.searchForNote(searchCriteria, null);

		// Then
		Assert.assertEquals("Should be 1 more in search result.",
				(countBefore + 1), searchResult.size());
	}

	@Test
	public void shouldSearchAndFindOneNoteBecauseActorIsAuthorized() {
		// Given
		String searchCriteria = "ote 1%";
		List<Note> searchResult = repository.searchForNote(searchCriteria,
				"robert.georen@gmail.com");
		int countBefore = searchResult.size();

		em.getTransaction().begin();
		repository
				.update(new Note(0, "Note 1", "robert.georen@gmail.com", true));
		em.getTransaction().commit();

		// When
		searchResult = repository.searchForNote(searchCriteria,
				"robert.georen@gmail.com");

		// Then
		Assert.assertEquals("Should be 1 more in search result.",
				(countBefore + 1), searchResult.size());
	}

	@Test
	public void shouldSearchAndNotFindAnyNoteBecauseActorIsNotAuthroizhed() {
		// Given
		String searchCriteria = "ote 1%";
		List<Note> searchResult = repository.searchForNote(searchCriteria,
				"sune.mangs@hotmail.com");
		int countBefore = searchResult.size();

		em.getTransaction().begin();
		repository
				.update(new Note(0, "Note 1", "robert.georen@gmail.com", true));
		em.getTransaction().commit();

		// When
		searchResult = repository.searchForNote(searchCriteria, null);

		// Then
		Assert.assertEquals("Should NOT find any more note.", (countBefore),
				searchResult.size());
	}

	@Test
	public void shouldSearchCasInsensitiveAndFindOneNote() {
		// Given
		String searchCriteria = "OtE 1%";
		List<Note> searchResult = repository
				.searchForNote(searchCriteria, null);
		int countBefore = searchResult.size();

		em.getTransaction().begin();
		repository.update(new Note(0, "Note 1"));
		em.getTransaction().commit();

		// When
		searchResult = repository.searchForNote(searchCriteria, null);

		// Then
		Assert.assertEquals("Should be 1 more in search result.",
				(countBefore + 1), searchResult.size());
	}
}
