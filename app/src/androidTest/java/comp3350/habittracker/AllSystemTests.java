package comp3350.habittracker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.habittracker.SystemTests.ManageHabitTests;
import comp3350.habittracker.SystemTests.ManageNotesTest;
import comp3350.habittracker.SystemTests.TestCalendarView;
import comp3350.habittracker.SystemTests.TestHabitStats;
import comp3350.habittracker.SystemTests.UserAccountTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ManageHabitTests.class,
        ManageNotesTest.class,
        TestCalendarView.class,
        TestHabitStats.class,
        UserAccountTests.class
})

public class AllSystemTests {
}
