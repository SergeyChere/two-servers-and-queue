import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerBalancer {

    public static void main(String[] args) {
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executeService = Executors.newFixedThreadPool(numThreads);
        ServerOneHandler serverOneHandler = new ServerOneHandler();

        try (ServerSocket server = new ServerSocket(8081)) {
            System.out.println("Server socket on port 8081 created");

            while (!server.isClosed()) {
                Socket client = server.accept();
                System.out.println("Connection accepted.");
                executeService.submit(() -> serverOneHandler.handler());
                executeService.execute(new MonoThreadClientHandler(client));
            }
            System.out.println("Shutdown");
            executeService.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
