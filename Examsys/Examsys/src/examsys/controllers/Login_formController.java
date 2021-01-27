package examsys.controllers;

import Functionality.AuthManager;
import examsys.ShowErrors;
import impl.Student;
import impl.LoggedInStudent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login_formController implements Initializable {

    @FXML
    private TextField email_fld;
    @FXML
    private PasswordField password_fld;
    @FXML
    private Button signInBtn;
    AuthManager authmanager;
    ShowErrors showerr;
    Student student;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showerr = new ShowErrors();
        authmanager = new AuthManager();
        student = new Student();
        email_fld.setText("nka@gmail.com");
        password_fld.setText("pass1234");
    }

    @FXML
    private void performSignIn(ActionEvent event) {
//        get data from textfields 
        String email = email_fld.getText();
        String password = password_fld.getText();
//On successful validation perform login
        if (validateStudent(email, password)) {
            try {
//                Launch students dashboard on success
                if (authmanager.login(email, password) == 1) {
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                    Parent nroot = FXMLLoader.load(getClass().getResource("gui/main.fxml"));
                    Stage sprimaryStage = new Stage();
                    sprimaryStage.setTitle("Hello There!" + LoggedInStudent.getStudent().getFirstname());
                    sprimaryStage.setScene(new Scene(nroot));
                    sprimaryStage.show();

                } else {
                    showerr.displayDialog("Authentication Failed!");
                }
            } catch (Exception ex) {
                showerr.displayDialog(ex.toString());
            }
        }
    }

    private boolean validateStudent(String email, String password) {
        if (email.length() < 2 || email.isEmpty()) {
            email_fld.setStyle("-fx-border-color: red;");
            return false;
        }
        if (password.length() < 2 || password.isEmpty()) {
            password_fld.setStyle("-fx-border-color: red;");
            return false;
        }
        return true;
    }

}
