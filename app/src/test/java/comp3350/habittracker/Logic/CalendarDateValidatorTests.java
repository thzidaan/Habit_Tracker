package comp3350.habittracker.Logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import comp3350.habittracker.Utils.TestUtils;

public class CalendarDateValidatorTests {

    private SimpleDateFormat formatter;
    private Date currentDate;
    private String stringCurrentDate;

    @Before
    public void setUp()throws ParseException{
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = new Date();

        //set the current time to be 0:00 (used for comparing two date objects)
        currentDate = formatter.parse(formatter.format(currentDate));
        stringCurrentDate = formatter.format(currentDate); //format current date into string
    }

    @Test
    public void testGetEndOfWeek()throws ParseException {
        String returnDate = CalendarDateValidator.getEndOfWeek(stringCurrentDate); //get end of week date as a string
        Date endOfWeek = formatter.parse(returnDate); //parse string into an date object

        //set calendar date to returned date
        Calendar cal = Calendar.getInstance();
        cal.setTime(endOfWeek);

        //given any input, the output day of week should always be sunday
        assertEquals("should return a sunday",cal.get(Calendar.DAY_OF_WEEK), Calendar.SUNDAY);
        //output is always after or equal to input
        assertTrue("Should return current date or a date in the future",endOfWeek.after(currentDate) || endOfWeek.equals(currentDate));
    }

    @Test
    public void testIsCurrentWeek()throws ParseException{
        //output must always be true if the input is the current date
        assertTrue("should be true since today in in the current week", CalendarDateValidator.isCurrentWeek(stringCurrentDate));

        //add a week to the current date
        String nextWeek = formatter.format(TestUtils.addDaysToDate(7));
        //output must me false if the input in upcoming weeks
        assertFalse("Should return false, today + 7 days isn't in current week",CalendarDateValidator.isCurrentWeek(nextWeek));

        //set calendar to last week
        String lastWeek = formatter.format(TestUtils.addDaysToDate(-7));
        //output must be false if the input was in the past weeks
        assertFalse("Should return false, today - 7 days isn't in current week",CalendarDateValidator.isCurrentWeek(lastWeek));
    }

    @Test
    public void testIsValidDate()throws ParseException{
        //output must be true, if the input is today
        assertTrue("today should be a valid date", CalendarDateValidator.isValidDate(stringCurrentDate));

        //set calendar to a future date
        String futureDate = formatter.format(TestUtils.addDaysToDate(5));
        //future date is a valid date
        assertTrue("future date should be valid", CalendarDateValidator.isValidDate(futureDate));

        String pastDate = formatter.format(TestUtils.addDaysToDate(-5));
        //past date is invalid
        assertFalse("past date should be invalid", CalendarDateValidator.isValidDate(pastDate));
    }

    @Test
    public void getCurrentDate()throws ParseException{
        assertEquals("should return current date", CalendarDateValidator.getCurrentDate(), currentDate);
    }

}
