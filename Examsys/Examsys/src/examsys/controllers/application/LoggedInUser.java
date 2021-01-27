package examsys.controllers.application;

import java.util.ArrayList;

public class LoggedInUser {
    public static ArrayList<String> user;

    public LoggedInUser() {
    }

    public static ArrayList<String> getAdmin() {
        return LoggedInUser.user;
    }

    public static void setAdmin(ArrayList<String> user) {
        LoggedInUser.user = user;
    }  
       
}
