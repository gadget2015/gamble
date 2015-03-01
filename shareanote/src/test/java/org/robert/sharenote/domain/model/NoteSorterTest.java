package org.robert.sharenote.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class NoteSorterTest {
	@Test
	public void shouldCompareThreeNotes() {
		// Given
		List<Note> notes = new ArrayList<Note>();
		notes.add(new Note(8774, "rob"));
		notes.add(new Note(1, "hhh"));
		notes.add(new Note(2, "323"));

		// When
		Collections.sort(notes, new NoteComparator());
		
		// Then
		Assert.assertEquals(8774, notes.get(0).getId());
		Assert.assertEquals(2, notes.get(1).getId());
		Assert.assertEquals(1, notes.get(2).getId());
	}
	
	@Test
	public void shouldCompareFourNotes() {
		// Given
		List<Note> notes = new ArrayList<Note>();
		notes.add(new Note(1, "rob"));
		notes.add(new Note(2, "hhh"));
		notes.add(new Note(8989, "323"));
		notes.add(new Note(3, "323"));

		// When
		Collections.sort(notes, new NoteComparator());
		
		// Then
		Assert.assertEquals(8989, notes.get(0).getId());
		Assert.assertEquals(3, notes.get(1).getId());
		Assert.assertEquals(2, notes.get(2).getId());
		Assert.assertEquals(1, notes.get(3).getId());
	}
	
	@Test
	public void shouldBeTwoEqualNotes() {
		// Given
		List<Note> notes = new ArrayList<Note>();
		notes.add(new Note(2, "rob"));
		notes.add(new Note(2, "hhh"));

		// When
		Collections.sort(notes, new NoteComparator());
		
		// Then
		Assert.assertEquals(2, notes.get(0).getId());
		Assert.assertEquals(2, notes.get(1).getId());
	}
}
