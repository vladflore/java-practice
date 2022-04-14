package tech.vladflore.practice.collections.queue;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Practice {
    public static void main(String[] args) throws InterruptedException {
        // PriorityBlockingQueue<Integer> pbq = new PriorityBlockingQueue<>();
        // pbq.add(1);
        // pbq.add(3);
        // pbq.add(4);
        // pbq.add(2);
        // pbq.add(5);
        // pbq.forEach(System.out::println);
        // ArrayList<Integer> elements = new ArrayList<>();
        // pbq.drainTo(elements);
        // System.out.println(elements);

        // PriorityBlockingQueue<Integer> pbq1 = new PriorityBlockingQueue<>();
        // Thread thread = new Thread(
        // () -> {
        // System.out.println("Polling...");
        // while (true) {
        // try {
        // Integer taken = pbq1.take();
        // System.out.println("Polled: " + taken);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // thread.start();
        // try {
        // Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // System.out.println("Adding to queue");
        // pbq1.addAll(List.of(1, 5, 6, 1, 2, 6, 7));
        // try {
        // Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }

        // System.out.println("===========================================");

        // LinkedTransferQueue<Integer> linkedTransferQueue = new
        // LinkedTransferQueue<>();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        DelayQueueProducer producer = new DelayQueueProducer(queue, 10);
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, 10);

        executor.submit(producer);
        executor.submit(consumer);

        executor.awaitTermination(5, TimeUnit.SECONDS);
        List<Runnable> stillAwaiting = executor.shutdownNow();
        stillAwaiting.forEach(System.out::println);
        System.out.println(consumer.getNumberOfConsumedElements());

    }
}
