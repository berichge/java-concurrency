package concurrency.cyclicBarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierSample {

    // It's a barrier that other threads need to wait for

    public static void main(String[] args) {
        int barrierCount = 5;

        CyclicBarrier barrier = new CyclicBarrier(barrierCount, new Runnable() {
            @Override
            public void run() {
                System.out.println("Aggregation coming!!!, let thread=" + Thread.currentThread().getId() + " do it!!!");
            }
        });

        for (int i = 0; i < 100; i++) {
            Thread thread = new Worker(barrier);
            thread.start();
        }

    }

}

class Worker extends Thread {

    final CyclicBarrier cyclicBarrier;
    public Worker(CyclicBarrier cb) {
        cyclicBarrier = cb;
    }
    @Override
    public void run() {
        Random random = new Random();

        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread" + Thread.currentThread().getId() + " finished!");

        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } finally {
            if (cyclicBarrier.getNumberWaiting() == 0)
            cyclicBarrier.reset();
        }
    }
}