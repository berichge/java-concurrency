package concurrency.synchronizedCollection;

import java.util.concurrent.*;

public class BlockingQueueExampleOfAsyncRequestHandler {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        BlockingQueue<String> bq = new LinkedBlockingDeque();
//
//        ExecutorService es = Executors.newFixedThreadPool(3);
//
//        Future<String> f1 = es.submit(new Task(10000, "jack"));
//        Future<String> f2 = es.submit(new Task(2000, "david"));
//        Future<String> f3 = es.submit(new Task(5000, "less"));
//
//
//        es.execute(()-> {
//            try {
//                bq.put(f1.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        });
//        es.execute(()-> {
//            try {
//                bq.put(f2.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        });
//        es.execute(()-> {
//            try {
//                bq.put(f3.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        });
//
//        for (int i =0 ; i < 3; i++) {
//            String l = bq.take();
//            System.out.println("Thread " + l + " Finished");
//        }
//
//        es.shutdown();
//    }

        ExecutorService es = Executors.newFixedThreadPool(3);
        CompletionService<String> cs = new ExecutorCompletionService<>(es);

        cs.submit(new Task(2000, "Cat"));
        cs.submit(new Task(500, "Dog"));
        cs.submit(new Task(1000, "Snake"));

        for (int i = 0; i < 3; i++) {
            String animal = cs.take().get();
            System.out.println(animal);
        }
    }

}

class Task implements Callable<String> {

    int waitTime;
    String taskName;

    Task(int waitTime, String taskName) {
        this.waitTime = waitTime;
        this.taskName = taskName;
    }
    @Override
    public String call() throws Exception {
        Thread.sleep(waitTime);
        return taskName;
    }
}