package examsys.controllers;

import Functionality.AnswersManager;
import Functionality.ExamManager;
import Functionality.ExamStudentManager;
import examsys.ShowErrors;
import Functionality.StudentManager;
import camera.Server;
import impl.Answers;
import impl.Exam;
import impl.Student;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Admin_exam_paneController implements Initializable {

    @FXML
    private TableView<Exam> exam_table;
    private TableColumn<Exam, Integer> id_fld;
    @FXML
    private TableColumn<Exam, String> name_fld;
    @FXML
    private TableColumn<Exam, String> status_fld;
    @FXML
    private Button startExam;
    @FXML
    private Button student_visual;
    @FXML
    private TableView<Student> student_list;
    @FXML
    private TableColumn<Student, String> firstname;
    @FXML
    private TableColumn<Student, String> lastname;
    @FXML
    private TableColumn<Student, String> email;
    @FXML
    private ImageView profile;
    StudentManager studentmanager;
    ExamManager exammanager;
    private InputStream input;
    private Server cameramethods;
    Stage stage;
    ShowErrors showerr = new ShowErrors();
    @FXML
    private Button new_exam;
    ObservableList<Exam> selected_exam;

    ObservableList<Student> selected_student;
    ExamStudentManager examstudentmanager;
    @FXML
    private VBox media_view;
    @FXML
    private SwingNode swingNode;
    static boolean serverStarted;
    @FXML
    private Button students_answers;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        showAllExams();
//        Listen for selection changes in exam table
        exam_table.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            listenForSelectionsInExamTable();
        });
//        Listen for Selection on Students table
        student_list.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            listenForSelectionsInStudentTable();
        });
        selected_student = student_list.getSelectionModel().getSelectedItems();
        selected_exam = exam_table.getSelectionModel().getSelectedItems();
        exammanager = new ExamManager();
        examstudentmanager = new ExamStudentManager();
        if (!serverStarted) {
            Server ser = new Server();
            try {
                ser.mainCaller();
                serverStarted = true;
            } catch (IOException ex) {
                showerr.displayDialog(ex.getLocalizedMessage());
            }
        }

    }

    @FXML
    private void startExam(ActionEvent event) {
//        Take selected exam and set status to started
        selected_exam.forEach((exam) -> {
            if (exam.getStatus().equalsIgnoreCase("pending")) {
                exammanager.changeExamState(exam.getId(), "started");
                showerr.displayDialog("You Have Started The Exam");
                showAllExams();
            } else {
                showerr.displayDialog("Sorry! Exam has already been " + exam.getStatus());
            }
        });
    }

    @FXML
    private void captureStudentVideo(ActionEvent event) {
//        Selecte a student for a started exam and View video feed
        if (selected_exam.size() == 0) {
            showerr.displayDialog("Please select Started exam and a student");
        } else if (selected_student.size() == 0) {
            showerr.displayDialog("Please select a student");
        } else {
            selected_exam.forEach((exam) -> {
                if (exam.getStatus().equalsIgnoreCase("started")) {
                    callServerCamera();
                } else {
                    showerr.displayDialog("You can only check for started exams");
                }
            });
        }

    }

    private void showAllExams() {
//        Method displays all exams from database and adds them to exam table
        try {
            name_fld.setCellValueFactory(new PropertyValueFactory<>("name"));
            status_fld.setCellValueFactory(new PropertyValueFactory<>("status"));
            exammanager = new ExamManager();
            exam_table.setItems(exammanager.getAllExams());
        } catch (SQLException ex) {
            showerr.displayDialog(ex.getLocalizedMessage());
        }
    }

    private void populateListWithStudents(int exam_id) {
//        Fetch students for a selected exam and populate the student list table
        try {
            firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            student_list.setItems(examstudentmanager.getStudentsForExam(exam_id));
        } catch (SQLException ex) {
            showerr.displayDialog(ex.getLocalizedMessage());
        }
    }

    private void listenForSelectionsInExamTable() {
//        for each selected row and to database exam id and student id
        selected_exam.forEach((exam) -> {
            populateListWithStudents(exam.getId());
        });
    }

    private void listenForSelectionsInStudentTable() {
//        for each selected row and to database exam id and student id
        selected_student.forEach((student) -> {
            try {
                input = student.getProfile().getBinaryStream();
                if (input != null && input.available() > 1) {
                    Image imge = new Image(input);
                    profile.setImage(imge);

                }
            } catch (Exception ex) {
                showerr.displayDialog(ex.getLocalizedMessage());
            }
        });

    }

    @FXML
    private void openNewExamDialog(ActionEvent event) {
//        Open dialog to Create an Exam
        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Add New Exam");
            VBox vbox = (VBox) FXMLLoader.load(getClass().getResource("gui/new_exam_dialog.fxml"));
            Scene scene = new Scene(vbox);
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException ex) {
            showerr.displayDialog(ex.getLocalizedMessage());
        }
    }

    private void callServerCamera() {
        try {
            (new Thread(new Server(swingNode))).start();
        } catch (Exception ex) {
            showerr.displayDialog(ex.getLocalizedMessage());
        }
    }

    @FXML
    private void viewAnswers(ActionEvent event) {
        try {
            selected_student = student_list.getSelectionModel().getSelectedItems();
            selected_exam = exam_table.getSelectionModel().getSelectedItems();
            if (selected_exam.size() == 0) {
                showerr.displayDialog("Please select an exam and a student");
            } else if (selected_student.size() == 0) {
                showerr.displayDialog("Please select a student");
            } else {
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/admin_answers_panel.fxml"));
                Parent root = loader.load();
                Admin_answers_panelController controller = loader.getController();

                selected_student.forEach((stud) -> {
                    try {
                        AnswersManager asm = new AnswersManager();
                        ObservableList<Answers> answers = asm.getAnswersForExamForStudent(stud.getId());
                        answers.forEach((answer) -> {
                            System.out.println(answer.getId());
                            controller.setAnswers(answer);
                        });
                    } catch (SQLException ex) {
                        showerr.displayDialog(ex.getLocalizedMessage());
                    }
                });
                selected_exam.forEach((exam) -> {
                    controller.setQuestions(exam);
                });

                //            Show page to display questions and enable answering
                primaryStage.setTitle("Answers");
                primaryStage.setScene(new Scene(root));
                primaryStage.showAndWait();
            }
        } catch (Exception ex) {
            showerr.displayDialog(ex.getLocalizedMessage());
        }
    }

}
