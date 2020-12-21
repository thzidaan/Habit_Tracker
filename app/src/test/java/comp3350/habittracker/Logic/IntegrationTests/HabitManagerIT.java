package comp3350.habittracker.Logic.IntegrationTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Logic.HabitManager;
import comp3350.habittracker.Persistence.HSQLDB.HabitsHSQLDB;
import comp3350.habittracker.Persistence.HabitsPersistence;
import comp3350.habittracker.Utils.TestUtils;

public class HabitManagerIT {
    private File tempDB;
    private Habit habit;
    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyDB();
        HabitsPersistence habitsPersistence = new HabitsHSQLDB(tempDB.getAbsolutePath().replace(".script",""));
        new HabitManager(habitsPersistence);
        habit = new Habit("Run", 1,0, new User("userA"),"Morning", 1);
    }

    @Test
    public void testSaveNewHabit(){
        int beforeSize = HabitManager.getHabits(new User("userA")).size();
        HabitManager.saveNewHabit("new","5", new User("userA"), "Morning", 1);
        int size = HabitManager.getHabits(new User("userA")).size();

        //the size of the habits for userA should increase by one after adding
        assertEquals(beforeSize + 1, size);
    }

    @Test
    public void testEditHabit(){
        HabitManager.editHabit(habit, "run2", "1", new User("userA"), "Morning", 1);

        //no new habit has been added
        assertEquals(1, HabitManager.getHabits(new User("userA")).size());
        //name should of been updated
        assertEquals("run2", HabitManager.getHabits(new User("userA")).get(0).getHabitName());
    }

    @Test
    public void testUpdateHabit(){
        HabitManager.updateHabit(habit);

        //no new habit has been added
        assertEquals(1, HabitManager.getHabits(new User("userA")).size());
    }

    @Test
    public void testDelete(){
        HabitManager.delete(habit);

        //no habit should be left for userA
        assertEquals(0, HabitManager.getHabits(new User("userA")).size());
    }

    @Test
    public void testGetHabits(){
        //returns only the habit for userA
        assertEquals(1, HabitManager.getHabits(new User("userA")).size());
    }

    @After
    public void tearDown() {
        // reset DB
        tempDB.delete();
    }

}
