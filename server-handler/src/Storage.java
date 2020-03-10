import java.util.LinkedList;
import java.util.Queue;

public class Storage {

    private static volatile Storage instance;
    private Queue<Integer> list;

    private Storage() {
        this.list = new LinkedList<>();
    }

    public void produce(Integer value) throws InterruptedException {
        synchronized (this) {
            if (list.size()==1000) {
                wait();
            }
            list.add(value);
            notify();
        }
    }

    public Integer consume() throws InterruptedException {
        synchronized (this) {
            while (list.size()==0) {
                wait();
            }
            Integer value = list.poll();
            notify();
            return value;
        }
    }

    public static Storage getInstance() {
        if (instance==null) {
            synchronized (Storage.class) {
                if (instance==null) {
                    instance = new Storage();
                }
            }
        }
        return instance;
    }

    public Queue<Integer> getList() {
        return list;
    }
}
