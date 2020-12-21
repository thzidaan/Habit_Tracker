package comp3350.habittracker.Presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.hsqldb.Table;

import java.lang.reflect.Array;
import java.util.ArrayList;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.Note;

import comp3350.habittracker.Logic.HabitManager;
import comp3350.habittracker.Logic.HabitStats;
import comp3350.habittracker.Logic.NotesManager;
import comp3350.habittracker.R;
public class ViewHabitStatsActivity extends AppCompatActivity {

    private Habit userHabit;
    private static final int CREATE_NOTE_ACTIVITY_ID = 0;
    private static final int EDIT_HABIT_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_view_notes);

        //Receive intent
        Intent intent = getIntent();

        userHabit = (Habit)intent.getSerializableExtra("habit");
        setTitle(userHabit.getHabitName());

        //config buttons
        configCreateButton();
        configList();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //reload everything
        setTitle(userHabit.getHabitName());
        reloadList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.habit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.editHabit){
            Intent nextActivity = new Intent(ViewHabitStatsActivity.this, AddHabitActivity.class);
            nextActivity.putExtra("user",userHabit.getUser());
            nextActivity.putExtra("habit",userHabit);
            startActivityForResult(nextActivity, EDIT_HABIT_ID);
            return true;
        }

        if(item.getItemId() == R.id.deleteHabit){
            final AlertDialog.Builder assurance = new AlertDialog.Builder(this);
            assurance.setMessage("Are you sure you want to delete this habit?");

            assurance.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //remove habit from list
                    HabitManager.delete(userHabit); //is being deleted from database
                    Toast.makeText(ViewHabitStatsActivity.this, "Habit removed",Toast.LENGTH_LONG).show();
                    finish();
                }
            });
            assurance.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    assurance.setCancelable(true);
                    Toast.makeText(ViewHabitStatsActivity.this, "Habit was unchanged",Toast.LENGTH_LONG).show();
                }
            });

            assurance.create().show();

            return true;
        }

        if(item.getItemId() == R.id.share){
            HabitStats stats = new HabitStats(userHabit);


            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Stats for: " + userHabit.getHabitName());
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, stats.getShareString());
            startActivity(Intent.createChooser(sharingIntent,"Share habit stats to..."));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //when another activity finishes and sends result and data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //if creating new Note, or updating a Note
        reloadList();
        if(requestCode == EDIT_HABIT_ID && resultCode == Activity.RESULT_OK){
            userHabit = (Habit) data.getSerializableExtra("habit");
            reloadList(); //redisplay the list
        }
    }
    private void configCreateButton(){
        FloatingActionButton btnCreate = findViewById(R.id.btnAddNote);
        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //start createNote activity
                Intent nextActivity = new Intent(ViewHabitStatsActivity.this,CreateNewNoteActivity.class);
                nextActivity.putExtra("habit",userHabit);
                startActivityForResult(nextActivity, CREATE_NOTE_ACTIVITY_ID);
            }
        });

    }

    private void configList(){
        //displays the notes for the current habit in the ListView
        ListView notesList = (ListView)findViewById(R.id.lvNotes);
        reloadList();
        //click listener
        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String)parent.getItemAtPosition(position);
                showAlertBox(selected);
            }
        });
    }
    private void reloadList(){//reload all notes for that habit
        loadStats(); //reload stats each time
        ListView notesList = (ListView)findViewById(R.id.lvNotes);
        ArrayList<Note> notes =  NotesManager.getNotes(userHabit);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, NotesManager.getNoteText(notes));
        notesList.setAdapter(adapter);
    }

    //create table to show stats
    private void loadStats(){
        //unable to get individual row by id, if the rows are premade in the activity.xml,
        //so the table rows are create during load

        HabitStats stats = new HabitStats(userHabit);
        final TableLayout tableLayout = findViewById(R.id.statsTable);
        tableLayout.removeAllViews();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        titles.add("Desired Weekly Completion Amount:");
        values.add(String.valueOf(userHabit.getWeeklyAmount()));

        titles.add("Current Weekly Completed Amount:");
        values.add(stats.getCompletedThisWeek());

        titles.add("Last Completed Date:");
        values.add(stats.getLastCompleteDate());

        titles.add("Average Mood of Notes:");
        values.add(stats.getAvgNoteFeeling());

        titles.add("Total Times Completed:");
        values.add(String.valueOf(stats.getTimesCompleted()));

        titles.add("Favourite Day to Complete:");
        values.add(stats.getFavDay());

        //create rows to display stats
        for(int i = 0; i < titles.size(); i++){
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            //create textView
            final TextView text = new TextView(this);
            text.setText(titles.get(i));
            text.setTextSize(25);
            text.setPadding(4,2,0,10);
            text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            //create valView
            final TextView value = new TextView(this);
            value.setText(values.get(i));
            value.setTextSize(25);
            value.setPadding(0,2,4,10);
            value.setGravity(Gravity.RIGHT);
            value.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            tableRow.addView(text);
            tableRow.addView(value);

            tableLayout.addView(tableRow);
        }
    }

    /*
        Builds the alert box for editing/removing Notes
     */
    public void showAlertBox(final String selected){
        //Build alerts
        final AlertDialog.Builder assurance = new AlertDialog.Builder(this);
        assurance.setMessage("Are you sure you want to delete this note?");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("To edit or remove a note, click one of the buttons below");
        //set pos neg buttons
        builder.setPositiveButton("Edit note", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //switch to edit note activity
                Intent nextActivity = new Intent(ViewHabitStatsActivity.this,CreateNewNoteActivity.class);
                Note currNote = NotesManager.getNoteByContents(userHabit, selected);
                nextActivity.putExtra("note",currNote);
                startActivityForResult(nextActivity,CREATE_NOTE_ACTIVITY_ID);
            }
        });
        builder.setNegativeButton("Remove note", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                assurance.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Note currNote = NotesManager.getNoteByContents(userHabit, selected);
                        //remove Note
                        NotesManager.deleteNote(currNote);
                        reloadList();
                        Toast.makeText(ViewHabitStatsActivity.this,"Note deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                assurance.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        assurance.setCancelable(true);
                        Toast.makeText(ViewHabitStatsActivity.this,"Note was unchanged",Toast.LENGTH_SHORT).show();
                    }
                });
                assurance.create().show();
            }
        });
        builder.create().show();
    }
}
