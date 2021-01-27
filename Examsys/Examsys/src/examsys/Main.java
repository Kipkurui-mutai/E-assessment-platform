package examsys;

import examsys.controllers.Admin_mainController;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {

        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("controllers/gui/admin_login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("ADMIN AUTHENTICATION");
        primaryStage.setScene(new Scene(root));        
        primaryStage.show();
        

        Parent nroot = FXMLLoader.load(getClass().getResource("controllers/gui/login_form.fxml"));
        Stage sprimaryStage = new Stage();
        sprimaryStage.setTitle("Student Login Form!");
        sprimaryStage.setScene(new Scene(nroot));
        sprimaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }

}
