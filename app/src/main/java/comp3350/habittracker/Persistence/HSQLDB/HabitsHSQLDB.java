package comp3350.habittracker.Persistence.HSQLDB;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import comp3350.habittracker.DomainObjects.Habit;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Persistence.HabitsPersistence;

public class HabitsHSQLDB implements HabitsPersistence {

    private ArrayList<Habit> habits;
    private String path;

    public HabitsHSQLDB(String db){
        habits = new ArrayList<>();
        path = db;
    }

    private Connection connection() throws SQLException{
        return DriverManager.getConnection("jdbc:hsqldb:file:" + path + ";shutdown=true", "SA", "");
    }

    private Habit fromResultSet(final ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        int weekAmt = rs.getInt("weekAmt");
        int completeAmt = rs.getInt("completeAmt");
        String lastCompleteDate = rs.getString("lastCompleteDate");
        String time = rs.getString("time");
        int sortTime = rs.getInt("sortTime");
        String createdDate = rs.getString("createDate");
        int totalCompleteAmt = rs.getInt("totalCompleteAmt");
        int monday = rs.getInt("mon");
        int tuesday = rs.getInt("tues");
        int wednesday = rs.getInt("wed");
        int thrusday = rs.getInt("thru");
        int friday = rs.getInt("fri");
        int saturday = rs.getInt("sat");
        int sunday = rs.getInt("sun");
        int[] daysOfWeek = new int[]{monday,tuesday,wednesday,thrusday,friday,saturday,sunday};
        String user = rs.getString("username");
        User u = new User(user);
        return new Habit(name, weekAmt, completeAmt, u, time, sortTime,lastCompleteDate, createdDate, totalCompleteAmt, daysOfWeek);
    }

    @Override
    public ArrayList<Habit> getUserHabits(User user) {
        if(habits.size() == 0){
            try(Connection c = connection()){
                String query = "SELECT * FROM HABITS WHERE USERNAME = ?";
                PreparedStatement st = c.prepareStatement(query);
                st.setString(1, user.getUsername());
                ResultSet rs = st.executeQuery();

                while(rs.next()){
                    Habit habit = fromResultSet(rs);
                    habits.add(habit);
                }

                st.close();
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
                Log.w("Getting Habits", e.toString());
            }
        }
        return habits;
    }

    @Override
    public void deleteHabit(Habit habit) {
        try(Connection c = connection()){
            PreparedStatement st = c.prepareStatement("DELETE FROM HABITS WHERE NAME=? AND USERNAME=?");
            st.setString(1, habit.getHabitName());
            st.setString(2, habit.getUser().getUsername());
            st.executeUpdate();

            //update cache
            habits.remove(habit);
            st.close();
        }catch (SQLException e){
            e.printStackTrace();
            Log.w("deleting habit", e.toString());
        }
    }

    @Override
    public boolean addHabit(Habit habit) {
        boolean returnValue = false;
        if(!habits.contains(habit)){
            try(Connection c = connection()){
                PreparedStatement st = c.prepareStatement("INSERT INTO HABITS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                st.setString(1, habit.getHabitName());
                st.setInt(2, habit.getWeeklyAmount());
                st.setInt(3,habit.getCompletedWeeklyAmount());
                st.setString(4,habit.getLastCompletedDate());
                st.setString(5,habit.getTimeOfDay());
                st.setInt(6, habit.getSortByDay());
                st.setString(7, habit.getCreatedDate());
                st.setInt(8, habit.getTotalCompletedAmt());
                int i = 9;
                for(int value : habit.getDaysOfWeek()){
                    st.setInt(i++,value);
                }
                st.setString(i, habit.getUser().getUsername());
                st.executeUpdate();

                //update cache
                habits.add(habit);
                st.close();
                returnValue = true;
            }catch (SQLException e){
                Log.w("Add Habit", e.toString());
                e.printStackTrace();
            }
        }
        return returnValue;
    }

    @Override
    public boolean edit(Habit habit, Habit newHabit) {
        boolean returnValue = false;
        try(Connection c = connection()){
            PreparedStatement st = c.prepareStatement("UPDATE habits SET NAME=?, WEEKAMT=?, TIME=?, SORTTIME=?, COMPLETEAMT=?, LASTCOMPLETEDATE=?, TOTALCOMPLETEAMT=?, " +
                    "MON=?, TUES=?, WED=?, THRU=?, FRI=?, SAT=?, SUN=? WHERE name=? AND username=?");
            st.setString(1, newHabit.getHabitName());
            st.setInt(2, newHabit.getWeeklyAmount());
            st.setString(3,newHabit.getTimeOfDay());
            st.setInt(4, newHabit.getSortByDay());
            st.setInt(5, newHabit.getCompletedWeeklyAmount());
            st.setString(6, newHabit.getLastCompletedDate());
            st.setInt(7, newHabit.getTotalCompletedAmt());
            int i = 8;
            for(int value : newHabit.getDaysOfWeek()){
                st.setInt(i++,value);
            }

            st.setString(i++, habit.getHabitName());
            st.setString(i,habit.getUser().getUsername());

            st.executeUpdate();
            st.close();
            returnValue = true;
        }catch (SQLException e){
            Log.w("Edit Habit", e.toString());
            e.printStackTrace();
        }

        return returnValue;
    }

    @Override
    public boolean update(Habit habit) {
        return edit(habit, habit);
    }
}
