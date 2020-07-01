package src.main.concurrency.lockAndCondition;

public class Consumer extends Thread{

    private final SimpleQueue queue;
    private final String id;

    public Consumer(SimpleQueue queue, String id) {
        this.queue = queue;
        this.id = id;
    }

    @Override
    public void run() {

        while (true) {
            try {
                Object obj = queue.remove();
                System.out.printf("[Consumer%s]: %s%n", id, obj);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
