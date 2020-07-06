package concurrency.lockAndCondition;


public class MessageQueueMode {

    public static void main(String[] arg) throws InterruptedException {
        SimpleQueue queue = new SimpleQueue(10);
        Thread producer = new Producer(queue, 100, 100);
        Thread consumer1 = new Consumer(queue, "1");
        Thread consumer2 = new Consumer(queue, "2");

        producer.start();
        consumer1.start();
        consumer2.start();

    }

}


