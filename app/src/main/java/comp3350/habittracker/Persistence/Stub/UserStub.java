package comp3350.habittracker.Persistence.Stub;

import java.util.HashMap;
import java.util.Map;

import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Persistence.UserPersistence;

public class UserStub implements UserPersistence {
    private Map<String, String> users;

    public UserStub(){
        users = new HashMap<>();
        users.put("userA", "passA");
        users.put("userB", "passB");
    }

    @Override
    public boolean addUser(String username, String password) {
        boolean returnValue = false;
        if(!users.keySet().contains(username)){
            users.put(username, password);
            returnValue = true;
        }
        return returnValue;
    }

    @Override
    public boolean getUser(String username, String password){
        boolean returnValue = false;
        if(users.keySet().contains(username) && users.get(username).equals(password)){
            returnValue = true;
        }
        return returnValue;
    }

    @Override
    public void changePassword(String username, String password) {
        return;
    }
}
