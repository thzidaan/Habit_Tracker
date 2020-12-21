package comp3350.habittracker.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Logic.HabitManager;
import comp3350.habittracker.R;

public class AddHabitActivity extends AppCompatActivity {

    private User user;
    private AlertDialog.Builder builder;
    private Habit editHabit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        builder = new AlertDialog.Builder(this);

        //get current user instance from homepage
        //also if an edit is being edited, get its name
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        editHabit = (Habit) intent.getSerializableExtra("habit");

        //attach listener
        setSpinnerText();
        setSpinnerTime();
        configAddButton();

        //editing a habit
        if(editHabit != null){
            fillInfo(editHabit);
        }
    }

    //fill the fields, with information about that habit that is being edited
    private void fillInfo(Habit habit){
        final EditText txtHabitName = findViewById(R.id.txtHabitName);
        final Spinner dropdown = findViewById(R.id.spinner);
        final Spinner dropdownTime = findViewById(R.id.spinnerTime);
        //set the fields to the correct values
        txtHabitName.setText(habit.getHabitName());
        dropdown.setSelection(habit.getWeeklyAmount() - 1);
        dropdownTime.setSelection(habit.getSortByDay() - 1);
    }


    //set the text style for the dropdown
    private void setSpinnerText(){
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.HabitWeeklyAmount, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    //sets the text style for the second dropdown
    private void setSpinnerTime(){
        Spinner timeSpinner = findViewById(R.id.spinnerTime);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.HabitTimes,R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        timeSpinner.setAdapter(adapter);
    }

    //set listener to the addHabit button
    private void configAddButton(){
        Button btnAddHabit = findViewById(R.id.btnAddHabit);
        final EditText txtHabitName = findViewById(R.id.txtHabitName);
        final Spinner dropdown = findViewById(R.id.spinner);
        final Spinner dropdownTime = findViewById(R.id.spinnerTime);

        //when clicked
        btnAddHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get data from the input fields and create habit object
                String habitName = txtHabitName.getText().toString();
                String timesPerWeek = dropdown.getSelectedItem().toString();
                String schedule = dropdownTime.getSelectedItem().toString();
                int scheduleAssoc;
                if(schedule.equals("Morning"))
                    scheduleAssoc=1;
                else if(schedule.equals("Afternoon"))
                    scheduleAssoc=2;
                else if(schedule.equals("Evening"))
                    scheduleAssoc=3;
                else
                    scheduleAssoc=4;

                Intent intent = new Intent();
                if(editHabit != null && HabitManager.editHabit(editHabit,habitName,timesPerWeek,user,schedule,scheduleAssoc)){
                    int perWeek = Integer.parseInt(timesPerWeek.substring(0,1));
                    Habit newHabit = new Habit(habitName, perWeek, editHabit.getCompletedWeeklyAmount(), user, schedule, scheduleAssoc);
                    intent.putExtra("habit", newHabit);
                    setResult(RESULT_OK,intent);
                    finish(); //close activity, returns back to the home screen
                }else if(HabitManager.saveNewHabit(habitName,timesPerWeek,user,schedule,scheduleAssoc)){ //if habit was saved, close the page
                    setResult(RESULT_OK,intent);
                    finish(); //close activity, returns back to the home screen
                }else{
                    builder.setMessage("Unable to save habit! Make sure habit is unique.").setTitle("Error!");
                    AlertDialog alert = builder.create();
                    alert.setTitle("Error!");
                    alert.show();
                }

            }
        });
    }
}
