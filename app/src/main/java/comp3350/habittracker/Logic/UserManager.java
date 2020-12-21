package comp3350.habittracker.Logic;

import comp3350.habittracker.Persistence.UserPersistence;

public class UserManager {

    public static final int SUCCESS = 0;
    public static final int DB_FAIL = 1;
    public static final int INPUT_FAIL = 2;
    private static UserPersistence userPersistence;

    public UserManager(UserPersistence db){
        userPersistence = db;
    }

    //return if login was a success
    public static int login(String username, String password){
        int returnVal = SUCCESS;
        if(username.length() > 0 && password.length() > 0){
            if(!userPersistence.getUser(username,password)){
                returnVal = DB_FAIL;
            }
        }else{
            returnVal = INPUT_FAIL;
        }
        return returnVal;
    }

    //register user
    public static int register(String username, String password){
        int returnVal = SUCCESS;
        if(username.length() <= 20 && password.length() <= 20 && username.length() > 0 && password.length() > 0){
            if(!userPersistence.addUser(username, password)){
                returnVal = DB_FAIL;
            }
        }else{
            returnVal = INPUT_FAIL;
        }
        return returnVal;
    }

    //change password
    public static int changePassword(String username, String password){
        int returnVal = SUCCESS;
        if(username.length() <= 20 && password.length() <= 20 && username.length() > 0 && password.length() > 0){
            userPersistence.changePassword(username, password);
        }else{
            returnVal = INPUT_FAIL;
        }
        return returnVal;
    }
}
