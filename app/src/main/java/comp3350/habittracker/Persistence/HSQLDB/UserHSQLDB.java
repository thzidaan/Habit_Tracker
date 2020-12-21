package comp3350.habittracker.Persistence.HSQLDB;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import comp3350.habittracker.Persistence.UserPersistence;

public class UserHSQLDB implements UserPersistence {

    private String path;

    public UserHSQLDB(String db){
        path = db;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + path + ";shutdown=true", "SA", "");
    }

    @Override
    public boolean addUser(String username, String password) {
        boolean returnValue = false;
        try(Connection c = connection()){
            PreparedStatement st = c.prepareStatement("INSERT INTO users VALUES(?,?)");
            st.setString(1, username);
            st.setString(2, password);
            int val = st.executeUpdate();
            if(val == 1){
                returnValue = true;
            }

            st.close();
        }catch (SQLException e){
            Log.w("adding user", e.toString());
            e.printStackTrace();
        }
        return returnValue;
    }

    @Override
    public boolean getUser(String username, String password) {
        boolean returnValue = false;
        try(Connection c = connection()){
            PreparedStatement st = c.prepareStatement("SELECT * FROM users WHERE USERNAME=? AND password=?");
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            returnValue = rs.next();
            rs.close();
            st.close();
        }catch (SQLException e){
            Log.w("getting user", e.toString());
            e.printStackTrace();
        }
        return returnValue;
    }

    @Override
    public void changePassword(String username, String password) {
        boolean returnValue = false;
        try(Connection c = connection()){
            PreparedStatement st = c.prepareStatement("UPDATE users SET PASSWORD=? WHERE USERNAME=?");
            st.setString(1, password);
            st.setString(2, username);
            st.executeUpdate();

            st.close();
        }catch (SQLException e){
            Log.w("changing password", e.toString());
            e.printStackTrace();
        }
    }
}
