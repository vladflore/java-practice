package tech.vladflore.practice.collections.queue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DelayQueueProducer implements Runnable {

    private BlockingQueue<DelayObject> queue;
    private Integer numberOfElementsToProduce;

    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToProduce; i++) {
            int randomValue = ThreadLocalRandom.current().nextInt(2);
            long start = randomValue == 0 ? System.currentTimeMillis()
                    : ZonedDateTime.of(LocalDateTime.now().plusHours(1L), ZoneId.systemDefault()).toInstant()
                            .toEpochMilli();
            DelayObject delayObject = new DelayObject(Integer.toString(i), start);
            System.out.println("Put object: " + delayObject);
            try {
                queue.put(delayObject);
                // Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
