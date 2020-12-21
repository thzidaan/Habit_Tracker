package comp3350.habittracker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.habittracker.Logic.IntegrationTests.HabitManagerIT;
import comp3350.habittracker.Logic.IntegrationTests.NotesManagerIT;
import comp3350.habittracker.Logic.IntegrationTests.UserManagerIT;

//run only integration tests
@RunWith(Suite.class)
@Suite.SuiteClasses({
        HabitManagerIT.class,
        NotesManagerIT.class,
        UserManagerIT.class
})
public class AllIntegrationTests {
}
