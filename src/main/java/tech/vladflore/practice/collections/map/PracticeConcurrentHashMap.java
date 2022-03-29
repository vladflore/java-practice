package tech.vladflore.practice.collections.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PracticeConcurrentHashMap {
    public static void main(String[] args) throws InterruptedException {
        // a HashMap delivers wrong results, a ConcurrentHashMap delivers the correct
        // results
        ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
        List<Integer> result = parallelSum100(map, 100);
        // result should have 100 values each equal to 100
        System.out.println(result.stream().distinct().count()); // should be 1
        long count = result.stream().filter(num -> num != 100).count();
        System.out.println(count); // should be 0
    }

    private static List<Integer> parallelSum100(Map<String, Integer> map, int executionTimes)
            throws InterruptedException {
        List<Integer> sumList = new ArrayList<>();
        for (int i = 0; i < executionTimes; i++) {
            map.put("test", 0);
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            for (int j = 0; j < 10; j++) {
                executorService.execute(() -> {
                    for (int k = 0; k < 10; k++) {
                        map.computeIfPresent("test", (key, val) -> val + 1);
                    }
                });
            }
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
            sumList.add(map.get("test"));
        }
        return sumList;
    }
}
