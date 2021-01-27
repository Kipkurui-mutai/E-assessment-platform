package Functionality;

import DatabaseIntergration.DatabaseMethods;
import impl.ExamStudent;
import impl.Student;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExamStudentManager {
//    variable declarations

    private final DatabaseMethods dbmethods;
    int id;

    public ExamStudentManager() {
        dbmethods = new DatabaseMethods();
    }

//    method that fetches list of students and returns them as observable list
    public ObservableList getExamsForStudent(int student_id) throws SQLException {
        ObservableList<ExamStudent> exams = FXCollections.observableArrayList();
        ResultSet rs = dbmethods.examsForStudent(student_id);
        while (rs.next()) {
            id = rs.getInt("id");
            String name = rs.getString("name");
            String quest1 = rs.getString("quest1");
            String quest2 = rs.getString("quest2");
            String quest3 = rs.getString("quest3");
            String status = rs.getString("status");
            String exam_status = rs.getString("exam_status");
            exams.add(new ExamStudent(id, name, quest1, quest2, quest3, status, exam_status));
//  New object of type Student            
        }
        return exams;
    }

    public ObservableList getStudentsForExam(int exam_id) throws SQLException {
        ObservableList<Student> students = FXCollections.observableArrayList();
        ResultSet rs = dbmethods.studentsForExam(exam_id);
        while (rs.next()) {
            id = rs.getInt("id");
            int stud_id = rs.getInt("student_id");
            String firstname = rs.getString("firstname");
            String lastname = rs.getString("lastname");
            Blob file = rs.getBlob("photo");

//  New object of type Student
            students.add(new Student(id, firstname, lastname, file));
        }
        return students;
    }

    public int registerForExam(int exam_id, int student_id) throws SQLException {
        return dbmethods.insertExamStudTable(exam_id, student_id);
    }
    
    public int doExam(int id, String status){
        return dbmethods.doExam(id, status);
    }

}
