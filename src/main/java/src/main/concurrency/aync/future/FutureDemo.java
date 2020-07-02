package src.main.concurrency.aync.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        int currentWaitTime = 0;
        int maxWaiting = 10000;
        SquareCalculator calculator = new SquareCalculator();

        Future<Integer> future = calculator.calculate(100);

        while(!future.isDone() && currentWaitTime < maxWaiting) {
            System.out.println("Calculating...");
            currentWaitTime+=300;
            Thread.sleep(300);
        }

        if (future.isDone()) {
            Integer rst = future.get();
            System.out.println("result is " + rst + " total take " + currentWaitTime);
        } else {
            future.cancel(true);
            System.out.println("cancel request, wait too long, " + currentWaitTime);
        }

        calculator.shutdown();
    }

}
