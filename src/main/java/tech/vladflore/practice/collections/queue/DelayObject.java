package tech.vladflore.practice.collections.queue;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DelayObject implements Delayed {

    private String data;
    private long startTime;

    @Override
    public int compareTo(Delayed o) {
        return (int) (startTime - ((DelayObject) o).startTime);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public String toString() {
        return String.format("{%s - %s}", data, new Date(startTime));
    }

}
