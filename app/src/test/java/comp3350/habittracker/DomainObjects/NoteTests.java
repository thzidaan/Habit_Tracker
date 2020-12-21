package comp3350.habittracker.DomainObjects;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NoteTests {

    @Test
    public void testEquals(){
        User user = new User("userA");
        Habit habit = new Habit("habit1",2,1,user,"Morning",1);
        Habit diffHabit = new Habit("habit2", 2,0,user,"Morning",1);

        Note note = new Note("note", 1, "date", habit);
        Note note1 = new Note("note", 1, "date", diffHabit);
        Note sameNote = new Note("note", 1, "date", habit);

        //notes are same if they have same text and habit
        assertEquals(note,sameNote);
        //same text, but different habits
        assertNotEquals(note,note1);
    }
}
