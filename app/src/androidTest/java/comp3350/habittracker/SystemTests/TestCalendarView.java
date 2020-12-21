package comp3350.habittracker.SystemTests;

import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.habittracker.Application.Services;
import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Persistence.HabitsPersistence;
import comp3350.habittracker.Presentation.LoginActivity;
import comp3350.habittracker.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestCalendarView {
    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp(){
        activityTestRule.finishActivity();
        //remove stored login info
        SystemTestUtils.cleanUp();
        //cache login, so it can auto login
        SystemTestUtils.setAccount("userA", "pass");

        HabitsPersistence habitsPersistence = Services.getHabitsPersistence();
        //add habits
        habitsPersistence.addHabit(new Habit("num0",1,0,new User("userA"),"Morning",0));
        habitsPersistence.addHabit(new Habit("num1",5,0,new User("userA"),"Morning",0));
        activityTestRule.launchActivity(new Intent());
    }

    @After
    public void tearDown(){
        SystemTestUtils.tearDown();
    }

    @Test
    public void testDisplay(){

        //both of habits are shown
        onView(withText("num0")).check(matches(isDisplayed()));
        onView(withText("num1")).check(matches(isDisplayed()));
    }

    @Test
    public void testCompleteHabit(){
        onView(withText("num1")).perform(click());
        //don't add note
        onView(withText("NO")).perform(click());
        //make sure habit isn't listed in the list anymore
        onView(withText("num1")).check(doesNotExist());
    }

    @Test
    public void testCompleteWithNote(){
        onView(withText("num0")).perform(click());
        //add note
        onView(withText("ADD NOTE")).perform(click());
        //enter note name
        onView(ViewMatchers.withId(R.id.etWriteNotes)).perform(typeText("complete note1"));
        //select feeling option
        onView(withId(R.id.radio_bad)).perform(click());
        Espresso.closeSoftKeyboard(); //close keyboard
        //click button update
        onView(withId(R.id.btnUpdateNote)).perform(click());
        //make sure habit isn't listed in the list anymore
        onView(withText("num0")).check(doesNotExist());
    }
}
