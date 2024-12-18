package com.dota;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.nanoTime();

        // runAsync()
        CompletableFuture.runAsync(() -> {
            System.out.println("runAsync: Task executed!");
        }).thenRunAsync(() -> {
            System.out.println("runAsync: Task completed!");
        });

        // supplyAsync()
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 2 + 2;
        });

        // thenApplyAsync()
        future.thenApplyAsync(result -> {
            System.out.println("thenApplyAsync: " + result * 2);
            return result * 2;
        });

        // thenAcceptAsync()
        future.thenAcceptAsync(result -> {
            System.out.println("thenAcceptAsync: Final result " + result);
        });

        // thenRunAsync()
        future.thenRunAsync(() -> {
            System.out.println("thenRunAsync: Task completed after all computations.");
        });

        // Очікуємо завершення всіх завдань
        future.get();

        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Total execution time: " + elapsedTime + " ns");
    }
}
