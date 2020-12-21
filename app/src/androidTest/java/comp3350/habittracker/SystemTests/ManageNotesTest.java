package comp3350.habittracker.SystemTests;

import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.habittracker.Application.Services;
import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.Note;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Persistence.HabitsPersistence;
import comp3350.habittracker.Persistence.NotePersistence;
import comp3350.habittracker.Presentation.LoginActivity;
import comp3350.habittracker.R;

//add adding, deleting and editing notes
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ManageNotesTest {
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
        Habit habit = new Habit("NoteHabit",1,0,new User("userA"),"Morning",0);
        HabitsPersistence habitsPersistence = Services.getHabitsPersistence();
        habitsPersistence.addHabit(habit);
        //create notes for habit
        NotePersistence notePersistence = Services.getNotePersistence();
        notePersistence.addNote(new Note("EDIT", 0, "03/23/2020",habit));
        notePersistence.addNote(new Note("DELETE", 0, "03/23/2020",habit));
        activityTestRule.launchActivity(new Intent());
    }

    @After
    public void tearDown(){
        SystemTestUtils.tearDown();
    }

    @Test
    public void testAddNote(){
        //open top right menu
        openActionBarOverflowOrOptionsMenu(getApplicationContext());
        //press habits
        onView(withText("Habits")).perform(click());
        //click the desired habit
        onView(withText("NoteHabit")).perform(click());
        //click add notes button
        onView(ViewMatchers.withId(R.id.btnAddNote)).perform(click());
        //enter note name
        onView(withId(R.id.etWriteNotes)).perform(typeText("addnote"));
        //select feeling option
        onView(withId(R.id.radio_bad)).perform(click());
        Espresso.closeSoftKeyboard(); //close keyboard
        //click button update
        onView(withId(R.id.btnUpdateNote)).perform(click());
        //make sure new note is present
        onView(withText("addnote")).check(matches(isDisplayed()));
    }

    @Test
    public void testEditNote(){
        //open top right menu
        openActionBarOverflowOrOptionsMenu(getApplicationContext());
        //press habits
        onView(withText("Habits")).perform(click());
        //click the desired habit
        onView(withText("NoteHabit")).perform(click());
        //click note to be edited
        onView(withText("EDIT")).perform(click());
        //click 'EDIT NOTE'
        onView(withText("EDIT NOTE")).perform(click());

        onView(withId(R.id.etWriteNotes)).perform(clearText()).perform(typeText("editNote"));
        //select feeling option
        onView(withId(R.id.radio_bad)).perform(click());
        Espresso.closeSoftKeyboard(); //close keyboard
        //click button update
        onView(withId(R.id.btnUpdateNote)).perform(click());
        //make sure EDIT is not displayed and test note2 is
        onView(withText("editNote")).check(matches(isDisplayed()));
        onView(withText("EDIT")).check(doesNotExist());
    }

    @Test
    public void testDeleteNote(){
        //open top right menu
        openActionBarOverflowOrOptionsMenu(getApplicationContext());
        //press habits
        onView(withText("Habits")).perform(click());
        //click the desired habit
        onView(withText("NoteHabit")).perform(click());
        //click note to be removed
        onView(withText("DELETE")).perform(click());
        //click 'REMOVE NOTE'
        onView(withText("REMOVE NOTE")).perform(click());
        onView(withText("YES")).perform(click());

        //make sure note doesn't exist
        onView(withText("DELETE")).check(doesNotExist());
    }

    @Test
    public void testCreateNoteOnComplete(){
        onView(withText("NoteHabit")).perform(click());
        //add note
        onView(withText("ADD NOTE")).perform(click());
        //enter note name
        onView(withId(R.id.etWriteNotes)).perform(typeText("complete note1"));
        //select feeling option
        onView(withId(R.id.radio_bad)).perform(click());
        Espresso.closeSoftKeyboard(); //close keyboard
        //click button update
        onView(withId(R.id.btnUpdateNote)).perform(click());
        //open top right menu
        openActionBarOverflowOrOptionsMenu(getApplicationContext());
        //press habits
        onView(withText("Habits")).perform(click());
        //click the desired habit
        onView(withText("NoteHabit")).perform(click());
        //make sure new note is present
        onView(withText("complete note1")).check(matches(isDisplayed()));
    }

}
