package com.dota;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.Random;

public class AsyncArray {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.nanoTime();

        // Генерація двовимірного масиву асинхронно
        CompletableFuture<int[][]> generateArray = CompletableFuture.supplyAsync(() -> {
            int[][] array = new int[3][3];
            Random random = new Random();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    array[i][j] = random.nextInt(100); // Заповнення випадковими числами від 0 до 99
                }
            }
            return array;
        });

        // Виведення початкового масиву асинхронно
        CompletableFuture<Void> printArrayFuture = generateArray.thenRunAsync(() -> {
            try {
                int[][] array = generateArray.get();
                System.out.println("Initial 3x3 array:");
                for (int[] row : array) {
                    for (int value : row) {
                        System.out.print(value + " ");
                    }
                    System.out.println();
                }
                long elapsedTime = System.nanoTime() - startTime;
                System.out.println("Time taken to generate and display array: " + elapsedTime + " ns");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        // Виведення кожного стовпця масиву асинхронно
        CompletableFuture<Void> printColumnsFuture = generateArray.thenRunAsync(() -> {
            try {
                int[][] array = generateArray.get();
                for (int col = 0; col < 3; col++) {
                    System.out.print("Column " + (col + 1) + ": ");
                    for (int row = 0; row < 3; row++) {
                        System.out.print(array[row][col] + " ");
                    }
                    System.out.println();
                }
                long elapsedTime = System.nanoTime() - startTime;
                System.out.println("Time taken to display columns: " + elapsedTime + " ns");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        // Ожидаємо завершення всіх асинхронних завдань
        CompletableFuture<Void> allOf = CompletableFuture.allOf(printArrayFuture, printColumnsFuture);
        allOf.thenRunAsync(() -> {
            System.out.println("All tasks completed!");
        });

        // Очікуємо завершення всіх асинхронних операцій
        allOf.join();
    }
}
