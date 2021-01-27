package Functionality;

import DatabaseIntergration.DatabaseMethods;
import impl.Student;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentManager {

//    variable declarations
    private final DatabaseMethods dbmethods;
    int id;
    String firstname;
    String lastname;
    String email;
    String password;

    public StudentManager() {
//        New instance of Databasemethods class which implements database queries
        dbmethods = new DatabaseMethods();
    }

//    method that fetches list of students and returns them as observable list
    public ObservableList<Student> getAllStudents() throws SQLException {
        ObservableList<Student> students = FXCollections.observableArrayList();
        ResultSet rs = dbmethods.dbFetchAllFromTable("student");
        while (rs.next()) {
            id = rs.getInt("id");
            firstname = rs.getString("firstname");
            lastname = rs.getString("lastname");
            email = rs.getString("email");
            password = rs.getString("password");
            Blob photo = rs.getBlob("photo");

//            New object of type Student
            students.add(new Student(id, firstname, lastname, email, password, photo));
        }

        return students;
    }

    public int deleteStudent(int id) {
        int deleted_id;
        deleted_id = dbmethods.deleteFromTable("student", id);
        return deleted_id;
    }

    public int addStudent(String sql) throws SQLException {
        int inserted_rows;
        inserted_rows = dbmethods.insertIntoTable(sql);
        return inserted_rows;
    }
}
