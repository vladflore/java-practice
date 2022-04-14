package tech.vladflore.practice.collections.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class HandoffsWithSynchronousQueue {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<Integer>();

        Runnable producer = () -> {
            int nextInt = ThreadLocalRandom.current().nextInt();
            System.out.println("Producing " + nextInt);
            try {
                synchronousQueue.put(nextInt);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable consumer = () -> {
            try {
                int consumed = synchronousQueue.take();
                System.out.println("Consuming " + consumed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        newFixedThreadPool.execute(producer);
        newFixedThreadPool.execute(consumer);

        newFixedThreadPool.awaitTermination(500, TimeUnit.MILLISECONDS);
        newFixedThreadPool.shutdown();
    }
}
