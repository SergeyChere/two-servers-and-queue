import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(3);
        for (int i = 0; i<3; i++) {
            System.out.println("Client: "+i);
            Thread.sleep(1000);
            exec.execute(new RunnableClient(i));
        }
        System.out.println("shutdown");
        exec.shutdown();
    }
}
