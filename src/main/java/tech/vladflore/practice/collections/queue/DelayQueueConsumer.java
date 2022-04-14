package tech.vladflore.practice.collections.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;

@Getter
public class DelayQueueConsumer implements Runnable {

    private BlockingQueue<DelayObject> queue;
    private Integer numberOfElementsToTake;

    private AtomicInteger numberOfConsumedElements = new AtomicInteger();

    public DelayQueueConsumer(BlockingQueue<DelayObject> queue, Integer numberOfElementsToTake) {
        this.queue = queue;
        this.numberOfElementsToTake = numberOfElementsToTake;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToTake; i++) {
            try {
                DelayObject object = queue.poll(1, TimeUnit.SECONDS);
                if (object != null) {
                    numberOfConsumedElements.incrementAndGet();
                }
                System.out.println("Consume object: " + object);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

}
