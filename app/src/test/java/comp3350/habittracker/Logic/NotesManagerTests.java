package comp3350.habittracker.Logic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.Note;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Persistence.Stub.NotesStub;
import comp3350.habittracker.Utils.TestUtils;

public class NotesManagerTests {
    private ArrayList<Note> notes;
    private Habit habit1;
    private Habit habit2;

    @Before
    public void setUp(){
        habit1 = new Habit("run", 1, 0, new User("userA"), "Morning", 1);
        habit2 = new Habit("read", 1, 0, new User("userA"), "Morning", 1);

        //add 4 notes for habit 1
        //add 2 notes for habit 2
        notes = TestUtils.createNoteDB(habit1, habit2);
        new NotesManager(new NotesStub()).setupTest(notes);
    }

    @Test
    public void testGetNoteText(){
        ArrayList<String> names = new ArrayList<>();
        names.add("H2note0");
        names.add("H2note1");

        //all the text of notes for habit2
        assertEquals(NotesManager.getNoteText(NotesManager.getNotes(habit2)), names);
    }

    @Test
    public void testSaveNewNote(){
        //unable to add the new note since its a note with his text already exists
        assertFalse(NotesManager.saveNewNote("H2note0", 1, Utils.formatDate(new Date()), habit2));
        assertEquals(2, NotesManager.getNotes(habit2).size());

        //unable to add empty text as a note
        assertFalse(NotesManager.saveNewNote("", 1, Utils.formatDate(new Date()), habit2));
        assertEquals(2, NotesManager.getNotes(habit2).size());

        //new note was added
        assertTrue(NotesManager.saveNewNote("notenote", 1, Utils.formatDate(new Date()), habit2));
        assertEquals(3, NotesManager.getNotes(habit2).size());
    }

    @Test
    public void testUpdateNote(){
        Note note = new Note("H2note0", 1, Utils.formatDate(new Date()), habit2);

        assertTrue(NotesManager.updateNote(note, "UPDATED", 1, Utils.formatDate(new Date())));
        //no new note was added
        assertEquals(2, NotesManager.getNotes(habit2).size());
        //value was updated
        assertEquals("UPDATED", NotesManager.getNoteByContents(habit2,"UPDATED").getNoteText());


    }


}
