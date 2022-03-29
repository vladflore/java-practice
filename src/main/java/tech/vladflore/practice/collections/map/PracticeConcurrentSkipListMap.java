package tech.vladflore.practice.collections.map;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class PracticeConcurrentSkipListMap {
    public static void main(String[] args) {

        ConcurrentNavigableMap<ZonedDateTime, String> events = new ConcurrentSkipListMap<>(
                Comparator.comparingLong(v -> v.toInstant().toEpochMilli()));

        Event event = new Event(ZonedDateTime.now(), "event1");
        events.put(event.getEventTime(), event.getContent());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        event = new Event(ZonedDateTime.now(), "event2");
        events.put(event.getEventTime(), event.getContent());

        System.out.println(events.tailMap(ZonedDateTime.now().minusSeconds(1)));

    }
}
