package comp3350.habittracker.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.Logic.HabitListManager;
import comp3350.habittracker.R;

public class ViewHabitsActivity extends AppCompatActivity {

    private HabitListManager habitList;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habits);
        setTitle("All Habits");
        Intent intent = getIntent();
        habitList = (HabitListManager)intent.getSerializableExtra("habitList");

        configList();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //reload everything
        reloadList();
    }

    private void configList(){
        ListView list = findViewById(R.id.habitListView);
        reloadList(); //load list for the first time

        //set a listener on the loaded list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                Habit clickedHabit = habitList.getHabit(selected);

                Intent nextActivity = new Intent(ViewHabitsActivity.this, ViewHabitStatsActivity.class);
                nextActivity.putExtra("habit", clickedHabit);
                startActivity(nextActivity);
            }
        });
    }

    public void reloadList(){
        ListView list = findViewById(R.id.habitListView);

        ArrayList<String> habitNames = habitList.getHabitNames(habitList.getHabits());
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, habitNames);
        list.setAdapter(adapter);
    }
}
