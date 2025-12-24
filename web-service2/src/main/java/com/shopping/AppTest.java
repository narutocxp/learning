package com.shopping;

import java.io.IOException;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static java.util.ServiceLoader.load;

/**
 * Unit test for simple App.
 */
public class AppTest {
    public static void main1(String[] args) {
        Animal[] animals = loadLoadingStrategies();
        System.out.printf("animals", animals);
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        DefaultFuture<String> defaultFuture = new DefaultFuture<>();

        System.out.println("我在一直等hello");

        defaultFuture.whenComplete((s, throwable) -> {
            System.out.println("收到," + s);
            ;
            System.out.println("测试哈哈哈哈楼楼楼");
        });

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            defaultFuture.complete("hello world");
        });


        System.in.read();
    }


    public static void main4(String[] args) throws IOException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("pre");
                Thread.sleep(10000L);
                System.out.println("post");
                return "hello";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        CompletableFuture<String> stringCompletableFuture = future.thenApply(s -> {
            return s + " world";
        });
        stringCompletableFuture.whenComplete((s, throwable) -> {
            System.out.printf("s: %s, throwable: %s", s, throwable);
        });
        System.in.read();
    }

    public static void main3(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "hello";
        });

        String s = future.get();
        System.out.printf(s);

        System.out.println("==================");

    }

    public static void main2(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> future = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(10000l);
                return "hello";
            }
        });
        new Thread(future).start();
        String s = future.get();
        System.out.printf(s);

    }

    private static class DefaultFuture<T> extends CompletableFuture<T> {

    }

    private static Animal[] loadLoadingStrategies() {
        return load(Animal.class).stream().map(ServiceLoader.Provider::get).toArray(Animal[]::new);
    }
}
