package concurrency.synchronizedCollection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

// Used in producer-consumer framework
public class BlockingQueueExample {

    public static void main(String[] args) {
        BlockingQueue bq = new LinkedBlockingDeque();
        new Producer(bq).start();
        new Consumer(bq).start();
        new Consumer(bq).start();
        new Consumer(bq).start();
    }
}

class Producer extends Thread {
    private final BlockingQueue queue;
    private int counter;

    Producer(BlockingQueue q) {
        queue = q;
        counter = 0;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                queue.put(produce());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Object produce() {
        System.out.println("Produce item " + counter);
        return counter++;
    }
}

class Consumer extends Thread {
    private final BlockingQueue queue;

    Consumer(BlockingQueue q) {
        queue = q;
    }

    @Override
    public void run() {
        while(true) {
            Object o = null;
            try {
                o = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId() + " Consumes item " + o);
        }
    }

}