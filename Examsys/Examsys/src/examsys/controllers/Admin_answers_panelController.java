package examsys.controllers;

import impl.Answers;
import impl.Exam;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Admin_answers_panelController implements Initializable {

    @FXML
    private Label quest1_val;
    @FXML
    private TextArea quest1_ans;
    @FXML
    private Label quest2_val;
    @FXML
    private TextArea quest2_ans;
    @FXML
    private Label quest3_val;
    @FXML
    private TextArea quest3_ans;
    Answers es;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void setAnswers(Answers answers) {               
        quest1_ans.setText(answers.getQuest1());
        quest2_ans.setText(answers.getQuest2());
        quest3_ans.setText(answers.getQuest3());
    }

    void setQuestions(Exam exam) {
        quest1_val.setText(exam.getQuest1());
        quest2_val.setText(exam.getQuest2());
        quest3_val.setText(exam.getQuest3());
    }

}
