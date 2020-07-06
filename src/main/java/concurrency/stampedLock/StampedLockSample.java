package concurrency.stampedLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockSample {

    public static void main(String[] arg) throws InterruptedException {
        StampedLock sl = new StampedLock();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        // Write
        Runnable r1 = ()->{
            long stamp = sl.writeLock();
            try {
                System.out.printf("[thread %s] is Holding the write lock%n", Thread.currentThread());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                sl.unlock(stamp);
                System.out.printf("[thread %s] Released the write lock%n", Thread.currentThread());
            }
        };

        // Pessimistic read
        Runnable r2 = ()-> {

            long stamp = sl.readLock();
            try {
                System.out.printf("[thread %s] is Holding the pessimistic read lock%n", Thread.currentThread());
                try {
                    TimeUnit.MICROSECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                sl.unlock(stamp);
                System.out.printf("[thread %s] released the pessimistic read lock%n", Thread.currentThread());
            }
        };

        // Optimistic read
        Runnable r3 = () -> {
            long stamp = sl.tryOptimisticRead();
            try {
                System.out.printf("[thread %s] is Holding the optimistic read lock%n", Thread.currentThread());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.printf("[thread %s] read is valid=%s %n", Thread.currentThread(), sl.validate(stamp));
            } finally {
                sl.unlock(stamp);
                System.out.printf("[thread %s] released the optimistic read lock%n", Thread.currentThread());
            }
        };

        executor.submit(r1);
        executor.submit(r3);
        executor.submit(r1);
        executor.submit(r3);
        Thread.sleep(1000);
        executor.submit(r3);
        executor.shutdown();
    }
}

