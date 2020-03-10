import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerOneHandler {

    private Storage storage = Storage.getInstance();
    static Socket socket;

    static {
        try {
            socket = new Socket(InetAddress.getLocalHost().getLocalHost(), 8083);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handler() {
        while (true) {
            try {
                int temp = storage.consume();
                System.out.println("Consumer -> "+temp);
                if (temp%2==0) {
                    synchronized (this) {
                        System.out.println("Creating connection to Server two");
                        Thread.sleep(1000);
                        try (DataOutputStream oos = new DataOutputStream(socket.getOutputStream())) {
                            while (true) {
                                System.out.println("writing to sending to server two");
                                oos.writeInt(temp);
                                oos.flush();
                                Thread.sleep(1000);
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
