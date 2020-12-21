package comp3350.habittracker.DomainObjects;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

public class HabitTests {

    @Test
    public void testEquals(){
        User user = new User("userA");
        User user1 = new User("userB");
        Habit habit = new Habit("habit1",2,1,user,"Morning",1);
        Habit diffHabit = new Habit("habit2", 2,0,user,"Morning",1);
        Habit sameHabit = new Habit("habit1", 2,0,user,"Morning",1);
        Habit diffUser = new Habit("habit1", 2,0,user1,"Morning",1);

        //habits are the same if they have same name and user
        assertEquals("both habits are equal",habit,sameHabit);
        //different names
        assertNotEquals("habits arent equal", habit,diffHabit);
        //same names but different users
        assertNotEquals("same name differt user, should not be equal", sameHabit,diffUser);
    }
}
