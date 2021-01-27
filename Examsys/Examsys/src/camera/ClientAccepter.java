package camera;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ClientAccepter
        implements Runnable, Serializable {

    private final Socket client;
    private final SwingNode viewv;
    private static ObjectInputStream in = null;

    public ClientAccepter(Socket clientSocket, SwingNode viewv) throws IOException {
        this.client = clientSocket;
        this.viewv = viewv;
        in = new ObjectInputStream(client.getInputStream());
    }

    @Override
    public void run() {
//        FOR EACH CLIENT CREATE SERVER CONNECTION THREAD AND READ IMAGE OBJECT
        try {
            while (true) {
                JLabel label = new JLabel();
                label.setSize(640, 360);
                label.setVisible(true);
                label.setIcon((ImageIcon) in.readObject());
                viewv.setContent(label);
            }
        } catch (Exception ex) {
            Logger.getLogger(ClientAccepter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientAccepter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
