package comp3350.habittracker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.habittracker.DomainObjects.HabitTests;
import comp3350.habittracker.DomainObjects.NoteTests;
import comp3350.habittracker.Logic.CalendarDateValidatorTests;
import comp3350.habittracker.Logic.HabitDateValidatorTests;
import comp3350.habittracker.Logic.HabitListManagerTests;
import comp3350.habittracker.Logic.HabitManagerTests;
import comp3350.habittracker.Logic.HabitStatsTests;
import comp3350.habittracker.Logic.IntegrationTests.HabitManagerIT;
import comp3350.habittracker.Logic.IntegrationTests.NotesManagerIT;
import comp3350.habittracker.Logic.IntegrationTests.UserManagerIT;
import comp3350.habittracker.Logic.NotesManagerTests;
import comp3350.habittracker.Logic.UserManager;
import comp3350.habittracker.Logic.UserManagerTests;

//run all the tests, include unit and integration
@RunWith(Suite.class)
@Suite.SuiteClasses({
        HabitManagerIT.class,
        NotesManagerIT.class,
        UserManagerIT.class,
        NotesManagerTests.class,
        CalendarDateValidatorTests.class,
        HabitDateValidatorTests.class,
        HabitListManagerTests.class,
        HabitManagerTests.class,
        HabitTests.class,
        NoteTests.class,
        HabitStatsTests.class,
        UserManagerTests.class
})
public class AllTests {
}
