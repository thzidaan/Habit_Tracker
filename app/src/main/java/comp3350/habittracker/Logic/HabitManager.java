package comp3350.habittracker.Logic;

import java.util.ArrayList;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Persistence.HabitsPersistence;
import comp3350.habittracker.Persistence.Stub.HabitsStub;

//manage habits
//add, remove, edit
public class HabitManager {

    private static HabitsPersistence habitsPersistence;

    public HabitManager(HabitsPersistence db){
        habitsPersistence = db;
    }

    /*
     * saveNewHabit
     * return true if the habit was saved
     *
     * Input: habit info
     */
    public static boolean saveNewHabit(String name, String timesPerWeek, User user, String timeOfDay, int timeAssoc){
        boolean returnValue = false;
        //timesPerWeek = "# times per week"
        //parse the first char into an int
        int perWeek = Integer.parseInt(timesPerWeek.substring(0,1));
        //if a habit name exists
        if(name.length() > 0){
            Habit habit = new Habit(name,perWeek,0,user, timeOfDay, timeAssoc);
            returnValue = habitsPersistence.addHabit(habit);
        }
        return returnValue;
    }

    //write to habitsPersistence
    public static boolean editHabit(Habit oldHabit,String name, String timesPerWeek, User user, String timeOfDay, int timeAssoc){
        boolean returnValue = false;
        int perWeek = Integer.parseInt(timesPerWeek.substring(0,1));
        if(name.length() > 0){
            Habit habit = new Habit(name,perWeek,oldHabit.getCompletedWeeklyAmount(),user, timeOfDay, timeAssoc);
            returnValue = habitsPersistence.edit(oldHabit,habit);
        }
        return returnValue;
    }

    public static boolean updateHabit(Habit habit){
        return habitsPersistence.update(habit);
    }

    public static void delete(Habit habit){
        habitsPersistence.deleteHabit(habit);
    }

    //return all habits created by a user
    public static ArrayList<Habit> getHabits(User user){
        return habitsPersistence.getUserHabits(user);
    }

    //temp database just for tests
    public void setupTest(ArrayList<Habit> testHabits){
        HabitsStub stub = (HabitsStub) habitsPersistence;
        stub.setTestList(testHabits);
    }

}
