package examsys.controllers;

import Functionality.AnswersManager;
import Functionality.ExamStudentManager;
import camera.Client;
import examsys.ShowErrors;
import impl.ExamStudent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

//This class displays exams questions and allows answering GUI
public class Answer_examController implements Initializable {

    @FXML
    private Label exam_name;
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
    @FXML
    private Button submit;
    ExamStudent es;
    ShowErrors showerr;
    ExamStudentManager esm;
    boolean submitted;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showerr = new ShowErrors();
        esm = new ExamStudentManager();
        submitted = false;
        Timer timer = new Timer();
        timer.schedule(new Client(), 0, 5000);
    }

    @FXML
    private void submitAnswer(ActionEvent event) {
        saveExamAnswers();
    }

    public void setExam(ExamStudent exs) {
//        Take exam questions and exam name and display them
        es = exs;
        exam_name.setText(exs.getName());
        quest1_val.setText(exs.getQuest1());
        quest2_val.setText(exs.getQuest2());
        quest3_val.setText(exs.getQuest3());
    }

    public void shutdown() {

        try {
//            launch dialog to confirm
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/confirm_dialog.fxml"));
            Parent root = loader.load();
            Confirm_dialogController controller = loader.getController();
            controller.setMessage("If you exit you cannot return");
            primaryStage.setTitle("Confirm Exit");
            primaryStage.setScene(new Scene(root));
            primaryStage.showAndWait();
//  if user confirms close the Answering Window
            if (controller.getAnswer()) {
                closeWindow();
            }

        } catch (IOException ex) {
            showerr.displayDialog(ex.getLocalizedMessage());
        }
    }

    void closeWindow() {
        Stage stage = (Stage) submit.getScene().getWindow();
//        if the answers have not been submitted submit then close the window
        if (!submitted) {
            saveExamAnswers();
        }
        stage.close();
    }

    public void saveExamAnswers() {
        try {
//            Take answers from TextFields and Save to database
            AnswersManager anm = new AnswersManager();
            if (anm.saveAnswers(quest1_ans.getText(), quest2_ans.getText(), quest3_ans.getText(), es.getId()) == 1) {
                if (esm.doExam(es.getId(), "completed") == 1) {
                    showerr.displayDialog("Answers Successfuly saved");
                    submitted = true;
                } else {
                    showerr.displayDialog("Could not save answwers");
                }
            }
        } catch (SQLException ex) {
            showerr.displayDialog(ex.getLocalizedMessage());
        }
    }
}
