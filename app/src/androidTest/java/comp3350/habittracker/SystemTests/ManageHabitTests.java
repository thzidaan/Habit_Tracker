package comp3350.habittracker.SystemTests;


import android.content.Intent;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

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

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
//system tests for adding, deleting and editing habits
public class ManageHabitTests {
    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp(){
        activityTestRule.finishActivity();
        //remove stored login info
        SystemTestUtils.cleanUp();
        //cache login, so it can auto login
        SystemTestUtils.setAccount("userA", "pass");
        //remove instance of habit from db
        HabitsPersistence habitsPersistence = Services.getHabitsPersistence();
        //add habits to edit and delete
        habitsPersistence.addHabit(new Habit("EDIT",1,0,new User("userA"),"Morning",0));
        habitsPersistence.addHabit(new Habit("DELETE",1,0,new User("userA"),"Morning",0));
        activityTestRule.launchActivity(new Intent());
    }

    @After
    public void tearDown(){
        SystemTestUtils.tearDown();
    }

    @Test
    public void testAddHabit(){
        //click add habit button
        onView(ViewMatchers.withId(R.id.btnAddHabit)).perform(click());
        //type habit name
        onView(withId(R.id.txtHabitName)).perform(typeText("addHabit"));
        //click add habit button
        onView(withId(R.id.btnAddHabit)).perform(click());
        //check activity to make sure it contains new habit text
        onView(withText("addHabit")).check(matches(isDisplayed()));
    }

    @Test
    public void testEditHabit(){
        //open top right menu
        openActionBarOverflowOrOptionsMenu(getApplicationContext());
        //press habits
        onView(withText("Habits")).perform(click());
        //press the created habit
        onView(withText("EDIT")).perform(click());

        //without this delay the next actionbar menu doesn't open for some reason
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        //open top right menu
        openActionBarOverflowOrOptionsMenu(getApplicationContext());
        //press Edit Habit
        onView(withText("Edit Habit")).perform(click());
        //enter text
        onView(withId(R.id.txtHabitName)).perform(clearText()).perform(typeText("editHabit"));
        //click add habit
        onView(withId(R.id.btnAddHabit)).perform(click());
        //check activity to make sure it contains new habit text
        onView(withText("editHabit")).check(matches(isDisplayed()));
        onView(withText("EDIT")).check(doesNotExist());
    }

    @Test
    public void testHabitDelete(){
        //add habit to be edited

        //open top right menu
        openActionBarOverflowOrOptionsMenu(getApplicationContext());
        //press habits
        onView(withText("Habits")).perform(click());
        //press the created habit
        onView(withText("DELETE")).perform(click());

        //without this delay the next actionbar menu doesn't open for some reason
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        //open top right menu
        openActionBarOverflowOrOptionsMenu(getApplicationContext());
        //press Delete Habit
        onView(withText("Delete Habit")).perform(click());
        //press yes
        onView(withText("YES")).perform(click());
        //make sure habit doesn't exist
        onView(withText("DELETE")).check(doesNotExist());
    }


}
