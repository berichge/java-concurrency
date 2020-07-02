package src.main.concurrency.aync.future;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SquareCalculator {

    private ExecutorService es = Executors.newSingleThreadExecutor();

    private Random random = new Random();
    public Future<Integer> calculate(Integer input) {
        return es.submit(()->{
            Thread.sleep(random.nextInt(20000));
            return input * input;
        });
    }

    public void shutdown() {
        es.shutdown();
    }
}
