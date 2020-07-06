package concurrency.countDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SubWorker extends Thread{

    private CountDownLatch latch;
    private AtomicInteger count;
    private String workerName;

    public SubWorker(CountDownLatch latch, String workerName) {
        this.workerName = workerName;
        count = new AtomicInteger();
        this.latch = latch;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            int rn = random.nextInt(1000);

            for (int i = 0; i < rn; i++) {
                count.incrementAndGet();
                Thread.sleep(10);
            }
            latch.countDown();
            System.out.println(workerName + " finished, total count=" + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showCount() {
        System.out.println(workerName + " count=" + count);
    }

}
