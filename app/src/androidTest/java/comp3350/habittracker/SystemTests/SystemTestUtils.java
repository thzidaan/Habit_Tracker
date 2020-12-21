package comp3350.habittracker.SystemTests;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import comp3350.habittracker.Application.Services;
import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Persistence.HabitsPersistence;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

public class SystemTestUtils {

    //remove stored account info, so app launches to the login screen
    public static void cleanUp(){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("account", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    //store login info, so app bypass login screen on launch
    public static void setAccount(String username, String pass){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("account", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username);
        editor.putString("password", pass);
        editor.commit();
    }

    //clear db for test user
    public static void tearDown(){
        HabitsPersistence habitsPersistence = Services.getHabitsPersistence();
        ArrayList<Habit> habitArrayList = habitsPersistence.getUserHabits(new User("userA"));
        for(int i = 0; i < habitArrayList.size(); i++){
            Habit habit = habitArrayList.get(i);
            habitsPersistence.deleteHabit(habit);
        }
    }
}
