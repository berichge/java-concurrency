package src.main.concurrency.aync.completableFuture;

import java.util.concurrent.*;

public class CompletableFutureSample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<String> completableFuture = calculateAsync();
        String result = completableFuture.get();
        System.out.println(result);

    }

    public static Future<String> calculateAsync() {
//        CompletableFuture<String> completableFuture = new CompletableFuture<>();
//
//        Executors.newCachedThreadPool().submit(()->{
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            completableFuture.complete("Hello");
//        });

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()-> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });

        // then apply
        CompletableFuture<String> future = completableFuture.thenApply(s -> {
            System.out.println("Computation finished");
            return s + " World";
        });

        // compose
        CompletableFuture<String> composedFuture = future.thenCompose(s->CompletableFuture.supplyAsync(()-> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Compose finished");
            return s + " !";
        }));
        return composedFuture;
    }
}
