package comp3350.habittracker.Logic;

import java.text.ParseException;
import java.util.Date;

import comp3350.habittracker.DomainObjects.Habit;

//validate if habits should be shown of given date
public class HabitDateValidator {

    /*
     * isCompleted
     * return true if habit has been completed for selected date
     *
     * Input: selected date and habit
     */
    public static boolean isCompleted(Habit habit, String date)throws ParseException{
        boolean returnValue = false;
        //since habits can only be marked completed for one week at a time
        //a habit is only completed for selected date, if the date is in current week
        if(CalendarDateValidator.isCurrentWeek(date)){
            //check if habit is completed
            returnValue = habit.isCompleted(date);
        }
        return returnValue;
    }

    /*
     * updateCompleteAmount
     * returns true if the habits weekly completed amount has been reset to 0
     *
     * Input: selected habit
     */
    public static boolean updateCompletedAmount(Habit habit)throws ParseException{
        Date today = CalendarDateValidator.getCurrentDate();
        boolean returnValue = false;
        //find the last day of the week in which this habit was last completed
        //ex) lastCompletedDate = Wednesday of last week
        //endOfWeek = The following sunday

        //if the habit has been completed at least once
        if(habit.getLastCompletedDate() != null){
            //get date for upcoming sunday as a Date object
            String endWeek = CalendarDateValidator.getEndOfWeek(habit.getLastCompletedDate());
            Date endOfWeek = Utils.parseString(endWeek);

            //if a new week has started, reset completed amount
            if (endOfWeek.before(today) && habit.getCompletedWeeklyAmount() != 0){
                habit.clearCompletedAmount();
                returnValue = true;
            }
        }
        return returnValue;
    }
}
