import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable {

    private static Socket clientDialog;
    private Storage storage = Storage.getInstance();

    public MonoThreadClientHandler(Socket client) {
        MonoThreadClientHandler.clientDialog = client;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            System.out.println("DataInputStream created");

            System.out.println("Server reading from channel");
            while (!clientDialog.isClosed()) {
                while (in.available()>0) {
                    try {
                        Integer entry = in.readInt();
                        System.out.println("Produce -> "+entry);
                        storage.produce(entry);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
