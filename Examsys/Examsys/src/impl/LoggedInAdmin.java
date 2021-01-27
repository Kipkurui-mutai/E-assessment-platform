package impl;

public class LoggedInAdmin {
    public static Admin admin;

    public LoggedInAdmin() {
    }

    public static Admin getAdmin() {
        return admin;
    }

    public static void setAdmin(Admin admin) {
        LoggedInAdmin.admin = admin;
    }  
       
}
