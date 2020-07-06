package concurrency.lockAndCondition;

import java.util.Random;

public class Producer extends Thread {

    private final SimpleQueue queue;
    private final Random random;
    private final int numUpperBound;
    private final int numberOfRecord;

    public Producer(SimpleQueue queue, int numUpperBound, int numberOfRecord) {
        this.queue = queue;
        random = new Random();
        this.numUpperBound = numUpperBound;
        this.numberOfRecord = numberOfRecord;
    }

    @Override
    public void run() {

        // Producer will keep generating random number between 0 to numUpperBound and send to simple queue
        int count = 0;
        while (count < numberOfRecord) {
            int item = random.nextInt(numUpperBound);
            System.out.println("[Producer]: " + item);
            try {
                queue.add(item);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }

        System.out.println("Shutdown Producer");
    }
}
