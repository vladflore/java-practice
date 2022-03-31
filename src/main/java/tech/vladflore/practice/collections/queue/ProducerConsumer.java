package tech.vladflore.practice.collections.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerConsumer {
    public static void main(String[] args) {

        int bound = 10;
        int producers = 4;
        int consumers = Runtime.getRuntime().availableProcessors();
        int poisonPill = Integer.MAX_VALUE;
        int poisonPillPerProducer = consumers / producers;
        int mod = consumers % producers;

        BlockingQueue<Integer> q = new LinkedBlockingQueue<>(bound);
        for (int i = 0; i < producers; i++) {
            new Thread(new NumberProducer(q, poisonPill, poisonPillPerProducer)).start();
        }
        for (int i = 0; i < consumers; i++) {
            new Thread(new NumberConsumer(q, poisonPill)).start();
        }
        new Thread(new NumberProducer(q, poisonPill, poisonPillPerProducer + mod)).start();
    }
}

class NumberProducer implements Runnable {

    private BlockingQueue<Integer> numbersQueue;
    private final int poisonPill;
    private final int poisonPillPerProducer;

    public NumberProducer(BlockingQueue<Integer> numbersQueue, int poisonPill, int poisonPillPerProducer) {
        this.numbersQueue = numbersQueue;
        this.poisonPill = poisonPill;
        this.poisonPillPerProducer = poisonPillPerProducer;
    }

    @Override
    public void run() {
        try {
            generateNumbers();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void generateNumbers() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            numbersQueue.put(ThreadLocalRandom.current().nextInt(100));
        }
        for (int i = 0; i < poisonPillPerProducer; i++) {
            numbersQueue.put(poisonPill);
        }
    }
}

class NumberConsumer implements Runnable {

    private BlockingQueue<Integer> numbersQueue;
    private final int poisonPill;

    public NumberConsumer(BlockingQueue<Integer> numbersQueue, int poisonPill) {
        this.numbersQueue = numbersQueue;
        this.poisonPill = poisonPill;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Integer number = numbersQueue.take();
                if (number.equals(poisonPill)) {
                    return;
                }
                System.out.println(Thread.currentThread().getName() + " result: " + number);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
