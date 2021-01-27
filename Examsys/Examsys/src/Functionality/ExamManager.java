package Functionality;

import DatabaseIntergration.DatabaseMethods;
import impl.Exam;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExamManager {

    private final DatabaseMethods dbmethods;
    int id;
    String name;
    String quest1;
    String quest2;
    String quest3;
    String status;

    public ExamManager() {
//        New instance of Databasemethods class which implements database queries
        dbmethods = new DatabaseMethods();
    }

//    method that fetches list of students and returns them as observable list
    public ObservableList<Exam> getAllExams() throws SQLException {
        ObservableList<Exam> exams = FXCollections.observableArrayList();        
        ResultSet rs = dbmethods.dbFetchAllFromTable("exam");
        
        while (rs.next()) {
            id = rs.getInt("id");
            name = rs.getString("name");            
            quest1 = rs.getString("quest1");
            quest2 = rs.getString("quest2");
            quest3 = rs.getString("quest3");
            status =rs.getString("status");
//            New object of type Student
            exams.add(new Exam(id, name, quest1, quest2, quest3, status));
        }
        return exams;
    }

    public int deleteExam(int id) {        
        return dbmethods.deleteFromTable("exam", id);        
    }

    public int addExam(Exam exam) throws SQLException {
         return dbmethods.addExam(exam);        
    }
    
    public int changeExamState(int exam_id, String status){        
        return  dbmethods.updateExamStatus(exam_id, status);        
    }

}
