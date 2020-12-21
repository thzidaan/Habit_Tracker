package comp3350.habittracker.Persistence;

public interface UserPersistence {
    boolean addUser(String username, String password);
    boolean getUser(String username, String password);
    void changePassword(String username, String password);
}
