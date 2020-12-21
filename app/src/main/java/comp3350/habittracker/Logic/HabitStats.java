package comp3350.habittracker.Logic;

import java.util.ArrayList;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.Note;

public class HabitStats {

    private Habit habit;

    public HabitStats(Habit h){
        habit = h;
    }

    /*
     * getCompletedThisWeek
     * return a string formatted as: {Completed amt} (percent of completed this week)
     */
    public String getCompletedThisWeek(){
        int toComplete = habit.getWeeklyAmount();
        int completed = habit.getCompletedWeeklyAmount();
        return completed + "(" + (completed/toComplete)*100 + "%)";
    }

    /*
     * getLastCompleteDate
     * return the date when this habit was last completed, if it hasn't been completed return
     * "Never found"
     */
    public String getLastCompleteDate(){
        String returnString = habit.getLastCompletedDate();
        if(returnString == null){
            returnString = "Never completed";
        }
        return returnString;
    }

    /*
     * getFavDay
     * return the day in which this habit has been completed the most
     */
    public String getFavDay(){
        String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int maxIndex = 0;
        String returnString = "None";
        //get array representing times completed for each day
        int[] array = habit.getDaysOfWeek();
        //find the index of the max number
        for(int i = 1; i < array.length; i++){
            if(array[i] > array[maxIndex]){
                maxIndex = i;
            }
        }

        if(array[maxIndex] > 0){
            //get the day represented by the index
            returnString = days[maxIndex];
        }
        return returnString;
    }

    /*
     * getAvgNoteFeeling
     * return a string representing average mood
     */
    public String getAvgNoteFeeling(){
        ArrayList<Note> notes = NotesManager.getNotes(habit);
        double sum = 0;
        //sum
        for(Note note : notes){
            sum += note.getFeeling();
        }

        int avg = (int) Math.round(sum/notes.size());
        String returnString = "";
        if(avg == 0){
            returnString = "Bad";
        }else if(avg == 1){
            returnString = "Average";
        }else if(avg == 2){
            returnString = "Good";
        }
        if(notes.size() == 0){
            returnString = "No notes added!";
        }
        return returnString;
    }

    public int getTimesCompleted(){
        return habit.getTotalCompletedAmt();
    }

    //habit is completed for current week,
    //if amt completed this week is equal to the desired weekly amt
    public boolean isCompletedThisweek(){
        return habit.getCompletedWeeklyAmount() >= habit.getWeeklyAmount();
    }

    public String getShareString(){
        String completedString = "\n\n Habit completed for this week :)";
        String shareBody = String.format("%s Habit Stats:\n",habit.getHabitName());
        shareBody += String.format("Completed %d out of %d times this week\n", habit.getCompletedWeeklyAmount(), habit.getWeeklyAmount());
        shareBody += String.format("Average feeling: %s\n", getAvgNoteFeeling());
        shareBody += String.format("Total times completed: %d\n", getTimesCompleted());
        shareBody += String.format("Favourite day to complete: %s", getFavDay());

        if(isCompletedThisweek()){
            shareBody += completedString;
        }
        return shareBody;
    }
}
