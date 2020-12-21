package comp3350.habittracker.Logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    //parse a string into a Date object
    public static Date parseString(String date)throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(date);
    }

    //format a Date object into a String
    public static String formatDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}
