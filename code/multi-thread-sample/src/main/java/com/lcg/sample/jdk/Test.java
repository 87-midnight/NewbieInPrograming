package com.lcg.sample.jdk;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String...args){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new PrintService("fixedThread"));
        executorService.execute(new PrintService("fixedThread"));
        executorService.execute(new PrintService("fixedThread"));
        executorService.shutdown();

        ExecutorService single = Executors.newSingleThreadExecutor();
        single.execute(new PrintService("SingleThread"));
        single.execute(new PrintService("SingleThread"));
        single.execute(new PrintService("SingleThread"));
        single.shutdown();

        ExecutorService cache = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
        cache.execute(new PrintService("cacheThread"));
        cache.execute(new PrintService("cacheThread"));
        cache.execute(new PrintService("cacheThread"));
        cache.shutdown();
    }
}
