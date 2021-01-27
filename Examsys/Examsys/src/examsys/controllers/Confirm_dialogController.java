package examsys.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Confirm_dialogController implements Initializable {

    @FXML
    private Label message_lbl;
    @FXML
    private Button yes_btn;
    @FXML
    private Button no_btn;
    static boolean answer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        answer = false;
    }

    @FXML
    private void answerYes(ActionEvent event) {
        answer = true;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void answerNo(ActionEvent event) {
        answer = false;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setMessage(String message) {
        message_lbl.setText(message);
    }

}
