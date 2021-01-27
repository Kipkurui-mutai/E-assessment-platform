package camera;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingNode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Server implements Runnable {

    private static final int PORT = 9090;
    private static final ArrayList<ClientAccepter> clients = new ArrayList<>();
    private static final ExecutorService pool = Executors.newFixedThreadPool(40);
    static ServerSocket listener;
    Socket client;
    private static ObjectInputStream in = null;
    private SwingNode viewV;

    public Server() {
    }

    public static void main(String[] args) throws IOException {
        listener = new ServerSocket(PORT);
        System.out.println("Server is waiting for connection on " + PORT);
    }

    public Server(SwingNode videoView) {
        this.viewV = videoView;
    }

    @Override
    public void run() {
//        try {
//            listener = new ServerSocket(PORT);
//            System.out.println("Server is waiting for connection on " + PORT);
//        } catch (Exception e) {
//
//        }
        while (true) {
            try {
                client = listener.accept();
                System.out.println("Server connected to client");
                ClientAccepter clientThread = new ClientAccepter(client, viewV);
                clients.add(clientThread);
                pool.execute(clientThread);

            } catch (IOException ex) {

            }
        }
    }

    public void recieveVideo() {

        try {
            in = new ObjectInputStream(client.getInputStream());
            in.readObject();
            System.out.println("recieving video");

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

    public static void handleInput(ImageView videoV) throws IOException {

        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Object output;
                while ((output = in.readObject()) != null) {
                    final Object value = output;
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            videoV.setImage((Image) value);
                        }
                    });
                }
                return null;
            }
        };
        new Thread(task).start();
    }

    public static void mainCaller() throws IOException {
        Server.main(null);
    }

    public void closeServer() {
        try {
            if (!listener.isClosed()) {
                listener.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
