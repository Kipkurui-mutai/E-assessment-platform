package examsys.controllers;

import DatabaseIntergration.DatabaseMethods;
import examsys.ShowErrors;
import examsys.controllers.application.SamController;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

//class to add a new student to database
public class New_student_dialogController implements Initializable {
//   Variable declaration and Instantiation

    @FXML
    private TextField firstname_tfld;
    @FXML
    private TextField lastname_tfld;
    @FXML
    private TextField email_tfld;
    @FXML
    private PasswordField password_tfld;
    @FXML
    private Button browse;
    @FXML
    private Button create_student;
    @FXML
    private ImageView profile;

    File file;
    FileInputStream fin;
    String firstname;
    String lastname;
    String email;
    String password;
    String error;
    DatabaseMethods dbmethods;
    ShowErrors showerr;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbmethods = new DatabaseMethods();
        showerr = new ShowErrors();
    }

    @FXML
    private void browsePhoto(ActionEvent event) throws IOException {
//        Instantiate file chooser and set file types
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        fc.getExtensionFilters().addAll(ext1, ext2);

        Stage window = new Stage();
        file = fc.showOpenDialog(window);

//        Buffer image and convert to FXImage then display on profile Image View
        BufferedImage bf;
        if (file != null) {
            bf = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bf, null);
            profile.setImage(image);
        }
    }

    @FXML
    private void createStudent(ActionEvent event) throws FileNotFoundException, SQLException {
//        Get values from input fields
        firstname = firstname_tfld.getText();
        lastname = lastname_tfld.getText();
        email = email_tfld.getText();
        password = password_tfld.getText();
//Check validation results
        if (validateStudent(firstname, lastname, email, password)) {
            if (file == null) {
                error = "Please select your profile photo";
                System.out.println(error);
            } else {
//            Call insert method and pass values to Database  
                ResultSet rs = dbmethods.insertIntoStudentTable(firstname, lastname, email, password, file);
                int stud_id =0;
                while (rs.next()) {
                    stud_id = rs.getInt(1);
                }
                try {
                    Stage primaryStage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("application/sam.fxml"));
                    Parent root = loader.load();
                    SamController controller = loader.getController();
                    controller.setStudent(stud_id, firstname, lastname);
            
                    primaryStage.setTitle("ADMIN AUTHENTICATION");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(Student_exam_registrationController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        } else {
            showerr.displayDialog("Some fields are invalid");
        }
    }

    private boolean validateStudent(String firstname, String lastname, String email, String password) {
        if (firstname.length() < 2 || firstname.isEmpty()) {
            firstname_tfld.setStyle("-fx-border-color: red;");
            return false;
        }
        if (lastname.length() < 2 || lastname.isEmpty()) {
            lastname_tfld.setStyle("-fx-border-color: red;");
            error = "Firstname should be atleast 2 characters";
            return false;
        }
        if (email.length() < 2 || email.isEmpty()) {
            email_tfld.setStyle("-fx-border-color: red;");
            return false;
        }
        if (password.length() < 2 || password.isEmpty()) {
            password_tfld.setStyle("-fx-border-color: red;");
            return false;
        }
        return true;
    }

}
