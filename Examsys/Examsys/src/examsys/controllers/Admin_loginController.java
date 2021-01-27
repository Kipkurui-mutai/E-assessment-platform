package examsys.controllers;

import Functionality.AuthManager;
import examsys.ShowErrors;
import impl.LoggedInAdmin;
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

public class Admin_loginController implements Initializable {

    @FXML
    private TextField email_fld;
    @FXML
    private PasswordField password_fld;
    @FXML
    private Button signInBtn;
    AuthManager authmanager;
    ShowErrors showerr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        email_fld.setText("admin@gmail.com");
        password_fld.setText("pass1234");
        authmanager = new AuthManager();
        showerr = new ShowErrors();
    }

    @FXML
    private void performSignIn(ActionEvent event) {
//        get data from TextFields
        String email = email_fld.getText();
        String password = password_fld.getText();

        if (validateFields(email, password)) {
            try {
//                If login succeeds open admin main page and close login page
                if (authmanager.admnLogin(email, password) == 1) {
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                    Parent nroot = FXMLLoader.load(getClass().getResource("gui/admin_main.fxml"));
                    Stage sprimaryStage = new Stage();
                    sprimaryStage.setTitle("Hello There! " + LoggedInAdmin.getAdmin().getUsername());
                    sprimaryStage.setScene(new Scene(nroot));
                    sprimaryStage.show();

                } else {
                    showerr.displayDialog("Authentication Failed!");
                }
            } catch (Exception ex) {
                showerr.displayDialog(ex.toString());
            }
        } else {
            showerr.displayDialog("some fields are invalid");
        }
    }

    private boolean validateFields(String email, String password) {
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
