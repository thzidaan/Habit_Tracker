package comp3350.habittracker.Application;

import comp3350.habittracker.Persistence.HSQLDB.HabitsHSQLDB;
import comp3350.habittracker.Persistence.HSQLDB.NotesHSQLDB;
import comp3350.habittracker.Persistence.HSQLDB.UserHSQLDB;
import comp3350.habittracker.Persistence.HabitsPersistence;
import comp3350.habittracker.Persistence.NotePersistence;
import comp3350.habittracker.Persistence.UserPersistence;

public class Services {
    private static HabitsPersistence habitsPersistence = null;
    private static NotePersistence notePersistence = null;
    private static UserPersistence userPersistence = null;

    public static synchronized HabitsPersistence getHabitsPersistence(){
        if(habitsPersistence == null){
           habitsPersistence = new HabitsHSQLDB(Main.getDBPathName());
        }
        return habitsPersistence;
    }

    public static synchronized NotePersistence getNotePersistence(){
        if(notePersistence == null){
            notePersistence = new NotesHSQLDB(Main.getDBPathName());
        }

        return notePersistence;
    }

    public static synchronized UserPersistence getUserPersistence(){
        if(userPersistence == null){
            userPersistence = new UserHSQLDB(Main.getDBPathName());
        }
        return userPersistence;
    }
}
