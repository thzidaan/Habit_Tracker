package comp3350.habittracker.Persistence.HSQLDB;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.Note;
import comp3350.habittracker.Persistence.NotePersistence;

public class NotesHSQLDB implements NotePersistence {

    private String path;

    public NotesHSQLDB(String db){
        path = db;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + path + ";shutdown=true", "SA", "");
    }

    private Note fromResultSet(final ResultSet rs, Habit habit) throws SQLException {
        String text = rs.getString("text");
        int feeling = rs.getInt("feeling");
        String date = rs.getString("date");
        return new Note(text, feeling, date, habit);
    }

    @Override
    public ArrayList<Note> getHabitNotes(Habit habit) {
        ArrayList<Note> notes = new ArrayList<>();
        try (Connection c  = connection()){
            PreparedStatement st = c.prepareStatement("SELECT * FROM notes WHERE NAME=? AND USERNAME=?");
            st.setString(1, habit.getHabitName());
            st.setString(2, habit.getUser().getUsername());
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                notes.add(fromResultSet(rs, habit));
            }

            st.close();
            rs.close();
        }catch (SQLException e){
            Log.w("Getting habit notes", e.toString());
            e.printStackTrace();
        }
        return notes;
    }

    @Override
    public void addNote(Note note) {
        try (Connection c = connection()){
            PreparedStatement st = c.prepareStatement("INSERT INTO notes VALUES(?,?,?,?,?)");
            st.setString(1, note.getHabit().getHabitName());
            st.setString(2, note.getHabit().getUser().getUsername());
            st.setString(3, note.getNoteText());
            st.setInt(4, note.getFeeling());
            st.setString(5, note.getNoteDate());
            st.executeUpdate();
            st.close();
        }catch(SQLException e){
            Log.w("Add note", e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteNote(Note note) {
        try(Connection c = connection()){
            PreparedStatement st = c.prepareStatement("DELETE FROM notes WHERE NAME=? AND USERNAME=? AND text=?");
            st.setString(1,note.getHabit().getHabitName());
            st.setString(2, note.getHabit().getUser().getUsername());
            st.setString(3, note.getNoteText());
            st.executeUpdate();
            st.close();
        } catch (SQLException e){
            Log.w("delete note", e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void editNote(Note note, Note newNote) {

        try(Connection c = connection()){
            PreparedStatement st = c.prepareStatement("UPDATE notes SET TEXT=?, FEELING=? WHERE NAME=? AND USERNAME=? AND TEXT=?");
            st.setString(1,newNote.getNoteText());
            st.setInt(2, newNote.getFeeling());
            st.setString(3, note.getHabit().getHabitName());
            st.setString(4, note.getHabit().getUser().getUsername());
            st.setString(5, note.getNoteText());
            st.executeUpdate();
            st.close();
        } catch (SQLException e){
            Log.w("updating note", e.toString());
            e.printStackTrace();
        }
    }

}
