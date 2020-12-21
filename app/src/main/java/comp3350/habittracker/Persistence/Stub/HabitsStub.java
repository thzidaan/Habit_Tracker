package comp3350.habittracker.Persistence.Stub;

import java.util.ArrayList;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Persistence.HabitsPersistence;

public class HabitsStub implements HabitsPersistence {

    private ArrayList<Habit> habits;
    public HabitsStub(){
        habits = new ArrayList<>();
        habits.add(new Habit("workout",5,3, new User("userA"),"Morning", 1));
        habits.add(new Habit("piano",7,1, new User("userA"),"Afternoon",2));
        habits.add(new Habit("read",7,0, new User("userA"),"Morning",1));
        habits.add(new Habit("run",2,0, new User("userA"),"Evening",3));
    }

    @Override
    public ArrayList<Habit> getUserHabits(User user) {
        ArrayList<Habit> userHabits = new ArrayList<>();
        for(Habit habit : habits){
            if(habit.getUser().equals(user)){
                userHabits.add(habit);
            }
        }
        return userHabits;
    }

    @Override
    public void deleteHabit(Habit habit) {
        habits.remove(habit);
    }

    @Override
    public boolean addHabit(Habit habit) {
        //only add to list, if the habit doesn't already exist
        //two habits are the same if they have the same user, and name
        boolean returnValue = false;
        if(!habits.contains(habit)){
            habits.add(habit);
            returnValue = true;
        }
        return returnValue;
    }

    @Override
    public boolean edit(Habit habit, Habit newHabit) {
        deleteHabit(habit);
        return addHabit(newHabit);
    }

    //update the same habit instance in the db
    @Override
    public boolean update(Habit habit) {
        deleteHabit(habit);
        return addHabit(habit);
    }

    //test up temp database just for running tests
    public void setTestList(ArrayList<Habit> testList){
        habits = testList;
    }
}
