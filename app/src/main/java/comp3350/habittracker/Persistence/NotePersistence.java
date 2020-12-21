package comp3350.habittracker.Persistence;

import java.util.ArrayList;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.Note;

public interface NotePersistence {
    ArrayList<Note> getHabitNotes(Habit habit);
    void addNote(Note note);
    void deleteNote(Note note);
    void editNote(Note note, Note newNote);
}
