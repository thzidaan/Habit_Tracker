package comp3350.habittracker.Logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Utils.TestUtils;

public class HabitDateValidatorTests {

    private SimpleDateFormat formatter;
    private Habit completeHabit;
    private Habit incompleteHabit;
    @Before
    public void setUp(){
        formatter = new SimpleDateFormat("dd/MM/yyyy");

        User user = new User("userA");
        completeHabit = new Habit("run", 2,2,user,"Morning",1);
        incompleteHabit = new Habit("walk", 2,1,user,"Morning",1);
    }

    @Test
    public void testIsCompleted()throws ParseException{
        String date = formatter.format(new Date());
        //return true, since habit has been completed for current week
        assertTrue("should return true, since habit has been completed for current week",HabitDateValidator.isCompleted(completeHabit,date));

        //return false, since the habit hasn't been completed for current week
        assertFalse("should be false, since habit isn't completed",HabitDateValidator.isCompleted(incompleteHabit,date));

        date = formatter.format(TestUtils.addDaysToDate(7)); //next week date
        //return false, since the completedhabit is only completed for one week at a time
        assertFalse("should return false, since habit isn't completed for next week", HabitDateValidator.isCompleted(completeHabit,date));
    }

    @Test
    public void testUpdateCompletedAmount()throws ParseException{
        HabitDateValidator.updateCompletedAmount(incompleteHabit);
        //habit completed amount doesn't reset since its still current week
        assertEquals("completed amount doesn't change since its still the same week",1,incompleteHabit.getCompletedWeeklyAmount());
    }

}
