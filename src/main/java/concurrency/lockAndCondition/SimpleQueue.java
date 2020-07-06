package concurrency.lockAndCondition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleQueue {

    private Object[] items;

    // How many unconsumed items in items queue
    private int current = 0;
    private int placeIndex = 0;
    private int removeIndex = 0;

    private final Lock lock;
    private final Condition isEmpty;
    private final Condition isFull;

    public SimpleQueue(int capacity) {
        items = new Object[capacity];
        lock = new ReentrantLock();
        isEmpty = lock.newCondition();
        isFull = lock.newCondition();
    }

    public void add(Object item) throws InterruptedException {
        lock.lock();

        if (current >= items.length) {
            isFull.await();
        }

        items[placeIndex] = item;
        placeIndex = (placeIndex + 1) % items.length;
        current++;

        isEmpty.signal();
        lock.unlock();
    }

    public Object remove() throws InterruptedException {
        lock.lock();
        if (current <= 0) {
            isEmpty.await();
        }

        Object item = items[removeIndex];
        removeIndex = (removeIndex+1) % items.length;
        current--;

        isFull.signal();
        lock.unlock();

        return item;
    }
}
