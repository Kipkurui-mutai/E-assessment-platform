package Functionality;

//Class to manage authantication Functions
import DatabaseIntergration.DatabaseMethods;
import impl.Admin;
import impl.LoggedInAdmin;
import impl.Student;
import impl.LoggedInStudent;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthManager {
    
    int stud_id;
    String stud_firstname;
    String stud_lastname;
    String stud_email;
    String stud_password;
    Blob profile;
//    Performs login

    public int login(String email, String password) throws SQLException {
        DatabaseIntergration.DatabaseMethods dbmethods = new DatabaseMethods();        
        ResultSet rs = dbmethods.login("student", email, password);        
        while (rs.next()) {
            stud_id = rs.getInt("id");
            stud_firstname = rs.getString("firstname");
            stud_lastname = rs.getString("lastname");
            stud_email = rs.getString("email");
            stud_password = rs.getString("password");
            profile = rs.getBlob("photo");
            LoggedInStudent.setStudent(new Student(stud_id, stud_firstname, stud_lastname, stud_email, stud_password, profile));
            return 1;
        }        
        return 0;
    }
    
    public int admnLogin(String email, String password) throws SQLException{
        DatabaseIntergration.DatabaseMethods dbmethods = new DatabaseMethods();        
        ResultSet rs = dbmethods.login("admin", email, password);        
        while (rs.next()) {
            int admin_id = rs.getInt("id");
            String admin_username = rs.getString("username");
            String admin_email = rs.getString("email");
            String admin_password = rs.getString("password");
            LoggedInAdmin.setAdmin(new Admin(admin_id,admin_username, admin_email, admin_password));            
            return  1;
        }        
        return 0;
    }
    
}
