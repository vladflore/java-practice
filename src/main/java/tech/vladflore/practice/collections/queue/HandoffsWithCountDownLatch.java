package tech.vladflore.practice.collections.queue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HandoffsWithCountDownLatch {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
        AtomicInteger sharedState = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Runnable producer = () -> {
            int nextInt = ThreadLocalRandom.current().nextInt();
            sharedState.set(nextInt);
            System.out.println("Producing " + nextInt);
            countDownLatch.countDown();
        };

        Runnable consumer = () -> {
            try {
                countDownLatch.await();
                int consumed = sharedState.get();
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
