package src.main.concurrency.countDownLatch;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AggregateWorker extends Thread{

    CountDownLatch cdl;
    List<SubWorker> workers;

    public AggregateWorker(CountDownLatch cdl, List<SubWorker> workers) {
        this.cdl = cdl;
        this.workers = workers;
    }

    @Override
    public void run() {
        try {
            cdl.await();
            workers.forEach(w->{
                w.showCount();
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
