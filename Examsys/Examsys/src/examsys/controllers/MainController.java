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

public class MainController implements Initializable {

    @FXML
    private VBox main_vbox;
    @FXML
    private Menu exam_menu;
    @FXML
    private MenuItem my_exams;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            main_vbox.getChildren().clear();
            main_vbox.getChildren().add(FXMLLoader.load(getClass().getResource("gui/student_exam_registration.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void myExams(ActionEvent event) throws IOException {
        main_vbox.getChildren().clear();
        main_vbox.getChildren().add(FXMLLoader.load(getClass().getResource("gui/student_exam_registration.fxml")));

    }

}
