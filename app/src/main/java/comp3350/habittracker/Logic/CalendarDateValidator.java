package comp3350.habittracker.Logic;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class CalendarDateValidator {

    /*
     * getEndofWeek
     * return date of the upcoming sunday/ or current date if its sunday
     * week starts from monday and ends on sunday
     *
     * Input: selected date
     */
    public static String getEndOfWeek(String date)throws ParseException{
        //turn selected date into Date object
        Date selectedDate = Utils.parseString(date);
        //set date
        Calendar cal = Calendar.getInstance();
        cal.setTime(selectedDate);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        //if its selected date isn't a sunday
        if(day != Calendar.SUNDAY){
            //days remaining until upcoming sunday
            int daysRemaining = (7 - day + 1);
            //add days to the date
            cal.add(Calendar.DATE, daysRemaining);
        }
        return Utils.formatDate(cal.getTime()); //format date into a string
    }

    /*
     * isCurrentWeek
     * return true if selected date is in the current week
     *
     * Input: selected date
     */
    public static boolean isCurrentWeek(String date)throws ParseException{
        boolean returnValue = false;
        //if the current date is valid
        if(isValidDate(date)) {
            //get date for the upcoming sunday (end of week) and turn it into a date object
            String endOfWeek = getEndOfWeek(Utils.formatDate(getCurrentDate()));
            Date endWeekDate = Utils.parseString(endOfWeek);
            //parse selected date into a date object
            Date selectedDate = Utils.parseString(date);
            //selected date is in current week if its before or equal to the upcoming sunday
            returnValue = selectedDate.before(endWeekDate) || endWeekDate.equals(selectedDate);
        }
        return returnValue;
    }

    /*
     * isValidDate
     * return true if selected date is valid for displaying the habits
     *
     * Input: selected date
     */
    public static boolean isValidDate(String date)throws ParseException {
        boolean returnValue = false;
        //parse current date and selected date into Date objects
        Date selectedDate = Utils.parseString(date);
        Date currentDate = getCurrentDate();
        //selected date is valid if its equal to the current date or is in the future
        if(selectedDate.equals(currentDate) || selectedDate.after(currentDate)){
            returnValue = true;
        }
        return returnValue;
    }

    /*
     * getCurrentDate
     * return a Date object representing the current date with the time set to 0:00:00
     *
     */
    public static Date getCurrentDate()throws ParseException{
        Calendar cal = Calendar.getInstance();
        //get date
        int day = cal.get(Calendar.DAY_OF_MONTH);
        //get one to month, since its indexed from 0
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        //parse the string into a Date object and then return
        return Utils.parseString(day + "/" + month + "/" + year);
    }
}
