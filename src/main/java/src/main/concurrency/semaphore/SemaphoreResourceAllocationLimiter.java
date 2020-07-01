package src.main.concurrency.semaphore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class SemaphoreResourceAllocationLimiter {

    // Semaphore can be used to limit the allocation of shared resource

    private final ExecutorService executor;
    private final Semaphore semaphore;

    public SemaphoreResourceAllocationLimiter(ExecutorService executor, int limit) {
        this.executor = executor;
        this.semaphore = new Semaphore(limit);
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        SemaphoreResourceAllocationLimiter sral = new SemaphoreResourceAllocationLimiter(es, 2);

        sral.submit(new MyCallable(1));
        sral.submit(new MyCallable(2));
        sral.submit(new MyCallable(3));
        sral.submit(new MyCallable(4));
        sral.submit(new MyCallable(5));
        sral.submit(new MyCallable(6));

        es.shutdown();

    }

    public <T> Future<T> submit(final Callable<T> task) throws InterruptedException {
        semaphore.acquire();
        System.out.println("semaphore.acquire()...");

        return executor.submit(()->{
           try {
               return task.call();
           } finally {
               semaphore.release();
               System.out.println("semaphore.release()...");
           }
        });
    }
}

class MyCallable implements Callable<String> {

    private static final DateFormat SDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    int id;

    public MyCallable(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        System.out.printf(getCurrentDateTime() + " : task%s is running!%n", id);
        Thread.sleep(2000);
        System.out.printf(getCurrentDateTime() + " : task%s is done!%n", id);
        return Integer.toString(id);
    }

    private static String getCurrentDateTime() {
        return SDF.format(new Date());
    }
}
