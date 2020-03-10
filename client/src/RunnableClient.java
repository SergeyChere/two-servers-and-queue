import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class RunnableClient implements Runnable {

    static Socket socket;
    int client;

    public RunnableClient(int client) {
        this.client=client;
        try {
            socket = new Socket(InetAddress.getLocalHost().getLocalHost(), 8081);
            System.out.println("Client: "+ client+" connected to socket");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (DataOutputStream oos = new DataOutputStream(socket.getOutputStream())) {
            for (int i=1; i<=5; i++) {
                oos.writeInt(i);
                System.out.println("Client: "+client+", Send: " + i);
                oos.flush();
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
