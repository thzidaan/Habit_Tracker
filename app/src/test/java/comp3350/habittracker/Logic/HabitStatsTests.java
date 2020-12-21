package comp3350.habittracker.Logic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.Note;
import comp3350.habittracker.Persistence.NotePersistence;

public class HabitStatsTests {

    private Habit habit;
    private HabitStats habitStats;

    @Before
    public void setUp(){
        //mock habit
        habit = mock(Habit.class);
        habitStats = new HabitStats(habit);
    }

    @Test
    public void testGetCompletedThisWeek(){
        when(habit.getWeeklyAmount()).thenReturn(5);
        when(habit.getCompletedWeeklyAmount()).thenReturn(5);
        assertEquals(habitStats.getCompletedThisWeek(),"5(100%)");
        //verify the called methods
        verify(habit).getWeeklyAmount();
        verify(habit).getCompletedWeeklyAmount();
    }

    @Test
    public void testGetLastCompleteDate(){
        String date = Utils.formatDate(new Date());
        when(habit.getLastCompletedDate()).thenReturn(date);
        assertEquals(habitStats.getLastCompleteDate(),date);
        //verify the call
        verify(habit).getLastCompletedDate();
    }

    @Test
    public void testGetFavDay(){
        when(habit.getDaysOfWeek()).thenReturn(new int[]{5,0,0,0,0,0,0});
        assertEquals(habitStats.getFavDay(), "Sunday");
        verify(habit).getDaysOfWeek();
    }

    @Test
    public void testGetHabitCompleted(){
        when(habit.getCompletedWeeklyAmount()).thenReturn(5);
        when(habit.getWeeklyAmount()).thenReturn(5);
        assertTrue(habitStats.isCompletedThisweek());
        //check calls
        verify(habit).getCompletedWeeklyAmount();
        verify(habit).getWeeklyAmount();
    }

    @Test
    public void testGetAvgNoteFeeling(){
        ArrayList<Note> notes = new ArrayList<>();
        Note note1 = mock(Note.class);
        Note note2 = mock(Note.class);
        notes.add(note1);
        notes.add(note2);
        when(note1.getFeeling()).thenReturn(0);
        when(note1.getFeeling()).thenReturn(2);

        NotePersistence notePersistence = mock(NotePersistence.class);
        new NotesManager(notePersistence);
        when(notePersistence.getHabitNotes(habit)).thenReturn(notes);

        assertEquals(habitStats.getAvgNoteFeeling(),"Average");

        verify(notePersistence).getHabitNotes(habit);
        verify(note1).getFeeling();
        verify(note2).getFeeling();
    }

    @Test
    public void testGetShareString(){
        ArrayList<Note> notes = new ArrayList<>();
        NotePersistence notePersistence = mock(NotePersistence.class);
        new NotesManager(notePersistence);
        when(notePersistence.getHabitNotes(habit)).thenReturn(notes);
        when(habit.getDaysOfWeek()).thenReturn(new int[]{5,0,0,0,0,0,0});

        when(habit.getHabitName()).thenReturn("hName");
        when(habit.getCompletedWeeklyAmount()).thenReturn(5);
        when(habit.getWeeklyAmount()).thenReturn(5);
        String shareString = habitStats.getShareString();

        assertTrue(shareString.contains("hName"));
        assertTrue(shareString.contains("Habit completed for this week :)"));

        verify(habit, times(2)).getCompletedWeeklyAmount();
        verify(habit, times(2)).getWeeklyAmount();
        verify(habit).getHabitName();
        verify(notePersistence).getHabitNotes(habit);
        verify(habit).getDaysOfWeek();
    }
}
