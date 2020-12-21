package comp3350.habittracker.DomainObjects;

import java.io.Serializable;

public class Note implements Serializable {

    private String noteText; //note itself
    private int feeling; //associated feeling with this note
    private String noteDate; //date the note was created
    private Habit habit;

    public Note(String text, int mood, String date, Habit habit){
        noteText = text;
        feeling = mood;
        noteDate = date;
        this.habit = habit;
    }

    public Habit getHabit(){
        return habit;
    }

    public String getNoteText() {
        return noteText;
    }

    public int getFeeling() {
        return feeling;
    }

    public String getNoteDate() {
        return noteDate;
    }

    //two notes are equal if the text and habit is the same
    @Override
    public boolean equals(Object o){
        boolean returnValue = false;
        if(o instanceof Note){
            Note otherNote = (Note) o;
            if(otherNote.getNoteText().equalsIgnoreCase(noteText) && otherNote.getHabit().equals(habit)){
                returnValue = true;
            }
        }

        return returnValue;
    }
}
