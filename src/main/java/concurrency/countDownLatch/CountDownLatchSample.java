package concurrency.countDownLatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchSample {

    public static void main(String[] args) {

        int subWorkerCount = 10;
        CountDownLatch latch = new CountDownLatch(subWorkerCount);
        List<SubWorker> workers = new ArrayList<>();
        for (int i = 0; i < subWorkerCount; i++) {
            workers.add(new SubWorker(latch, "worker" + Integer.toString(i)));
        }

        AggregateWorker aggregateWorker = new AggregateWorker(latch, workers);
        workers.forEach(x->x.start());
        aggregateWorker.start();
    }
}
