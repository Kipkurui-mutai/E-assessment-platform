package examsys.controllers;

import Functionality.ExamManager;
import examsys.ShowErrors;
import impl.Exam;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 */
public class New_exam_dialogController implements Initializable {

    @FXML
    private TextField exam_name;
    @FXML
    private TextField quest_one;
    @FXML
    private TextField question_two;
    @FXML
    private TextField question_three;
    @FXML
    private Label error_message;
    @FXML
    private Button create_exam;
    ShowErrors showerr;
    Exam exam;
    ExamManager exammanager;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showerr = new ShowErrors();
    }

    @FXML
    private void newExam(ActionEvent event) {
//        get data from TextFields
        String examname = exam_name.getText();
        String question1 = quest_one.getText();
        String question2 = question_two.getText();
        String question3 = question_three.getText();
//        If validation succeeds add the data to Database
        if (validateStudent(examname, question1, question2, question3)) {
            try {
                exam = new Exam(examname, question1, question2, question3, "pending");
                exammanager = new ExamManager();
                if (exammanager.addExam(exam) == 1) {
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                    showerr.displayDialog("Exam Added Successfully");                    
                } else{
                    showerr.displayDialog("Could not add exam");
                }
            } catch (SQLException ex) {
                showerr.displayDialog(ex.getLocalizedMessage());
            }
        } else {
            showerr.displayDialog("Some fields are invalid");
        }
    }

    private boolean validateStudent(String examname, String question_one, String questiontwo, String questionthree) {
        if (examname.length() < 2 || examname.isEmpty()) {
            exam_name.setStyle("-fx-border-color: red;");
            return false;
        }
        if (question_one.length() < 2 || question_one.isEmpty()) {
            quest_one.setStyle("-fx-border-color: red;");
            return false;
        }
        if (questiontwo.length() < 2 || questiontwo.isEmpty()) {
            question_two.setStyle("-fx-border-color: red;");
            return false;
        }
        if (questionthree.length() < 2 || questionthree.isEmpty()) {
            question_three.setStyle("-fx-border-color: red;");
            return false;
        }
        return true;
    }

}
