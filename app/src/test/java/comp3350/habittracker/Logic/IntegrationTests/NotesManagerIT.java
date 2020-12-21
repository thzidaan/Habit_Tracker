package comp3350.habittracker.Logic.IntegrationTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.Note;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Logic.NotesManager;
import comp3350.habittracker.Logic.Utils;
import comp3350.habittracker.Persistence.HSQLDB.NotesHSQLDB;
import comp3350.habittracker.Persistence.NotePersistence;
import comp3350.habittracker.Utils.TestUtils;

public class NotesManagerIT {
    private File tempDB;
    private Habit habit;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyDB();
        NotePersistence notePersistence = new NotesHSQLDB(tempDB.getAbsolutePath().replace(".script",""));
        new NotesManager(notePersistence);
        habit = new Habit("Workout", 1,0,new User("userB"), "Morning", 1);
    }

    @Test
    public void testGetNotes(){
        //should have 2 notes from userB for the "workout" habit
        assertEquals(2, NotesManager.getNotes(habit).size());
    }

    @Test
    public void testNoteByContents(){
        Note note = NotesManager.getNoteByContents(habit, "note1");
        //name of the note user should equal name of habit user
        assertEquals("userB", note.getHabit().getUser().getUsername());

        //make sure right note object is returned
        assertEquals("note1", note.getNoteText());
    }

    @Test
    public void testSaveNewNote(){
        NotesManager.saveNewNote("note3", 1, Utils.formatDate(new Date()), habit);

        //new note was added
        assertEquals(3, NotesManager.getNotes(habit).size());
    }

    @Test
    public void testUpdateNote(){
        Note oldNote = new Note("note1", 2, "'04/03/2020'", habit);
        NotesManager.updateNote(oldNote, "updatenote", 1, "'04/03/2020'");
        oldNote = NotesManager.getNoteByContents(habit, "updatenote");
        //updated note exists
        assertNotEquals(null, oldNote);
    }

    @Test
    public void testDeleteNote(){
        Note note = new Note("note1", 2, "'04/03/2020'", habit);
        NotesManager.deleteNote(note);

        //only one note left for "workout" habit
        assertEquals(1,NotesManager.getNotes(habit).size());
    }

    @After
    public void tearDown() {
        // reset DB
        tempDB.delete();
    }
}
