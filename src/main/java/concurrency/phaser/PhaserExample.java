package concurrency.phaser;

import java.time.LocalTime;
import java.util.concurrent.Phaser;

public class PhaserExample {

    private static final Phaser phaser = new Phaser(5);
    private static final Phaser phaser2 = new Phaser();

    public static void main(String[] args) throws InterruptedException {
        startTask(0);
        startTask(500);
        startTask(1000);
        startTask(2000);
        startTask(5000);

        phaser.arriveAndDeregister();
    }

    private static void startTask(long initialDelay) throws InterruptedException {
        Thread.sleep(initialDelay);
        new Thread(PhaserExample::taskRun).start();
    }

    private static void taskRun() {

        /* Register count can be pre-defined.
        phaser.register();
        print("after registering");
         */

        for (int i = 0; i <= 5; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            print("Before arrive " + 1);
            // This thread has arrived to the current point, wait other thread to be there
            phaser.arriveAndAwaitAdvance();
            print("after arrive " + i);
        }
    }

    private static void print(String msg) {
        System.out.printf("%-20s: %10s, t=%s, registered=%s, arrived=%s, unarrived=%s phase=%s%n",
                msg,
                Thread.currentThread().getName(),
                LocalTime.now(),
                phaser.getRegisteredParties(),
                phaser.getArrivedParties(),
                phaser.getUnarrivedParties(),
                phaser.getPhase()
        );
    }

}
