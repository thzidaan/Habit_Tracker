package comp3350.habittracker.DomainObjects;

import java.io.Serializable;

public class User implements Serializable {
    private String username;

    public User(String userName){
        username = userName.trim();
    }

    public String getUsername() {
        return username;
    }

    /*
     * equals
     * return true if two users are equal
     * two users are equal if they have the same username
     *
     * Input: other user
     */
    public boolean equals(Object o){
        boolean returnValue = false;

        if(o instanceof User){
            User otherUser = (User) o;
            if(otherUser.username.equalsIgnoreCase(username)){
                returnValue = true;
            }
        }
        return returnValue;
    }
}
