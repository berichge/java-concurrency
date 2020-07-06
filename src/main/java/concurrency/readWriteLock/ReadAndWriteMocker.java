package concurrency.readWriteLock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadAndWriteMocker {

    private final ReadWriteLock rwLock;
    private final Lock wl;
    private final Lock rl;

    private final Random random;

    public ReadAndWriteMocker() {
        random = new Random();
        rwLock = new ReentrantReadWriteLock();
        wl = rwLock.writeLock();
        rl = rwLock.readLock();
    }

    public void set() throws InterruptedException {
        Thread.sleep(random.nextInt(200));
        wl.lock();

        try {
            System.out.printf("Thread %s [Mock] start writing, currentTime=%s %n", System.currentTimeMillis(), Thread.currentThread().getId());
            Thread.sleep(random.nextInt(2000));
            System.out.printf("Thread %s [Mock] end writing, currentTime=%s %n", System.currentTimeMillis(), Thread.currentThread().getId());
        } finally {
            wl.unlock();
        }

    }

    public void get() throws InterruptedException {

        Thread.sleep(random.nextInt(100));

        rl.lock();
        int val = 0;

        try {
            System.out.printf("Thread %s [Mock] start reading, currentTime=%s %n", System.currentTimeMillis(), Thread.currentThread().getId());
            Thread.sleep(random.nextInt(500));
            System.out.printf("Thread %s [Mock] end reading, currentTime=%s %n", System.currentTimeMillis(), Thread.currentThread().getId());
        } finally {
            rl.unlock();
        }
    }


}
