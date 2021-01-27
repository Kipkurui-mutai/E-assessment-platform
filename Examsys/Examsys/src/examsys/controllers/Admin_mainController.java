package examsys.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 */
public class Admin_mainController implements Initializable {

    @FXML
    private Menu students_menu;
    @FXML
    private Menu exmam_menu;
    @FXML
    private MenuItem viewstudents;
    @FXML
    private MenuItem viewexams;
    @FXML
    private VBox main_vbox;
    

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        main_vbox.getChildren().clear();
        try {
            main_vbox.getChildren().add(FXMLLoader.load(getClass().getResource("gui/admin_student_pane.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(Admin_mainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void viewStudents(ActionEvent event) throws IOException {
        main_vbox.getChildren().clear();
        main_vbox.getChildren().add(FXMLLoader.load(getClass().getResource("gui/admin_student_pane.fxml")));
    }

    @FXML
    private void viewExams(ActionEvent event) throws IOException {        
        main_vbox.getChildren().clear();
        main_vbox.getChildren().add(FXMLLoader.load(getClass().getResource("gui/admin_exam_pane.fxml")));
    }
    
}
