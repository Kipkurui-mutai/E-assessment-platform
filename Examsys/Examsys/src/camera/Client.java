package camera;

import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class Client extends TimerTask implements Runnable {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9090;
    private static Socket client;
    private static Webcam webcam;
    private static ImageIcon im;
    private static BufferedImage bm;
    private static ObjectOutputStream dout;

    @Override
    public void run() {
//        SOCKET CLIENT THREAD, READ IMAGE FROM WEBCAM AND TRANSFER
//VIA SOCKET TO SERVER
        try {
            client = new Socket(SERVER_IP, SERVER_PORT);
            webcam = Webcam.getDefault();
            webcam.open();

            bm = webcam.getImage();
            dout = new ObjectOutputStream(client.getOutputStream());
            im = new ImageIcon(bm);

            while (true) {
                bm = webcam.getImage();
                im = new ImageIcon(bm);
                dout.writeObject(im);
                dout.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
