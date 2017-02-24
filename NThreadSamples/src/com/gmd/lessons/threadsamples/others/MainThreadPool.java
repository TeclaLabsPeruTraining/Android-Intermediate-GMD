package com.gmd.lessons.threadsamples.others;

import com.gmd.lessons.threadsamples.basic.SomeRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by eduardo on 10/01/17.
 */
public class MainThreadPool {

    public static void main(String[] args) {
        System.out.println("Thread ThreadPool");

        Runnable runnable= new SomeRunnable();
        ExecutorService executorService= Executors.newFixedThreadPool(3);

        for (int i = 0; i <5 ; i++) {
            executorService.execute(runnable);
        }
        executorService.shutdown();
    }
}
