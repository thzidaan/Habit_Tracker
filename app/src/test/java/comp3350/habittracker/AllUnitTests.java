package comp3350.habittracker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.habittracker.DomainObjects.HabitTests;
import comp3350.habittracker.DomainObjects.NoteTests;
import comp3350.habittracker.Logic.CalendarDateValidatorTests;
import comp3350.habittracker.Logic.HabitDateValidatorTests;
import comp3350.habittracker.Logic.HabitListManagerTests;
import comp3350.habittracker.Logic.HabitManagerTests;
import comp3350.habittracker.Logic.HabitStats;
import comp3350.habittracker.Logic.HabitStatsTests;
import comp3350.habittracker.Logic.NotesManagerTests;
import comp3350.habittracker.Logic.UserManager;
import comp3350.habittracker.Logic.UserManagerTests;

//run only unit tests
@RunWith(Suite.class)
@Suite.SuiteClasses({
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
public class AllUnitTests {
}
