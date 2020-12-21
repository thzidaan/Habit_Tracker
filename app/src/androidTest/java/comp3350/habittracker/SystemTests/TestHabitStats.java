package comp3350.habittracker.SystemTests;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import comp3350.habittracker.Application.Services;
import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Logic.Utils;
import comp3350.habittracker.Persistence.HabitsPersistence;
import comp3350.habittracker.Presentation.LoginActivity;

//test being able to view stats
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestHabitStats {
    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp(){
        activityTestRule.finishActivity();
        //remove stored login info
        SystemTestUtils.cleanUp();
        //cache login, so it can auto login
        SystemTestUtils.setAccount("userA", "pass");

        //make sure atleast one habit is in the db
        Habit habit = new Habit("statHabit",7,3,new User("userA"),"Morning",0);
        habit.complete();
        HabitsPersistence habitsPersistence = Services.getHabitsPersistence();
        habitsPersistence.addHabit(habit);
        activityTestRule.launchActivity(new Intent());
    }

    @After
    public void tearDown(){
        SystemTestUtils.tearDown();
    }

    @Test
    public void testHabitStats(){
        //open top right menu
        openActionBarOverflowOrOptionsMenu(getApplicationContext());
        //press habits
        onView(withText("Habits")).perform(click());
        //click the desired habit
        onView(withText("statHabit")).perform(click());
        //assert stats
        onView(withText(Utils.formatDate(new Date()))).check(matches(isDisplayed()));
        onView(withText(day())).check(matches(isDisplayed()));
    }

    private String day(){
        String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return days[day-1];
    }
}
