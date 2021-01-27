package examsys.controllers.application;

import Functionality.ExamManager;
import examsys.controllers.application.Database;
import examsys.controllers.application.FaceDetector;
import examsys.controllers.application.FaceRecognizer;
import examsys.controllers.application.LoggedInUser;
import impl.ExamStudent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;

//class to add a new student to database
public class SamController implements Initializable {
//   Variable declaration and Instantiation

    private TextField firstname_tfld;
    private TextField lastname_tfld;

    @FXML
    private ImageView profile;

    ExamStudent exam;
    File file;
    FileInputStream fin;
    String firstname;
    String lastname;
    int email;
    @FXML
    private Button startCamBtn;
    @FXML
    private Button stopRecBtn;
    public String filePath = "./faces";
    public ListView<String> output;

    //Creating Database object
    Database database = new Database();
    ArrayList<String> user = new ArrayList<String>();
    ImageView imageView1;

    public static ObservableList<String> event = FXCollections.observableArrayList();
    public static ObservableList<String> outEvent = FXCollections.observableArrayList();

    public boolean enabled = false;
    public boolean isDBready = false;

    FaceDetector faceDetect = new FaceDetector();	//Creating Face detector object									
    @FXML
    private Button recogniseBtn;
    @FXML
    private Button stopCamBtn;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void createStudent(ActionEvent event) throws FileNotFoundException, SQLException {
//        Get values from input fields
        firstname = firstname_tfld.getText();
        lastname = lastname_tfld.getText();
        String fileName = file.getName();
        try {
            BufferedImage bi = ImageIO.read(file);
            File outputfile = new File("faces/saved.png");
            ImageIO.write(bi, "png", outputfile);
//            String filePath = fileName;
//            File saveFile = new File(filePath);
//            saveFile.createNewFile();
//            file.write(saveFile);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    private void startCamera(ActionEvent event) throws SQLException {
        faceDetect.init();
        faceDetect.setFrame(profile);
        faceDetect.start();
        startCamBtn.setVisible(false);
        if (!database.init()) {
            System.out.println("Error: Database Connection Failed ! ");
        } else {
            isDBready = true;
            System.out.println("Success: Database Connection Succesful ! ");
        }
        if (isDBready) {
            recogniseBtn.setDisable(false);
        }

        String path = filePath;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        System.out.println("number of files is: " + listOfFiles.length);

        for (final File file : listOfFiles) {
            imageView1 = createImageView(file);
        }

        //Image reader from the mentioned folder
        if (stopRecBtn.isDisable()) {
            stopRecBtn.setDisable(false);
        }

    }

    @FXML
    private void stopRec(ActionEvent event) {
        faceDetect.stop();
        startCamBtn.setDisable(false);
        stopRecBtn.setDisable(true);
        stopRecBtn.setDisable(true);
        startCamBtn.setVisible(true);
    }

    @FXML
    private void saveFace(ActionEvent event) {
        int count = 0;
        new Thread(() -> {
            try {
                faceDetect.setFname(firstname);
                faceDetect.setCode(email);
                faceDetect.setLname(lastname);

                database.setCode(email);
                database.setFname(firstname);
                database.setLname(lastname);
                database.insert();

            } catch (Exception ex) {
            }

        }).start();
        System.out.println(count++);

        faceDetect.setSaveFace(true);

    }

    int count = 0;

    @FXML
    protected void faceRecognise() {
        faceDetect.setExam(getExam());
        faceDetect.setIsRecFace(true);

//        System.out.println(faceDetect.getOutput());
        recogniseBtn.setText("Get Face Data");
//        user = LoggedInAdmin.getAdmin();

        user = faceDetect.getOutput();

        if (count > 0) {
            //Retrieved data will be shown in Fetched Data pane
            String t = "********* Face Data: " + user.get(1) + " " + user.get(2) + " *********";

            outEvent.add(t);
            String n1 = "First Name\t\t:\t" + user.get(1);
            outEvent.add(n1);
            output.setItems(outEvent);
            String n2 = "Last Name\t\t:\t" + user.get(2);
            outEvent.add(n2);
            output.setItems(outEvent);
            String fc = "Face Code\t\t:\t" + user.get(0);
            outEvent.add(fc);
            output.setItems(outEvent);

        }

        count++;

        stopRecBtn.setDisable(false);
    }

    @FXML
    protected void stopRecognise() {

        faceDetect.setIsRecFace(false);
        faceDetect.clearOutput();

//        this.user.clear();
//
//        recogniseBtn.setText("Recognise Face");
        stopRecBtn.setDisable(true);

//        putOnLog("Face Recognition Deactivated !");
    }

    private ImageView createImageView(final File imageFile) {

        try {
            final Image img = new Image(new FileInputStream(imageFile), 120, 0, true, true);
            imageView1 = new ImageView(img);

            imageView1.setStyle("-fx-background-color: BLACK");
            imageView1.setFitHeight(120);

            imageView1.setPreserveRatio(true);
            imageView1.setSmooth(true);
            imageView1.setCache(true);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return imageView1;
    }

    public void setStudent(int id, String fname, String lname) {
        firstname = fname;
        lastname = lname;
        email = id;
        recogniseBtn.setVisible(false);
        stopRecBtn.setVisible(false);
    }

    public void setExam(ExamStudent exs) {
        exam = new ExamStudent(exs.getId(), exs.getName(), exs.getQuest1(), exs.getQuest2(), exs.getQuest3(), exs.getStatus(), exs.getExam_status());
    }

    public ExamStudent getExam() {
        return exam;
    }

}
