package examsys.controllers;

import impl.Exam;
import Functionality.ExamManager;
import Functionality.ExamStudentManager;
import camera.Client;
import examsys.ShowErrors;
import examsys.controllers.application.SamController;
import impl.ExamStudent;
import impl.LoggedInStudent;
import impl.Student;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Student_exam_registrationController implements Initializable {

    @FXML
    private TableView<Exam> exam_register;
    @FXML
    private TableColumn<Exam, String> exam_name;
    @FXML
    private Button register_btn;
    @FXML
    private TableColumn<Exam, Integer> id;

    ExamStudentManager examstudentmanager;
    @FXML
    private TableView<ExamStudent> r_examtable;
    @FXML
    private TableColumn<ExamStudent, Integer> rexam_id;
    @FXML
    private TableColumn<ExamStudent, String> r_examname;
    @FXML
    private TableColumn<ExamStudent, String> r_exam_status;
    @FXML
    private TableColumn<Exam, String> exam_status;
    @FXML
    private Button start_exam;
    ObservableList<Exam> examSelected;
    Student student;
    ExamManager exammanager;
    @FXML
    private TableColumn<Student, String> r_exam_student_status;
    ShowErrors showeerr;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        student = LoggedInStudent.getStudent();
        examSelected = exam_register.getSelectionModel().getSelectedItems();
        examstudentmanager = new ExamStudentManager();
        exammanager = new ExamManager();
        showeerr = new ShowErrors();
        showAllExams();
        showRegisteredExams();
    }

    @FXML
    private void registerExam(ActionEvent event) {
//        for each selected row and to database exam id and student id
        if (examSelected.size() == 0) {
            showeerr.displayDialog("Select an exam to register");
        } else {
            examSelected.forEach((exam) -> {
                try {
                    if (exam.getStatus().equalsIgnoreCase("pending")) {
                        if (examstudentmanager.registerForExam(exam.getId(), student.getId()) == 1) {
                            showRegisteredExams();
                            showeerr.displayDialog("Successfully registered for " + exam.getName());
                        } else {
                            showeerr.displayDialog("Could not register for exam");
                        }
                    } else {
                        showeerr.displayDialog("You can only register for pending exams");
                    }
                } catch (SQLException ex) {
                    showeerr.displayDialog(ex.getLocalizedMessage());
                }
            });
        }

    }

    @FXML
    private void startExam(ActionEvent event) throws IOException {
        ObservableList<ExamStudent> r_exam_selected;
        r_exam_selected = r_examtable.getSelectionModel().getSelectedItems();
        if (r_exam_selected.size() == 0) {
            showeerr.displayDialog("Select an Exam to Start");
        } else {
            r_exam_selected.forEach((exam) -> {
                try {
                    if (exam.getStatus().equalsIgnoreCase("started")) {
                        if (exam.getExam_status().equalsIgnoreCase("pending")) {
                            examstudentmanager.doExam(exam.getId(), "started");
//                            Timer timer = new Timer();
//                            timer.schedule(new Client(), 0, 5000);
//                            launchExam(exam);
                            faceValidation(exam);
                        } else {
                            showeerr.displayDialog("You have " + exam.getExam_status() + " this exam");
                        }
                    } else {
                        showeerr.displayDialog("You can only do a started exam");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showeerr.displayDialog(ex.getLocalizedMessage());
                }
            });
        }

    }

    private void showAllExams() {
        try {
//            Fetch all exams and display on table
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            exam_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            exam_status.setCellValueFactory(new PropertyValueFactory<>("status"));
            exam_register.setItems(exammanager.getAllExams());
        } catch (SQLException ex) {
            showeerr.displayDialog(ex.getLocalizedMessage());
        }
    }

    private void showRegisteredExams() {
        try {
//            Fetch exams the student has registered for and display them
            rexam_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            r_examname.setCellValueFactory(new PropertyValueFactory<>("name"));
            r_exam_status.setCellValueFactory(new PropertyValueFactory<>("status"));
            r_exam_student_status.setCellValueFactory(new PropertyValueFactory<>("exam_status"));
            r_examtable.setItems(examstudentmanager.getExamsForStudent(student.getId()));
        } catch (SQLException ex) {
            showeerr.displayDialog(ex.getLocalizedMessage());
        }
    }

    private void launchExam(ExamStudent exs) {
        try {
//            Show page to display questions and enable answering
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/answer_exam.fxml"));
            Parent root = loader.load();
            Answer_examController controller = loader.getController();
            controller.setExam(exs);
            primaryStage.setTitle("Exam Portal");
            primaryStage.setScene(new Scene(root));
//           Intercept a close request
            primaryStage.setOnCloseRequest(e -> {
                e.consume();
                controller.shutdown();
            });
            primaryStage.show();
        } catch (IOException ex) {
            showeerr.displayDialog(ex.getLocalizedMessage());
        }

    }

    private void faceValidation(ExamStudent exs) {
        try {
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("application/sam.fxml"));
            Parent root = loader.load();
            SamController controller = loader.getController();
            controller.setExam(exs);
            primaryStage.setTitle("ADMIN AUTHENTICATION");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(Student_exam_registrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
