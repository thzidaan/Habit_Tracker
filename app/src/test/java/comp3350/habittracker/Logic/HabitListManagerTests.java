package comp3350.habittracker.Logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Persistence.Stub.HabitsStub;
import comp3350.habittracker.Utils.TestUtils;

public class HabitListManagerTests {

    private SimpleDateFormat formatter;
    private HabitListManager habitListManager;
    private ArrayList<Habit> habits;

    @Before
    public void setUp() throws ParseException {
        HabitManager hm = new HabitManager(new HabitsStub()); //populate stub database
        User user = new User("userA");
        habits = TestUtils.createDB(user);
        hm.setupTest(habits);
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        habitListManager = new HabitListManager(user);
    }

    @Test
    public void testGetUncompletedHabits()throws ParseException{
        //no habits currently completed, return all habits
        assertEquals(5,habitListManager.getUncompletedHabits(formatter.format(new Date())).size());

        habits.get(0).complete(); //complete one habit
        //one habit has been completed this week
        assertEquals("one habit has been completed, size should be 4",4,habitListManager.getUncompletedHabits(formatter.format(new Date())).size());
        //all habits are uncompleted for next week
        assertEquals("no habits completed next week",5,habitListManager.getUncompletedHabits(formatter.format(TestUtils.addDaysToDate(7))).size());
    }

    @Test
    public void testGetHabitNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("h0");
        names.add("h1");
        names.add("h2");
        names.add("h3");
        names.add("h4");
        assertEquals("name of all the habits (h1,h2,h3,h4,h5)", habitListManager.getHabitNames(habits),names);
    }

    @Test
    public void testCompleteHabit()throws ParseException{
        //complete h1 habit
        habitListManager.completeHabit("h4");
        //habit is completed
        assertTrue("h4 should be completed",habits.get(4).isCompleted(formatter.format(new Date())));
    }

    @Test
    public void testGetHabit(){
        //return habit based on name
        assertEquals("return H0 habit",habits.get(0),habitListManager.getHabit("h0"));
    }

}
