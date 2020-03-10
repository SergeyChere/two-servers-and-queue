import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTwo {

    static Socket client;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8083)) {
            System.out.println("Server socket on port 8083 created");
            while (!server.isClosed()) {
                client = server.accept();
                System.out.println("Client received");

                DataInputStream in = new DataInputStream(client.getInputStream());
                System.out.println("DataInputStream created");
                System.out.println("Server reading from channel");
                while (!client.isClosed()) {
                    while (in.available()>0) {
                        try {
                            Integer entry = in.readInt();
                            System.out.println("Server two -> "+entry);
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
