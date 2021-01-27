package examsys.controllers;

import Functionality.StudentManager;
import impl.Student;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Admin_student_paneController implements Initializable {

    @FXML
    private TableColumn<Student, Integer> id;
    @FXML
    private TableColumn<Student, String> firstname;
    @FXML
    private TableColumn<Student, String> lastname;
    @FXML
    private TableColumn<Student, String> email;
    @FXML
    private TableView<Student> student_table;
    @FXML
    private Button ne_student_btn;
    @FXML
    private Button edit_student;

    StudentManager studentmanager;
    private InputStream input;
    @FXML
    private ImageView profile;

    
    @FXML
    private void openNewStudentDialog(ActionEvent event) throws IOException {
//        display dialog to add new student
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add New Student");
        VBox vbox = (VBox) FXMLLoader.load(getClass().getResource("gui/new_student_dialog.fxml"));
        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateTableWithStudents();
        student_table.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) ->{
            listenForSelections();
        } );        
    }

    @FXML
    private void openEditStudentDialog(ActionEvent event) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Student");
        VBox vbox = (VBox) FXMLLoader.load(getClass().getResource("gui/new_student_dialog.fxml"));
        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.showAndWait();
    }

    private void populateTableWithStudents() {
        try {
//            Fetch students from database and show them on student table
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            studentmanager = new StudentManager();
            student_table.setItems(studentmanager.getAllStudents());
        } catch (SQLException ex) {
            Logger.getLogger(Admin_student_paneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listenForSelections() {
        
        ObservableList<Student> selected;
        selected = student_table.getSelectionModel().getSelectedItems();
        studentmanager = new StudentManager();
//        for each selected row and to database exam id and student id
        selected.forEach((exam) -> {
            try {
                input = exam.getProfile().getBinaryStream();
                if (input != null && input.available() > 1) {                    
                    Image imge = new Image(input);
                    profile.setImage(imge);

                }
            } catch (SQLException ex) {
                Logger.getLogger(Admin_student_paneController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Admin_student_paneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

}
