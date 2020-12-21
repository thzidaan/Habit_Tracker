package comp3350.habittracker.Persistence.Stub;

import java.util.ArrayList;
import java.util.Date;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.Note;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Logic.Utils;
import comp3350.habittracker.Persistence.NotePersistence;

public class NotesStub implements NotePersistence {

    ArrayList<Note> notes;

    public NotesStub(){
        notes = new ArrayList<>();
        Habit habit = new Habit("workout",5,3, new User("userA"),"Morning", 1);
        notes.add(new Note("Good", 5, Utils.formatDate(new Date()), habit));
        notes.add(new Note("Bad", 1, Utils.formatDate(new Date()), habit));
        habit = new Habit("piano",7,1, new User("userA"),"Afternoon",2);
        notes.add(new Note("GREAT", 5, Utils.formatDate(new Date()), habit));
        notes.add(new Note("AMAZING", 1, Utils.formatDate(new Date()), habit));
    }

    @Override
    public ArrayList<Note> getHabitNotes(Habit habit) {
        ArrayList<Note> habitNotes= new ArrayList<>();
        for(Note note : notes){
            if(note.getHabit().equals(habit)){
                habitNotes.add(note);
            }
        }
        return habitNotes;
    }

    @Override
    public void addNote(Note note) {
        notes.add(note);
    }

    @Override
    public void deleteNote(Note note) {
        for(int i = 0; i < notes.size(); i++){
            if(notes.get(i).equals(note)){
                notes.remove(i);
                break;
            }
        }
    }

    @Override
    public void editNote(Note note, Note newNote) {
        deleteNote(note);
        addNote(newNote);
    }

    //test up temp database just for running tests
    public void setTestList(ArrayList<Note> testList){
        notes = testList;
    }
}
