package comp3350.habittracker.SystemTests;

import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.habittracker.Presentation.ChangePasswordActivity;
import comp3350.habittracker.Presentation.HomeActivity;
import comp3350.habittracker.Presentation.LoginActivity;
import comp3350.habittracker.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
//system tests for login, register and change password
public class UserAccountTests {
    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setup(){
        activityTestRule.finishActivity();
        SystemTestUtils.cleanUp();
        activityTestRule.launchActivity(new Intent());
    }

    @After
    public void tearDown(){
        SystemTestUtils.cleanUp();
    }

    @Test
    public void testSuccessLogin(){
        //userA already exists by default, so we can use that test login
        Intents.init();
        //type in username and password
        onView(ViewMatchers.withId(R.id.userNameLogin)).perform(typeText("userA"));
        onView(withId(R.id.passwordLogin)).perform(typeText("pass"));
        //click login
        onView(withId(R.id.loginButton)).perform(click());
        //make sure HomeActivity has been intended
        intended(hasComponent(HomeActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testFailLogin(){
        //try incorrect username and password

        //type in username and passwords
        onView(withId(R.id.userNameLogin)).perform(typeText("1"));
        onView(withId(R.id.passwordLogin)).perform(typeText("1"));
        //click login
        onView(withId(R.id.loginButton)).perform(click());
        //make sure still on login page
        onView(withText("Login")).check(matches(isDisplayed()));
    }

    @Test
    public void testLogout(){
        //follow the same steps as for success login test
        //userA already exists by default, so we can use that test login
        Intents.init();
        //type in username and password
        onView(withId(R.id.userNameLogin)).perform(typeText("userA"));
        onView(withId(R.id.passwordLogin)).perform(typeText("pass"));
        //click login
        onView(withId(R.id.loginButton)).perform(click());
        //make sure HomeActivity has been intended
        intended(hasComponent(HomeActivity.class.getName()));

        //open top right menu
        openActionBarOverflowOrOptionsMenu(getApplicationContext());
        //press logout
        onView(withText("Logout")).perform(click());
        //make sure loginActivity was intended
        intended(hasComponent(LoginActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testChangePassword(){
        //follow the same steps as for success login test
        //userA already exists by default, so we can use that test login
        Intents.init();
        //type in username and password
        onView(withId(R.id.userNameLogin)).perform(typeText("userA"));
        onView(withId(R.id.passwordLogin)).perform(typeText("pass"));
        //click login
        onView(withId(R.id.loginButton)).perform(click());
        //make sure HomeActivity has been intended
        intended(hasComponent(HomeActivity.class.getName()));

        //open top right menu
        openActionBarOverflowOrOptionsMenu(getApplicationContext());
        //press Change password
        onView(withText("Change Password")).perform(click());
        //make sure changepassword activity was intended
        intended(hasComponent(ChangePasswordActivity.class.getName()));
        //enter new pass
        onView(withId(R.id.newPassword)).perform(typeText("pass"));
        //click changepass button
        onView(withId(R.id.changeButton)).perform(click());
        //make sure HomeActivity has been intended
        intended(hasComponent(HomeActivity.class.getName()));
        Intents.release();
    }
}
