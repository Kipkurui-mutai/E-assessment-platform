package examsys;

import examsys.controllers.Error_pageController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//This class takes messages from other classes
//Instantiates alert dialog to Display the message to the user
public class ShowErrors {

    public void displayDialog(String m) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("controllers/gui/error_page.fxml"));
        VBox vbox;
        try {
            vbox = loader.load();
        Error_pageController epc = loader.getController();
        epc.setmessage(m);

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Error Dialog");
        window.setScene(new Scene(vbox));
        window.showAndWait();
         } catch (IOException ex) {
            Logger.getLogger(ShowErrors.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

}
